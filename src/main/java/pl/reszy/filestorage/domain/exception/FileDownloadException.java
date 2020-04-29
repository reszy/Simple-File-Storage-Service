package pl.reszy.filestorage.domain.exception;

public class FileDownloadException extends RuntimeException {

    private static final long serialVersionUID = 8595419407816027675L;

    public FileDownloadException() {
        super();
    }

    public FileDownloadException(String message) {
        super(message);
    }
}