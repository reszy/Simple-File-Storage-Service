package pl.reszy.filestorage.service.dto;

import org.springframework.http.MediaType;

import lombok.Builder;
import lombok.Value;
import pl.reszy.filestorage.domain.FileId;

@Value
@Builder
public class UploadedFileDto {
    FileId fileId;
    String fileName;
    MediaType contentType;
    Long contentLength;
    String md5;
}