package engine.data.exeption;

public class TeamCardLimitExceededException extends RuntimeException {
    public TeamCardLimitExceededException(String message) {
        super(message);
    }
}
