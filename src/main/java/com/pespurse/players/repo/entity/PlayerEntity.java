package com.pespurse.players.repo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "player")
@Table(name = "player")
@AllArgsConstructor
@NoArgsConstructor
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String nationality;
    String club;
    Integer rating;
    Integer position; //cf, cmf, gk
    Integer cardType;  //featured, epic, highlight, standard, big time
    String tag;  //can be used as a custom system generated tag
    Integer version;  //can be used to track the version of the player
}
