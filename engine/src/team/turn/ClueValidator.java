package team.turn;
import components.card.Card;

import java.util.*;


/**
 * This class provides methods to validate clue words in the game of Codenames.
 * It ensures that clues meet the game's basic rules for word selection, such as
 * not being a word currently visible on the board, not containing spaces, and
 * not being a substring or superstring of any word on the board.
 */
public class ClueValidator {

    /**
     * Checks if the clue word contains spaces.
     *
     * @param clueWord The clue word to check.
     * @return true if the clue word contains spaces, false otherwise.
     */
    public static boolean isClueContainSpaces(String clueWord) {
        return clueWord.contains(" ");
    }

    /**
     * Checks if the clue word matches any word on the game board exactly.
     *
     * @param clueWord The clue word to be validated.
     * @param cards A list of Card objects representing the words currently on the board.
     * @return true if the clue word is found among the board words, false otherwise.
     */
    public static boolean isClueWordOnBoard(String clueWord, List<Card> cards) {
        String normalizedClue = clueWord.toLowerCase();
        for (Card card : cards) {
            if (card.getWord().equalsIgnoreCase(normalizedClue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the clue word is a substring of any board words or vice versa.
     * This method is optional and can be used depending on the strictness of the game rules.
     *
     * @param clueWord The clue word to be validated.
     * @param cards A list of Card objects representing the words currently on the board.
     * @return true if the clue word is a substring or superstring of any word on the board, false otherwise.
     */
    public static boolean isClueWordSubStringOfWordOnBoard(String clueWord, List<Card> cards) {
        String normalizedClue = clueWord.toLowerCase();
        for (Card card : cards) {
            if (normalizedClue.contains(card.getWord().toLowerCase()) || card.getWord().toLowerCase().contains(normalizedClue)) {
                return true;
            }
        }
        return false;
    }
}
