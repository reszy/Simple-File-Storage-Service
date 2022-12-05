package pl.reszy.filestorage.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Paths;

@Slf4j
@Configuration
public class StorageConfig {

    @Bean
    public String storageDirectory(
            @Value("${fileStorage.storage_directory}") String storageDirectory,
            @Value("${fileStorage.create_storage:true}") boolean create
    ) {
        String absolutePath = Paths.get(storageDirectory).toAbsolutePath().toString();
        if (storageDirectory.equals("tmpFileStorage")) {
            File file = new File(storageDirectory);
            if(create) {
                //noinspection ResultOfMethodCallIgnored
                file.mkdir();
            }
            log.warn("Using temporary file in project/executable directory");
        } else if (!storageDirectory.equals(absolutePath)) {
            throw new IllegalStateException("storage_directory is not absolute path");
        }
        if(create) {
            createStorage(absolutePath);
        }
        return absolutePath;
    }

    public void createStorage(String storageDirectory) {
        File file = new File(storageDirectory);
        if (!file.exists()) {
            throw new IllegalStateException("storage_directory not found");
        }
        if (!file.isDirectory()) {
            throw new IllegalStateException("storage_directory is not directory");
        }
        File storageFilesDirectory = new File(storageDirectory + "/files");
        if (!storageFilesDirectory.mkdir() && !storageFilesDirectory.canWrite()) {
            throw new IllegalStateException("cannot write in storage_directory");
        }
    }

    @Bean
    @ConfigurationProperties
    public DataSource dataSource(
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String pass,
            @Value("${spring.datasource.driverClassName}") String driver,
            @Value("${spring.datasource.url}") String url,
            String storageDirectory
    ) {
        if(url.isEmpty()) {
            url = "jdbc:h2:file:" + storageDirectory + '/' + "storedFiles";
        }

        return DataSourceBuilder.create()
                .username(username)
                .password(pass)
                .url(url)
                .driverClassName(driver)
                .build();
    }
}