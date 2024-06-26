package engine.game;

import java.util.List;

import components.board.Board;
import components.card.Card;
import engine.data.loader.GameDataLoader;
import team.team.Team;
import team.team.TeamsInfo;
import team.turn.Guess;

public class Game {

    private List<Team> teams;
   // private int currentTeamIndex;
    private TeamsInfo teamsInfo;
    private Board board;
    private Team winningTeam;

    public Game(GameDataLoader gameGameDataLoader){
        this.teams = gameGameDataLoader.initializeTeams();
        this.teamsInfo = new TeamsInfo(this.teams);
        this.board = gameGameDataLoader.initializeBoard(this.teams);
    }
    /*
    public Game(List<String> teamNames, List<Integer> teamCardNumber, List<String> regularWords, List<String> blackWords,
                int regularWordsAmount, int blackWordsAmount, int boardRows, int boardCol){
        this.setTeams(teamNames, teamCardNumber);
        teamsInfo = new TeamsInfo(teams);
        //this.currentTeamIndex = 0;
        board = new Board(regularWords, blackWords, regularWordsAmount, blackWordsAmount, boardRows, boardCol, this.teams);
    }
     */


    //useless func foe ex1 there are only two teams
   /* public void setTeams(List<String> teamNames, List<Integer> teamCardNumber){
        teams = new ArrayList<>();
        int howManyTeams = teamNames.size();
        for (int i = 0; i < howManyTeams; i++) {
            teams.add(new Team(teamNames.get(i), teamCardNumber.get(i)));
        }
    }
    */

    public TeamsInfo getTeamsInfo(){
        return this.teamsInfo;
    }

    public void passTurn(){
        this.teamsInfo.passTurn();
    }
    public List<Team> getTeams(){
        return this.teams;
    }

    public int getCurrentTeamIndex(){
        return this.teamsInfo.getCurrentTeamIndex();
    }

    public Board getBoard(){
        return this.board;
    }

    public Team getWinningTeam(){
        return this.winningTeam;
    }

    public void setWinningTeam(Team team){
        this.winningTeam = team;
    }


    /**
     * Analyzes a player's guess and updates game state based on the guess.
     * This method determines whether the guessed word is on the board, whether it belongs to the current team,
     * an opponent's team, or is a neutral or black card, and updates the score accordingly.
     *
     * @param guess The word guessed by the player.
     * @return A {@link Guess} object containing details about the outcome of the guess including
     * whether the word was on the board, which team's word was guessed, and whether it was correct or not.
     */
    public Guess makeGuess(int guess) {
        Guess guessInfo = new Guess();

        // Retrieve the card that matches the guessed word.
        Card cardGuess = this.getGuessCard(guess);

        // Check if the player decides to pass their turn
        if (guess == -1) {
            guessInfo.setTurnPassed(true);// Set that the turn was passed if guess is -1
            return guessInfo;
        } else {
            guessInfo.setTurnPassed(false); // Set that the turn was not passed otherwise
        }

        // Check if the guessed word is on the board.
        if (cardGuess == null) {
            guessInfo.setWordOnBoard(false); // The guessed word is not on the board.
            return guessInfo;

        } else {
            guessInfo.setWordOnBoard(true);
        }


        // Check if the card has already been found in previous guesses.
        if (cardGuess.isFound()) {
            guessInfo.setWordAlreadyFound(true); // The card was already found, so no further action is taken on it.
            return guessInfo;

        } else {
            guessInfo.setWordAlreadyFound(false); // The card has not been found yet.
            cardGuess.setFound(); // Mark the card as found, indicating it has been guessed this game.
        }

        // Determine if the guess was correct, i.e., matches a card of the guessing team.
        if (this.isGuessCorrect(cardGuess)) {
            this.teams.get(this.getCurrentTeamIndex()).addScore(); // Correct guesses score a point for the guessing team.
            guessInfo.setGuessCorrect(true);
            return guessInfo;

        } else {
            guessInfo.setGuessCorrect(false); // Record that the guess was incorrect.
        }

        // Determine if the guess is for a card belonging to another team.
        if (this.isGuessForOtherTeam(cardGuess, guessInfo)) {
            this.getTeamByName(cardGuess.getTeamName()).addScore(); // Incorrectly guessing an opponent's word gives them a point.
            guessInfo.setGuessedForOtherTeam(true);
            guessInfo.setTeamNameOnCard(cardGuess.getTeamName()); // Record the team name associated with the guessed card.
            return guessInfo;

        }

        // Check if the guess was for a black card, which is typically a losing move.
        guessInfo.setGuessedWordBlack(this.isGuessBlack(cardGuess));


        // Check if the guessed card is a neutral card (not belonging to any team).
        if (cardGuess.getTeamName().isEmpty()) {
            guessInfo.setGuessedWordWithoutTeam(true); // The guessed word is a neutral word.
            return guessInfo;

        } else {
            guessInfo.setGuessedWordWithoutTeam(false); // The guessed word is not a neutral word.
        }

        return guessInfo;
    }


    private Card getGuessCard(int guess){
        if (guess < 0 || guess >= board.getCards().size())
            return null;
        else
            return this.board.getCards().get(guess);
    }

    private boolean isGuessBlack(Card guess){
        return guess.getTeamName().equals("BLACK");
    }

    private boolean isGuessCorrect(Card guess){
        return guess.getTeamName().equals(teams.get(this.getCurrentTeamIndex()).getTeamName());
    }

    private boolean isGuessForOtherTeam(Card guess, Guess currGuess){
        return !guess.getTeamName().equals("") && !guess.getTeamName().equals("BLACK") && !currGuess.isGuessCorrect();
    }

    private Team getTeamByName(String teamName){
        for(Team team: this.teams){
            if(team.getTeamName().equals(teamName))
                return team;
        }
        return null;
    }

}
