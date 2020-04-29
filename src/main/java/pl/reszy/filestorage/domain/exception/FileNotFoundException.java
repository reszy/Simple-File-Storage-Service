package pl.reszy.filestorage.domain.exception;

public class FileNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8595419407416027675L;

    public FileNotFoundException() {
        super();
    }

    public FileNotFoundException(String message) {
        super(message);
    }
}