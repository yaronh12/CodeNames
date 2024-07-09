package engine.data.exception;

public class TeamCardLimitExceededException extends RuntimeException {
    public TeamCardLimitExceededException(String message) {
        super(message);
    }
}
