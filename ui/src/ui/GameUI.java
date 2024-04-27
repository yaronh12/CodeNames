package ui;

import engine.Engine;
import engine.EngineImpl;
import team.*;
import components.*;


import java.util.List;
import java.util.Scanner;

public class GameUI {

    //1. wrap it up under packege
    private Engine game;
    private boolean isGameOver;
    public void printGameData(){
        System.out.println("--------------------------");
        System.out.println("Game Details:");
        System.out.println("There are " + game.getRegularWords().size() + " words to choose from.");
        System.out.println("There are " + game.getBlackWords().size() + " black words to choose from.");
        System.out.println("We'll play " + game.getHowManyRegularWordsInGame() + " regular words and " + game.getHowManyBlackWordsInGame() + " black words.");
        for(Team team: game.getTeams()){
            System.out.println(team);
        }
        System.out.println("--------------------------");


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
        game = new EngineImpl();
    }


    public void mainMenu(){
        Scanner in = new Scanner(System.in);
        System.out.println("1. Show game details");
        System.out.println("2. Start new game");
        System.out.println("3. Exit system");
        System.out.println("Please enter number:");
        int userChoice = in.nextInt();
        switch(userChoice){
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

    public void printAgentBoard(){
        int rows = game.getBoardRows();
        int cols = game.getBoardCols();
        List<Card> cards = game.getBoardState();
        int cardIndex = 0;
        for (int i = 0; i < rows; i++) {
            System.out.print("|  ");
            for (int j = 0; j < cols; j++) {
                if(cardIndex+1<10)
                    System.out.print(" ");
                System.out.printf("%d. %-20s", (cardIndex+1), cards.get(cardIndex).getWord());
                cardIndex++;
            }
            System.out.println("|");
        }
    }

    public void startGame(){
        this.isGameOver = false;
        while (!this.isGameOver){
            for (Team team: game.getTeams()){
                this.isGameOver = playTeamTurn(team);
                this.game.passTurn();
            }
        }
    }

    private boolean playTeamTurn(Team currentTeam){
        this.printAgentBoard();
        //******* print agents board ********

        int amountOfWordsToGuess;
        String clue;
        String guessWord;
        Guess guess;
        Scanner in = new Scanner(System.in);
        System.out.println(this.game.getCurrentTeamName() + "'s agent, please give a clue. to confirm press Enter.");
        System.out.println("Enter the clue: ");
        clue = in.nextLine();
        System.out.println("Enter how many guesses: ");
        amountOfWordsToGuess = in.nextInt();
        in.nextLine();
        //******* print guessers board **********
        this.printAgentBoard();
        System.out.println("Hello "+this.game.getCurrentTeamName() + "'s guessers! your clue is "+clue+ "and you have "+amountOfWordsToGuess+" words to guess.");
        int guesserTurnIndex=0;
        boolean correctGuess = true;
        while (guesserTurnIndex < amountOfWordsToGuess && correctGuess) {
            System.out.println("guess #"+(guesserTurnIndex+1)+": ");
            guessWord = in.nextLine();
            correctGuess = this.giveGuessResponse(this.game.makeGuess(guessWord));
        }

        return correctGuess;
    }

    private boolean giveGuessResponse(Guess guess){
        if(!guess.isWordOnBoard()){
            System.out.println("word is not on board. please make another guess.");
            return true;
        }
        if(guess.isWordAlreadyFound()){
            System.out.println("this word has already discovered! please make another guess.");
            return true;
        }
        if(guess.isGuessedForOtherTeam()){
            System.out.println("Wrong! this word belongs to "+guess.getTeamNameOnCard());
            return false;
        }
        if(guess.isGuessCorrect()){
            System.out.println("Correct!");
            return true;
        }
        if(guess.isGuessedWordBlack()){
            System.out.println("Black word! you lose!");
            return false;
        }
        if(guess.isGuessedWordWithoutTeam()){
            System.out.println("Wrong! this word belongs to no team.");
            return false;
        }
        return true;
    }
}



