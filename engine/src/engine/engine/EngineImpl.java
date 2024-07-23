package engine.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import engine.data.exception.UsernameAlreadyExistInSystemException;
import engine.data.loader.GameDataLoader;
import components.card.Card;
import engine.game.Game;
import generated.ECNTeam;
import team.turn.Guess;
import team.team.Team;
import team.team.TeamsInfo;
import generated.ECNGame;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import static engine.data.validators.Validators.*;

public class EngineImpl implements Engine {

    private List<Game> activeGames = new ArrayList<>();
    private ECNGame gameDataFile;
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";

    private Set<String> usernames = new HashSet<>();
    private String txtFileLocation;
    public void readXmlFile(String xmlName) throws FileNotFoundException, JAXBException, RuntimeException {

            InputStream inputStream = new FileInputStream(new File(xmlName));
            JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
            Unmarshaller u = jc.createUnmarshaller();
            gameDataFile = (ECNGame) u.unmarshal(inputStream);
            txtFileLocation = Paths.get(xmlName).getParent().resolve(gameDataFile.getECNDictionaryFile()).toString();
            xmlContentValidator(gameDataFile, txtFileLocation);

    }

    @Override
    public synchronized void addPlayer(String username) throws RuntimeException{
        validateUsername(usernames, username);
        usernames.add(username);
    }

    @Override
    public void removePlayer(String username){
        usernames.remove(username);
    }

    @Override
    public void registerPlayerToGame(int gameIndex, int teamIndex, String role){
        Team team = activeGames.get(gameIndex).getTeams().get(teamIndex);
        if(role.equals("SPYMASTER")){
            team.increaseReadyDefinersAmount();
        }
        else{
            team.increaseReadyGuessersAmount();
        }

        if(team.getGuessersAmount() == team.getReadyGuessersAmount() &&
            team.getDefinersAmount() == team.getReadyDefinersAmount()){
            activeGames.get(gameIndex).increaseReadyTeamsAmount();
            if(activeGames.get(gameIndex).areAllTeamsReady())
                activeGames.get(gameIndex).setGameActive(true);
        }

    }

    @Override
    public boolean isTurnAllowedForRole(String role, int gameIndex){
        if(activeGames.get(gameIndex).isDefinitionAlreadyTaken()){
            if(role.equals("GUESSER"))
                return true;
            return false;
        }
        else{
            if(role.equals("SPYMASTER"))
                return true;
            return false;
        }
    }

    @Override
    public boolean isTurnAllowedForTeam(int teamIndex , int gameIndex){
        return activeGames.get(gameIndex).getTeamsInfo().getCurrentTeamIndex() == teamIndex;
    }

    @Override
    public boolean isGameActive(int gameIndex){
        return activeGames.get(gameIndex).isGameActive();
    }



    private void xmlContentValidator(ECNGame gameInfo, String txtFileLocation) throws RuntimeException, FileNotFoundException {
        validateGameName(gameInfo, activeGames.stream()
                                                .map(Game::getGameName)
                                                .collect(Collectors.toList()));
        validateCardCounts(gameInfo, txtFileLocation);
        validateBlackCardCounts(gameInfo, txtFileLocation);
        validateTeamTotalCardAmount(gameInfo);
        validateBoardSize(gameInfo);
        validateTeamNames(gameInfo);
        validateParticipants(gameInfo);
        validateMoreThanOneTeam(gameInfo);
        validateLessThanFiveTeams(gameInfo);
    }

    public void loadGameData(){
        GameDataLoader gameDataLoader = new GameDataLoader(this.gameDataFile);
        gameDataLoader.setTxtFileLocation(txtFileLocation);
        activeGames.add(new Game(gameDataLoader));

    }

    public void resetGame(int gameIndex){
        GameDataLoader gameDataLoader = new GameDataLoader(activeGames.get(gameIndex).getEcnGame());
        activeGames.get(gameIndex).resetGame(gameDataLoader);
        activeGames.get(gameIndex).setGameActive(false);
    }

  /*  @Override
   public List<String> getRegularWords(){
        return Arrays.asList(this.gameDataFile.getECNWords().getECNGameWords().split(" "));
   }
    @Override
   public List<String> getBlackWords(){
       return Arrays.asList(this.gameDataFile.getECNWords().getECNBlackWords().split(" "));
   }*/
    @Override
   public int getHowManyRegularWordsInGame(){
       return this.gameDataFile.getECNBoard().getCardsCount();
   }
    @Override
   public int getHowManyBlackWordsInGame(){
       return this.gameDataFile.getECNBoard().getBlackCardsCount();
   }
    @Override
   public int getBoardRows(){
       return this.gameDataFile.getECNBoard().getECNLayout().getRows();
   }
    @Override
    public int getBoardCols(){
        return this.gameDataFile.getECNBoard().getECNLayout().getColumns();
    }

    @Override
    public List<Team> getTeams(){
        List<Team> teams = new ArrayList<>();
        for(ECNTeam team: gameDataFile.getECNTeams().getECNTeam()){
            teams.add(new Team(team.getName(),team.getCardsCount(), team.getGuessers(), team.getDefiners()));
        }
       /* teams.add(new Team(this.gameDataFile.getECNTeam1().getName(),this.gameDataFile.getECNTeam1().getCardsCount()));
        teams.add(new Team(this.gameDataFile.getECNTeam2().getName(),this.gameDataFile.getECNTeam2().getCardsCount()));*/
        return teams;
    }

    @Override
    public List<Team> getGameTeamsByGameIndex(int gameIndex){
        return activeGames.get(gameIndex).getTeams();
    }

    @Override
    public Team getCurrentTeam(int gameIndex){
      // return this.activeGame.getTeams().get(this.activeGame.getCurrentTeamIndex());
       return this.activeGames.get(gameIndex)
               .getTeams()
               .get(this.activeGames.get(gameIndex).getCurrentTeamIndex());
    }

    @Override
    public List<Card> getBoardState(int gameIndex){
        //return this.activeGame.getBoard().getCards();
        return this.activeGames.get(gameIndex).getBoard().getCards();
    }

    @Override
    public void passTurn(int gameIndex){
       //this.activeGame.passTurn();
       this.activeGames.get(gameIndex).passTurn();
    }

    @Override
    public Guess makeGuess(int guess, int gameIndex){
       //return this.activeGame.makeGuess(guess);
       return this.activeGames.get(gameIndex).makeGuess(guess);
    }



    public boolean isGameOver(Guess guess, int gameIndex){
        Game currGame = this.activeGames.get(gameIndex);
        if(guess.isGuessedWordBlack()){
            // picking the other team to win in case of black word
            int currTeamIndex = currGame.getCurrentTeamIndex();
            currGame.getTeams().get(currTeamIndex).setLost(true);
            getTeamsInfo(gameIndex).removeTeam(currTeamIndex);
            currGame.passTurn();

            //currGame.setWinningTeam(currGame.getTeams().get(currGame.getCurrentTeamIndex()));
            return true;
        }


        for(int i=0;i<currGame.getTeams().size();i++){
            Team team = currGame.getTeams().get(i);
            if(team.getCardAmount() == team.getScore()){
                currGame.setWinningTeam(team);
                currGame.getTeams().get(i).setWon(true);
                getTeamsInfo(gameIndex).removeTeam(i);
                currGame.passTurn();

                return true;
            }
        }
        return false;

    }

    @Override
    public Team getWinningTeam(int gameIndex){
       //return this.activeGame.getWinningTeam();
       return this.activeGames.get(gameIndex).getWinningTeam();
    }

    @Override
    public TeamsInfo getTeamsInfo(int gameIndex){
       //return this.activeGame.getTeamsInfo();
       return this.activeGames.get(gameIndex).getTeamsInfo();
    }


    @Override
    public List<Game> getAllGamesList() {
        return activeGames;
    }


}
