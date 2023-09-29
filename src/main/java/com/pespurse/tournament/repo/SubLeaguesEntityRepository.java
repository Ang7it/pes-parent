package com.pespurse.tournament.repo;

import com.pespurse.tournament.repo.entity.SubLeaguesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubLeaguesEntityRepository extends JpaRepository<SubLeaguesEntity, Long> {
}
