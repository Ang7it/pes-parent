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
@Entity(name = "knockoutTournament")
@Table(name = "knockout_tournament")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KnockoutEntity {

    @Id
    private Long id;
    private String name;
    private Long adminUser;
    private Integer tournamentStatus;
    private Integer tournamentType; //refer tournament type enum
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private Integer participantCount;
    private String tournamentLogo;
    private boolean isPublic;
    private int numOfGroups;
    private int groupParticipantCount; //number of participants in each group
}
