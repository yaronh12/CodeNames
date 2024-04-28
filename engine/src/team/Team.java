package team;

public class Team {
    private final String name;
    private final int cardsAmount;
    private int score;
    private int turnCounter;

    public Team(String name, int cardsAmount){
        this.name = name;
        this.cardsAmount = cardsAmount;
        this.score = 0;
        this.turnCounter = 0;
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
