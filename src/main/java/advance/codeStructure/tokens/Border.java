package advance.codeStructure.tokens;

/**
 * Border stands for 'begin' and 'end' of code block.
 */
public class Border extends Token {

    private BorderType borderType;

    public Border(String actualString) {
        super(actualString);
        this.tokenType = TokenType.BORDER;
        this.borderType = determineBorderType(actualString);
    }

    private static BorderType determineBorderType(String border) {
        if (border.equals("}")) {
            return BorderType.CLOSING;
        }
        return BorderType.OPENING;
    }

    @Override
    public int compare(Token token) {
        if (token.tokenType != TokenType.BORDER) {
            return 0;
        }
        final Border other = (Border) token;
        return this.borderType == other.borderType ? 1 : 0;
    }

    public BorderType getBorderType() {
        return borderType;
    }

    @Override
    public String toString() {
        return "Border \"" + actualString + "\"";
    }

    public enum BorderType {
        OPENING,
        CLOSING
    }
}
