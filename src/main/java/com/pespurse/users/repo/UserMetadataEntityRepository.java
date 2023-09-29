package com.pespurse.users.repo;

import com.pespurse.users.repo.entity.UserMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMetadataEntityRepository extends JpaRepository<UserMetadataEntity, Long> {
}
