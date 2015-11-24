package com.guidestone.wifi.streamer.configurations;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@Configuration
@EnableAutoConfiguration(exclude = {JacksonAutoConfiguration.class})
public class StreamerConfiguration {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(StreamerConfiguration.class);

    private @Value("${s3.apiKey}")
    String apiKey;

    private @Value("${s3.apiKeySecret}")
    String apiKeySecret;

    @Bean
    public AmazonS3 amazonS3() {
        return new AmazonS3Client(new BasicAWSCredentials(apiKey, apiKeySecret));
    }
}
