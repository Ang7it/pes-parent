package com.pespurse.players.repo;

import com.pespurse.players.repo.entity.PlayerMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerMetadataEntityRepository extends JpaRepository<PlayerMetadataEntity, Long> {
}
