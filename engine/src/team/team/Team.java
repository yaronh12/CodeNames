package team.team;

public class Team {
    private final String name;
    private final int cardsAmount;
    private int score;
    private int turnCounter;
    private int guessersAmount;
    private int definersAmount;

    private int readyGuessersAmount;
    private int readyDefinersAmount;

    public int getGuessersAmount() {
        return guessersAmount;
    }

    public int getDefinersAmount() {
        return definersAmount;
    }

    public Team(String name, int cardsAmount, int guessersAmount, int definersAmount){
        this.name = name;
        this.cardsAmount = cardsAmount;
        this.score = 0;
        this.turnCounter = 0;
        this.definersAmount = definersAmount;
        this.guessersAmount = guessersAmount;
    }

    public int getReadyGuessersAmount() {
        return readyGuessersAmount;
    }

    public int getReadyDefinersAmount() {
        return readyDefinersAmount;
    }

    public void increaseReadyGuessersAmount(){
        readyGuessersAmount++;
    }

    public void increaseReadyDefinersAmount(){
        readyDefinersAmount++;
    }

    public String getTeamName(){
        return this.name;
    }
    public int getCardAmount(){
        return this.cardsAmount;
    }

    public void addScore(){
        this.score++;
    }

    public int getScore(){
        return this.score;
    }

    public void addTurn(){
        this.turnCounter++;
    }

    public int getTeamCounter(){
        return this.turnCounter;
    }

    @Override
    public String toString(){
        return ""+this.name+" have "+this.cardsAmount+" words";
    }
}
