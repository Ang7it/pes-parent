package com.pespurse.match.web.dto;

import com.pespurse.match.repo.entity.enums.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveMatchDTO {
    Long id;
    Long player1Id;
    Long player2Id;
    Long winnerId;
    Integer player1Score;
    Integer player2Score;
    LocalDateTime matchPlayedTime;
    String matchScreenshotUrl;
    Integer matchType;
    MatchStatus matchStatus;
}
