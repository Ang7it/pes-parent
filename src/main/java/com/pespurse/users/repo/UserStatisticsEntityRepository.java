package com.pespurse.users.repo;

import com.pespurse.users.repo.entity.UserStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatisticsEntityRepository extends JpaRepository<UserStatisticsEntity, Long> {
}
