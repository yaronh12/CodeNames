package engine;

import java.util.List;
import team.Team;
import components.*;

public class EngineImpl implements Engine{
    private Data gameData = new Data();
    private Game activeGame = new Game(this.gameData.getTeamsNames(), this.gameData.getHowManyWordsForEachTeam(), this.getRegularWords(),
                                        this.getBlackWords(), this.getHowManyRegularWordsInGame(),
                                        this.getHowManyBlackWordsInGame(), this.getBoardRows(),this.getBoardCols());
   @Override
    public List<String> getRegularWords(){
        return this.gameData.getWordsList();
   }
    @Override
   public List<String> getBlackWords(){
       return this.gameData.getBlackWordsList();
   }
    @Override
   public int getHowManyRegularWordsInGame(){
       return this.gameData.getCardsCount();
   }
    @Override
   public int getHowManyBlackWordsInGame(){
       return this.gameData.getBlackCardsCount();
   }
    @Override
   public int getBoardRows(){
       return this.gameData.getRows();
   }
    @Override
    public int getBoardCols(){
        return this.gameData.getCols();
    }
    @Override
    public List<Team> getTeams(){
        return this.activeGame.getTeams();
    }
    @Override
    public String getCurrentTeamName(){
       return this.activeGame.getTeams().get(this.activeGame.getCurrentTeamIndex()).getTeamName();
    }
    @Override
    public int getCurrentTeamScore(){
        return this.activeGame.getTeams().get(this.activeGame.getCurrentTeamIndex()).getScore();
    }
    @Override
    public int getCurrentTeamTotalWords(){
        return this.activeGame.getTeams().get(this.activeGame.getCurrentTeamIndex()).getCardAmount();
    }
    @Override
    public List<Card> getBoardState(){
        return this.activeGame.getBoard().getCards();
    }


}
