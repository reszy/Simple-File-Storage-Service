package pl.reszy.filestorage.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.reszy.filestorage.domain.File;
import pl.reszy.filestorage.domain.FileId;
import pl.reszy.filestorage.domain.entity.FileStorageInfo;
import pl.reszy.filestorage.domain.exception.FileDownloadException;
import pl.reszy.filestorage.domain.exception.FileNotFoundException;
import pl.reszy.filestorage.domain.exception.FileUploadException;
import pl.reszy.filestorage.service.dto.UploadedFileDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${fileStorage.storage_directory}")
    private String storageDirectory;

    private final FileInfoRepository fileRepository;

    public File getFileFromStorage(FileId fileId) {

        FileStorageInfo fileStorageInfo = fileRepository.findByFileId(fileId);
        if (fileStorageInfo == null) {
            throw new FileNotFoundException();
        }

        String path = storageDirectory + fileStorageInfo.getRelativePath();
        try {
            InputStream inputStream = new FileInputStream(new java.io.File(path));
            return new File(inputStream, fileStorageInfo);
        } catch (IOException e) {
            log.info("Could not load file with id {} at {}", fileId, fileStorageInfo.getRelativePath(), e);
            throw new FileDownloadException();
        }
    }

    public UploadedFileDto uploadFile(MultipartFile file) {
        FileId fileId = FileId.random();
        String relativePath = buildRelativeFilePath(fileId);
        try {
            MediaType contentType = StringUtils.isNotBlank(file.getContentType())
                    ? MediaType.valueOf(file.getContentType())
                    : MediaType.ALL;
            java.io.File fileInStorage = new java.io.File(storageDirectory + relativePath);
            FileUtils.copyInputStreamToFile(file.getInputStream(), fileInStorage);


            UploadedFileDto dto = UploadedFileDto.builder()
                    .contentLength(fileInStorage.length())
                    .fileId(fileId)
                    .fileName(file.getOriginalFilename())
                    .contentType(contentType)
                    .md5("md5")//todo
                    .build();

            fileRepository.save(FileStorageInfo.from(dto, relativePath));
            return dto;

        } catch (IOException e) {
            log.info("Could not upload file with id {} at {}", fileId, relativePath, e);
            throw new FileUploadException();
        }
    }

    private static String buildRelativeFilePath(FileId fileId) {
        return "/files/" + fileId;
    }

}