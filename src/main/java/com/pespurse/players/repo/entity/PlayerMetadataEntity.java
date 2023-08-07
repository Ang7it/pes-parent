package com.pespurse.players.repo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "playerMetadata")
@Table(name = "player_metadata")
@AllArgsConstructor
@NoArgsConstructor
public class PlayerMetadataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long playerId;
    Integer playingStyle;
}
