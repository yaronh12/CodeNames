package engine.data.validators;

import engine.data.exception.CardCountException;
import engine.data.exception.InsufficientTableSizeException;
import engine.data.exception.TeamCardLimitExceededException;
import engine.data.exception.TeamNamesNotUniqueException;
import generated.ECNGame;
import generated.ECNTeam1;
import generated.ECNTeam2;

import java.util.Arrays;
import java.util.Optional;
public class Validators {
    /**
     * Validates that the number of cards on the board does not exceed the number of available game words.
     *
     * @param gameInfo The game information containing all relevant data structures.
     * @throws CardCountException If the number of cards exceeds the number of available words.
     */
    public static void validateCardCounts(ECNGame gameInfo) throws RuntimeException {
        int cardsCount = gameInfo.getECNBoard().getCardsCount();
        long availableWordsCount = Arrays.stream(Optional.ofNullable(gameInfo.getECNWords().getECNGameWords())
                        .orElse("").split(" "))
                .filter(word -> !word.isEmpty() && !word.equals("\n"))
                .distinct()
                .count();

        if (cardsCount > availableWordsCount) {
            throw new CardCountException("The number of cards on the board (" + cardsCount +
                    ") exceeds the number of available game words (" + availableWordsCount + ").");
        }
    }

    /**
     * Validates that the number of black cards on the board does not exceed the number of available black words.
     *
     * @param gameInfo The game information containing all relevant data structures.
     * @throws CardCountException If the number of black cards exceeds the number of available black words.
     */
    public static void validateBlackCardCounts(ECNGame gameInfo) throws RuntimeException {
        int blackCardsCount = gameInfo.getECNBoard().getBlackCardsCount();
        long availableBlackWordsCount = Arrays.stream(Optional.ofNullable(gameInfo.getECNWords().getECNBlackWords())
                        .orElse("").split(" "))
                .filter(word -> !word.isEmpty() && !word.equals("\n"))
                .distinct()
                .count();

        if (blackCardsCount > availableBlackWordsCount) {
            throw new CardCountException("The number of black cards on the board (" + blackCardsCount +
                    ") exceeds the number of available black words (" + availableBlackWordsCount + ").");
        }
    }

    /**
     * Validates that the combined card count of all teams does not exceed the total number of cards on the board.
     *
     * @param gameInfo The game information containing all relevant data structures.
     * @throws TeamCardLimitExceededException If the combined card count exceeds the total available cards.
     */
    public static void validateTeamTotalCardAmount(ECNGame gameInfo) throws RuntimeException {
        int cardsCount = gameInfo.getECNBoard().getCardsCount();
        int teamsTotalCardAmount = Optional.ofNullable(gameInfo.getECNTeam1()).map(ECNTeam1::getCardsCount).orElse(0) +
                Optional.ofNullable(gameInfo.getECNTeam2()).map(ECNTeam2::getCardsCount).orElse(0);

        if (teamsTotalCardAmount > cardsCount) {
            throw new TeamCardLimitExceededException("The combined total card amount of the teams (" + teamsTotalCardAmount +
                    ") exceeds the total available cards (" + cardsCount + ").");
        }
    }

    /**
     * Validates that the board size matches the combined total of regular and black cards.
     *
     * @param gameInfo The game information containing all relevant data structures.
     * @throws InsufficientTableSizeException If the board size does not match the total number of cards.
     */
    public static void validateBoardSize(ECNGame gameInfo) throws RuntimeException {
        int boardSize = gameInfo.getECNBoard().getECNLayout().getRows() * gameInfo.getECNBoard().getECNLayout().getColumns();
        int cardsCount = gameInfo.getECNBoard().getCardsCount();
        int blackCardsCount = gameInfo.getECNBoard().getBlackCardsCount();

        if (boardSize != cardsCount + blackCardsCount) {
            throw new InsufficientTableSizeException("Board size mismatch: The total number of cells on the board (" + boardSize +
                    ") does not equal the combined total of regular cards and black cards (" +
                    (cardsCount + blackCardsCount) + ").");
        }
    }

    /**
     * Validates that the names of the two teams are unique.
     *
     * @param gameInfo The game information containing all relevant data structures.
     * @throws TeamNamesNotUniqueException If both teams have the same name.
     */
    public static void validateTeamNames(ECNGame gameInfo) throws RuntimeException {
        String team1Name = Optional.ofNullable(gameInfo.getECNTeam1()).map(ECNTeam1::getName).orElse("");
        String team2Name = Optional.ofNullable(gameInfo.getECNTeam2()).map(ECNTeam2::getName).orElse("");

        if (team1Name.equals(team2Name)) {
            throw new TeamNamesNotUniqueException("The names of the two teams are identical, which is not allowed." +
                    " Both teams are named '" + team1Name + "'.");
        }
    }
}
