package com.pespurse.users.repo;

import com.pespurse.users.repo.entity.UserStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStatisticsEntityRepository extends JpaRepository<UserStatisticsEntity, Long> {
    Optional<UserStatisticsEntity> findByUserId(Long playerId);

    @Query(value = "select * from user_statistics where user_id in (:userIds)", nativeQuery = true)
    List<UserStatisticsEntity> findUserStatistics(@Param("userIds") List<Long> userIds);
}
