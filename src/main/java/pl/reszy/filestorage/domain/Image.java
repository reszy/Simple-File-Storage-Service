package pl.reszy.filestorage.domain;

import java.io.InputStream;

import pl.reszy.filestorage.domain.entity.FileStorageInfo;

public class Image extends File {

    public Image(InputStream inputStream, FileStorageInfo fileStorageInfo) {
        super(inputStream, fileStorageInfo);
    }

}