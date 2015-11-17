package com.guidestone.wifi.streamer.repositories;

import com.guidestone.wifi.streamer.entities.PiEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PiRepository extends JpaRepository<PiEntity, String> {

    PiEntity findByMacId(String macId);

    Page<PiEntity> findAll(Pageable pageable);
}
