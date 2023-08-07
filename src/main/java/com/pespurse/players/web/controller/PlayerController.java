package com.pespurse.players.web.controller;

import com.pespurse.global.response.Response;
import com.pespurse.players.web.dto.PlayerDTO;
import com.pespurse.players.web.handler.InternalPlayerHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/internal/players/v1/")
public class PlayerController {

    private final InternalPlayerHandler internalPlayerHandler;

    @PostMapping(value = "save")
    public Response<PlayerDTO> saveNewPlayer(@RequestBody final PlayerDTO playerDTO){
        return internalPlayerHandler.saveNewPlayer(playerDTO);
    }

    @GetMapping(value = "getByName")
    public Response<PlayerDTO> getPlayerByName(@RequestParam String name){
        return internalPlayerHandler.getPlayerByName(name);
    }

    @GetMapping(value = "getById")
    public Response<PlayerDTO> getPlayerById(@RequestParam Long id){
        return internalPlayerHandler.getPlayerById(id);
    }

    @PutMapping(value = "update")
    public Response<PlayerDTO> updatePlayerById(@RequestParam Long id,@RequestBody PlayerDTO playerDTO){
        return internalPlayerHandler.updatePlayerById(id, playerDTO);
    }
    public void deletePlayer(@RequestBody Long id){};
}
