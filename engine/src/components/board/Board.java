package components.board;

import components.card.Card;
import team.team.Team;

import java.util.*;

public class Board {
    private List<Card> cards = new ArrayList<>();
    private final int rows;
    private final int cols;
    private int regularWordsAmount;
    private int blackWordsAmount;

    public Board(List<String> regularWords, List<String> blackWords, int regularWordsAmountForGame, int blackWordsAmountForGame, int boardRows, int boardCols, List<Team> teams) {
        /*// Convert List to Set to remove duplicates
        Set<String> WithoutDuplicates = new HashSet<>(regularWords);
        // Convert Set back to List
        List<String> wordWithoutDuplicates = new ArrayList<>(WithoutDuplicates);*/
        regularWordsAmount = regularWords.size();
        blackWordsAmount = blackWords.size();
        chooseRegularWordForTeams(regularWords, teams);
        chooseRegularWord(regularWords,regularWordsAmountForGame - totalTeamsCardAmount(teams));
        chooseBlackWords(blackWords, regularWords, blackWordsAmountForGame);

        Collections.shuffle(cards);

        this.rows = boardRows;
        this.cols = boardCols;
    }

    public int getRegularWordsAmount() {
        return regularWordsAmount;
    }

    public int getBlackWordsAmount() {
        return blackWordsAmount;
    }

    private void chooseRegularWordForTeams(List<String> regularWords, List<Team> teams){
        for (Team team: teams) {
            for ( int i = 0; i < team.getCardAmount(); i++) {
                cards.add(new Card(regularWords.remove(0), team.getTeamName(), false));
            }
        }
    }

    private void chooseRegularWord(List<String> regularWords, int numOfWordsLeft){
        for ( int i = 0; i < numOfWordsLeft; i++) {
            cards.add(new Card(regularWords.remove(0), "", false));
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    private void chooseBlackWords(List<String> blackWords, List<String> regularWord, int blackWordsAmountForGame){
        // Convert List to Set to remove duplicates
        Set<String> WithoutDuplicates = new HashSet<>(blackWords);
        // Convert Set back to List
        List<String> BlackWordWithoutDuplicates = new ArrayList<>(WithoutDuplicates);

        int count = 0;
        while (count < blackWordsAmountForGame){
            if(!regularWord.contains(BlackWordWithoutDuplicates.get(0))){
                cards.add(new Card(BlackWordWithoutDuplicates.remove(0), "BLACK", false));
                count++;
            }
            else{
                BlackWordWithoutDuplicates.remove(0);
            }
        }
    }

    private int totalTeamsCardAmount(List<Team> teams) {
        int sum = 0;
        for (Team team: teams){
            sum = team.getCardAmount() + sum;
        }
        return sum;
    }

    public List<Card> getCards() {
        return cards;
    }
}
