package lk.ijse.main.exception;


public class DataPersistFailedException extends RuntimeException {

    public DataPersistFailedException() {
    }

    public DataPersistFailedException(String message) {
        super(message);
    }

    public DataPersistFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}