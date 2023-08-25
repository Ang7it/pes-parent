package com.pespurse.team.repo;

import com.pespurse.team.repo.entity.TeamPlayersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamPlayersEntityRepository extends JpaRepository<TeamPlayersEntity, Long> {

    Optional<TeamPlayersEntity> findByTeamIdAndPlayerIdAndUserId(Long teamId, Long playerId, Long userId);

    Optional<List<TeamPlayersEntity>> findAllByTeamIdAndUserId(Long teamId, Long userId);
}
