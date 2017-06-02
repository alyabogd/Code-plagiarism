package advance.codeComprators.greedyStringTiling;

import java.util.List;

public class TiledMatches {

    private List<Match> tiles;
    private int lengthOfTokensTiled;

    public TiledMatches(List<Match> tiles, int lengthOfTokensTiled) {
        this.tiles = tiles;
        this.lengthOfTokensTiled = lengthOfTokensTiled;
    }

    public List<Match> getTiles() {
        return tiles;
    }

    public int getLengthOfTokensTiled() {
        return lengthOfTokensTiled;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TiledMatches{\n" +
                "tiles:\n");
        for (Match match : tiles) {
            sb.append("\t ").append(match).append("\n");
        }
        sb.append("lengthOfTokensTiled=").append(lengthOfTokensTiled).append("}");
        return sb.toString();
    }
}
