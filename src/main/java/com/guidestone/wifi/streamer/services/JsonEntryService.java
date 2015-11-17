package com.guidestone.wifi.streamer.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guidestone.wifi.streamer.domain.MediaUpdates;
import com.guidestone.wifi.streamer.entities.MediaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by z013w8c on 11/14/15.
 */
@Service
public class JsonEntryService {

    private static final Logger LOG = LoggerFactory.getLogger(JsonEntryService.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    MediaService mediaService;

    @Value("${s3.bucket}")
    String bucket;

    @Value("${s3.apiKey}")
    String apiKey;

    @Value("${s3.url}")
    String url;

    @Value("${json.checkdays}")
    String checkdays;

    @Value("${json.version}")
    String version;

    @Async
    public void updateMediaJson() {

        LOG.info("Calling async update json");
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<MediaEntity> videos = mediaService.findByMediaCategory("videos");
            List<MediaEntity> audios = mediaService.findByMediaCategory("audios");
            List<MediaEntity> tv = mediaService.findByMediaCategory("tv");
            List<MediaEntity> movies = mediaService.findByMediaCategory("movies");

            MediaUpdates mediaUpdates = new MediaUpdates();
            mediaUpdates.setAudios(audios);
            mediaUpdates.setVideos(videos);
            mediaUpdates.setTv(tv);
            mediaUpdates.setMovies(movies);
            mediaUpdates.setCategories(Arrays.asList(new String[] {"videos", "audios", "books", "tv", "movies"}));

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            mediaUpdates.setLastUpdatedOn(dateFormat.format(date));
            mediaUpdates.setVersion(version);
            mediaUpdates.setCheckdays(checkdays);
            mediaUpdates.setS3bucket(url + bucket);

            File file = new File("./updates.json");
            mapper.writeValue(file, mediaUpdates);

            LOG.debug("Uploading a new object to S3 from a file\n");
            PutObjectResult result = amazonS3.putObject(new PutObjectRequest(
                    bucket, "updates.json", file));

            LOG.info(result.getETag());

        } catch (Exception e) {
            LOG.error("Exception in async json create", e);
        }
    }
}
