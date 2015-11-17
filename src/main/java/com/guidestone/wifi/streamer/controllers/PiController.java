package com.guidestone.wifi.streamer.controllers;

import com.guidestone.wifi.streamer.entities.PiEntity;
import com.guidestone.wifi.streamer.services.PiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
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
@RequestMapping("/api/wifistreamer/v1/pi")
@EnableAutoConfiguration
@ComponentScan
public class PiController {

    private static final Logger LOG = LoggerFactory.getLogger(PiController.class);
    @Autowired
    PiService piService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addPis(@RequestHeader HttpHeaders headers, @RequestBody List<PiEntity> pis) throws Exception {

        LOG.debug("Pi to add {}", pis);
        return new ResponseEntity<>(piService.addPis(pis), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPis(@RequestHeader HttpHeaders headers) throws Exception {
        return new ResponseEntity<>(piService.getPis(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPi(@RequestHeader HttpHeaders headers, @PathVariable String id) throws Exception {
        return new ResponseEntity<>(piService.getPi(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addPi(@RequestHeader HttpHeaders headers, @RequestBody PiEntity pi) throws Exception {
        return new ResponseEntity<>(piService.addPi(pi), HttpStatus.OK);
    }
}