package com.guidestone.wifi.streamer.controllers;

import com.guidestone.wifi.streamer.domain.GraphData;
import com.guidestone.wifi.streamer.entities.AnalyticsEntity;
import com.guidestone.wifi.streamer.services.AnalyticsService;
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


//@EnableWebMvc
@RestController
@RequestMapping("/api/wifistreamer/v1/analytics")
@EnableAutoConfiguration
@ComponentScan
public class AnalyticsController {

    private static final Logger LOG = LoggerFactory.getLogger(AnalyticsController.class);
    @Autowired
    AnalyticsService analyticsService;

    @RequestMapping(value = "/media/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMediaAnalytics(@RequestHeader HttpHeaders headers, @PathVariable Integer id,
            Pageable pageable, PagedResourcesAssembler assembler) throws Exception {

        Page<AnalyticsEntity> analytics = analyticsService.getAnalyticsForMedia(id, pageable);
        return new ResponseEntity<>(assembler.toResource(analytics), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserAnalytics(@RequestHeader HttpHeaders headers, @PathVariable Integer id,
            Pageable pageable, PagedResourcesAssembler assembler) throws Exception {

        Page<AnalyticsEntity> analytics = analyticsService.getAnalyticsForUser(id, pageable);
        return new ResponseEntity<>(assembler.toResource(analytics), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMediaAnalytics(@RequestHeader HttpHeaders headers, @RequestBody AnalyticsEntity analytics) throws Exception {

        return new ResponseEntity<>(analyticsService.addAnalyticData(analytics), HttpStatus.OK);
    }

    @RequestMapping(value = "/categorycount", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String fetchMediaCategoryCount(@RequestHeader HttpHeaders headers) throws Exception {

        GraphData gd = analyticsService.fetchMediaCategoryCount();
        LOG.info("GraphData in category count {}", gd);
        return gd.toString();
    }

    @RequestMapping(value = "/topviewed", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String fetchTopViewedMedia(@RequestHeader HttpHeaders headers) throws Exception {

        GraphData gd = analyticsService.fetchTopViewedMedia();
        LOG.info("GraphData in top viewed media {}", gd);
        return gd.toString();
    }
}