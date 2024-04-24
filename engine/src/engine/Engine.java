package engine;

import java.util.List;

public interface Engine {
    void readXmlFile();

    List<String> getRegularWords();
    List<String> getBlackWords();

    int getHowManyRegularWords();
    int getHowManyBlackWords();

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
