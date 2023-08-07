package com.pespurse.players.repo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "player")
@Table(name = "player")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String nationality;
    String club;
}
