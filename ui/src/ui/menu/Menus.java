package ui.menu;

import engine.engine.Engine;
import engine.engine.EngineImpl;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.concurrent.atomic.AtomicInteger;

import static ui.game.GameInitializer.loadFile;
import static ui.game.TurnManager.playTeamTurn;
import static ui.display.GeneralGameInfo.*;
/**
 * The GameUI class handles the user interface for the game, managing user interactions,
 * displaying menus, and controlling the flow of the game through various stages.
 */
public class Menus {

    private Engine game = new EngineImpl();
    private boolean isGameOver;
    private boolean isGameActive = false;
    private AtomicInteger guesserTurnIndex = new AtomicInteger();

    /**
     * Initializes the game system and loads necessary data.
     * Welcomes the user and loads the game data from an XML file.
     */
    public void startSystem() {
        System.out.println("Welcome to CodeNames!\n-----------------------------");
       // loadFile(game);
        mainMenu();
    }

    /**
     * Displays the main menu and processes user input to navigate different parts of the game such as starting a new game,
     * loading game data, or exiting the system.
     */
    private void mainMenu(){
        int numOfOptions = 2;
        boolean isFileLoaded = false;
        while(true) {
           //numOfOptions = 2;
            System.out.println();
            System.out.println("MAIN MENU:");
            System.out.println("1. Load XML File");
            if(isFileLoaded){
                System.out.println("2. File details");
                System.out.println("3. Start new game");
                System.out.println("4. Play Turn");
                System.out.println("5. Active game details");
            }
            System.out.println(numOfOptions+". Exit system");

            System.out.println("Please enter number:");

            int userChoice = getValidInteger(1,numOfOptions);

            switch (userChoice) {
                case 1:
                    loadFile(game);
                    isFileLoaded = true;
                    numOfOptions=6;
                    isGameActive = false;
                    break;
                case 2:
                    if(isFileLoaded)
                        printGameData(game);
                    else {
                        System.out.println("Goodbye!");
                        return;
                    }
                    break;
                case 3:
                        game.loadGameData();
                        isGameActive = true;
                        isGameOver = false;
                        System.out.println("New game...");
                    break;
                case 4:
                    if(isGameActive){
                        this.isGameOver = playTeamTurn(game, guesserTurnIndex);
                        if(isGameOver){
                            System.out.println("The winning team is " + this.game.getWinningTeam().getTeamName() + "!!!");
                            isGameActive = false;
                        }
                        this.game.passTurn();
                    }
                    else System.out.println("Start game to see active game details!");
                    break;
                case 5:
                    if(isGameActive)
                         printActiveGameInfo(game);
                    else System.out.println("Start game to see active game details!");
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    return;
               /* case 1:
                    printGameData(game);
                    break;
                case 2:
                    game.loadGameData();
                    isGameActive = true;
                    isGameOver = false;
                    startGame();
                    break;
                case 3:
                    loadFile(game);
                    isGameActive = false;
                    break;
                case 4:
                    if(isGameActive){
                        startGame();
                    }
                    else{
                        System.out.println("Goodbye!");
                         return;
                    }
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    return;*/
            }
        }
    }

    /**
     * Manages the gameplay loop, continuing to play turns until the game is over or the user chooses to exit.
     */
    public void startGame(){
        boolean keepPlaying = true;
        while (!this.isGameOver && isGameActive && keepPlaying){
            keepPlaying = startTurnMenu();
        }
        if(isGameOver){
            System.out.println("The winning team is " + this.game.getWinningTeam().getTeamName() + "!!!");
            isGameActive = false;
        }
    }

    /**
     * Displays turn options and processes user choices during an active game.
     * @return boolean indicating if the game should continue.
     */
    private boolean startTurnMenu(){
        System.out.println();
        System.out.println(this.game.getCurrentTeam().getTeamName() + "'s Turn!");
        System.out.println("Please select one of the following options:");
        System.out.println("1. Play your turn");
        System.out.println("2. Show Active Game Details");
        System.out.println("3. Start new game");
        System.out.println("4. Exit to main menu");
        int userChoice = getValidInteger(1, 4);
        switch(userChoice){
            case 1:
                this.isGameOver = playTeamTurn(game, guesserTurnIndex);
                this.game.passTurn();
                break;
            case 2:
                printActiveGameInfo(game);
                break;
            case 3:
                System.out.println("Starting new game....");
                game.loadGameData();
                break;
            case 4:
                return false;
        }
        return true;
    }

    /**
     * Validates that user input is an integer within the specified range.
     * @param lowerLimit the lower bound of the allowable range.
     * @param upperLimit the upper bound of the allowable range.
     * @return the user's choice as an integer.
     */
    public static int getValidInteger(int lowerLimit, int upperLimit) {
        Scanner scanner = new Scanner(System.in);
        int userChoice = 0;
        boolean isAnInt = false;

        do {
            try {
                userChoice = scanner.nextInt();
                isAnInt = true;
                if (userChoice < lowerLimit || userChoice > upperLimit) {
                    System.out.println("Error: Please enter a valid number between " + lowerLimit + " and " + upperLimit + ".");
                }
            } catch (InputMismatchException ime) {
                scanner.nextLine(); // Consume the invalid input and reset
                System.out.println("Invalid input (not a number). Please enter a valid number.");
            }
        } while (userChoice < lowerLimit || userChoice > upperLimit || !isAnInt);

        return userChoice;
    }
}




