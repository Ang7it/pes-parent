package com.pespurse.team.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddTeamPlayerResponseDTO {
    private Long teamId;
    private List<SaveTeamPlayerResponseDTO> players;
}
