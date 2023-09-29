package com.pespurse.tournament.web.dto;

import com.pespurse.tournament.repo.entity.TournamentParticipantEntity;
import com.pespurse.tournament.repo.entity.enums.TournamentStatus;
import com.pespurse.tournament.repo.entity.enums.TournamentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveLeagueDTO {
    private Long id;
    private String name;
    private Long adminUser;
    private TournamentStatus tournamentStatus;
    private TournamentType tournamentType; //refer tournament type enum
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private Integer participantCount; //number of participants
    private String tournamentLogo;
    private Boolean isPublic; //to check if the tournament is public or private
    private List<TournamentParticipantEntity> participants;
    private LocalDateTime matchDayStartTime; //at what time the user wants to start scheduling the match
    private LocalDateTime matchDayEndTime;  //at what time the user wants to stop
    private Integer matchesPerDayCount;
    private Integer matchRolloverDayCount; //after how many days the match will either be abandoned or given walkover on admin consent
    private Boolean isSeason;
    private Long currentSeasonId;
    private Boolean includeReverseFixtures;
}
