package com.pespurse.match.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveMatchDTO implements Serializable {
    private Long id;
    private Long parentLeagueId;
    private Long childLeagueId;
    private Long player1Id;
    private String player1Name;
    private Long player2Id;
    private String player2Name;
    private Long winnerId;
    private Integer player1Score;
    private Integer player2Score;
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdTime; //when the match was created
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime scheduledTime; //when the match is scheduled
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime matchPlayedTime; //when the match was played
//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime rescheduledTime; //when the match was rescheduled to
    private String matchScreenshotUrl;
    private Integer matchStatus;
    private Integer round;
}
