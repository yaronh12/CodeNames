package main;

import gameInfo.Data;
import board.Board;
import player.Guesser;
import player.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gameInfo.GameLogic;

public class Game {

    private Data gameinfo = new Data();
    //private Board board;
    private GameLogic gamelogic;
    private boolean gameStatus = true;

    private List<String> teamNames = new ArrayList<>();
    private int agentTurn = 0;
    private int guesserTurn = 1;


    public Game(){
        //this.board = new Board(gameinfo);
        gamelogic = new GameLogic(gameinfo);
        teamNames.add(gameinfo.getTeam1name());
        teamNames.add(gameinfo.getTeam2name());
    }

    //phase one - reading and checking tha data from the XML file
    //and implement that data into the data class for the game
    //maybe will be changed to ctor
    public void readAndCheckXmlFile(){

    }


    //phase 2 - printing the game info to the user
    public  void printGameInfo(){
        System.out.println("Game Details:");
        System.out.println("There are " + gameinfo.getWordsSet().size() + " words to choose from.");
        System.out.println("There are " + gameinfo.getBlackWordsSet().size() + " black words to choose from.");
        System.out.println("We'll play " + gameinfo.getCardsCount() + " regular words and " + gameinfo.getBlackCardsCount() + " black words.");
        System.out.println(gameinfo.getTeam1name()+" have "+gameinfo.getTeam1cards()+" words," +
                            " and "+gameinfo.getTeam2name()+" have "+gameinfo.getTeam2cards()+" words.");

    }

    //phase 3- starting the game yeeyyyeyeye
    //game loop start here
    public void startGame(){
        //phase 4- will be implemented here
        String currentTeam = this.teamNames.get(0);
        Scanner scanner = new Scanner(System.in);
        int amountOfWordsToGuess;
        while(gameStatus) {
            //print board for agent
            amountOfWordsToGuess = this.giveClue(currentTeam);
            for(int i=0; i<amountOfWordsToGuess; i++){
                if(this.gamelogic.wordsLeftToGuess(currentTeam))

            }


        }
    }

    private int giveClue(String currentTeam){
        Scanner scanner = new Scanner(System.in);
        int amountOfWordsToGuess;
        System.out.println(currentTeam + " please give a clue:");
        System.out.println("The clue word:");
        scanner.nextLine();
        System.out.println("How many words to guess?");
        amountOfWordsToGuess = scanner.nextInt();
        return amountOfWordsToGuess;
    }




    //phase 5- this method will print the game status when and ony when tha game is active
    public void gameStatus(){

    }

    //phase 6 - end game and exit the system
    public void endGame(){

    }
    
}
