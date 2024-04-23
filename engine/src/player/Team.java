package player;

public class Team implements Agent, Guesser{

    final private String teamName;
    private int score;
    private Agent agent = new Player();
    private Guesser guesser = new Player();
    final private  int  wordToGuess;

    public Team(String teamName) {
        this.teamName = teamName;
        this.teamScore = 0;
    }


    public String getTeamName() {
        return teamName;
    }

    public void setTeamScore(int teamScore) {
        this.teamScore = teamScore;
    }

    public int getTeamScore() {
        return teamScore;
    }

    @Override
    public String whichTeam() {
        return null;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public int totalWordAmount() {
        return 0;
    }
}
