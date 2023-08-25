package com.pespurse.team.web.dto;

import com.pespurse.players.web.dto.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddTeamPlayerRequestDTO {
   private List<SaveTeamPlayerDTO> players;
}
