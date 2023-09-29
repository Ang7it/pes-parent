package com.pespurse.tournament.repo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity(name = "leagueTournament")
@Table(name = "league_tournament")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeagueEntity {

    @Id
    private Long id;
    private String name;
    private Long adminUser;
    private Integer tournamentStatus;
    private Integer tournamentType; //refer tournament type enum
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private LocalDateTime leagueStartDate;
    private Integer participantCount;
    private String tournamentLogo;
    private Boolean isPublic;
    private LocalDateTime matchDayStartTime; //at what time the user wants to start scheduling the match
    private LocalDateTime matchDayEndTime;  //at what time the user wants to stop
    private Integer matchRolloverDayCount; //after how many days the match will either be abandoned or given walkover on admin consent
    private Boolean isSeason;
    private Long currentSeasonId;
    private Boolean includeReverseFixtures;
    private Long preferredMatchDateDelay; //this specifies the initial delay for the first match from current day (by default the matches will start from next day 12:00 midnight)
}
