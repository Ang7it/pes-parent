package com.pespurse.team.repo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "team")
@Table(name = "team")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamEntity {
    @Id
    private Long id;
    private Long userId;
    private String name;
    private String formation;
}
