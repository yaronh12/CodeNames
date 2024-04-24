package components;

import team.Team;

import java.util.*;

public class Board {
    private List<Card> cards = new ArrayList<>();
    private final int rows;
    private final int cols;

    public Board(List<String> regularWords, List<String> blackWords, int regularWordsAmountForGame, int blackWordsAmountForGame, int boardRows, int boardCols, List<Team> teams) {
        // Convert List to Set to remove duplicates
        Set<String> WithoutDuplicates = new HashSet<>(regularWords);
        // Convert Set back to List
        List<String> wordWithoutDuplicates = new ArrayList<>(WithoutDuplicates);

        chooseRegularWordForTeams(wordWithoutDuplicates, teams);
        chooseRegularWord(wordWithoutDuplicates,regularWordsAmountForGame - totalTeamsCardAmount(teams));
        chooseBlackWords(blackWords, regularWords, blackWordsAmountForGame);

        Collections.shuffle(cards);

        this.rows = boardRows;
        this.cols = boardCols;
    }

    private void chooseRegularWordForTeams(List<String> regularWords, List<Team> teams){
        for (Team team: teams) {
            for ( int i = 0; i < team.getCardAmount(); i++) {
                Card tmpCard = new Card(regularWords.get(i), team.getTeamName(), false);
                cards.add(tmpCard);
                regularWords.remove(i);
            }
        }
    }

    private void chooseRegularWord(List<String> regularWords, int numOfWordsLeft){
        for ( int i = 0; i < numOfWordsLeft; i++) {
            Card tmpCard = new Card(regularWords.get(i), "NONE", false);
            cards.add(tmpCard);
            regularWords.remove(i);
        }
    }

    private void chooseBlackWords(List<String> blackWords, List<String> regularWord, int blackWordsAmountForGame){
        // Convert List to Set to remove duplicates
        Set<String> WithoutDuplicates = new HashSet<>(blackWords);
        // Convert Set back to List
        List<String> BlackWordWithoutDuplicates = new ArrayList<>(WithoutDuplicates);

        int count = 0;
        while (count < blackWordsAmountForGame){
            if(regularWord.contains(BlackWordWithoutDuplicates.get(0))){
                continue;
            }
            Card tmpCard = new Card(BlackWordWithoutDuplicates.get(0), "BLACK", false);
            cards.add(tmpCard);
            BlackWordWithoutDuplicates.remove(0);
            count++;

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
