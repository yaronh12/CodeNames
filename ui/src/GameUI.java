import engine.Engine;
import engine.EngineImpl;
import team.Team;
import components.*;

import java.util.Scanner;

public class GameUI {
    private Engine game = new EngineImpl();

    public void printGameData(){
        System.out.println("Game Details:");
        System.out.println("There are " + game.getRegularWords().size() + " words to choose from.");
        System.out.println("There are " + game.getBlackWords().size() + " black words to choose from.");
        System.out.println("We'll play " + game.getHowManyRegularWordsInGame() + " regular words and " + game.getHowManyBlackWordsInGame() + " black words.");
        for(Team team: game.getTeams()){
            System.out.println(team);
        }

    }
    public void printCards(){
        for (int i = 0; i < game.getBoardState().size(); i++) {
            System.out.println((i+1)+". "+game.getBoardState().get(i).toString());
        }
        System.out.println("There are " + game.getBoardState().size() + " cards");

    }

    public void startSystem(){
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to CodeNames!");
        System.out.println("Press Enter to load the Game.");
        in.nextLine();
    }

    public void mainMenu(){
        Scanner in = new Scanner(System.in);
        System.out.println("1. Show game details");
        System.out.println("2. Start new game");
        System.out.println("3. Exit system");
        System.out.println("Please enter number:");
        int num = in.nextInt();
        switch(num){
            case 1:
                printGameData();
                break;
            case 2:
                startGame();
                break;
            case 3:
                //exitGame();
                break;
        }
    }

    public void startGame(){

    }
}