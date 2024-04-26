package main;

import ui.GameUI;

public class Main {

    public static void main(String[] args) {
        GameUI game = new GameUI();
        game.startSystem();
        while(true){
            game.mainMenu();
        }



        //game.printGameData();
        //game.printCards();

    }


}
