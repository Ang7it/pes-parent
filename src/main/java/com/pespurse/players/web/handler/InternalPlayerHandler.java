package com.pespurse.players.web.handler;

import com.pespurse.global.exception.CustomException;
import com.pespurse.global.exception.errorCode.PlayerErrorCode;
import com.pespurse.global.response.Response;
import com.pespurse.players.repo.PlayerEntityRepository;
import com.pespurse.players.repo.entity.PlayerEntity;
import com.pespurse.players.web.dto.PlayerDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@AllArgsConstructor
@Service
@Slf4j
public class InternalPlayerHandler {

    private final PlayerEntityRepository playerEntityRepository;

    public Response<PlayerDTO> saveNewPlayer(PlayerDTO playerDTO) {
        log.info("In handle save new player {}", playerDTO);
        if(Objects.nonNull(playerDTO.getId())) {
            Optional<PlayerEntity> savedPlayer = playerEntityRepository.findById(playerDTO.getId());
            if(savedPlayer.isPresent()) {
                log.warn("Player already saved {}", playerDTO);
                throw new CustomException(PlayerErrorCode.PLAYER_ALREADY_SAVED);
            }
        }
        //save the new player
        PlayerEntity playerEntity = new PlayerEntity();
        BeanUtils.copyProperties(playerDTO, playerEntity);
        PlayerEntity savedPlayer = playerEntityRepository.save(playerEntity);
        BeanUtils.copyProperties(savedPlayer, playerDTO);
        return new Response<>(playerDTO);
    }

    public Response<PlayerDTO> getPlayerByName(String name) {
        log.info("In handle get player by name {}", name);
        //get the player from repository by name
        PlayerEntity playerByName = playerEntityRepository.findByName(name).orElseThrow(() -> {
            log.warn("Player not found by name {}", name);
            return new CustomException(PlayerErrorCode.PLAYER_NOT_FOUND);
        });
        PlayerDTO playerDTO = new PlayerDTO();
        BeanUtils.copyProperties(playerByName, playerDTO);
        return new Response<>(playerDTO);
    }

    public Response<PlayerDTO> getPlayerById(Long id) {
        log.info("In handle get player by id {}", id);
        //get the player from repository by id
        PlayerEntity playerById = playerEntityRepository.findById(id).orElseThrow(() -> {
            log.warn("Player not found by id {}", id);
            return new CustomException(PlayerErrorCode.PLAYER_NOT_FOUND);
        });
        PlayerDTO playerDTO = new PlayerDTO();
        BeanUtils.copyProperties(playerById, playerDTO);
        return new Response<>(playerDTO);

    }

    public Response<PlayerDTO> updatePlayerById(Long id, PlayerDTO playerDTO) {
        log.info("In handle update player by id {}", id);
        //first get the existing player by id
        PlayerEntity playerEntity = playerEntityRepository.findById(id).orElseThrow(() -> {
            log.warn("Player not found by id {}", id);
            return new CustomException(PlayerErrorCode.PLAYER_NOT_FOUND);
        });
        //copy the value to be updated and update.
        BeanUtils.copyProperties(playerDTO, playerEntity,"id");
        PlayerEntity updatedPlayer = playerEntityRepository.save(playerEntity);
        BeanUtils.copyProperties(updatedPlayer, playerDTO);
        return new Response<>(playerDTO);
    }
}
