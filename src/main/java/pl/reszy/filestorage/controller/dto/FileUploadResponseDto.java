package pl.reszy.filestorage.controller.dto;

import lombok.Builder;
import lombok.Value;
import pl.reszy.filestorage.service.dto.UploadedFileDto;

@Value
@Builder
public class FileUploadResponseDto {
    String fileId;
    String fileName;
    String contentType;
    Long contentLength;
    String md5;

    public static FileUploadResponseDto from(UploadedFileDto uploadedFileDto) {
        return FileUploadResponseDto.builder()
            .fileId(uploadedFileDto.getFileId().toString())
            .fileName(uploadedFileDto.getFileName())
            .contentLength(uploadedFileDto.getContentLength())
            .contentType(uploadedFileDto.getContentType().toString())
            .md5(uploadedFileDto.getMd5())
            .build();
    }
}