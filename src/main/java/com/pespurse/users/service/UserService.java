package com.pespurse.users.service;

import com.pespurse.match.web.dto.SaveMatchDTO;
import com.pespurse.users.repo.UserEntityRepository;
import com.pespurse.users.repo.UserStatisticsEntityRepository;
import com.pespurse.users.repo.entity.UserStatisticsEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final UserStatisticsEntityRepository userStatisticsEntityRepository;

    @Transactional
    public void updateUserMatchStatistics(SaveMatchDTO saveMatchDTO) {
        log.info("In update user match statistics {}", saveMatchDTO);
        List<UserStatisticsEntity> userStatisticsEntities = userStatisticsEntityRepository.findUserStatistics(List.of(saveMatchDTO.getPlayer1Id(), saveMatchDTO.getPlayer2Id()));
        userStatisticsEntities.forEach(userStatisticsEntity -> {
            if(userStatisticsEntity.getId().equals(saveMatchDTO.getPlayer1Id())){
                userStatisticsEntity.setMatchesPlayed(userStatisticsEntity.getMatchesPlayed() + 1);
                userStatisticsEntity.setGoalsScored(userStatisticsEntity.getGoalsScored() + saveMatchDTO.getPlayer1Score());
                userStatisticsEntity.setGoalsAllowed(userStatisticsEntity.getGoalsAllowed() + saveMatchDTO.getPlayer2Score());
                userStatisticsEntity.setGoalsForwarded((double) (userStatisticsEntity.getGoalsScored() / userStatisticsEntity.getMatchesPlayed()));
                userStatisticsEntity.setGoalsConceded((double) (userStatisticsEntity.getGoalsAllowed() / userStatisticsEntity.getMatchesPlayed()));
                if(saveMatchDTO.getPlayer2Score() == 0){
                    userStatisticsEntity.setCleanSheets(userStatisticsEntity.getCleanSheets() + 1);
                }
                if(saveMatchDTO.getPlayer1Score() > saveMatchDTO.getPlayer2Score()) {
                    //player 1 win
                    userStatisticsEntity.setMatchesWon(userStatisticsEntity.getMatchesWon() + 1);
                } else if (saveMatchDTO.getPlayer1Score() < saveMatchDTO.getPlayer2Score()) {
                    //player 2 win
                    userStatisticsEntity.setMatchesLost(userStatisticsEntity.getMatchesLost() + 1);
                } else {
                    //draw
                    userStatisticsEntity.setMatchesDrawn(userStatisticsEntity.getMatchesDrawn() + 1);
                }
            }else {
                userStatisticsEntity.setMatchesPlayed(userStatisticsEntity.getMatchesPlayed() + 1);
                userStatisticsEntity.setGoalsScored(userStatisticsEntity.getGoalsScored() + saveMatchDTO.getPlayer2Score());
                userStatisticsEntity.setGoalsAllowed(userStatisticsEntity.getGoalsAllowed() + saveMatchDTO.getPlayer1Score());
                userStatisticsEntity.setGoalsForwarded((double) (userStatisticsEntity.getGoalsScored() / userStatisticsEntity.getMatchesPlayed()));
                userStatisticsEntity.setGoalsConceded((double) (userStatisticsEntity.getGoalsAllowed() / userStatisticsEntity.getMatchesPlayed()));
                if(saveMatchDTO.getPlayer1Score() == 0){
                    userStatisticsEntity.setCleanSheets(userStatisticsEntity.getCleanSheets() + 1);
                }
                if(saveMatchDTO.getPlayer2Score() > saveMatchDTO.getPlayer1Score()) {
                    //player 1 win
                    userStatisticsEntity.setMatchesWon(userStatisticsEntity.getMatchesWon() + 1);
                } else if (saveMatchDTO.getPlayer2Score() < saveMatchDTO.getPlayer1Score()) {
                    //player 2 win
                    userStatisticsEntity.setMatchesLost(userStatisticsEntity.getMatchesLost() + 1);
                } else {
                    //draw
                    userStatisticsEntity.setMatchesDrawn(userStatisticsEntity.getMatchesDrawn() + 1);
                }
            }
        });
        userStatisticsEntityRepository.saveAll(userStatisticsEntities);
    }
}
