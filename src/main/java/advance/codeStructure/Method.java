package advance.codeStructure;

import advance.codeStructure.tokens.Token;

import java.util.List;

public class Method {

    private String name;
    private List<Token> structure;
    private int startLine;
    private int endLine;

    public Method(String name, List<Token> structure, int startLine, int endLine) {
        this.name = name;
        this.structure = structure;
        this.startLine = startLine;
        this.endLine = endLine;
    }

    public String getName() {
        return name;
    }

    public List<Token> getStructure() {
        return structure;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public int getNumberOfTokens() {
        return structure.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Method: ")
                .append(name).append(" ")
                .append(startLine).append(" - ").append(endLine)
                .append(System.lineSeparator());
        for(Token token: structure) {
            sb.append("\t").append(token).append("\n");
        }
        return sb.toString();
    }
}
