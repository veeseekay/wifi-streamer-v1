package com.guidestone.wifi.streamer.repositories;

import com.guidestone.wifi.streamer.entities.MediaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<MediaEntity, Long> {

    MediaEntity findById(String id);

    Page<MediaEntity> findAll(Pageable pageable);

    List<MediaEntity> findByMediaCategory(String category);

}
