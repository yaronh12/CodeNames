package engine.data.exeption;

public class TeamNamesNotUniqueException extends RuntimeException{
    public TeamNamesNotUniqueException(String message) {
        super(message);
    }
}
