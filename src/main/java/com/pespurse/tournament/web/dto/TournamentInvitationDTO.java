package com.pespurse.tournament.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TournamentInvitationDTO {
    private Long id;
    private Long invitationFrom; //user sending the invitation request
    private Long invitationTo; //user to whom the invitation request is being sent
    private Long tournamentId;
    private Integer tournamentType;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Integer invitationStatus; //refer TournamentInvitationStatus enum
}
