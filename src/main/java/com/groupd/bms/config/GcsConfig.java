package com.groupd.bms.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class GcsConfig {

    @Value("${gcs.credentials.location}")
    private String gcsCredentialsLocation;

    private final ResourceLoader resourceLoader;

    public GcsConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public Storage storage() throws IOException {
        Resource gcsCredentials = resourceLoader.getResource(gcsCredentialsLocation);
        InputStream credentialsStream = gcsCredentials.getInputStream();
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
        return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }
}
