package pl.reszy.filestorage.domain;

import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;

import lombok.Getter;
import pl.reszy.filestorage.domain.entity.FileStorageInfo;

@Getter
public class File extends InputStreamResource {

    private String filename;
    private String contentType;
    private Long contentLength;

    public File(InputStream inputStream, FileStorageInfo fileStorageInfo) {
        super(inputStream);
        filename = fileStorageInfo.getFileName();
        contentType = fileStorageInfo.getContentType();
        contentLength = fileStorageInfo.getContentLength();
    }

}