package team.turn;

public class Guess {
    private boolean isWordOnBoard;

    private boolean isGuessedWordBlack;

    private boolean isGuessCorrect;

    private boolean isGuessedForOtherTeam;

    private boolean isGuessedWordWithoutTeam;

    private boolean isWordAlreadyFound;

    private boolean isTurnPassed;

    private boolean isNeedToPassTurn = true;

    private String teamNameOnCard;

    public boolean isNeedToPassTurn() {
        return isNeedToPassTurn;
    }

    public void setNeedToPassTurn(boolean needToPassTurn) {
        isNeedToPassTurn = needToPassTurn;
    }

    public String getTeamNameOnCard() {
        return teamNameOnCard;
    }

    public boolean isTurnPassed() {
        return isTurnPassed;
    }

    public void setTurnPassed(boolean turnPassed) {
        isTurnPassed = turnPassed;
    }

    public void setTeamNameOnCard(String teamNameOnCard) {
        this.teamNameOnCard = teamNameOnCard;
    }

    public boolean isWordAlreadyFound() {
        return isWordAlreadyFound;
    }

    public void setWordAlreadyFound(boolean wordAllReadyFound) {
        isWordAlreadyFound = wordAllReadyFound;
    }

    public boolean isWordOnBoard() {
        return isWordOnBoard;
    }

    public void setWordOnBoard(boolean wordOnBoard) {
        isWordOnBoard = wordOnBoard;
    }

    public boolean isGuessedWordBlack() {
        return isGuessedWordBlack;
    }

    public void setGuessedWordBlack(boolean guessedWordBlack) {
        isGuessedWordBlack = guessedWordBlack;
    }

    public boolean isGuessCorrect() {
        return isGuessCorrect;
    }

    public void setGuessCorrect(boolean guessCorrect) {
        isGuessCorrect = guessCorrect;
    }

    public boolean isGuessedForOtherTeam() {
        return isGuessedForOtherTeam;
    }

    public void setGuessedForOtherTeam(boolean guessedForOtherTeam) {
        isGuessedForOtherTeam = guessedForOtherTeam;
    }

    public boolean isGuessedWordWithoutTeam() {
        return isGuessedWordWithoutTeam;
    }

    public void setGuessedWordWithoutTeam(boolean guessedWordWithoutTeam) {
        isGuessedWordWithoutTeam = guessedWordWithoutTeam;
    }
}
