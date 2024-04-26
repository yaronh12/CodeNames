package engine;

import java.util.List;
import components.*;
import team.Team;
//menu:
//1. load xml file
//after the file got loaded:
//1. view game info   2. start game   3. exit system
//if pressed 1, show game info and *exit to main menu*
//if pressed 2:
// - showing board for agent
// - which team is currently playing
// - menu of game:
//for agent: 1. give a clue   2. exit game
// print board for guesser:
// guesser loop:
//     make a guess
//        if correct - update team data and board then continue
//        else - break the loop and pass the turn to the next team

public interface Engine {
    //void readXmlFile();

    List<String> getRegularWords();
    List<String> getBlackWords();

    int getHowManyRegularWordsInGame();
    int getHowManyBlackWordsInGame();

    int getBoardRows();
    int getBoardCols();

    List<Team> getTeams();

    List<Card> getBoardState();

    //this function gets the users guess
    //if the guess isn't on the board the func returns null
    //if the guess is a word on the board, it returns the word Card class
    Card makeGuess(String guess);

    boolean isGuessBlack(Card guess);

    boolean isGuessCorrect(Card guess, Team currentTeam);

    boolean isGuessForOtherTeam(Card guess);

  /*  void startGame();
    boolean isGameOver();
    void endGame();*/

    String getCurrentTeamName();

    int getCurrentTeamScore();

    int getCurrentTeamTotalWords();



}
