package ui.display;

import components.card.Card;

import java.util.*;

/**
 * Handles the printing of the game board on the console, providing different views based on the user's role,
 * such as SPYMASTER or GUESSER. This class formats and displays the board dynamically based on the content
 * and size of the game board.
 */
public class BoardPrinter {

    /**
     * Enum defining the different roles that can view the board, which affects how the board is displayed.
     */
    public enum Role {
        SPYMASTER, GUESSER
    }


    /**
     * Prints a single card with formatting based on the viewer's role.
     *
     * @param card  The card to be printed.
     * @param index The index of the card on the board, used for display purposes.
     * @param role  The viewer's role which determines the format of the display.
     * @return      A formatted string representation of the card.
     */
    public static CardToString printCard(Card card, int index, Role role) {
        String word = card.getWord();
        String wordFound = card.isFound() ? "V" : "X";
        String formatLine;

        if (role == Role.SPYMASTER) {
            // Check if the card is associated with a team
            if (!card.getTeamName().isEmpty()) {
                formatLine = "[" + index + "]" + " " + wordFound + " (" + card.getTeamName() + ")";
            } else {
                formatLine = "[" + index + "]" + " " + wordFound;
            }
        }
        else
            formatLine = "[" + index + "]" + " " + wordFound;

        return new CardToString(word, formatLine);
    }



    /**
     * Displays the entire game board, adjusting the spacing and alignment based on the maximum width of
     * cards in each column to ensure a clean layout.
     *
     * @param cards The list of cards to display.
     * @param rows  The number of rows in the board layout.
     * @param cols  The number of columns in the board layout.
     * @param role  The role of the viewer, affecting how cards are displayed.
     */
    public static void displayBoard(List<Card> cards, int rows, int cols, Role role) {
        int index = 0;
        int[] maxWidths = new int[cols]; // Array to hold the maximum width needed for each column
        List<CardToString> formattedCards = new ArrayList<>();

        formattedCards = formatCards(cards, role);

        // First pass: calculate the maximum width needed for each column

        maxWidths = calculateMaxWidths(formattedCards, rows, cols);


        index = 0; // Reset index for the second pass
        // Second pass: print each row with proper spacing
        for (int i = 0; i < rows; i++) {
            // Print words with correct padding
            for (int j = 0; j < cols; j++) {
                if (index < formattedCards.size()) {
                    CardToString card = formattedCards.get(index++);
                    System.out.print(formatStringToWidth(card.word, maxWidths[j]));
                }
            }
            System.out.println();

            index -= cols; // Reset index to the start of the current row for format lines
            // Print format lines with correct padding
            for (int j = 0; j < cols; j++) {
                if (index < formattedCards.size()) {
                    CardToString card = formattedCards.get(index++);
                    System.out.print(formatStringToWidth(card.formatLine, maxWidths[j]));
                }
            }
            System.out.println();
            System.out.println(); // Extra newline for spacing between rows
        }
    }

    /**
     * Helper method to format strings to a specified width by centering text within the width.
     *
     * @param input The string to format.
     * @param width The width within which to center the string.
     * @return      The centered string.
     */
    private static String formatStringToWidth(String input, int width) {
        int padSize = (width - input.length()) / 2;
        String paddedString = String.format("%" + (input.length() + padSize) + "s", input);
        paddedString = String.format("%-" + width + "s", paddedString);
        return paddedString;
    }

    /**
     * Formats a list of cards into a list of string representations suitable for printing.
     *
     * @param cards The list of cards to format.
     * @param role  The viewer's role.
     * @return      A list of formatted card strings.
     */
    private static List<CardToString> formatCards(List<Card> cards, Role role) {
        List<CardToString> formattedCards = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            formattedCards.add(printCard(cards.get(i), i + 1, role));
        }
        return formattedCards;
    }

    /**
     * Calculates the maximum widths needed for each column based on the longest card string in each column,
     * ensuring proper alignment when printed.
     *
     * @param formattedCards The formatted card strings.
     * @param rows           The number of rows in the board layout.
     * @param cols           The number of columns in the board layout.
     * @return               An array of maximum widths for each column.
     */
    private static int[] calculateMaxWidths(List<CardToString> formattedCards, int rows, int cols) {
        int index = 0;
        int[] maxWidths = new int[cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols && index < formattedCards.size(); j++) {
                CardToString card = formattedCards.get(index++);
                int maxCardWidth = Math.max(card.word.length(), card.formatLine.length());
                maxWidths[j] = Math.max(maxWidths[j], maxCardWidth + 4);  // Add 4 for padding
            }
        }
        return maxWidths;
    }
}

