package engine.data.validators;

import engine.data.exeption.CardCountException;
import generated.ECNGame;

import java.util.Arrays;
import java.util.Optional;

public class validators {
    public static void validateCardCounts(ECNGame gameInfo) throws RuntimeException{

        int cardsCount = gameInfo.getECNBoard().getCardsCount();
        long availableWordsCount = Arrays.stream(Optional.ofNullable(gameInfo.getECNWords().getECNGameWords())
                        .orElse("").split(" "))
                        .filter(word-> !word.equals("\n"))
                        .distinct()
                        .count();

        if (cardsCount > availableWordsCount){
            throw new CardCountException("The number of cards on the board (" + cardsCount + ") exceeds the number of available game words ("
                    + availableWordsCount + ").");
        }
    }
    public static void validateBlackCardCounts(ECNGame gameInfo) throws RuntimeException{

    }
    public static void validateTeamTotalCardAmount(ECNGame gameInfo) throws RuntimeException{

    }
    public static void validateBoardSize(ECNGame gameInfo) throws RuntimeException{

    }
    public static void validateTeamNames(ECNGame gameInfo) throws RuntimeException{

    }
}
