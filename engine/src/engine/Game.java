package engine;

import java.util.ArrayList;
import java.util.List;
import components.*;
import team.Team;

public class Game {
    private List<Team> teams;
    private int currentTeamIndex;
    private Board board;


    public Game(List<String> teamNames, List<Integer> teamCardNumber, List<String> regularWords, List<String> blackWords,
                int regularWordsAmount, int blackWordsAmount, int boardRows, int boardCol){
        this.setTeams(teamNames, teamCardNumber);
        this.currentTeamIndex = 0;
        board = new Board(regularWords, blackWords, regularWordsAmount, blackWordsAmount, boardRows, boardCol, this.teams);
    }

    public void setTeams(List<String> teamNames, List<Integer> teamCardNumber){
        teams = new ArrayList<>();
        int howManyTeams = teamNames.size();
        for (int i = 0; i < howManyTeams; i++) {
            teams.add(new Team(teamNames.get(i), teamCardNumber.get(i)));
        }
    }

    public void passTurn(){
        this.currentTeamIndex = (++this.currentTeamIndex) % this.teams.size();
    }

    public List<Team> getTeams(){
        return this.teams;
    }

    public int getCurrentTeamIndex(){
        return this.currentTeamIndex;
    }

    public Board getBoard(){
        return this.board;
    }



}
