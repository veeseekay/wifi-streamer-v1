package com.guidestone.wifi.streamer.configurations;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration(exclude = {JacksonAutoConfiguration.class})
public class StreamerConfiguration {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(StreamerConfiguration.class);
    @Value("${http.read.timeout.ms:20000}")
    String readTimeout;

    @Value("${http.connection.timeout.ms:20000}")
    String connectionTimeout;

    @Value("${http.Pool.MinSize:100}")
    int minPoolSize;

    @Value("${http.Pool.MaxSize:200}")
    int maxPoolSize;

    @Value("${useHttpClientPool:false}")
    boolean useHttpClientPool;

    private @Value("${s3.apiKey}")
    String apiKey;
    private @Value("${s3.apiKeySecret}")
    String apiKeySecret;


    @Bean
    public AmazonS3 amazonS3() {
        return new AmazonS3Client(new BasicAWSCredentials(apiKey, apiKeySecret));
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        HttpComponentsClientHttpRequestFactory requestFactory =
                (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();

        if (useHttpClientPool) {
	    LOG.debug("Using HTTP Client Pool");
            PoolingHttpClientConnectionManager connectionMgr = new PoolingHttpClientConnectionManager();
            connectionMgr.setDefaultMaxPerRoute(minPoolSize);
            connectionMgr.setMaxTotal(maxPoolSize);
            HttpClient client = HttpClientBuilder.create().setConnectionManager(connectionMgr).build();
            requestFactory.setHttpClient(client);
        }

        requestFactory.setConnectTimeout(Integer.valueOf(readTimeout));
        requestFactory.setReadTimeout(Integer.valueOf(connectionTimeout));

        return restTemplate;
    }
}
