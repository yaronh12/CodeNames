package board;

import word_card.WordCard;

import java.util.*;

public class Board {


        final List<WordCard> allWords = new ArrayList<>();
        final List<Integer> boardDimensions = new ArrayList<>();

    /**
     * Constructs a game board with shuffled word cards assigned to different teams.
     * The board is initialized based on data provided by a Data object which includes
     * the number of rows, columns, total cards, black cards, and team-related information.
     * The constructor sets up the board with these specifications:
     * - Shuffles word cards and black cards separately.
     * - Assigns a portion of the word cards to two teams and assigns any remaining word cards as neutral.
     * - Assigns all black cards as special game-ending cards.
     * - Finally, shuffles the whole set of cards for random board layout.
     */
        public Board() {
            Data data = new Data();

            // Set board dimensions based on rows and columns from Data
            this.boardDimensions.add(0, data.getRows());
            this.boardDimensions.add(1, data.getCols());

            // Prepare the list of words and black words from Data
            List<String> words = new ArrayList<>(data.getWordsSet());
            List<String> blackWords = new ArrayList<>(data.getBlackWordsSet());

            // Shuffle both lists to randomize card placement
            Collections.shuffle(words);
            Collections.shuffle(blackWords);

            // Trim the lists to the required number of cards
            words = words.subList(0, data.getCardsCount());
            blackWords = blackWords.subList(0, data.getBlackCardsCount());

            // Assign word cards to teams or set as neutral
            int t1Count = 0;
            int t2Count = 0;
            int i = 0;
            while (i < data.getCardsCount()) {
                this.allWords.add(new WordCard(words.get(i), false));
                if (t1Count < data.getTeam1cards()) {
                    this.allWords.get(i).setTeamName(data.getTeam1name());
                    t1Count++;
                } else if (t2Count < data.getTeam2cards()) {
                    this.allWords.get(i).setTeamName(data.getTeam2name());
                    t2Count++;
                } else {
                    this.allWords.get(i).setTeamName("regular");
                }
                i++;
            }

            // Assign black cards as special game-ending cards
            int j = 0;
            while (i < data.getCardsCount() + data.getBlackCardsCount()) {
                this.allWords.add(new WordCard(blackWords.get(j), true));
                this.allWords.get(i).setTeamName("black");
                i++;
                j++;
            }

            // Shuffle the entire list of cards to ensure random distribution on the board
            Collections.shuffle(this.allWords);
        }

}
