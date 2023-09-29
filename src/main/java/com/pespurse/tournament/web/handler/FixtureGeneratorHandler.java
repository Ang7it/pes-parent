package com.pespurse.tournament.web.handler;

import com.pespurse.global.util.IdGenerator;
import com.pespurse.match.repo.entity.enums.MatchStatus;
import com.pespurse.match.service.MatchService;
import com.pespurse.match.web.dto.SaveMatchDTO;
import com.pespurse.tournament.repo.entity.LeagueEntity;
import com.pespurse.tournament.repo.entity.TournamentParticipantEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class FixtureGeneratorHandler {

    private final MatchService matchService;
    public List<SaveMatchDTO> generateLeagueFixtures(LeagueEntity leagueEntity, List<TournamentParticipantEntity> participants) {
        int numberOfTeams = participants.size();
        Collections.shuffle(participants);

        // Generate the fixtures using the cyclic algorithm.
        int totalRounds = numberOfTeams - 1;
        int matchesPerRound = numberOfTeams / 2;
        List<List<SaveMatchDTO>> rounds = new LinkedList<>();
        List<SaveMatchDTO> matches = new ArrayList<>();

        LocalDateTime currDate = LocalDate.now().atStartOfDay().plusDays(1);
        if(Objects.nonNull(leagueEntity.getPreferredMatchDateDelay()) && leagueEntity.getPreferredMatchDateDelay()!=0) {
            currDate = currDate.plusDays(leagueEntity.getPreferredMatchDateDelay());
        }

        for (int round = 0; round < totalRounds; round++) {
            List<SaveMatchDTO> fixtures = new LinkedList<>();
            for (int match = 0; match < matchesPerRound; match++) {
                int home = (round + match) % (numberOfTeams - 1);
                int away = (numberOfTeams - 1 - match + round) % (numberOfTeams - 1);
                // Last team stays in the same place while the others
                // rotate around it.
                if (match == 0) {
                    away = numberOfTeams - 1;
                }
                TournamentParticipantEntity homePlayer = participants.get(home);
                TournamentParticipantEntity awayPlayer = participants.get(away);
                fixtures.add(SaveMatchDTO.builder()
                        .parentLeagueId(leagueEntity.getId())
                        .childLeagueId(leagueEntity.getCurrentSeasonId())
                        .createdTime(LocalDateTime.now())
                        .matchStatus(MatchStatus.PENDING.getMatchStatusCode())
                        .player1Id(homePlayer.getId())
                        .player1Name(homePlayer.getUserName())
                        .player1Score(0)
                        .player2Id(awayPlayer.getId())
                        .player2Name(awayPlayer.getUserName())
                        .player2Score(0)
                        .scheduledTime(currDate)
                        .round(round+1)
                        .build());
            }
            rounds.add(fixtures);
            currDate = currDate.plusDays(1);
        }

        // Interleave so that home and away games are fairly evenly dispersed.
        List<List<SaveMatchDTO>> interleaved = new LinkedList<>();

        int evn = 0;
        int odd = (numberOfTeams / 2);
        for (int i = 0; i < rounds.size(); i++) {
            if (i % 2 == 0) {
                interleaved.add(rounds.get(evn++));
            } else {
                interleaved.add(rounds.get(odd++));
            }
        }

        rounds = interleaved;

        // Last team can't be away for every game so flip them
        // to home on odd rounds.
        for (int roundNumber = 0; roundNumber < rounds.size(); roundNumber++) {
            if (roundNumber % 2 == 1) {
                SaveMatchDTO fixture = rounds.get(roundNumber).get(0);
                Long tempId = fixture.getPlayer1Id();
                fixture.setPlayer1Id(fixture.getPlayer2Id());
                fixture.setPlayer2Id(tempId);
                rounds.get(roundNumber).set(0, fixture);
            }
        }

        if(leagueEntity.getIncludeReverseFixtures()){
            List<List<SaveMatchDTO>> reverseFixtures = new LinkedList<>();
            int numberOfRounds = rounds.size();
            for(int roundNumber = 0; roundNumber < numberOfRounds; roundNumber++){
                List<SaveMatchDTO> reverseRound = new LinkedList<>();
                for(SaveMatchDTO fixture: rounds.get(roundNumber)){
                    reverseRound.add(SaveMatchDTO.builder()
                            .parentLeagueId(leagueEntity.getId())
                            .childLeagueId(leagueEntity.getCurrentSeasonId())
                            .createdTime(LocalDateTime.now())
                            .matchStatus(MatchStatus.PENDING.getMatchStatusCode())
                            .player1Id(fixture.getPlayer2Id())
                            .player1Name(fixture.getPlayer2Name())
                            .player1Score(0)
                            .player2Id(fixture.getPlayer1Id())
                            .player2Name(fixture.getPlayer1Name())
                            .player2Score(0)
                            .scheduledTime(currDate)
                            .round((roundNumber+1)+numberOfRounds)
                            .build());
                }
                reverseFixtures.add(reverseRound);
                currDate = currDate.plusDays(1);
            }
            rounds.addAll(reverseFixtures);
        }

        for (List<SaveMatchDTO> round : rounds) {
            matches.addAll(round);
        }

        return matches;
    }

    public void saveFixtures(List<SaveMatchDTO> fixtures) {
        log.info("In handle saving fixtures");
        fixtures.forEach(matchService::saveNewMatch);
    }

    public List<SaveMatchDTO> getLeagueFixtures(Long leagueId, Long currentSeasonId) {
        log.info("In handle get fixtures");
        return  matchService.getLeagueFixtures(leagueId, currentSeasonId);
    }
}
