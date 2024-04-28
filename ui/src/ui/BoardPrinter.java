package ui;

import components.Card;

import java.util.*;

public class BoardPrinter {

    public enum Role {
        SPYMASTER, GUESSER
    }


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



    // Display board with role-based output and ensure word is printed above the format line
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

    // Helper method to format strings to a specific width, centering the text
    private static String formatStringToWidth(String input, int width) {
        int padSize = (width - input.length()) / 2;
        String paddedString = String.format("%" + (input.length() + padSize) + "s", input);
        paddedString = String.format("%-" + width + "s", paddedString);
        return paddedString;
    }

    private static List<CardToString> formatCards(List<Card> cards, Role role) {
        List<CardToString> formattedCards = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            formattedCards.add(printCard(cards.get(i), i + 1, role));
        }
        return formattedCards;
    }

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

