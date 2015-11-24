package com.guidestone.wifi.streamer.repositories;

import com.guidestone.wifi.streamer.entities.PiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PiRepository extends JpaRepository<PiEntity, String> {

    PiEntity findByMacId(String macId);

    List<PiEntity> findAll();
}
