package com.pespurse.team.repo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "team_players")
@Table(name = "team_players")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamPlayersEntity {
    @Id
    private Long id;
    private Long teamId;
    private Long playerId;
    private Long userId;
    private Integer playerPosition; //this is required for the storing the position to check with the players position to check compatibility
    private boolean isSubstitute;
}
