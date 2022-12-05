package pl.reszy.filestorage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.reszy.filestorage.domain.File;
import pl.reszy.filestorage.domain.FileId;
import pl.reszy.filestorage.domain.entity.FileStorageInfo;
import pl.reszy.filestorage.domain.exception.FileDownloadException;
import pl.reszy.filestorage.domain.exception.FileNotFoundException;
import pl.reszy.filestorage.domain.exception.FileUploadException;
import pl.reszy.filestorage.service.dto.UploadedFileDto;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileInfoRepository fileInfoRepository;
    private final FileRepository fileRepository;

    public File getFileFromStorage(FileId fileId) {

        FileStorageInfo fileStorageInfo = fileInfoRepository.findByFileId(fileId);
        if (fileStorageInfo == null) {
            throw new FileNotFoundException();
        }

        try {
            InputStream fileStream = fileRepository.LoadFile(fileStorageInfo.getRelativePath());
            return new File(fileStream, fileStorageInfo);
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

            long fileLength = fileRepository.SaveFile(file,relativePath);

            UploadedFileDto dto = UploadedFileDto.builder()
                    .contentLength(fileLength)
                    .fileId(fileId)
                    .fileName(file.getOriginalFilename())
                    .contentType(contentType)
                    .md5("md5")//todo
                    .build();

            fileInfoRepository.save(FileStorageInfo.from(dto, relativePath));
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