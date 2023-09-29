package com.pespurse.tournament.repo;

import com.pespurse.tournament.repo.entity.TournamentInvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentInvitationRepository extends JpaRepository<TournamentInvitationEntity, Long> {
}
