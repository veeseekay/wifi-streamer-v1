package com.guidestone.wifi.streamer.services;

import com.guidestone.wifi.streamer.entities.UserEntity;
import com.guidestone.wifi.streamer.exceptions.ExceptionType;
import com.guidestone.wifi.streamer.exceptions.StreamerException;
import com.guidestone.wifi.streamer.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsersService {

    private static final Logger LOG = LoggerFactory.getLogger(UsersService.class);

    @Autowired
    private UserRepository userRepository;


    public Page<UserEntity> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Object addUsers(List<UserEntity> users) {
        return userRepository.save(users);
    }


    public Object getUser(String id) {
        UserEntity user = userRepository.findById(id);
        if(user == null) {
            throw new StreamerException("User not found").setType(ExceptionType.NOT_FOUND);
        }
        return user;
    }
}
