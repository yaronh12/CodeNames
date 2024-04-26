package components;

public class Card {
    private String word;
    private String teamName;
    private boolean isFound;

    public Card(String word, String teamName, boolean isFound) {
        this.word = word;
        this.teamName = teamName;
        this.isFound = isFound;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public boolean isFound() {
        return isFound;
    }

    public void setFound() {
        isFound = !isFound;
    }

    @Override
    public String toString() {
        return "Card{" +
                "word='" + word + '\'' +
                ", teamName='" + teamName + '\'' +
                ", isFound=" + isFound +
                '}';
    }
}
