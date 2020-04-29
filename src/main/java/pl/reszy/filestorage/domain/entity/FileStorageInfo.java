package pl.reszy.filestorage.domain.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.reszy.filestorage.domain.FileId;
import pl.reszy.filestorage.service.dto.UploadedFileDto;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileStorageInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private FileId fileId;

    private String fileName;
    private String relativePath;
    private String contentType;
    private Long contentLength;
    private String md5;

    @Transient
    public static FileStorageInfo from(UploadedFileDto uploadedFileDto, String relativePath) {
        return FileStorageInfo.builder()
            .fileId(uploadedFileDto.getFileId())
            .fileName(uploadedFileDto.getFileName())
            .contentLength(uploadedFileDto.getContentLength())
            .contentType(uploadedFileDto.getContentType().toString())
            .relativePath(relativePath)
            .md5(uploadedFileDto.getMd5())
            .build();
    }

}