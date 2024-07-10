package display;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.util.ArrayList;
import java.util.List;

import static display.BoardPrinter.displayBoard;


/**
 * Manages the display of game-related information on the console, providing methods to print game details
 * and active game state including team scores and game progress.
 */
public class GeneralGameInfo {



    private static List<String> getTeamsScore(JsonArray teams){
        List<String> teamsScores = new ArrayList<>();
        JsonObject currTeam;
        for(int i=0;i<teams.size();i++){
            currTeam = teams.get(i).getAsJsonObject();
            teamsScores.add(i, currTeam.get("score").getAsString() + " / " + currTeam.get("cardsAmount").getAsString());
        }
        return teamsScores;
    }

    private static List<Integer> getTeamsTurnCounter(JsonArray teams){
        List<Integer> teamsTurnCounters = new ArrayList<>();
        JsonObject currTeam;
        for(int i=0;i<teams.size();i++){
            currTeam = teams.get(i).getAsJsonObject();
            teamsTurnCounters.add(i, currTeam.get("turnCounter").getAsInt());
        }
        return teamsTurnCounters;
    }

    private static String getNextTeamName(JsonObject teamsInfo){
        int nextTeamIndex = (teamsInfo.get("currentTeamIndex").getAsInt() + 1) % teamsInfo.get("teams").getAsJsonArray().size();
        return teamsInfo.get("teams").getAsJsonArray().get(nextTeamIndex).getAsJsonObject().get("name").getAsString();
    }
    public static void printActiveGameInfo(JsonObject game){
        JsonObject teamsInfo = game.get("teamsInfo").getAsJsonObject();
        JsonObject board = game.get("board").getAsJsonObject();
        displayBoard(board.get("cards").getAsJsonArray(), board.get("rows").getAsInt(), board.get("cols").getAsInt(), BoardPrinter.Role.SPYMASTER);
        JsonArray teamNames = teamsInfo.get("teams").getAsJsonArray();
        //List<String> teamScores = teamsInfo.getTeamsCurrentScore();
        JsonArray teams = game.get("teams").getAsJsonArray();
        List<String> teamsScores = getTeamsScore(teams);
        List<Integer> teamTurnCounter = getTeamsTurnCounter(teams);
        System.out.println("Teams Information:");
        for(int i = 0; i < teams.size(); i++){
            System.out.println("Team #" + (i+1));
            System.out.println(" - Name: " + teamNames.get(i).getAsJsonObject().get("name").getAsString());
            System.out.println(" - Score: " + teamsScores.get(i));
            System.out.println(" - Turns played: " + teamTurnCounter.get(i));
        }
        System.out.println("Next turn is " + getNextTeamName(teamsInfo));
    }



    /**
     * Prints an overview of the game details, including the total number of words available for play,
     * both regular and black words, and the number of each type that will be used in the game.
     * This method provides a clear and concise summary of the game setup, which is useful for players to understand
     * the scope and rules of the game at a glance.
     *
     * @param game The game engine that provides access to the overall game configuration.
     */
    /*public static void printGameData(Engine game) {
        System.out.println("--------------------------");
        System.out.println("Game Details:");
        *//*System.out.println("There are " + game.getRegularWords().size() + " words to choose from.");
        System.out.println("There are " + game.getBlackWords().size() + " black words to choose from.");*//*
        System.out.println("We'll play " + game.getHowManyRegularWordsInGame() + " regular words and " + game.getHowManyBlackWordsInGame() + " black words.");
        for (Team team : game.getTeams()) {
            System.out.println(team);
        }
        System.out.println("--------------------------");
    }*/
}