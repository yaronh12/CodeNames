package word_card;

public class WordCard {
    private final String word;
    private final boolean isBlack;
    private boolean discoveredFlag;
    private final String teamName;

    public WordCard(String word, boolean isBlack, String teamName){
        this.word = word;
        this.isBlack = isBlack;
        this.discoveredFlag = false;
        this.teamName = teamName;
    }

    public void setDiscoveredFlag() {
        this.discoveredFlag = !this.discoveredFlag;
    }

    public String getWord() {
        return word;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public boolean isDiscoveredFlag() {
        return discoveredFlag;
    }

    public String getTeamName() {
        return teamName;
    }
}
