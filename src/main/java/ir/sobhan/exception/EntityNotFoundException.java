package ir.sobhan.exception;

public class EntityNotFoundException extends Exception{
    public EntityNotFoundException(Long id) {
        super("there is no entity with this id " + id);
    }
}
