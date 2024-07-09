package engine.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import engine.data.loader.GameDataLoader;
import components.card.Card;
import engine.game.Game;
import team.turn.Guess;
import team.team.Team;
import team.team.TeamsInfo;
import generated.ECNGame;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import static engine.data.validators.Validators.*;

public class EngineImpl implements Engine {
    /*private Data gameData = new Data();
    private Game activeGame = new Game(this.gameData.getTeamsNames(), this.gameData.getHowManyWordsForEachTeam(), this.getRegularWords(),
                                       this.getBlackWords(), this.getHowManyRegularWordsInGame(),
                                        this.getHowManyBlackWordsInGame(), this.getBoardRows(),this.getBoardCols());*/
    private Game activeGame;
    private ECNGame gameDataFile;
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";
    public void readXmlFile(String xmlName) throws FileNotFoundException, JAXBException, RuntimeException {

            InputStream inputStream = new FileInputStream(new File(xmlName));
            JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
            Unmarshaller u = jc.createUnmarshaller();
            gameDataFile = (ECNGame) u.unmarshal(inputStream);

            xmlContentValidator(gameDataFile);

    }

    private void xmlContentValidator(ECNGame gameInfo) throws RuntimeException{
        validateCardCounts(gameInfo);
        validateBlackCardCounts(gameInfo);
        validateTeamTotalCardAmount(gameInfo);
        validateBoardSize(gameInfo);
        validateTeamNames(gameInfo);
    }

    public void loadGameData(){
        GameDataLoader gameDataLoader = new GameDataLoader(this.gameDataFile);
        activeGame = new Game(gameDataLoader);
    }
    @Override
   public List<String> getRegularWords(){
        return Arrays.asList(this.gameDataFile.getECNWords().getECNGameWords().split(" "));
   }
    @Override
   public List<String> getBlackWords(){
       return Arrays.asList(this.gameDataFile.getECNWords().getECNBlackWords().split(" "));
   }
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
        teams.add(new Team(this.gameDataFile.getECNTeam1().getName(),this.gameDataFile.getECNTeam1().getCardsCount()));
        teams.add(new Team(this.gameDataFile.getECNTeam2().getName(),this.gameDataFile.getECNTeam2().getCardsCount()));
        return teams;
    }
    @Override
    public Team getCurrentTeam(){
       return this.activeGame.getTeams().get(this.activeGame.getCurrentTeamIndex());
    }

    @Override
    public List<Card> getBoardState(){
        return this.activeGame.getBoard().getCards();
    }

    @Override
    public void passTurn(){
       this.activeGame.passTurn();
    }

    public Guess makeGuess(int guess){
       return this.activeGame.makeGuess(guess);
    }

    public boolean isGameOver(Guess guess){
        if(guess.isGuessedWordBlack()){
            // picking the other team to win in case of black word
            // ask aviad what to do in case of multiple teams
            this.activeGame.passTurn();
            this.activeGame.setWinningTeam(this.activeGame.getTeams().get(this.activeGame.getCurrentTeamIndex()));
            return true;
        }


        for(Team team: this.activeGame.getTeams()){
            if(team.getCardAmount() == team.getScore()){
                this.activeGame.setWinningTeam(team);
                return true;
            }
        }
        return false;

    }

    public Team getWinningTeam(){
       return this.activeGame.getWinningTeam();
    }

    public TeamsInfo getTeamsInfo(){
       return this.activeGame.getTeamsInfo();
    }


}
