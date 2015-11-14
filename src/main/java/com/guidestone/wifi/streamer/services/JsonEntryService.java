package com.guidestone.wifi.streamer.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guidestone.wifi.streamer.entities.MediaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
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
    private String bucket;

    private @Value("${s3.apiKey}")
    String apiKey;

    @Async
    public void updateMediaJson() {

        LOG.info("Here in async");
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<MediaEntity> media = mediaService.getMedia();
            LOG.debug("size {}", media.size());
            File file = new File("./media.json");
            mapper.writeValue(file, media);

            LOG.debug("Uploading a new object to S3 from a file\n");
            PutObjectResult result = amazonS3.putObject(new PutObjectRequest(
                    bucket, "media.json", file));

            LOG.info(result.getETag());

        } catch (Exception e) {
            LOG.error("Exception in async json create", e);
        }
    }
}
