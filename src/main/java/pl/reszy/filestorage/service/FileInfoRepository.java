package pl.reszy.filestorage.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.reszy.filestorage.domain.FileId;
import pl.reszy.filestorage.domain.entity.FileStorageInfo;

public interface  FileInfoRepository extends JpaRepository<FileStorageInfo, Long> {

    FileStorageInfo findByFileId(FileId fileId);

    @Query("SELECT coalesce(SUM(i.contentLength), 0) FROM FileStorageInfo AS i")
    Long sumByContentLength();

}