package engine.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import engine.data.exeption.CardCountException;
import engine.data.exeption.InsufficientTableSizeException;
import engine.data.exeption.TeamCardLimitExceededException;
import engine.data.exeption.TeamNamesNotUniqueException;
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
        int cardsCount = gameInfo.getECNBoard().getCardsCount();

        long availableWordsCount = Arrays.stream(Optional.ofNullable(gameInfo.getECNWords().getECNGameWords())
                .orElse("").split(" "))
                .filter(word-> !word.equals("\n"))
                .distinct()
                .count();



        if (cardsCount > availableWordsCount){
            throw new CardCountException("The number of cards on the board (" + cardsCount + ") exceeds the number of available game words ("
                    + availableWordsCount + ").");
        }

        int blackCardsCount = gameInfo.getECNBoard().getBlackCardsCount();
        int availableBlackWordsCount = gameInfo.getECNWords().getECNBlackWords().trim().split(" ").length;

        if(blackCardsCount > availableBlackWordsCount){
            throw new CardCountException("The number of black cards on the board (" + blackCardsCount+ ") exceeds the number" +
                    " of available game words (" + availableBlackWordsCount + ").");
        }

        int teamsTotalCardAmount = gameInfo.getECNTeam1().getCardsCount() + gameInfo.getECNTeam2().getCardsCount();
        if(teamsTotalCardAmount > cardsCount){
            throw new TeamCardLimitExceededException("The combined total card amount of the teams (" + teamsTotalCardAmount +
                    ") exceeds the total available cards (" + cardsCount + ").");
        }

        int boardSize = gameInfo.getECNBoard().getECNLayout().getRows() * gameInfo.getECNBoard().getECNLayout().getColumns();
        if(boardSize != cardsCount + blackCardsCount){
            throw new InsufficientTableSizeException("Board size mismatch: The total number of cells on the board (" + boardSize +
                    ") does not equal the combined total of regular cards and black cards (" +
                    (cardsCount + blackCardsCount) + ").");
        }
        if(gameInfo.getECNTeam1().getName().equals(gameInfo.getECNTeam2().getName())){
            throw new TeamNamesNotUniqueException("The names of the two teams are identical, which is not allowed." +
                    " Both teams are named '" + gameInfo.getECNTeam1().getName() + "'.");
        }
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
