package com.pespurse.team.web.handler;

import com.pespurse.global.exception.CustomException;
import com.pespurse.global.exception.errorCode.TeamErrorCode;
import com.pespurse.global.response.Response;
import com.pespurse.team.repo.TeamEntityRepository;
import com.pespurse.team.repo.TeamPlayersEntityRepository;
import com.pespurse.team.repo.entity.TeamEntity;
import com.pespurse.team.repo.entity.TeamPlayersEntity;
import com.pespurse.team.web.dto.TeamDTO;
import com.pespurse.team.web.dto.AddTeamPlayerResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class TeamHandler {
    private final TeamEntityRepository teamEntityRepository;
    private final TeamPlayersEntityRepository teamPlayersEntityRepository;

    public Response<TeamDTO> createNewTeam(TeamDTO teamDTO, Long userId) {
        log.info("In handle create new team");
        if(Objects.nonNull(teamDTO.getId())) {
            Optional<TeamEntity> savedTeam = teamEntityRepository.findById(teamDTO.getId());
            if(savedTeam.isPresent()) {
                log.warn("Team already created {}", teamDTO.getId());
                throw  new CustomException(TeamErrorCode.TEAM_ALREADY_SAVED);
            }
        }
        TeamEntity teamEntity = new TeamEntity();
        BeanUtils.copyProperties(teamDTO, teamEntity, "id");
        teamEntity.setUserId(userId);
        log.info("Creating new team {} for user {}", teamDTO, userId);
        teamEntityRepository.save(teamEntity);
        BeanUtils.copyProperties(teamEntity, teamDTO);
        return new Response<>(teamDTO);
    }


    public Response<TeamDTO> getTeamById(Long teamId, Long userId) {
        log.info("In handle get team by id {} for user {}", teamId, userId);
        TeamEntity teamEntity = teamEntityRepository.findByIdAndUserId(teamId, userId).orElseThrow(() -> {
            log.warn("No team found {} for user {}", teamId, userId);
            return new CustomException(TeamErrorCode.TEAM_NOT_FOUND);
        });
        TeamDTO teamDTO = new TeamDTO();
        BeanUtils.copyProperties(teamEntity, teamDTO);
        Optional<List<TeamPlayersEntity>> teamPlayers = teamPlayersEntityRepository.findAllByTeamIdAndUserId(teamId, userId);
        Set<AddTeamPlayerResponseDTO> players = new HashSet<>();
        teamPlayers.ifPresent(teamPlayersEntities -> teamPlayersEntities.stream()
                .map(p -> {
                    AddTeamPlayerResponseDTO teamPlayersDTO = new AddTeamPlayerResponseDTO();
                    BeanUtils.copyProperties(p, teamPlayersDTO);
                    return teamPlayersDTO;
                }).forEach(players::add));
        teamDTO.setPlayers(players);
        return new Response<>(teamDTO);
    }


    public Response<TeamDTO> updateTeam(Long teamId, TeamDTO teamDTO, Long userId) {
        log.info("In handle update team by id {} for user {}", teamId, userId);
        TeamEntity teamEntity = teamEntityRepository.findByIdAndUserId(teamId, userId).orElseThrow(() -> {
            log.warn("No team found {} for user {}", teamId, userId);
            return new CustomException(TeamErrorCode.TEAM_NOT_FOUND);
        });
        BeanUtils.copyProperties(teamDTO, teamEntity);
        TeamEntity updatedTeam = teamEntityRepository.save(teamEntity);
        BeanUtils.copyProperties(updatedTeam, teamDTO);
        return new Response<>(teamDTO);
    }
}
