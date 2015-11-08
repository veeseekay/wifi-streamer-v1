package com.guidestone.wifi.streamer.controllers;

import com.guidestone.wifi.streamer.entities.MediaEntity;
import com.guidestone.wifi.streamer.services.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//@EnableWebMvc
@RestController
@RequestMapping("/wifistreamer/v1/media")
@EnableAutoConfiguration
@ComponentScan
public class MediaController {

    private static final Logger LOG = LoggerFactory.getLogger(MediaController.class);
    @Autowired
    MediaService mediaService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMedia(@RequestHeader HttpHeaders headers,
            Pageable pageable, PagedResourcesAssembler assembler) throws Exception {

        Page<MediaEntity> users = mediaService.getMedia(pageable);
        return new ResponseEntity<>(assembler.toResource(users), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMedia(@RequestHeader HttpHeaders headers, @RequestBody List<MediaEntity> media) throws Exception {

        LOG.debug("Media to add {}", media);
        return new ResponseEntity<>(mediaService.addMedia(media), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMedium(@RequestHeader HttpHeaders headers, @PathVariable String id) throws Exception {
        return new ResponseEntity<>(mediaService.getMedium(id), HttpStatus.OK);
    }
}