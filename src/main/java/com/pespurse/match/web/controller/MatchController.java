package com.pespurse.match.web.controller;

import com.pespurse.global.response.Response;
import com.pespurse.match.web.dto.SaveMatchDTO;
import com.pespurse.match.web.handler.MatchHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/match/v1/")
@AllArgsConstructor
public class MatchController {

    private final MatchHandler matchHandler;
    @PostMapping(value = "create")
    private Response<SaveMatchDTO> createNewMatch(@RequestBody SaveMatchDTO saveMatchDTO) {
        return matchHandler.createNewMatch(saveMatchDTO);
    }
    @PutMapping(value = "update/{matchId}")
    private Response<SaveMatchDTO> updateMatch(@RequestBody SaveMatchDTO saveMatchDTO, @PathVariable Long matchId) {
        return matchHandler.updateMatch(saveMatchDTO, matchId);
    }

    @GetMapping(value = "getById/{matchId}")
    private Response<SaveMatchDTO> getMatch(@PathVariable Long matchId) {
        return matchHandler.getMatchById(matchId);
    }
    private void deleteMatch(){}

}
