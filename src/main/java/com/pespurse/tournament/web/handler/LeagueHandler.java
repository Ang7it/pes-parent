package com.pespurse.tournament.web.handler;

import com.pespurse.global.exception.CustomException;
import com.pespurse.global.exception.errorCode.MatchErrorCode;
import com.pespurse.global.exception.errorCode.SystemErrorCode;
import com.pespurse.global.exception.errorCode.UserErrorCode;
import com.pespurse.global.response.Response;
import com.pespurse.global.util.IdGenerator;
import com.pespurse.match.web.dto.SaveMatchDTO;
import com.pespurse.tournament.repo.LeagueEntityRepository;
import com.pespurse.tournament.repo.SubLeaguesEntityRepository;
import com.pespurse.tournament.repo.entity.LeagueEntity;
import com.pespurse.tournament.repo.entity.SubLeaguesEntity;
import com.pespurse.tournament.repo.entity.TournamentParticipantEntity;
import com.pespurse.tournament.repo.entity.enums.TournamentInvitationStatus;
import com.pespurse.tournament.repo.entity.enums.TournamentStatus;
import com.pespurse.tournament.repo.entity.enums.TournamentType;
import com.pespurse.tournament.web.dto.SaveLeagueDTO;
import com.pespurse.tournament.web.dto.TournamentInvitationDTO;
import com.pespurse.users.repo.UserEntityRepository;
import com.pespurse.users.repo.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class LeagueHandler {

    private final LeagueEntityRepository leagueEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final SubLeaguesEntityRepository subLeaguesEntityRepository;
    private final TournamentInvitationHandler tournamentInvitationHandler;
    private final TournamentParticipantHandler tournamentParticipantHandler;
    private final FixtureGeneratorHandler fixtureGeneratorHandler;

    /**
     * First we check if the user is present by userID and the tournament already exists by the matchID.
     *
     * @param saveLeagueDTO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Response<SaveLeagueDTO> createNewLeague(SaveLeagueDTO saveLeagueDTO, Long userId) {
        try{
            log.info("In handle create new league {} userId {}", saveLeagueDTO, userId);
            performUserSanityChecks(userId);

            if(Objects.nonNull(saveLeagueDTO.getId())) {
                Optional<LeagueEntity> savedTournament = leagueEntityRepository.findById(saveLeagueDTO.getId());
                if(savedTournament.isPresent()) {
                    log.warn("League already created {}", saveLeagueDTO.getId());
                    throw new CustomException(MatchErrorCode.MATCH_ALREADY_CREATED);
                }
            }
            if(saveLeagueDTO.getParticipantCount() % 2 != 0){
                log.warn("Number of participants in group cannot be odd");
                throw new CustomException(MatchErrorCode.USER_NOT_FOUND);
            }
            LeagueEntity newTournament = new LeagueEntity();
            BeanUtils.copyProperties(saveLeagueDTO, newTournament, "id");
            newTournament.setId(IdGenerator.getInstance().getID());
            newTournament.setAdminUser(userId);
            newTournament.setCreatedTime(LocalDateTime.now());
            newTournament.setTournamentStatus(TournamentStatus.CREATED.getTournamentStatusCode());
            newTournament.setTournamentType(TournamentType.LEAGUE.getTournamentTypeCode());
            log.info("Saving new tournament");
            LeagueEntity savedTournament = leagueEntityRepository.save(newTournament);
            if(saveLeagueDTO.getIsSeason()) {
                handleSeasonBasedLeague(saveLeagueDTO, savedTournament);
            }
            BeanUtils.copyProperties(savedTournament, saveLeagueDTO);
            saveLeagueDTO.setTournamentStatus(TournamentStatus.getTournamentStatusByCode(savedTournament.getTournamentStatus()));
            saveLeagueDTO.setTournamentType(TournamentType.getTournamentTypeByCode(savedTournament.getTournamentType()));
            return new Response<>(saveLeagueDTO);

        } catch (Exception e) {
            log.error("Some exception occurred while creating league",e);
            throw new CustomException(SystemErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    private void handleSeasonBasedLeague(SaveLeagueDTO saveLeagueDTO, LeagueEntity newTournament) {
        LocalDateTime seasonStartDate = LocalDateTime.now();
        String seasonName = String.format("SEASON %s",seasonStartDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        log.info("League is season based creating first season {} league id {}", seasonName, newTournament.getId());
        SubLeaguesEntity subLeague = subLeaguesEntityRepository.save(SubLeaguesEntity.builder()
                .id(IdGenerator.getInstance().getID())
                .parentLeagueId(newTournament.getId())
                .name(seasonName)
                .created(seasonStartDate)
                .build());
        newTournament.setCurrentSeasonId(subLeague.getId());
        leagueEntityRepository.updateLeagueCurrentSeason(subLeague.getId(), newTournament.getId());
    }

    @Transactional
    public Response<SaveLeagueDTO> updateLeague(SaveLeagueDTO saveTournamentDTO, Long leagueId, Long userId) {
        log.info("In handle update tournament {}", saveTournamentDTO);
        performUserSanityChecks(userId);
        if(Objects.isNull(leagueId)) {
            log.info("Tournament id is null");
            throw new CustomException(MatchErrorCode.MATCH_ALREADY_CREATED);
        }
        LeagueEntity savedTournament = leagueEntityRepository.findById(leagueId).orElseThrow(() -> {
            log.warn("Tournament not found {}", leagueId);
            return new CustomException(MatchErrorCode.MATCH_ALREADY_CREATED);
        });
        BeanUtils.copyProperties(saveTournamentDTO, savedTournament, "id");
        log.info("Updating match");
        LeagueEntity updatedTournament = leagueEntityRepository.save(savedTournament);
        BeanUtils.copyProperties(updatedTournament, saveTournamentDTO);
        return new Response<>(saveTournamentDTO);
    }

    public Response<SaveLeagueDTO> getLeagueById(Long leagueId, Long userId) {
        log.info("In handle get match by id {}", leagueId);
        performUserSanityChecks(userId);
        LeagueEntity savedTournament = performLeagueSanityChecks(leagueId);
        SaveLeagueDTO saveTournamentDTO = new SaveLeagueDTO();
        BeanUtils.copyProperties(savedTournament, saveTournamentDTO);
        return new Response<>(saveTournamentDTO);
    }

    @Transactional(noRollbackFor = {CustomException.class})
    public Response<SaveLeagueDTO> inviteParticipants(Long leagueId, Long userId, List<Long> participantIds) {
        log.info("In handle invite participants by id {}", leagueId);
        performUserSanityChecks(userId);
        LeagueEntity savedTournament = performLeagueSanityChecks(leagueId);

        SaveLeagueDTO saveLeagueDTO = new SaveLeagueDTO();
        BeanUtils.copyProperties(savedTournament, saveLeagueDTO);
//        saveLeagueDTO.setParticipants(new ArrayList<>(savedTournament.getParticipantCount()));
        participantIds.forEach(participantId -> {
            UserEntity savedUser = userEntityRepository.findById(participantId).orElseThrow(() -> {
               log.warn("User id {} not found", participantId);
               return new CustomException(UserErrorCode.USER_NOT_FOUND);
            });
//            UserDTO userDTO = new UserDTO(); //TODO: Think about this
//            BeanUtils.copyProperties(savedUser, userDTO);
//            saveLeagueDTO.getParticipants().add(userDTO);
            //sending tournament invitation
            tournamentInvitationHandler.sendInvitation(TournamentInvitationDTO.builder()
                            .tournamentId(savedTournament.getId())
                            .tournamentType(savedTournament.getTournamentType())
                            .invitationFrom(userId)
                            .invitationTo(participantId)
                            .invitationStatus(TournamentInvitationStatus.CREATED.ordinal())
                            .created(LocalDateTime.now())
                            .build());

        });
        return new Response<>(saveLeagueDTO);
    }

    @Transactional
    public Response<SaveLeagueDTO> startRegistration(Long leagueId, Long userId) {
        log.info("In handle start registration status league id {} user id {}", leagueId, userId);
        performUserSanityChecks(userId);
        LeagueEntity leagueEntity = performLeagueSanityChecks(leagueId);

        if (!leagueEntity.getIsPublic()) { //if the league is private registration status cannot be set to open or closed
            log.warn("Registration status cannot be changed as league is private {}", leagueEntity.getId());
            throw new CustomException(MatchErrorCode.USER_NOT_FOUND);
        }
        //if the tournament has status other than created or registration open
        if(TournamentStatus.CREATED.getTournamentStatusCode() != leagueEntity.getTournamentStatus() || TournamentStatus.REGISTRATION_OPEN.getTournamentStatusCode() != leagueEntity.getTournamentStatus()) {
            log.warn("Registration is already opened for this league {}", leagueEntity.getId());
            throw new CustomException(MatchErrorCode.USER_NOT_FOUND);
        }
        SaveLeagueDTO saveLeagueDTO = new SaveLeagueDTO();
        changeRegistrationStatus(leagueEntity, saveLeagueDTO, TournamentStatus.REGISTRATION_OPEN);
        return new Response<>(saveLeagueDTO);
    }

    @Transactional
    public Response<SaveLeagueDTO> endRegistration(Long leagueId, Long userId) {
        log.info("In handle end registration status league id {} user id {}", leagueId, userId);
        performUserSanityChecks(userId);
        LeagueEntity leagueEntity = performLeagueSanityChecks(leagueId);
        if (!leagueEntity.getIsPublic()) {
            log.warn("Registration status cannot be changed as league is private {}", leagueEntity.getId());
            throw new CustomException(MatchErrorCode.USER_NOT_FOUND);
        }
        if(TournamentStatus.CREATED.getTournamentStatusCode() == leagueEntity.getTournamentStatus() || TournamentStatus.REGISTRATION_OPEN.getTournamentStatusCode() == leagueEntity.getTournamentStatus()) {
            log.warn("Registration not open for this league {}", leagueEntity.getId());
            throw new CustomException(MatchErrorCode.USER_NOT_FOUND);
        }
        SaveLeagueDTO saveLeagueDTO = new SaveLeagueDTO();
        changeRegistrationStatus(leagueEntity, saveLeagueDTO,TournamentStatus.REGISTRATION_CLOSED);
        return new Response<>(saveLeagueDTO);
    }

    public Response<SaveLeagueDTO> register(Long leagueId, Long userId) {
        log.info("In handle user league register league id {} user id {}", leagueId, userId);
        UserEntity userEntity = performUserSanityChecks(userId);
        LeagueEntity leagueEntity = performLeagueSanityChecks(leagueId);
        if (!leagueEntity.getIsPublic()) {
            log.warn("Registration status cannot be changed as league is private {}", leagueEntity.getId());
            throw new CustomException(MatchErrorCode.USER_NOT_FOUND);
        }
        if(TournamentStatus.REGISTRATION_OPEN.getTournamentStatusCode() != leagueEntity.getTournamentStatus()) {
            log.warn("Registration not open for this league {}", leagueEntity.getId());
            throw new CustomException(MatchErrorCode.USER_NOT_FOUND);
        }
        if(leagueEntity.getParticipantCount() <= tournamentParticipantHandler.getParticipantsCount(leagueEntity.getId())) {
            log.warn("Participant count exceeded league id {}", leagueEntity.getId());
            throw new CustomException(MatchErrorCode.USER_NOT_FOUND);  //TODO:Change exception
        }
        tournamentParticipantHandler.addParticipant(leagueEntity, userEntity);
        return new Response<>(new SaveLeagueDTO()); //TODO:Change this
    }

    @Transactional
    public Response<TournamentInvitationDTO> acceptInvitation(TournamentInvitationDTO tournamentInvitationDTO, Long userId) {
        log.info("In handle accept invitation league id {} user id {}", tournamentInvitationDTO.getId(), userId);
        UserEntity userEntity = performUserSanityChecks(userId);
        LeagueEntity leagueEntity = performLeagueSanityChecks(tournamentInvitationDTO.getTournamentId());
        //check if participant count has already exceeded
        if(leagueEntity.getParticipantCount() <= tournamentParticipantHandler.getParticipantsCount(leagueEntity.getId())) {
            log.warn("Participant count exceeded league id {}", leagueEntity.getId());
            throw new CustomException(MatchErrorCode.USER_NOT_FOUND);
        }
        TournamentInvitationDTO acceptedTournamentInvitationDTO = tournamentInvitationHandler.changeInvitationStatus(tournamentInvitationDTO.getId(), userId, TournamentInvitationStatus.ACCEPTED);
        //TODO:Emit a Kafka invitation acceptation event and add the user to the league
        tournamentParticipantHandler.addParticipant(leagueEntity, userEntity);
        return new Response<>(acceptedTournamentInvitationDTO);
    }

    public Response<TournamentInvitationDTO> declineInvitation(TournamentInvitationDTO tournamentInvitationDTO, Long userId) {
        log.info("In handle decline invitation league id {} user id {}", tournamentInvitationDTO.getId(), userId);
        performUserSanityChecks(userId);
        performLeagueSanityChecks(tournamentInvitationDTO.getTournamentId());
        TournamentInvitationDTO acceptedTournamentInvitationDTO = tournamentInvitationHandler.changeInvitationStatus(tournamentInvitationDTO.getId(), userId, TournamentInvitationStatus.DECLINED);
        return new Response<>(acceptedTournamentInvitationDTO);
    }

    public Response<List<SaveMatchDTO>> generateLeagueFixtures(Long leagueId, Long userId) {
        log.info("In handle generate fixtures league id {} user id {}", leagueId, userId);
        performUserSanityChecks(userId);
        LeagueEntity leagueEntity = performLeagueSanityChecks(leagueId);
        List<TournamentParticipantEntity> participants = tournamentParticipantHandler.getParticipants(leagueId);
        List<SaveMatchDTO> fixtures = fixtureGeneratorHandler.generateLeagueFixtures(leagueEntity, participants);
        return new Response<>(fixtures); //TODO:Need to think about this
    }

    public Response<List<SaveMatchDTO>> saveFixtures(Long leagueId, Long userId, List<SaveMatchDTO> fixtures) {
        log.info("In handle generate fixtures league id {} user id {}", leagueId, userId);
        performUserSanityChecks(userId);
        LeagueEntity leagueEntity = performLeagueSanityChecks(leagueId);
        fixtureGeneratorHandler.saveFixtures(fixtures);
        return new Response<>(fixtures);
    }

    public Response<List<SaveMatchDTO>> getFixtures(Long leagueId, Long userId) {
        log.info("In handle get fixtures league id {} user id {}", leagueId, userId);
        performUserSanityChecks(userId);
        LeagueEntity leagueEntity = performLeagueSanityChecks(leagueId);
        List<SaveMatchDTO> fixtures = fixtureGeneratorHandler.getLeagueFixtures(leagueEntity.getId(), leagueEntity.getCurrentSeasonId());
        return new Response<>(fixtures);
    }

    public Response<SaveLeagueDTO> addDummyParticipants(Long leagueId, List<Long> participantIds) {
        try{
            LeagueEntity leagueEntity = leagueEntityRepository.findById(leagueId).get();
            SaveLeagueDTO saveLeagueDTO = new SaveLeagueDTO();
            saveLeagueDTO.setParticipants(new ArrayList<>());
            saveLeagueDTO.setTournamentType(TournamentType.getTournamentTypeByCode(leagueEntity.getTournamentType()));
            saveLeagueDTO.setTournamentStatus(TournamentStatus.getTournamentStatusByCode(leagueEntity.getTournamentStatus()));
            BeanUtils.copyProperties(leagueEntity, saveLeagueDTO);
            participantIds.forEach(participantId -> {
                UserEntity userEntity = userEntityRepository.findById(participantId).get();
                TournamentParticipantEntity tournamentParticipantEntity = tournamentParticipantHandler.addParticipant(leagueEntity, userEntity);
                saveLeagueDTO.getParticipants().add(tournamentParticipantEntity);
            });
            return new Response<>(saveLeagueDTO);
        } catch (Exception e) {
            log.error("Some exception occurred while adding dummy participants", e);
            throw new CustomException(SystemErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    public void changeRegistrationStatus(LeagueEntity leagueEntity, SaveLeagueDTO saveLeagueDTO, TournamentStatus status) {
        leagueEntity.setTournamentStatus(status.getTournamentStatusCode());
        LeagueEntity updatedLeagueEntity = leagueEntityRepository.save(leagueEntity);
        BeanUtils.copyProperties(updatedLeagueEntity, saveLeagueDTO);
    }

    private LeagueEntity performLeagueSanityChecks(Long leagueId) {
        if(Objects.isNull(leagueId)) {
            log.info("Match id is null");
            throw new CustomException(MatchErrorCode.MATCH_ALREADY_CREATED);
        }
        return leagueEntityRepository.findById(leagueId).orElseThrow(() -> {
            log.warn("Match not found {}", leagueId);
            return new CustomException(MatchErrorCode.MATCH_ALREADY_CREATED);
        });
    }

    private UserEntity performUserSanityChecks(Long userId) {
        if(Objects.isNull(userId)) {
            log.warn("User id cannot be null");
            throw new CustomException(UserErrorCode.USER_NOT_FOUND);
        }
        return userEntityRepository.findById(userId).orElseThrow(() -> {
            log.warn("User id not registered");
            return new CustomException(UserErrorCode.USER_NOT_REGISTERED);
        });
    }

}
