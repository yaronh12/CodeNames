package engine;

import java.util.List;

import team.Guess;
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
        if(guess.isGuessedWordBlack())
            return true;

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


}
