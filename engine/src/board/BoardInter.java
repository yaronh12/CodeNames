package board;

import word_card.WordCard;

import java.util.List;
import java.util.Set;

public interface BoardInter {
    List<WordCard> getAllWords();
    List<Integer> getBoardDimensions();
    List<String> getTeamNames();


}
