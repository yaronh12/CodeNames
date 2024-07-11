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
                break;
            case 3:
                isPlayerWantsToStay = false;
                break;
            default:
                break;
        }
        return isPlayerWantsToStay;
    }
}
