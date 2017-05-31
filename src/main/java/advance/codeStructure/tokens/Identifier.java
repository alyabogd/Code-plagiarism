package advance.codeStructure.tokens;

public class Identifier extends Token {

    private IdentifierType identifierType;

    public Identifier(String actualString, IdentifierType type) {
        super(actualString);
        this.tokenType = TokenType.IDENTIFIER;
        identifierType = type;
    }

    @Override
    public int compare(Token token) {
        if (token.getTokenType() != TokenType.IDENTIFIER) {
            return 0;
        }
        final Identifier other = (Identifier) token;
        if (this.identifierType != other.identifierType) {
            return 0;
        }
        return this.actualString.equals(other.actualString) ? 2 : 1;
    }

    @Override
    public String toString() {
        return "Identifier {type=" + identifierType + "} \"" + actualString + "\"";
    }

    public enum IdentifierType {
        OTHER,
        NUMBER,
        STRING,
        BOOLEAN,
    }
}
