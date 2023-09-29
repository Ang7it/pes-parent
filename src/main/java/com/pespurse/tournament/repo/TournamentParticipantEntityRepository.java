package com.pespurse.tournament.repo;

import com.pespurse.tournament.repo.entity.TournamentParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TournamentParticipantEntityRepository extends JpaRepository<TournamentParticipantEntity, Long> {

    @Query("select tp from tournamentParticipant tp where tp.parentLeagueId = :leagueId")
    List<TournamentParticipantEntity> getListOfParticipants(@Param("leagueId") Long leagueId);

    @Query("select count(1) from tournamentParticipant tp where tp.parentLeagueId = :leagueId")
    Integer countNumberOfParticipants(@Param("leagueId") Long leagueId);

    @Query("select tp from tournamentParticipant tp where tp.parentLeagueId = :leagueId and tp.userId = :userId")
    Optional<TournamentParticipantEntity> findByExistingParticipantById(@Param("leagueId") Long leagueId, Long userId);
}
