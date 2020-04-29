package pl.reszy.filestorage.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Value("${fileStorage.storage_directory}")
    private String storageDirectory;

    @Bean
    public void createStorage() {
        File file = new File(storageDirectory);
        if(!file.exists()) {
            throw new IllegalStateException("storage_directory not found");
        }
        if(!file.isDirectory()) {
            throw new IllegalStateException("storage_directory is not directory");
        }
        File storageFilesDirectory = new File(storageDirectory + "/files");
        if(!storageFilesDirectory.mkdir()) {
            throw new IllegalStateException("cannot write in storage_directory");
        }
    }

}