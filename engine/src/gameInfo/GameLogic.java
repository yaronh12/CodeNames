package gameInfo;

import board.Board;
import player.Team;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private Board board;
    private Data gameData;

    private List<Team> teams = new ArrayList<>();

    public GameLogic(Data data){
        this.board = new Board(data);
        this.gameData = new Data();
        this.teams.add(new Team(this.gameData.getTeam1name(), this.gameData.getTeam1cards()));
        this.teams.add(new Team(this.gameData.getTeam2name(), this.gameData.getTeam2cards()));
    }

    public int wordsLeftToGuess(String teamName){
        for (Team team: teams) {
            if(team.getTeamName().equals(teamName)){
                return
            }
        }
    }
    public Data getGameData() {
        return gameData;
    }
}
