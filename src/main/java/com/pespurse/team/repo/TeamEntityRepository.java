package com.pespurse.team.repo;

import com.pespurse.team.repo.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamEntityRepository extends JpaRepository<TeamEntity, Long> {

    Optional<TeamEntity> findByIdAndUserId(Long teamId, Long userId);
}
