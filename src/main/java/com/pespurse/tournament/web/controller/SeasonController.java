package com.pespurse.tournament.web.controller;

import com.pespurse.global.response.Response;
import com.pespurse.tournament.web.dto.SaveLeagueDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tournament/season/v1/")
@AllArgsConstructor
public class SeasonController {

//    @PostMapping(value = "create")
//    private Response<SaveLeagueDTO> createNewSeason(@RequestBody SaveSeasonDTO saveSeasonDTO, @RequestHeader Long userId) {
//        return tournamentHandler.createNewSeason(saveSeasonDTO, userId);
//    }
//    @PostMapping(value = "createSeasonR")
//    private Response<SaveLeagueDTO> createNewSeasonWithRelegation(@RequestBody SaveSeasonWithRelegationDTO saveSeasonWithRelegationDTO, @RequestHeader Long userId) {
//        return tournamentHandler.createNewSeasonWithRelegation(saveSeasonWithRelegationDTO, userId);
//    }
}
