package engine.data.exception;

public class GameNameAlreadyExistException extends RuntimeException{
    public GameNameAlreadyExistException(String message) {
        super(message);
    }

}
