package ui.game;

import engine.engine.Engine;
import team.turn.Clue;
import team.turn.ClueValidator;
import team.turn.Guess;
import ui.display.BoardPrinter;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static ui.display.BoardPrinter.displayBoard;
import static ui.menu.Menus.getValidInteger;


/**
 * Manages the turn-based gameplay interactions within the game, including receiving clues from the Spymaster,
 * processing guesses from players, and managing the game state based on these interactions.
 */
public class TurnManager {

    /**
     * Coordinates a single turn in the game, handling the clue acquisition, guess processing,
     * and determining if the game should end based on the guess outcomes.
     *
     * @param game The game engine that provides access to game data and state management.
     * @param guesserTurnIndex An AtomicInteger to track the number of guesses made during the current turn.
     * @return boolean True if the game ends during this turn, otherwise false.
     */
    public static boolean playTeamTurn(Engine game, AtomicInteger guesserTurnIndex) {
        Scanner in = new Scanner(System.in);

        displayBoard(game.getBoardState(), game.getBoardRows(), game.getBoardCols(), BoardPrinter.Role.SPYMASTER);
        System.out.println(game.getCurrentTeam().getTeamName() + "'s Turn!");
        System.out.println("Your score is " + game.getCurrentTeam().getScore() + " / " + game.getCurrentTeam().getCardAmount());

        Clue clue = getSpyMasterClue(in, game);
        System.out.println("Hello " + game.getCurrentTeam().getTeamName() + "'s guessers! your clue is " + clue.getClueWord() + " and you have " + clue.getNumOfWordToGuess() + " words to guess.");
        System.out.println("if you want to pass this turn, enter 0.");

        guesserTurnIndex.set(0);
        boolean correctGuess = true;

        while (guesserTurnIndex.get() < clue.getNumOfWordToGuess() && correctGuess) {
            Guess guess = askForGuess(guesserTurnIndex.get() + 1, game);
            correctGuess = giveGuessResponse(guess, guesserTurnIndex);

            if (game.isGameOver(guess))
                return true;
        }

        System.out.println("Your updated score is: " + game.getCurrentTeam().getScore() + " / " + game.getCurrentTeam().getCardAmount());
        return false;
    }

    /**
     * Prompts the player for a guess based on the game state and the current turn number.
     *
     * @param turn The turn number for which the guess is being made.
     * @param game The game engine to access current game state.
     * @return Guess The guess object representing the player's guess.
     */
    private static Guess askForGuess(int turn, Engine game) {
        displayBoard(game.getBoardState(), game.getBoardRows(), game.getBoardCols(), BoardPrinter.Role.GUESSER);
        System.out.println("Guess #" + turn + ": ");
        int guessIndex = getValidInteger(0, game.getBoardState().size());
        return game.makeGuess(guessIndex - 1);
    }

    /**
     * Evaluates the guess made by a player and determines if the game turn should continue.
     *
     * @param guess The guess made by the player.
     * @param guesserTurnIndex The index used to track the number of valid guesses.
     * @return boolean True if the guess is valid and the turn should continue, false otherwise.
     */
    private static boolean giveGuessResponse(Guess guess, AtomicInteger guesserTurnIndex) {
        if (guess.isTurnPassed()) {
            System.out.println("Turn passed to the next team.");
            return false;
        }
        if (!guess.isWordOnBoard()) {
            System.out.println("Word is not on board. Please make another guess.");
            return true;
        }
        if (guess.isWordAlreadyFound()) {
            System.out.println("This word has already been discovered! Please make another guess.");
            return true;
        }
        if (guess.isGuessedForOtherTeam()) {
            System.out.println("Wrong! This word belongs to " + guess.getTeamNameOnCard());
            return false;
        }
        if (guess.isGuessCorrect()) {
            System.out.println("Correct!");
            guesserTurnIndex.getAndIncrement();
            return true;
        }
        if (guess.isGuessedWordBlack()) {
            System.out.println("Black word! You lose!");
            return false;
        }
        if (guess.isGuessedWordWithoutTeam()) {
            System.out.println("Wrong! This word belongs to no team.");
            return false;
        }
        return true;
    }

    /**
     * Retrieves a clue from the Spymaster by validating input against game rules.
     *
     * @param in The Scanner used to take input from the console.
     * @param game The game engine which maintains the current state and validates clues.
     * @return Clue A valid clue created based on Spymaster input.
     */
    private static Clue getSpyMasterClue(Scanner in, Engine game) {
        String clueWord;
        int amountOfWordsToGuess;
        boolean clueWordIsValid;

        System.out.println(game.getCurrentTeam().getTeamName() + "'s spymaster, please give a clue. To confirm press Enter.");

        do {
            System.out.println("Please enter a clue word:");
            clueWord = in.nextLine();
            clueWordIsValid =/* !ClueValidator.isClueContainSpaces(clueWord) &&*/
                    !ClueValidator.isClueWordOnBoard(clueWord, game.getBoardState()) &&
                    !ClueValidator.isClueWordSubStringOfWordOnBoard(clueWord, game.getBoardState());
            if (!clueWordIsValid) {
                System.out.println("Error: The clue word must not contain spaces, cannot match or be part of any word on the board.");
            }
        } while (!clueWordIsValid);

        System.out.println("Enter how many guesses: ");
        amountOfWordsToGuess = getValidInteger(1, game.getCurrentTeam().getCardAmount() - game.getCurrentTeam().getScore());
        return new Clue(clueWord, amountOfWordsToGuess);
    }
}