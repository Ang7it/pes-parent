package com.pespurse.tournament.repo;

import com.pespurse.tournament.repo.entity.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueEntityRepository extends JpaRepository<LeagueEntity, Long> {

    @Modifying
    @Query("update leagueTournament lt set lt.currentSeasonId = :currentSeasonId where lt.id = :leagueId")
    void updateLeagueCurrentSeason(@Param("currentSeasonId") Long currentSeasonId, @Param("leagueId") Long leagueId);
}
