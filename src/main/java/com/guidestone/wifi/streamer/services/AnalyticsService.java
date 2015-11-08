package com.guidestone.wifi.streamer.services;

import com.guidestone.wifi.streamer.entities.AnalyticsEntity;
import com.guidestone.wifi.streamer.repositories.AnalyticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AnalyticsService {

    private static final Logger LOG = LoggerFactory.getLogger(AnalyticsService.class);

    @Autowired
    private AnalyticsRepository analyticsRepository;

    public Page<AnalyticsEntity> getAnalyticsForMedia(Integer mediaId, Pageable pageable) {
        return analyticsRepository.findByMediaId(mediaId, pageable);
    }

    public Page<AnalyticsEntity> getAnalyticsForUser(Integer userId, Pageable pageable) {
        return analyticsRepository.findByUserId(userId, pageable);
    }

    public Object addAnalytics(List<AnalyticsEntity> analytics) {
        return analyticsRepository.save(analytics);
    }
}