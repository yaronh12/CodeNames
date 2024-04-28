package team;

public class Clue {
    private String clueWord;
    private int numOfWordToGuess;

    public Clue(String clueWord, int numOfWordToGuess) {
        this.clueWord = clueWord;
        this.numOfWordToGuess = numOfWordToGuess;
    }

    public String getClueWord() {
        return clueWord;
    }

    public int getNumOfWordToGuess() {
        return numOfWordToGuess;
    }
}
