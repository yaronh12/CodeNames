package word_card;

public class WordCard {
    private final String word;
    private final boolean isBlack;
    private boolean discoveredFlag;
    private String teamName;

    public WordCard(String word, boolean isBlack){
        this.word = word;
        this.isBlack = isBlack;
        this.discoveredFlag = false;

    }

    public void setTeamName(String teamName) {
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
