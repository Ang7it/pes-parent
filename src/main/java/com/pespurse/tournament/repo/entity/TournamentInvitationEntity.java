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
@Entity(name = "tournamentInvitation")
@Table(name = "tournament_invitation")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TournamentInvitationEntity {
    @Id
    private Long id;
    private Long invitationFrom; //user sending the invitation request
    private Long invitationTo; //user to whom the invitation request is being sent
    private Long tournamentId;
    private Integer tournamentType;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Integer invitationStatus; //refer TournamentInvitationStatus enum
}
