package com.pespurse.team.web.controller;

import com.pespurse.global.response.Response;
import com.pespurse.team.web.dto.AddTeamPlayerRequestDTO;
import com.pespurse.team.web.dto.AddTeamPlayerResponseDTO;
import com.pespurse.team.web.handler.TeamPlayersHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/team/players/v1/")
public class TeamPlayersController {

    private final TeamPlayersHandler teamPlayersHandler;

    @GetMapping(value = "/getAllPlayers")
    public Response<AddTeamPlayerResponseDTO> getAllPlayersFromTeam(@RequestParam Long teamId, @RequestHeader Long userId) {
        return teamPlayersHandler.getAllPlayers(teamId, userId);
    }

    @PostMapping(value = "/addPlayer")
    public Response<AddTeamPlayerResponseDTO> addPlayersToTeam(@RequestParam Long teamId, @RequestBody AddTeamPlayerRequestDTO addTeamPlayerRequestDTO, @RequestHeader Long userId) {
        return teamPlayersHandler.addPlayerToTeam(teamId, addTeamPlayerRequestDTO, userId);
    }

    public void removePlayerFromTeam() {}

}
