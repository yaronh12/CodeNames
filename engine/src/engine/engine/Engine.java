package engine.engine;

import java.io.FileNotFoundException;
import java.util.List;

import components.card.Card;
import team.team.Team;
import team.team.TeamsInfo;
import team.turn.Guess;

import javax.xml.bind.JAXBException;

public interface Engine {
    void readXmlFile(String xmlName) throws FileNotFoundException, JAXBException;

    void loadGameData();

    List<String> getRegularWords();
    List<String> getBlackWords();

    int getHowManyRegularWordsInGame();
    int getHowManyBlackWordsInGame();

    int getBoardRows();
    int getBoardCols();

    List<Team> getTeams();


    void passTurn();

    List<Card> getBoardState();

    public Guess makeGuess(int guess);

  /*  void startGame();
    boolean isGameOver();
    void endGame();*/

    Team getCurrentTeam();

    Team getWinningTeam();
    boolean isGameOver(Guess guess);

    TeamsInfo getTeamsInfo();




}
