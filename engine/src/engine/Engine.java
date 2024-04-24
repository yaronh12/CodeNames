package engine;

import java.util.List;

public interface Engine {
    void readXmlFile();

    List<String> getRegularWords();
    List<String> getBlackWords();

    int getHowManyRegularWords();
    int getHowManyBlackWords();

    int getBoardRows();
    int getBoardCols();


    void startGame();
}
