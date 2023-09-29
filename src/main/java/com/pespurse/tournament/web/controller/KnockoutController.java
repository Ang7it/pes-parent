package com.pespurse.tournament.web.controller;

import com.pespurse.global.response.Response;
import com.pespurse.tournament.web.dto.SaveLeagueDTO;
import com.pespurse.tournament.web.handler.KnockoutHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tournament/knockout/v1/")
@AllArgsConstructor
public class KnockoutController {

    private final KnockoutHandler knockoutHandler;

//    @PostMapping(value = "create")
//    private Response<SaveLeagueDTO> createNewKnockout(@RequestBody SaveKnockoutDTO saveKnockoutDTO, @RequestHeader Long userId) {
//        return tournamentHandler.createNewKnockoutCompetetion(saveKnockoutDTO, userId);
//    }
}
