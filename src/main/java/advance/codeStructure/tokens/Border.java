package advance.codeStructure.tokens;

/**
 * Border stands for 'begin' and 'end' of code block.
 */
public class Border extends Token {

    public enum BorderType {
        OPENING,
        CLOSING
    }

    private BorderType borderType;

    private static BorderType determineBorderType(String border) {
        if (border.equals("}")) {
            return BorderType.CLOSING;
        }
        return BorderType.OPENING;
    }

    public Border(String actualString) {
        super(actualString);
        this.tokenType = TokenType.BORDER;
        this.borderType = determineBorderType(actualString);
    }

    public BorderType getBorderType() {
        return borderType;
    }

    @Override
    public String toString() {
        return "Border \"" + actualString + "\"";
    }
}
