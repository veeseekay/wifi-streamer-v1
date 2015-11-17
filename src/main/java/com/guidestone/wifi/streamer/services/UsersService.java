package com.guidestone.wifi.streamer.services;

import com.guidestone.wifi.streamer.entities.UserEntity;
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

    private void mergeAttributes(UserEntity user, UserEntity userEntity) {
        if(userEntity != null) {
            if(user.getAppUsageDuration() == null) {
                user.setAppUsageDuration(userEntity.getAppUsageDuration());
            } else {
                user.setAppUsageDuration(userEntity.getAppUsageDuration() + user.getAppUsageDuration());
            }
        }
        if(user.getAge() == null) user.setAge(userEntity.getAge());
        if(user.getUserName() == null) user.setUserName(userEntity.getUserName());
        if(user.getPasswd() == null) user.setPasswd(userEntity.getPasswd());
        if(user.getType() == null) user.setType(userEntity.getType());
        if(user.getCreated() == null) user.setCreated(userEntity.getCreated());
    }

    public Object addUsers(List<UserEntity> users) {

        for(UserEntity user : users) {
            UserEntity userEntity = userRepository.findById(user.getId());
            mergeAttributes(user, userEntity);
            LOG.info("User {}", user);
        }

        return userRepository.save(users);
    }

    public Object addUser(UserEntity user) {

        UserEntity userEntity = userRepository.findById(user.getId());
        mergeAttributes(user, userEntity);

        return userRepository.save(user);
    }

    public Page<UserEntity> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    public Object getUser(Double id) {
        return userRepository.findById(id);
    }
}
