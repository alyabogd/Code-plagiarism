package advance;

import advance.codeStructure.tokens.*;

import java.util.LinkedList;
import java.util.List;

public class CTokenIdentifier {

    private boolean isIdentification;
    private List<String> identifiers;

    public CTokenIdentifier() {
        isIdentification = false;
        identifiers = new LinkedList<>();
    }

    public void clean() {
        isIdentification = false;
        identifiers.clear();
    }

    private Token.TokenType processIdentification(String word) {
        Keyword.KeywordType keywordType = Keyword.determineKeywordType(word);
        if (keywordType == Keyword.KeywordType.DATA_TYPE) {
            //return Token.TokenType.KEYWORD;
            return Token.TokenType.OTHER;
        }
        identifiers.add(word);
        isIdentification = false;
        return Token.TokenType.IDENTIFIER;
    }


    public Token.TokenType determineTokenType(String word) {
        if (isIdentification) {
            return processIdentification(word);
        }

        if (word.equals("}") || word.equals("{")) {
            return Token.TokenType.BORDER;
        }

        if (Operator.determineOperatorType(word) != Operator.OperatorType.OTHER) {
            return Token.TokenType.OPERATOR;
        }

        if (Literal.determineLiteralType(word) != Literal.LiteralType.OTHER) {
            return Token.TokenType.LITERAL;
        }

        if (identifiers.contains(word)) {
            return Token.TokenType.IDENTIFIER;
        }

        Keyword.KeywordType keywordType = Keyword.determineKeywordType(word);
        switch (keywordType) {
            case CONTROL:
                if (Cycle.isCycle(word)) {
                    return Token.TokenType.CYCLE;
                }
                if (Condition.isCondition(word)) {
                    return Token.TokenType.CONDITION;
                }
                return Token.TokenType.KEYWORD;
            case DATA_TYPE:
                isIdentification = true;
                //return Token.TokenType.KEYWORD;
                return Token.TokenType.OTHER;
            case DEFINE:
                //--- TODO make map with defines and translate them
                return null;
            case STRUCTURAL:
                return Token.TokenType.KEYWORD;
        }
        return Token.TokenType.OTHER;
    }
}
