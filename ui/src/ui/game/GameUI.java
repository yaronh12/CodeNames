package ui.game;

import engine.engine.Engine;
import engine.engine.EngineImpl;
import team.team.Team;
import team.team.TeamsInfo;
import team.turn.Clue;
import team.turn.ClueValidator;
import team.turn.Guess;
import ui.components.BoardPrinter;


import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

import static ui.components.BoardPrinter.displayBoard;

public class GameUI {

    //1. wrap it up under packege
    private Engine game;
    private boolean isGameOver;

    private boolean isGameActive=false;

    private int guesserTurnIndex; //ask aviad for better solution
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
        int numOfOptions;
        while(true) {
            Scanner in = new Scanner(System.in);
            numOfOptions=3;
            System.out.println();
            System.out.println("MAIN MENU:");
            System.out.println("1. Show game details");
            System.out.println("2. Start new game");
            System.out.println("3. Exit system");
            if(isGameActive){
                System.out.println("4. Continue");
                numOfOptions++;
            }
            System.out.println("Please enter number:");


            int userChoice = getValidInteger(1, numOfOptions);

            switch (userChoice) {
                case 1:
                    printGameData();
                    break;
                case 2:
                    if(isGameActive)
                        game = new EngineImpl();
                    else
                        isGameActive = true;
                    isGameOver=false;
                    startGame();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                case 4:
                    startGame();
                    break;
            }
        }
    }

    private int getValidInteger(int lowerLimit, int upperLimit) {
        Scanner scanner = new Scanner(System.in);
        boolean isAnInt = false;
        int userChoice = 0;

        do {
            try {
                userChoice = scanner.nextInt();
                isAnInt = true;
                if ((userChoice < lowerLimit || userChoice > upperLimit)){
                    System.out.println("Error: Please enter a valid number between " +lowerLimit+ " and " + upperLimit+ ".");
                }
            } catch (InputMismatchException ime) {
                scanner.nextLine(); // Consume the invalid input and reset
                System.out.println("Invalid input(not a number). Please enter a valid number.");
            }
        }while ((userChoice < lowerLimit || userChoice > upperLimit) || !isAnInt);

       return userChoice;
    }


    public void startGame(){

        boolean keepPlaying=true;
        while (!this.isGameOver && isGameActive && keepPlaying){
            keepPlaying = startTurnMenu();
        }
        if(isGameOver){
            System.out.println("The winning team is "+this.game.getWinningTeam().getTeamName()+"!!!");
            isGameActive=false;
        }
    }

        private boolean startTurnMenu(){
            System.out.println();
            System.out.println(this.game.getCurrentTeam().getTeamName()+"'s Turn!");
            System.out.println("Please select one of the following optins:");
            System.out.println("1. Play your turn");
            System.out.println("2. Show Active Game Details");
            System.out.println("3. Start new game");
            System.out.println("4. Exit to main menu");
            int userChoice = getValidInteger(1,4);
            switch(userChoice){
                case 1:
                    this.isGameOver = playTeamTurn();
                    this.game.passTurn();
                    break;
                case 2:
                    printActiveGameInfo();
                    break;
                case 3:
                    System.out.println("Starting new game....");
                    game = new EngineImpl();
                    break;
                case 4:
                    return false;

            }
            return true;
        }


    /**
     * Manages a single turn for the current team in the game. This method orchestrates the process of
     * displaying the game board, getting a clue from the Spymaster, and managing guesses from the guessers.
     * It continues to prompt for guesses until the number of guesses allowed (based on the clue) is reached
     * or until a guess ends the turn prematurely (e.g., a wrong guess or a guess on a black card).
     *
     * @return true if the game is over (triggered by a game-ending guess), otherwise false.
     */
    private boolean playTeamTurn(){
        Scanner in = new Scanner(System.in);
        Guess guess;
        Clue clue;

        // Display the game board for the Spymaster view
        displayBoard(game.getBoardState(), game.getBoardRows(), game.getBoardCols(), BoardPrinter.Role.SPYMASTER);

        // Announce the current team's turn and display their score
        System.out.println(this.game.getCurrentTeam().getTeamName()+"'s Turn!");
        System.out.println("Your score is "+this.game.getCurrentTeam().getScore() + " / " + this.game.getCurrentTeam().getCardAmount());

        // Get the clue from the Spymaster
        clue = getSpyMasterClue(in);

        // Inform the guessers of the clue and the number of words they need to guess
        System.out.println("Hello "+this.game.getCurrentTeam().getTeamName() + "'s guessers! your clue is "+clue.getClueWord()+ " and you have "+clue.getNumOfWordToGuess()+" words to guess.");
        System.out.println("if you want to pass this turn, enter 0.");

        // Process guesses until all are made or a guess ends the turn
        this.guesserTurnIndex=0;
        boolean correctGuess = true;

        while (guesserTurnIndex < clue.getNumOfWordToGuess() && correctGuess) {
            guess = askForGuess(guesserTurnIndex+1);
            correctGuess = this.giveGuessResponse(guess);

            if(this.game.isGameOver(guess))
                return true;
        }

        // Provide feedback on the updated score after the turn is complete
        System.out.println("Your updated score is: "+this.game.getCurrentTeam().getScore() + " / " + this.game.getCurrentTeam().getCardAmount());
        return false;
    }

    private Guess askForGuess(int turn){
        Scanner in = new Scanner(System.in);
        int guessIndex;

        // Display the game board for the guesser, indicating where each card is and which ones are still available
        displayBoard(game.getBoardState(), game.getBoardRows(), game.getBoardCols(), BoardPrinter.Role.GUESSER);

        // Prompt the player to enter their guess for the current turn
        System.out.println("guess #" + turn + ": ");

        // Retrieve the guess index entered by the player, adjust for zero-based index used in the game logic
        guessIndex = getValidInteger(1, game.getBoardState().size());

        // Process the guess and return the outcome
        return this.game.makeGuess(guessIndex - 1);

    }



    private boolean giveGuessResponse(Guess guess){
        if(guess.isTurnPassed()){
            System.out.println("Turn passed to the next team.");
            return false;
        }
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
            this.guesserTurnIndex++;
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


    private Clue getSpyMasterClue(Scanner in) {
        String clueWord;
        int amountOfWordsToGuess;
        boolean clueWordIsValid = true;

        System.out.println(this.game.getCurrentTeam().getTeamName() + "'s spymaster, please give a clue. to confirm press Enter.");

        do {
            System.out.println("Please enter a clue word:");
            clueWord = in.nextLine();
            clueWordIsValid = true; // Assume valid unless proven otherwise

            if (ClueValidator.isClueContainSpaces(clueWord)) {
                System.out.println("Error: The clue word must not contain spaces.");
                clueWordIsValid = false;
            } else if (ClueValidator.isClueWordOnBoard(clueWord, game.getBoardState())) {
                System.out.println("Error: The clue word cannot match any word exactly on the board.");
                clueWordIsValid = false;
            } else if (ClueValidator.isClueWordSubStringOfWordOnBoard(clueWord, game.getBoardState())) {
                System.out.println("Error: The clue word cannot be a substring or superstring of any word on the board.");
                clueWordIsValid = false;
            }
        } while (!clueWordIsValid);



        System.out.println("Enter how many guesses: ");
        amountOfWordsToGuess = getValidInteger(1, game.getCurrentTeam().getCardAmount() - game.getCurrentTeam().getScore());

        return new Clue(clueWord, amountOfWordsToGuess);
    }



    private void printActiveGameInfo(){
        TeamsInfo teamsInfo = this.game.getTeamsInfo();
        displayBoard(game.getBoardState(), game.getBoardRows(), game.getBoardCols(), BoardPrinter.Role.SPYMASTER);
        List<String> teamNames = teamsInfo.getTeamNames();
        List<String> teamScores = teamsInfo.getTeamsCurrentScore();
        List<Integer> teamTurnCounter = teamsInfo.getTeamsTurnCounter();
        System.out.println("Teams Information:");
        for(int i=0;i< teamsInfo.getHowManyTeams();i++){
            System.out.println("Team #"+(i+1));
            System.out.println(" - Name: "+teamNames.get(i));
            System.out.println(" - Score: "+teamScores.get(i));
            System.out.println(" - Turns played: "+teamTurnCounter.get(i));
        }
        System.out.println("Next turn is "+teamsInfo.getNextTeamName());
    }
}


