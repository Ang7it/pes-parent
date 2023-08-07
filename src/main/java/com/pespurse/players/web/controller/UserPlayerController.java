package com.pespurse.players.web.controller;

import com.pespurse.players.web.dto.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@NoArgsConstructor
@RestController
@RequestMapping(value = "/players/v1/")
public class UserPlayerController {

    public void addPlayerToTeam(PlayerDTO playerDTO){}
    public void addPlayersToTeam(List<PlayerDTO> playerDTOs) {}
    public void removePlayerFromTeam(PlayerDTO playerDTO) {}
    public void removePlayersFromTeam(List<PlayerDTO> playerDTOs) {}

}
