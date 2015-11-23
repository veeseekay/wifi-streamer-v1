package com.guidestone.wifi.streamer.repositories;

import com.guidestone.wifi.streamer.entities.AnalyticsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnalyticsRepository extends JpaRepository<AnalyticsEntity, Long> {

    Page<AnalyticsEntity> findAll(Pageable pageable);

    Page<AnalyticsEntity> findByMediaId(Integer mediaId, Pageable pageable);

    Page<AnalyticsEntity> findByUserId(Integer userId, Pageable pageable);

    AnalyticsEntity findByUserIdAndMediaId(Integer userId, Integer mediaId);

    @Query(value = "select  count(m.id), m.media_category from media m GROUP BY m.media_category", nativeQuery = true)
    List<Object[]> fetchMediaCategoryCount();

    @Query(value = "select media.title as title, analytics.views as views from media media, " +
            "analytics analytics where media.id=analytics.media_id order by views desc limit 5;", nativeQuery = true)
    List<Object[]> fetchTopViewedMedia();
}
