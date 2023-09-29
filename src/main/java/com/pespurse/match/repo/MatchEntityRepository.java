package com.pespurse.match.repo;

import com.pespurse.match.repo.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchEntityRepository extends JpaRepository<MatchEntity, Long> {

    @Query("select m from match m where m.parentLeagueId = :leagueId")
    List<MatchEntity> getAllLeagueFixtures(Long leagueId);

    @Query("select m from match m where m.childLeagueId=:currentSeasonId and m.parentLeagueId = :leagueId")
    List<MatchEntity> getLeagueFixturesForCurrentSeason(@Param("leagueId") Long leagueId, @Param("currentSeasonId") Long currentSeasonId);
}
