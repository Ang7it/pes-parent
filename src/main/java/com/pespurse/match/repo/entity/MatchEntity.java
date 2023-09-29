package com.pespurse.match.repo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity(name = "match")
@Table(name = "match")
@AllArgsConstructor
@NoArgsConstructor
public class MatchEntity {

    @Id
    private Long id;
    private Long parentLeagueId;
    private Long childLeagueId;
    private Long player1Id;
    private Long player2Id;
    private Long winnerId;
    private Integer player1Score;
    private Integer player2Score;
    private LocalDateTime createdTime; //when the match was created
    private LocalDateTime scheduledTime; //when the match is scheduled
    private LocalDateTime matchPlayedTime; //when the match was played
    private LocalDateTime rescheduledTime; //when the match was rescheduled to
    private String matchScreenshotUrl;
    private Integer matchStatus;
    private Integer round;
}
