package com.example.momsytdownloaderserver.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@PropertySource("classpath:env.properties")
public class S3Config {

    @Value("${ACCESS}")
    private String accessKey;

    @Value("${PASS}")
    private String secretKey;

    @Value("${BUCKET}")
    private String bucket;

    private final String region = "ap-northeast-2";

    private final String directory = "mp3/";

    @Value("${URL}")
    private String prefixUrl;

    public S3Config() {}

    @Bean
    public AmazonS3 amazonS3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();

        return s3Client;
    }

    public String getBucket() {
        return bucket;
    }

    public String getPrefixUrl() {
        return prefixUrl;
    }

    public String getDirectory() {
        return directory;
    }
}
