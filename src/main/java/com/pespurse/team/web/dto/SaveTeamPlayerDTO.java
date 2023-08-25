package com.pespurse.team.web.dto;

import com.pespurse.players.repo.entity.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveTeamPlayerDTO {
    private Long playerId;
    private Position position;
    private boolean isSubstitute;
}
