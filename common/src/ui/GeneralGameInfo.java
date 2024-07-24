package ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static ui.BoardPrinter.displayBoard;


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

    public static void printAllGamesInfo(JsonArray gamesList, Utils.UserType userType){
        JsonObject currGame;
        for(int i=0;i<gamesList.size();i++){
            currGame = gamesList.get(i).getAsJsonObject();
            printGame(currGame, Utils.UserType.ADMIN,i);
        }
    }

    private static void printGame(JsonObject currGame, Utils.UserType userType, int gameIndex){
        System.out.println("------Game #"+(gameIndex+1)+"------");
        System.out.println("Name: "+currGame.get("gameName").getAsString());
        System.out.println("Status: "+(currGame.get("isGameActive").getAsBoolean() ? "Active" : "Pending"));
        System.out.println("Board Layout: "+currGame.get("board").getAsJsonObject().get("rows").getAsInt() + " X " +
                currGame.get("board").getAsJsonObject().get("cols").getAsInt());
        System.out.println("Text file: " + currGame.get("txtFileName").getAsString() + " contains "+
                currGame.get("totalWordsInFile").getAsInt() + " unique words.");
        System.out.println("Regular words: "+currGame.get("board").getAsJsonObject().get("regularWordsAmount").getAsInt()
                +", Black words: "+currGame.get("board").getAsJsonObject().get("blackWordsAmount").getAsInt());
        JsonArray teamsList = currGame.get("teams").getAsJsonArray();
        JsonObject currTeam;
        for(int j=0;j<teamsList.size();j++){
            currTeam = teamsList.get(j).getAsJsonObject();
            System.out.println("Team #"+(j+1)+":");
            System.out.println("\tName: " + currTeam.get("name").getAsString());
            System.out.println("\tWords Amount: " + currTeam.get("cardsAmount").getAsInt());
            if(userType == Utils.UserType.PLAYER){
                System.out.println("\tDefiners: "+ currTeam.get("readyDefinersAmount").getAsInt() + " / " + currTeam.get("definersAmount").getAsInt());
                System.out.println("\tGuessers: "+ currTeam.get("readyGuessersAmount").getAsInt() + " / " + currTeam.get("guessersAmount").getAsInt());
            }
            else{
                System.out.println("\tDefiners amount: " + currTeam.get("definersAmount").getAsInt());
                System.out.println("\tGuessers amount: " + currTeam.get("guessersAmount").getAsInt());
            }

        }
    }

    public static JsonArray printAllActiveGames(JsonArray activeGamesJson){
        if(!activeGamesJson.isEmpty()) {
            JsonObject currGame;
            for (int i = 0; i < activeGamesJson.size(); i++) {
                currGame = activeGamesJson.get(i).getAsJsonObject();
                System.out.println("Game #" + (i + 1) + ":");
                System.out.println("\tName: " + currGame.get("gameName").getAsString());
                System.out.println("\t" + currGame.get("readyTeamsAmount").getAsInt() + " / " +
                        currGame.get("totalTeamsAmount").getAsInt() + " teams are ready to play");

            }
            return activeGamesJson;
        }
        else{
            System.out.println("No active games!");
            return null;
        }
    }

    public static void printAllPendingGamesInfo(JsonArray pendingGamesJson) {
        if(!pendingGamesJson.isEmpty()){
            JsonObject currGame;
            for(int i=0;i< pendingGamesJson.size();i++){
                currGame = pendingGamesJson.get(i).getAsJsonObject().get("game").getAsJsonObject();
                printGame(currGame, Utils.UserType.PLAYER,i);
            }
        }
        else{
            if(pendingGamesJson.isEmpty())
                System.out.println("No pending games in system.");
            //System.out.println("All games are active. No pending games.");
        }
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
        System.out.println("Now Playing: " + teams.get(teamsInfo.get("currentTeamIndex").getAsInt()).getAsJsonObject().get("name").getAsString());
        System.out.println("Next turn is " + getNextTeamName(teamsInfo));
    }

    public static void printLiveGameStatus(JsonObject game, BoardPrinter.Role role){
        System.out.println("------ Game Status ------");
        System.out.println("1. Status: " + ((game.get("isGameActive").getAsBoolean()) ? "Active" : "Pending"));
        JsonObject teamsInfo = game.get("teamsInfo").getAsJsonObject();
        JsonObject currTeam = teamsInfo.get("teams").getAsJsonArray().get(teamsInfo.get("currentTeamIndex").getAsInt()).getAsJsonObject();
        System.out.println("2. Current team: " + currTeam.get("name").getAsString());
        System.out.println("\tscore: "+currTeam.get("score").getAsInt() +" / "+currTeam.get("cardsAmount").getAsInt());
        System.out.println("\tturns played: "+currTeam.get("turnCounter").getAsInt());
        int nextTeamIndex = (teamsInfo.get("currentTeamIndex").getAsInt() + 1) % teamsInfo.get("teams").getAsJsonArray().size();
        JsonObject nextTeam = teamsInfo.get("teams").getAsJsonArray().get(nextTeamIndex).getAsJsonObject();
        System.out.println("3. Next team to play: " + nextTeam.get("name").getAsString());
        JsonObject board = game.get("board").getAsJsonObject();
        JsonArray cards = board.get("cards").getAsJsonArray();
        int rows = board.get("rows").getAsInt();
        int cols = board.get("cols").getAsInt();
        if(game.get("isGameActive").getAsBoolean())
            displayBoard(cards,rows,cols, role);



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