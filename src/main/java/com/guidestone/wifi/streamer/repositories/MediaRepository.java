package com.guidestone.wifi.streamer.repositories;

import com.guidestone.wifi.streamer.entities.MediaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<MediaEntity, Long> {

    MediaEntity findById(String id);

    Page<MediaEntity> findAll(Pageable pageable);

}
