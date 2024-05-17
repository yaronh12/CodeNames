package main;

import ui.menu.Menus;

/**
 * Main class to start the game application. This class initializes the game interface and
 * begins the user interaction process by loading the system and displaying the main menu.
 */
public class Main {

    /**
     * The main method is the entry point for the application. It creates an instance of
     * GameUI, starts the system, and displays the main menu to the user.
     *
     * @param args Command line arguments passed to the application (not used).
     */
    public static void main(String[] args) {

        // Create an instance of GameUI, the main user interface class of the game
        Menus game = new Menus();

        // Initialize the game system and load necessary resources
        game.startSystem();

        // Display the main menu and begin handling user input
       // game.mainMenu();
    }
}
