package com.pespurse.tournament.repo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "tournamentParticipant")
@Table(name = "tournament_participant")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TournamentParticipantEntity {
    @Id
    private Long id;
    private Long parentLeagueId;
    private Long childLeagueId;
    private Long userId;
    private String userName;
    private Integer position;
    private Integer matchesPlayed;
    private Integer matchesWon;
    private Integer matchesDrawn;
    private Integer matchesLost;
    private Integer points;
    private Integer goalsForwarded;
    private Integer goalsAllowed;
    private Integer goalsDifference;
}
