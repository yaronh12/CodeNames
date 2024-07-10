package team.team;

import team.team.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamsInfo {
    private List<Team> teams;
    private int currentTeamIndex;


    public TeamsInfo(List<Team> teams){
        this.teams = teams;
        currentTeamIndex = 0;
    }

    public int getHowManyTeams(){
        return this.teams.size();
    }

    public void passTurn(){
        this.teams.get(currentTeamIndex).addTurn();
        currentTeamIndex = (++currentTeamIndex) % this.teams.size();
    }

    public int getCurrentTeamIndex(){
        return currentTeamIndex;
    }
    public List<String> getTeamNames(){
        List<String> teamNames = new ArrayList<>();
        for(Team team: this.teams){
            teamNames.add(team.getTeamName());
        }
        return teamNames;
    }

    public String getNextTeamName(){
        int nextTeamIndex = (currentTeamIndex+1) % this.teams.size();
        return this.teams.get(nextTeamIndex).getTeamName();
    }

    public List<String> getTeamsCurrentScore(){
        List<String> scores = new ArrayList<>();
        for(Team team:this.teams){
            scores.add(team.getScore() + " / " + team.getCardAmount());
        }
        return scores;
    }

    public List<Integer> getTeamsTurnCounter(){
        List<Integer> turnCounters = new ArrayList<>();
        for(Team team:this.teams){
            turnCounters.add(team.getTeamCounter());
        }
        return turnCounters;
    }
}
