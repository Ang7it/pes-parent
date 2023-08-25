package com.pespurse.team.web.dto;

import com.pespurse.players.web.dto.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveTeamPlayerResponseDTO {
    private PlayerDTO playerDTO;
    private boolean isSubstitute;
}
