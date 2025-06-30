package lk.ijse.cmjd109.lostAndFoundSystem.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super();
    }

    public ItemNotFoundException(String message) {

    }

    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


}
