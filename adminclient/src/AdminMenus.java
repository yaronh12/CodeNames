import com.google.gson.JsonArray;
import okhttp3.*;

import java.io.IOException;
import java.util.Scanner;

public class AdminMenus {


    public boolean displayMainMenu(){
        int numOfOptions = 2;
        System.out.println("Choose one of the following options: ");
        System.out.println("1. Upload new game file.");
        if(FileUpload.oneFileGotUploaded){
            System.out.println("2. Watch games details");
            System.out.println("3. Watch live game");
            numOfOptions = 4;
        }
        System.out.println(numOfOptions + ". Exit System");
        System.out.println("Please enter a number: ");
        int userChoice = Utils.getValidInteger(1,numOfOptions);
        boolean isAdminWantToStay;
        switch(userChoice){
            case 1:
                FileUpload.uploadFile();
                isAdminWantToStay= true;
                break;
            case 2:
                DisplayGamesDetails.displayGamesDetails();
                isAdminWantToStay= true;
                break;
            case 3:
                JsonArray activeGamesList = WatchLiveGame.displayActiveGames();
                if(activeGamesList != null){
                    int adminLiveGameChoice = scanAdminLiveGameChoice(activeGamesList.size());
                    int chosenGameIndex = activeGamesList.get(adminLiveGameChoice).getAsJsonObject().get("gameIndex").getAsInt();
                    liveGameDisplayMenu(chosenGameIndex);
                }
                isAdminWantToStay= true;
                break;
            case 4:
                isAdminWantToStay= false;
                break;
            default:
                isAdminWantToStay=true;
        }
        return isAdminWantToStay;


    }

    private int scanAdminLiveGameChoice(int howManyActiveGames){
        System.out.println("Select game to watch. Please enter game index:");
        int adminChoice = Utils.getValidInteger(1, howManyActiveGames);
        return (adminChoice-1);
    }

    private void liveGameDisplayMenu(int adminChoice){
        boolean isAdminStayInLiveGame = true;
        int adminMenuChoice;
        while(isAdminStayInLiveGame){
            WatchLiveGame.displayLiveGameByChoice(adminChoice);
            System.out.println("-----Live Game Menu-----");
            System.out.println("1. Update live game score");
            System.out.println("2. Exit to main menu");
            System.out.println("Please select an option:");
            adminMenuChoice = Utils.getValidInteger(1,2);
            if(adminMenuChoice == 2)
                isAdminStayInLiveGame = false;
        }
    }


}

