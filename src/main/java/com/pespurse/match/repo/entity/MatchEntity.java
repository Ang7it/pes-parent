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
    Long id;
    Long player1Id;
    Long player2Id;
    Long winnerId;
    Integer player1Score;
    Integer player2Score;
    LocalDateTime matchPlayedTime;
    String matchScreenshotUrl;
    Integer matchType;
}
