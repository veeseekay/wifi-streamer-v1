package com.guidestone.wifi.streamer.repositories;

import com.guidestone.wifi.streamer.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
