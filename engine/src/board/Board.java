package board;

import word_card.WordCard;

import java.util.*;

public class Board {
    final List<WordCard> allWords = new ArrayList<>();
    final List<Integer> boardDimensions = new ArrayList<>();

    Board() {
        Data data = new Data();

        this.boardDimensions.add(0,data.getRows());
        this.boardDimensions.add(1,data.getCols());

        List<String> words = new ArrayList<>(data.getWordsSet());
        List<String> blackWords = new ArrayList<>(data.getBlackWordsSet());

        Collections.shuffle(words);
        Collections.shuffle(blackWords);

        words=words.subList(0,data.getCardsCount());
        blackWords=blackWords.subList(0,data.getBlackCardsCount());
        int t1Count=0;
        int t2Count=0;
        int i=0;
        while(i < data.getCardsCount()){
            this.allWords.add(i, new WordCard(words.get(i), false));
            if(t1Count <= data.getTeam1cards()){
                this.allWords.get(i).setTeamName(data.getTeam1name());
                t1Count++;
            }
            else{
                if(t2Count <= data.getTeam2cards()){
                    this.allWords.get(i).setTeamName(data.getTeam2name());
                    t2Count++;
                }
                else{
                    this.allWords.get(i).setTeamName("regular");
                }
            }
            i++;
        }
        int j=0;
        while(i<data.getCardsCount() + data.getBlackCardsCount()){
            this.allWords.add(i, new WordCard(blackWords.get(j), true));
            this.allWords.get(i).setTeamName("black");
            i++;
            j++;
        }

        Collections.shuffle(this.allWords);
    }
}
