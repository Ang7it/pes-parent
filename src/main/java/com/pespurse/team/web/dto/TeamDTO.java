package com.pespurse.team.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDTO {
    private Long id;
    private Long userId;
    private String name;
    private String formation;
    private Set<AddTeamPlayerResponseDTO> players;
}
