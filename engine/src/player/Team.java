package player;

public class Team{

    final private String teamName;
    private int score;
    private Agent agent = new Player();
    private Guesser guesser = new Player();
    final private  int  wordToGuess;

    public Team(String teamName, int wordToGuess) {
        this.teamName = teamName;
        this.score=0;
        this.wordToGuess = wordToGuess;
    }


    public String getTeamName() {
        return teamName;
    }




}
