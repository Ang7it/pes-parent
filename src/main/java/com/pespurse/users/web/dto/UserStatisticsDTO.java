package com.pespurse.users.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatisticsDTO {
    Long id;
    Long userId;
    Long matchesPlayed;
    Long matchesWon;
    Long matchesDrawn;
    Long matchesLost;
    Long goalsScored;
    Long goalsAllowed;
    Long cleanSheets;
    Double goalsForwarded;
    Double goalsConceded;
}
