package engine.engine;

import java.io.FileNotFoundException;
import java.util.List;

import components.card.Card;
import engine.game.Game;
import team.team.Team;
import team.team.TeamsInfo;
import team.turn.Guess;

import javax.management.relation.Role;
import javax.xml.bind.JAXBException;

public interface Engine {
    void readXmlFile(String xmlName) throws FileNotFoundException, JAXBException;

    void loadGameData();
/*
    List<String> getRegularWords();
    List<String> getBlackWords();*/

    int getHowManyRegularWordsInGame();
    int getHowManyBlackWordsInGame();

    int getBoardRows();
    int getBoardCols();

    List<Team> getTeams();


    void passTurn(int gameIndex);

    List<Card> getBoardState(int gameIndex);

    public Guess makeGuess(int guess, int gameIndex);

  /*  void startGame();
    boolean isGameOver();
    void endGame();*/

    Team getCurrentTeam(int gameIndex);

    Team getWinningTeam(int gameIndex);
    boolean isGameOver(Guess guess, int gameIndex);

    TeamsInfo getTeamsInfo(int gameIndex);

    void addPlayer(String username) throws RuntimeException;

    List<Game> getAllGamesList();

    void registerPlayerToGame(int gameIndex, int teamIndex, String role);

    boolean isTurnAllowedForRole(String role, int gameIndex);

    boolean isGameActive(int gameIndex);

    boolean isTurnAllowedForTeam(int teamIndex , int gameIndex);


}
