package com.pespurse.match.web.handler;

import com.pespurse.global.exception.CustomException;
import com.pespurse.global.exception.errorCode.MatchErrorCode;
import com.pespurse.global.response.Response;
import com.pespurse.global.util.IdGenerator;
import com.pespurse.match.repo.MatchEntityRepository;
import com.pespurse.match.repo.entity.MatchEntity;
import com.pespurse.match.repo.entity.enums.MatchStatus;
import com.pespurse.match.web.dto.SaveMatchDTO;
import com.pespurse.users.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MatchHandler {

    private final MatchEntityRepository matchEntityRepository;
    private final UserService userService;

    /**
     * First we check if the match already exists by the matchID.
     * If not already saved we create an entry of the match in database
     *
     * @param saveMatchDTO
     * @return
     */
    public Response<SaveMatchDTO> createNewMatch(SaveMatchDTO saveMatchDTO) {
        log.info("In handle create new match {}", saveMatchDTO);
        if(Objects.nonNull(saveMatchDTO.getId())) {
            Optional<MatchEntity> savedMatch = matchEntityRepository.findById(saveMatchDTO.getId());
            if(savedMatch.isPresent()) {
                log.warn("Match already created {}", saveMatchDTO.getId());
                throw new CustomException(MatchErrorCode.MATCH_ALREADY_CREATED);
            }
        }
        MatchEntity newMatch = new MatchEntity();
        newMatch.setId(IdGenerator.getInstance().getID());
        BeanUtils.copyProperties(saveMatchDTO, newMatch, "id");
        log.info("Saving new match");
        MatchEntity savedMatch = matchEntityRepository.save(newMatch);
        BeanUtils.copyProperties(savedMatch, saveMatchDTO);
        return new Response<>(saveMatchDTO);
    }

    /**
     * First we check if the match already exists by the matchID.
     * If already saved we update the match with the new details
     *
     * @param saveMatchDTO
     * @return
     */
    public Response<SaveMatchDTO> updateMatch(SaveMatchDTO saveMatchDTO, Long matchId) {
        log.info("In handle update match {}", saveMatchDTO);
        if(Objects.isNull(matchId)) {
            log.info("Match id is null");
            throw new CustomException(MatchErrorCode.MATCH_ALREADY_CREATED);
        }
        MatchEntity savedMatch = matchEntityRepository.findById(matchId).orElseThrow(() -> {
            log.warn("Match not found {}", matchId);
            return new CustomException(MatchErrorCode.MATCH_ALREADY_CREATED);
        });
        BeanUtils.copyProperties(saveMatchDTO, savedMatch, "id");
        log.info("Updating match");
        MatchEntity updatedMatch = matchEntityRepository.save(savedMatch);
        BeanUtils.copyProperties(updatedMatch, saveMatchDTO);
        return new Response<>(saveMatchDTO);
    }


    public Response<SaveMatchDTO> getMatchById(Long matchId) {
        log.info("In handle get match by id {}", matchId);
        if(Objects.isNull(matchId)) {
            log.info("Match id is null");
            throw new CustomException(MatchErrorCode.MATCH_ALREADY_CREATED);
        }
        MatchEntity savedMatch = matchEntityRepository.findById(matchId).orElseThrow(() -> {
            log.warn("Match not found {}", matchId);
            return new CustomException(MatchErrorCode.MATCH_ALREADY_CREATED);
        });
        SaveMatchDTO saveMatchDTO = new SaveMatchDTO();
        BeanUtils.copyProperties(savedMatch, saveMatchDTO);
        return new Response<>(saveMatchDTO);
    }

    @Transactional
    public Response<SaveMatchDTO> rescheduleMatch(SaveMatchDTO saveMatchDTO, Long matchId) {
        log.info("In handle get match by id {}", matchId);
        if(Objects.isNull(matchId)) {
            log.info("Match id is null");
            throw new CustomException(MatchErrorCode.MATCH_ALREADY_CREATED);
        }
        MatchEntity savedMatch = matchEntityRepository.findById(matchId).orElseThrow(() -> {
            log.warn("Match not found {}", matchId);
            return new CustomException(MatchErrorCode.MATCH_ALREADY_CREATED);
        });
        savedMatch.setRescheduledTime(saveMatchDTO.getRescheduledTime());
        MatchEntity rescheduledMatch = matchEntityRepository.save(savedMatch);
        BeanUtils.copyProperties(rescheduledMatch, saveMatchDTO);
        return new Response<>(saveMatchDTO);
    }

    @Transactional
    public Response<SaveMatchDTO> playMatch(SaveMatchDTO saveMatchDTO, Long matchId) {
        log.info("In handle get match by id {}", matchId);
        if(Objects.isNull(matchId)) {
            log.info("Match id is null");
            throw new CustomException(MatchErrorCode.MATCH_ALREADY_CREATED);
        }
        MatchEntity savedMatch = matchEntityRepository.findById(matchId).orElseThrow(() -> {
            log.warn("Match not found {}", matchId);
            return new CustomException(MatchErrorCode.MATCH_ALREADY_CREATED);
        });
        savedMatch.setMatchPlayedTime(LocalDateTime.now());
        savedMatch.setMatchStatus(MatchStatus.COMPLETED.getMatchStatusCode());
        savedMatch.setPlayer1Score(saveMatchDTO.getPlayer1Score());
        savedMatch.setPlayer2Score(saveMatchDTO.getPlayer2Score());
        Long winnerId = -1L;
        if(saveMatchDTO.getPlayer1Score() > saveMatchDTO.getPlayer2Score()) {
            winnerId = saveMatchDTO.getPlayer1Id();
        } else {
            winnerId = saveMatchDTO.getPlayer2Id();
        }
        savedMatch.setWinnerId(winnerId);
        MatchEntity playedMatch = matchEntityRepository.save(savedMatch);
        BeanUtils.copyProperties(playedMatch, saveMatchDTO);
        CompletableFuture.runAsync(() -> userService.updateUserMatchStatistics(saveMatchDTO)); //TODO:Change this to Kafka event
        return new Response<>(saveMatchDTO);
    }

    public List<SaveMatchDTO> getLeagueFixtures(Long leagueId, Long currentSeasonId) {

        List<MatchEntity> fixtures = matchEntityRepository.getLeagueFixturesForCurrentSeason(leagueId, currentSeasonId);
        return fixtures.stream()
                .map(fixture -> {
                    SaveMatchDTO saveMatchDTO = new SaveMatchDTO();
                    BeanUtils.copyProperties(fixture, saveMatchDTO);
                    return saveMatchDTO;
                })
                .collect(Collectors.toList());
    }
}
