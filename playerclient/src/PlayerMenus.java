import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ui.BoardPrinter;
import utils.Utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PlayerMenus {

    private int chosenGameIndex;
    private int chosenTeamIndex;
    private BoardPrinter.Role role;




    public boolean playerMainMenu(){
        System.out.println("------ Main Menu ------");
        System.out.println("1. Watch all games details");
        System.out.println("2. Join Game");
        System.out.println("3. Exit System");
        System.out.println("Please choose an option:");
        int playerMainMenuChoice = Utils.getValidInteger(1,3);
        boolean isPlayerWantsToStay = true;
        switch(playerMainMenuChoice){
            case 1:
                GetGamesDetails.getGamesDetails();
                break;
            case 2:
                JsonArray pendingGames = PendingGamesDisplay.displayPendingGames();
                if(pendingGames != null){
                    int gameListIndex = scanPlayerIndexGame(pendingGames.size());
                    //chosenGameIndex = gameListIndex - 1;
                    JsonObject chosenGameToPlay = pendingGames.get(gameListIndex-1).getAsJsonObject().get("game").getAsJsonObject();
                    chosenGameIndex = pendingGames.get(gameListIndex-1).getAsJsonObject().get("gameIndex").getAsInt();
                    while((chosenTeamIndex = scanTeamToPlay(chosenGameToPlay)) == -1){
                        System.out.println("This team is fully booked. Please choose other team.");
                    }
                    JsonObject team = chosenGameToPlay.get("teams").getAsJsonArray().get(chosenTeamIndex).getAsJsonObject();
                    BoardPrinter.Role roleChosen;
                    while((roleChosen = scanRoleToPlay(team)) == null){
                        System.out.println("This role is fully booked in this team. Please choose other role.");
                    }
                    role = roleChosen;
                    RegisterToGame.registerToGame(chosenGameIndex,chosenTeamIndex,role);
                    inGameMenu();


                }

                break;
            case 3:
                isPlayerWantsToStay = false;
                break;
            default:
                break;
        }
        return isPlayerWantsToStay;
    }

    private boolean inGameMenu(){
        boolean isGameOver = false;
        while(!isGameOver){
            System.out.println("------ Game Menu ------");
            System.out.println("Please choose option:");
            System.out.println("1. Display game status");
            System.out.println("2. Play your turn");
            int optionChosen = Utils.getValidInteger(1,2);
            if(optionChosen == 1){
                isGameOver = LiveGameStatus.getLiveGameStatus(chosenGameIndex,chosenTeamIndex,role);
            }
            else{
                isGameOver = PlayTurn.askToPlayTurn(role,chosenGameIndex, chosenTeamIndex);
            }
        }


        return true;
    }

    private int scanPlayerIndexGame(int size){
        System.out.println("Enter the game number you want to play:");
        return Utils.getValidInteger(1,size);
    }

    private int scanTeamToPlay(JsonObject game){
        int howManyTeams = game.get("teams").getAsJsonArray().size();
        System.out.println("Please enter team index:");
        int teamIndex = Utils.getValidInteger(1,howManyTeams);
        JsonObject teamChosen = game.get("teams").getAsJsonArray().get(teamIndex-1).getAsJsonObject();
        if(teamChosen.get("readyGuessersAmount").getAsInt() == teamChosen.get("guessersAmount").getAsInt() &&
                teamChosen.get("readyDefinersAmount").getAsInt() == teamChosen.get("definersAmount").getAsInt()){
            return -1;
        }
        return teamIndex-1;
    }

    private BoardPrinter.Role scanRoleToPlay(JsonObject team){
        System.out.println("Please choose play role:");
        System.out.println("1. Definer");
        System.out.println("2. Guesser");
        int roleChosen = Utils.getValidInteger(1,2);
        if(roleChosen == 1){
            if(team.get("readyDefinersAmount").getAsInt() == team.get("definersAmount").getAsInt()){
                return null;
            }
            return BoardPrinter.Role.SPYMASTER;
        }
        else{
            if(team.get("readyGuessersAmount").getAsInt() == team.get("guessersAmount").getAsInt()){
                return null;
            }
            return BoardPrinter.Role.GUESSER;
        }

        //return roleChosen;
    }


}
