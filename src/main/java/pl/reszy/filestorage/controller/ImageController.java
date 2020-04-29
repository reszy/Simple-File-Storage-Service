package pl.reszy.filestorage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import pl.reszy.filestorage.controller.dto.FileUploadResponseDto;
import pl.reszy.filestorage.domain.File;
import pl.reszy.filestorage.domain.FileId;
import pl.reszy.filestorage.domain.exception.FileDownloadException;
import pl.reszy.filestorage.domain.exception.FileNotFoundException;
import pl.reszy.filestorage.domain.exception.FileUploadException;
import pl.reszy.filestorage.service.FileService;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
class ImageController {

    private final FileService fileService;

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNotFoundException.class)
    public String notFound(FileNotFoundException e) {
        return "404";
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileUploadException.class)
    public String notUploaded(FileUploadException e) {
        return "400";
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileDownloadException.class)
    public String notDownloaded(FileDownloadException e) {
        return "400";
    }

    @GetMapping(value = "/{imageId}")
    public ResponseEntity<File> getImage(@PathVariable String imageId) {
        File file = fileService.getFileFromStorage(new FileId(imageId));
        return ResponseEntity.ok()
            .contentLength(file.getContentLength())
            .contentType(MediaType.parseMediaType(file.getContentType()))
            .body(file);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponseDto> uploadImage(MultipartFile image) {
        if(image == null) {
            throw new FileNotFoundException();
        }
        FileUploadResponseDto response = FileUploadResponseDto.from(fileService.uploadFile(image));
        return ResponseEntity.ok()
            .body(response);
    }

}