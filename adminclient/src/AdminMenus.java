import okhttp3.*;

import java.io.IOException;
import java.util.Scanner;

public class AdminMenus {

    public void displayMainMenu(){
        int numOfOptions = 1;
        System.out.println("Choose one of the following options: ");
        System.out.println("1. Upload new game file.");
        if(FileUpload.oneFileGotUploaded){
            System.out.println("2. Watch games details");
            System.out.println("3. Watch live game");
            numOfOptions = 3;
        }
        System.out.println("Please enter a number: ");
        int userChoice = Utils.getValidInteger(1,numOfOptions);

        switch(userChoice){
            case 1:
                FileUpload.uploadFile();
                break;
            case 2:
                DisplayGamesDetails.displayGamesDetails();
                break;
            case 3:
                break;
        }

    }


}

