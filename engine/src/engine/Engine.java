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

  /*  void startGame();
    boolean makeGuess(String guess, String team);
    boolean isGameOver();
    void endGame();*/

    String getCurrentTeamName();

    int getCurrentTeamScore();

    int getCurrentTeamTotalWords();



}
