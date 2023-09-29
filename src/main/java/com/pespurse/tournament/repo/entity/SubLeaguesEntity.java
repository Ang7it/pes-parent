package com.pespurse.tournament.repo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity(name = "subLeague")
@Table(name = "sub_league")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubLeaguesEntity {
    @Id
    private Long id;
    private Long parentLeagueId;
    private String name;
    private LocalDateTime created;
}
