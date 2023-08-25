package com.pespurse.players.repo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "player")
@Table(name = "player")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nationality;
    private String club;
    private Integer rating;
    private Integer position; //cf, cmf, gk
    private Integer cardType;  //featured, epic, highlight, standard, big time
    private String tag;  //can be used as a custom system generated tag
    private String version;  //can be used to track the version of the player
    private String playerImage;
}
