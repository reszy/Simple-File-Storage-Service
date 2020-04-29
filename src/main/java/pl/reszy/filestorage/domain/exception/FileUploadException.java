package pl.reszy.filestorage.domain.exception;

public class FileUploadException extends RuntimeException {

    private static final long serialVersionUID = 8595458407816027675L;

    public FileUploadException() {
        super();
    }

    public FileUploadException(String message) {
        super(message);
    }
}