package pl.reszy.filestorage.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileRepository {

    private final String storageDirectory;

    public long SaveFile(MultipartFile file, String relativePath) throws IOException {
        java.io.File fileInStorage = new java.io.File(storageDirectory + relativePath);
        FileUtils.copyInputStreamToFile(file.getInputStream(), fileInStorage);
        return fileInStorage.length();
    }

    public InputStream LoadFile(String relativePath) throws IOException {
        return new FileInputStream(storageDirectory + relativePath);
    }
}
