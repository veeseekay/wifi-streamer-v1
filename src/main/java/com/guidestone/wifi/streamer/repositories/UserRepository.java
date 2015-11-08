package com.guidestone.wifi.streamer.repositories;

import com.guidestone.wifi.streamer.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findById(String id);

    Page<UserEntity> findAll(Pageable pageable);

}
