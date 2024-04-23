package board;

import java.util.List;
import java.util.Set;

public interface BoardInter {
    Set<String> getAllWords();

    List<Integer> getBoardDimensions();

    List<String> getTeamNames();


}
