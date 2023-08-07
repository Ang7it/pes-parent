package com.pespurse.users.repo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "userStatistics")
@Table(name = "user_statistics")
@AllArgsConstructor
@NoArgsConstructor
public class UserStatisticsEntity {

    @Id
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
