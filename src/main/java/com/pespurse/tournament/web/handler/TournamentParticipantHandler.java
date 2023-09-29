package com.pespurse.tournament.web.handler;

import com.pespurse.global.exception.CustomException;
import com.pespurse.global.exception.errorCode.MatchErrorCode;
import com.pespurse.global.util.IdGenerator;
import com.pespurse.tournament.repo.TournamentParticipantEntityRepository;
import com.pespurse.tournament.repo.entity.LeagueEntity;
import com.pespurse.tournament.repo.entity.TournamentParticipantEntity;
import com.pespurse.tournament.web.dto.TournamentInvitationDTO;
import com.pespurse.users.repo.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TournamentParticipantHandler {

    private final TournamentParticipantEntityRepository tournamentParticipantEntityRepository;

    public Integer getParticipantsCount(Long leagueId) {
        return tournamentParticipantEntityRepository.countNumberOfParticipants(leagueId);
    }

    public List<TournamentParticipantEntity> getParticipants(Long leagueId) {
        return tournamentParticipantEntityRepository.getListOfParticipants(leagueId);
    }

    @Transactional
    public TournamentParticipantEntity addParticipant(LeagueEntity leagueEntity, UserEntity userEntity) {
        log.info("In handle add participant league id {} user id {}", leagueEntity.getId(), userEntity.getId());

        Optional<TournamentParticipantEntity> existingParticipant = tournamentParticipantEntityRepository.findByExistingParticipantById(leagueEntity.getId(), userEntity.getId());
        if(existingParticipant.isPresent()) {
            log.warn("Participant has already been added {}", userEntity.getId());
            throw new CustomException(MatchErrorCode.USER_NOT_FOUND);//TODO:Change exception code
        }

        return tournamentParticipantEntityRepository.save(TournamentParticipantEntity.builder()
                .id(IdGenerator.getInstance().getID())
                .userId(userEntity.getId())
                .parentLeagueId(leagueEntity.getId())
                .childLeagueId(leagueEntity.getCurrentSeasonId())
                .userName(userEntity.getUserName())
                .matchesPlayed(0) //initialising
                .matchesWon(0)
                .matchesLost(0)
                .matchesDrawn(0)
                .position(-1)
                .goalsForwarded(0)
                .goalsAllowed(0)
                .goalsDifference(0)
                .points(0)
                .build());
    }

    @Transactional
    public void addParticipants(LeagueEntity leagueEntity, List<UserEntity> participants) {
        //TODO:Need to think about this and refactor
        participants.forEach(participant -> addParticipant(leagueEntity, participant));
    }
}
