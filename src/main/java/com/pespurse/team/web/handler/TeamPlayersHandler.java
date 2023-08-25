package com.pespurse.team.web.handler;

import com.pespurse.global.exception.CustomException;
import com.pespurse.global.exception.errorCode.TeamErrorCode;
import com.pespurse.global.response.Response;
import com.pespurse.players.web.dto.PlayerDTO;
import com.pespurse.players.web.handler.InternalPlayerHandler;
import com.pespurse.team.repo.TeamEntityRepository;
import com.pespurse.team.repo.TeamPlayersEntityRepository;
import com.pespurse.team.repo.entity.TeamEntity;
import com.pespurse.team.repo.entity.TeamPlayersEntity;
import com.pespurse.team.web.dto.AddTeamPlayerRequestDTO;
import com.pespurse.team.web.dto.AddTeamPlayerResponseDTO;
import com.pespurse.team.web.dto.SaveTeamPlayerResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TeamPlayersHandler {

    private final TeamPlayersEntityRepository teamPlayersEntityRepository;
    private final InternalPlayerHandler internalPlayerHandler;
    private final TeamEntityRepository teamEntityRepository;

    /**
     * First we check whether the team is present by using the teamID and userID.
     * Then we iterate over the list of playerID passed in the body and check if the playerID exists.
     * We then map the playerID,teamID, and userID and save it in our database.
     * TODO:Need to improve the logic
     * @param teamId
     * @param addTeamPlayerRequestDTO
     * @param userId
     * @return
     */
    public Response<AddTeamPlayerResponseDTO> addPlayerToTeam(Long teamId, AddTeamPlayerRequestDTO addTeamPlayerRequestDTO, Long userId) {
        TeamEntity savedUserTeam = teamEntityRepository.findByIdAndUserId(teamId, userId).orElseThrow(() -> {
            log.warn("No team {} found for user id {}", teamId, userId);
            return new CustomException(TeamErrorCode.TEAM_NOT_FOUND);
        });
        List<SaveTeamPlayerResponseDTO> savedPlayers = new ArrayList<>();
        List<TeamPlayersEntity> newPlayers = new ArrayList<>();
        addTeamPlayerRequestDTO.getPlayers().forEach(playerDTO -> {
            Response<PlayerDTO> playerById = internalPlayerHandler.getPlayerById(playerDTO.getPlayerId());
            if(Objects.nonNull(playerById)) {
                newPlayers.add(TeamPlayersEntity.builder()
                        .teamId(savedUserTeam.getId())
                        .playerId(playerById.getData().getId())
                        .playerPosition(playerById.getData().getPosition().getPositionCode())
                        .isSubstitute(playerDTO.isSubstitute())
                        .userId(userId)
                        .build());
                savedPlayers.add(SaveTeamPlayerResponseDTO.builder()
                                .playerDTO(playerById.getData())
                                .isSubstitute(playerDTO.isSubstitute())
                        .build());
            }
        });
        teamPlayersEntityRepository.saveAll(newPlayers);
        return new Response<>(AddTeamPlayerResponseDTO.builder()
                .teamId(teamId)
                .players(savedPlayers)
                .build());
    }

    /**
     * First we find the list of playerID by using teamID and userID.
     * We then iterate over the list of playerID and find the corresponding playerDTO and return it as a list.
     *
     * @param teamId
     * @param userId
     * @return
     */
    public Response<AddTeamPlayerResponseDTO> getAllPlayers(Long teamId, Long userId) {
        List<TeamPlayersEntity> savedTeamPlayers = teamPlayersEntityRepository.findAllByTeamIdAndUserId(teamId, userId).orElseThrow(() -> {
            log.warn("No team {} found for user id {}", teamId, userId);
            return new CustomException(TeamErrorCode.TEAM_NOT_FOUND);
        });
        List<SaveTeamPlayerResponseDTO> teamPlayers = savedTeamPlayers.stream()
                .map(player -> {
                    SaveTeamPlayerResponseDTO saveTeamPlayerResponseDTO = new SaveTeamPlayerResponseDTO();
                    Response<PlayerDTO> playerDTO = internalPlayerHandler.getPlayerById(player.getId());
                    saveTeamPlayerResponseDTO.setPlayerDTO(playerDTO.getData());
                    saveTeamPlayerResponseDTO.setSubstitute(player.isSubstitute());
                    return saveTeamPlayerResponseDTO;
                })
                .collect(Collectors.toList());
        return new Response<>(AddTeamPlayerResponseDTO.builder()
                .teamId(teamId)
                .players(teamPlayers)
                .build());
    }
}
