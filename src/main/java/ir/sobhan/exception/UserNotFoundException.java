package ir.sobhan.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(Long id) {
        super("there is no user with this id " + id);
    }
}
