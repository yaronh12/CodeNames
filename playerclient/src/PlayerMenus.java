import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import utils.Utils;

public class PlayerMenus {

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
                    JsonObject chosenGameToPlay = pendingGames.get(gameListIndex-1).getAsJsonObject().get("game").getAsJsonObject();
                    JsonObject teamChosen;
                    while((teamChosen = scanTeamToPlay(chosenGameToPlay)) == null){
                        System.out.println("This team is fully booked. Please choose other team.");
                    }
                    int roleChosen;
                    while((roleChosen = scanRoleToPlay(teamChosen)) == -1){
                        System.out.println("This role is fully booked in this team. Please choose other role.");
                    }

                    //KEEP CODE FROM HERE!!!!!
                    //GOT IN HAND THE TEAM IN JSON OBJECT AND THE ROLE PLAY
                    //NEED TO REGISTER THE PLAYER INTO THE GAME!!!!

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

    private int scanPlayerIndexGame(int size){
        System.out.println("Enter the game number you want to play:");
        return Utils.getValidInteger(1,size);
    }

    private JsonObject scanTeamToPlay(JsonObject game){
        int howManyTeams = game.get("teams").getAsJsonArray().size();
        System.out.println("Please enter team index:");
        int teamIndex = Utils.getValidInteger(1,howManyTeams);
        JsonObject teamChosen = game.get("teams").getAsJsonArray().get(teamIndex-1).getAsJsonObject();
        if(teamChosen.get("readyGuessersAmount").getAsInt() == teamChosen.get("guessersAmount").getAsInt() &&
                teamChosen.get("readyDefinersAmount").getAsInt() == teamChosen.get("definersAmount").getAsInt()){
            return null;
        }
        return teamChosen;
    }

    private int scanRoleToPlay(JsonObject team){
        System.out.println("Please choose play role:");
        System.out.println("1. Definer");
        System.out.println("2. Guesser");
        int roleChosen = Utils.getValidInteger(1,2);
        if(roleChosen == 1){
            if(team.get("readyDefinersAmount").getAsInt() == team.get("definersAmount").getAsInt()){
                return -1;
            }
        }
        else{
            if(team.get("readyGuessersAmount").getAsInt() == team.get("guessersAmount").getAsInt()){
                return -1;
            }
        }

        return roleChosen;
    }


}
