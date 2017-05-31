package advance.codeStructure.tokens;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represent literals in source code.
 */
public class Literal extends Token {

    private static final Pattern stringPattern = Pattern.compile("([\"']).*\\1");
    private static final Pattern booleanPattern = Pattern.compile("(true|false)");
    private static final Pattern integerNumberPattern =
            Pattern.compile("(?i)([+-])?(\\d+)(u|l|uu|ll|ull|llu)?");
    private static final Pattern doubleNumberPattern =
            Pattern.compile("(?i)([+-])?\\d*\\.?\\d*(f|d)?");
    private static final Pattern digitPattern = Pattern.compile("\\d");
    private LiteralType literalType;

    public Literal(String actualString) {
        super(actualString);
        this.tokenType = TokenType.LITERAL;
        this.literalType = determineLiteralType(actualString);
    }

    public static LiteralType determineLiteralType(String literal) {
        if (literal.matches(booleanPattern.toString())) {
            return LiteralType.BOOLEAN;
        }
        if (literal.matches(stringPattern.toString())) {
            return LiteralType.STRING;
        }
        if (literal.matches(integerNumberPattern.toString())) {
            return LiteralType.NUMBER;
        }
        Matcher digitMatcher = digitPattern.matcher(literal);
        if (literal.matches(doubleNumberPattern.toString()) && digitMatcher.find()) {
            return LiteralType.NUMBER;
        }
        return LiteralType.OTHER;
    }

    @Override
    public int compare(Token token) {
        if (token.getTokenType() != TokenType.LITERAL) {
            return 0;
        }
        final Literal other = (Literal) token;
        if (this.tokenType != other.tokenType) {
            return 1;
        }
        return this.actualString.equals(other.actualString) ? 2 : 1;
    }

    public LiteralType getLiteralType() {
        return literalType;
    }

    @Override
    public String toString() {
        return "Literal{" +
                "literalType=" + literalType +
                "}  \"" + actualString + "\"";
    }

    /**
     * NUMBER - stands for any number literal: integer long double float unsigned or not etc
     * STRING - stands for any string literal, which is captured in quotes
     * BOOLEAN - stands for any boolean literal: true or false
     * OTHER - other literal, that doesn't match any existing group
     */
    public enum LiteralType {
        OTHER,
        NUMBER,
        STRING,
        BOOLEAN
    }
}
