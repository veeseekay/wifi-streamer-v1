package com.guidestone.wifi.streamer.services;

import com.guidestone.wifi.streamer.entities.MediaEntity;
import com.guidestone.wifi.streamer.exceptions.ExceptionType;
import com.guidestone.wifi.streamer.exceptions.StreamerException;
import com.guidestone.wifi.streamer.repositories.MediaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MediaService {

    private static final Logger LOG = LoggerFactory.getLogger(MediaService.class);

    @Autowired
    private MediaRepository mediaRepository;


    public Page<MediaEntity> getMedia(Pageable pageable) {
        return mediaRepository.findAll(pageable);
    }

    public Object addMedia(List<MediaEntity> media) {
        return mediaRepository.save(media);
    }


    public Object getMedium(String id) {
        MediaEntity media = mediaRepository.findById(id);
        if(media == null) {
            throw new StreamerException("Media not found").setType(ExceptionType.NOT_FOUND);
        }
        return media;
    }
}
