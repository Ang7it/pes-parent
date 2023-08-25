package com.pespurse.team.web.controller;

import com.pespurse.global.response.Response;
import com.pespurse.players.web.dto.PlayerDTO;
import com.pespurse.team.web.dto.TeamDTO;
import com.pespurse.team.web.handler.TeamHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/team/v1/")
public class TeamController {

    private final TeamHandler teamHandler;
    @PostMapping(value = "create")
    public Response<TeamDTO> createNewTeam(@RequestBody TeamDTO teamDTO, @RequestHeader Long userId) {
        return teamHandler.createNewTeam(teamDTO, userId);
    }

    @GetMapping(value = "getById")
    public Response<TeamDTO> getTeamById(@RequestParam Long teamId, @RequestHeader Long userId) {
        return teamHandler.getTeamById(teamId, userId);
    }

    @PutMapping(value = "update/{teamId}")
    public Response<TeamDTO> updateTeam(@PathVariable Long teamId, @RequestBody TeamDTO teamDTO, @RequestHeader Long userId) {
        return teamHandler.updateTeam(teamId, teamDTO, userId);
    }

    public void deleteTeam(){}
}
