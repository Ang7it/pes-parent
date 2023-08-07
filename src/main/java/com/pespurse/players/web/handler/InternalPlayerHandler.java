package com.pespurse.players.web.handler;

import com.pespurse.global.exception.CustomException;
import com.pespurse.global.exception.SystemFailureException;
import com.pespurse.global.exception.errorCode.PlayerErrorCode;
import com.pespurse.global.exception.errorCode.SystemErrorCode;
import com.pespurse.global.response.Response;
import com.pespurse.players.repo.PlayerEntityRepository;
import com.pespurse.players.repo.entity.PlayerEntity;
import com.pespurse.players.web.dto.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
@Slf4j
public class InternalPlayerHandler {

    private final PlayerEntityRepository playerEntityRepository;

    public Response<PlayerDTO> saveNewPlayer(PlayerDTO playerDTO) {
        try{
            log.info("Saving new player {}", playerDTO);
            //add pre-checks
            //save the new player
            PlayerEntity playerEntity = new PlayerEntity();
            BeanUtils.copyProperties(playerDTO, playerEntity);
            PlayerEntity savedPlayer = playerEntityRepository.save(playerEntity);
            PlayerDTO savedPlayerDTO = new PlayerDTO();
            BeanUtils.copyProperties(savedPlayer, savedPlayerDTO);
            return new Response<>(savedPlayerDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public Response<PlayerDTO> getPlayerByName(String name) {
        //get the player from repository by name
        PlayerEntity playerByName = playerEntityRepository.findByName(name).orElseThrow(() -> new CustomException(PlayerErrorCode.PLAYER_NOT_FOUND));
        PlayerDTO playerDTO = new PlayerDTO();
        BeanUtils.copyProperties(playerByName, playerDTO);
        return new Response<>(playerDTO);
    }

    public Response<PlayerDTO> getPlayerById(Long id) {
        //get the player from repository by id
        PlayerEntity playerById = playerEntityRepository.findById(id).orElseThrow(() -> new CustomException(PlayerErrorCode.PLAYER_NOT_FOUND));
        PlayerDTO playerDTO = new PlayerDTO();
        BeanUtils.copyProperties(playerById, playerDTO);
        return new Response<>(playerDTO);

    }

    public Response<PlayerDTO> updatePlayerById(Long id, PlayerDTO playerDTO) {
        //first get the existing player by id
        PlayerEntity playerEntity = playerEntityRepository.findById(id).orElseThrow(() -> new CustomException(PlayerErrorCode.PLAYER_NOT_FOUND));
        //copy the value to be updated and update.
        BeanUtils.copyProperties(playerDTO, playerEntity,"id");
        PlayerEntity updatedPlayer = playerEntityRepository.save(playerEntity);
        PlayerDTO updatedPlayerDTO = new PlayerDTO();
        BeanUtils.copyProperties(updatedPlayer, updatedPlayerDTO);
        return new Response<>(updatedPlayerDTO);
    }
}
