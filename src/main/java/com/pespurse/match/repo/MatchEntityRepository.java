package com.pespurse.match.repo;

import com.pespurse.match.repo.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchEntityRepository extends JpaRepository<MatchEntity, Long> {
}
