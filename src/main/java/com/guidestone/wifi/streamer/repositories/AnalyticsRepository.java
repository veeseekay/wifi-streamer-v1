package com.guidestone.wifi.streamer.repositories;

import com.guidestone.wifi.streamer.entities.AnalyticsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsRepository extends JpaRepository<AnalyticsEntity, Long> {

    //AnalyticsEntity findById(String id);

    Page<AnalyticsEntity> findAll(Pageable pageable);

    Page<AnalyticsEntity> findByMediaId(Integer mediaId, Pageable pageable);

    Page<AnalyticsEntity> findByUserId(Integer userId, Pageable pageable);

}
