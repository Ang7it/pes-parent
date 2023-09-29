package com.pespurse.tournament.web.controller;

import com.pespurse.global.response.Response;
import com.pespurse.tournament.web.dto.SaveLeagueDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tournament/seasonKnockout/v1/")
@AllArgsConstructor
public class LeagueKnockoutController {

//    @PostMapping(value = "create")
//    private Response<SaveLeagueDTO> createNewLeaguePlusKnockout(@RequestBody SaveLeaguePlusKnockoutDTO saveLeaguePlusKnockoutDTO, @RequestHeader Long userId) {
//        return tournamentHandler.createNewLeaguePlusKnockout(saveLeaguePlusKnockoutDTO, userId);
//    }

}
