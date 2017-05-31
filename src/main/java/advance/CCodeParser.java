package advance;

import advance.codeStructure.Method;
import advance.codeStructure.SourceCode;
import advance.codeStructure.tokens.*;
import advance.fileParcers.FileUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * C++ code parser
 */
public class CCodeParser implements CodeParser {

    /**
     * Detects beginning of method inside string
     * <p>
     * First group (.*) stands for all keywords before brackets. It also
     * includes method name as last 'word'
     * <p>
     * There might be a space between method name and round brackets
     * <p>
     * There might be arguments inside round brackets. Legal characters
     * are: 'word' characters (\\w), pointer('*'), link('&'), space and comma
     * <p>
     * It is necessary for round brackets to be followed by opening curly
     * bracket. There might be one or more spaces between them.
     */
    private static final Pattern methodBeginPattern = Pattern.compile("(.*)\\s?\\([^)]*\\)\\s*\\{");

    /**
     * Pattern detects inline comment in input string
     * <p>
     * First group (.*) stands for comment itself.
     */
    private static final Pattern inlineCommentPattern = Pattern.compile("//(.*)");

    /**
     * Pattern detects multi-line comments
     * <p>
     * First group (.*) stands for comment itself
     */
    private static final Pattern multilineCommentPattern = Pattern.compile("/\\*(.*)\\*/");

    /**
     * Pattern detects all possible operators
     */
    private static final Pattern operatorPattern = Pattern.compile("[+\\-*/%><=&|]{1,2}+");

    private static final Pattern doubleArithmeticOperator = Pattern.compile("[+\\-*/%]=");

    public static void main(String[] args) {
        String text = FileUtil.getText("test/BubbleSort/test6.cpp", "UTF8");
        CodeParser parser = new CCodeParser();
        SourceCode sourceCode = parser.parse(text);
        System.out.println(sourceCode);
    }

    private boolean isMethodBegin(String line) {
        return line.matches(methodBeginPattern.toString());
    }

    /**
     * '&&' operator will be transformed into sequential if's // TODO fix borders in this case
     * '>' && '>=' operators will be transformed into '<' && '<='
     * double operators ('+=', '-=', '*=' and so on) will be transformed in corresponding single operators.
     * mb smth else
     */
    private List<Token> normalizeTokensInsideStatement(List<Token> tokens) {
        for (int index = 0; index < tokens.size(); ++index) {
            final Token currentToken = tokens.get(index);
            if (currentToken.getTokenType() == Token.TokenType.OPERATOR) {
                final String actualString = currentToken.getActualString();
                if (actualString.equals("&&")) {
                    tokens.set(index, new Condition("if"));
                    continue;
                }
                if (actualString.matches(doubleArithmeticOperator.toString())) {
                    tokens.set(index, new Operator("="));
                    tokens.add(index + 1, new Operator(String.valueOf(actualString.charAt(0))));
                    continue;
                }
                // TODO relational operators matching
            }
        }
        return tokens;
    }

    private List<Token> parseStatement(String statement) {
        List<Token> tokens = new LinkedList<>();
        CTokenIdentifier tokenIdentifier = new CTokenIdentifier();
        String[] words = statement.split("\\s+");
        for (String word : words) {
            // determine type of token
            Token.TokenType type = tokenIdentifier.determineTokenType(word);
            switch (type) {
                case CYCLE:
                    tokens.add(new Cycle(word));
                    break;
                case KEYWORD:
                    tokens.add(new Keyword(word));
                    break;
                case BORDER:
                    tokens.add(new Border(word));
                    break;
                case OPERATOR:
                    tokens.add(new Operator(word));
                    break;
                case CONDITION:
                    tokens.add(new Condition(word));
                    break;
                case LITERAL:
                    tokens.add(new Literal(word));
                    break;
                case IDENTIFIER:
                    Identifier.IdentifierType identifierType = tokenIdentifier.getLastIdentificationType();
                    tokens.add(new Identifier(word, identifierType));
                    break;
                case METHOD_CALL:
                    break;
            }
        }

        tokens = normalizeTokensInsideStatement(tokens);

        return tokens;
    }

    /**
     * Performs statement normalization.
     * <ul>
     * <li> Add spaces near operators </li>
     * <li> Add spaces near round brackets </li>
     * </ul>
     */
    private String normalizeStatement(String statement) {
        //System.out.print(statement + " -> ");
        statement = statement.replaceAll(operatorPattern.toString(), " $0 ");
        statement = statement.replaceAll("[()]", " $0 ");
        //System.out.println(statement);
        return statement;
    }

    private List<Token> parseLine(String line) {
        line = line.trim();
        final List<Token> lineTokens = new LinkedList<>();

        final String[] statements = line.split(";");
        for (String statement : statements) {
            statement = normalizeStatement(statement);
            lineTokens.addAll(parseStatement(statement));
        }
        return lineTokens;
    }

    private Method extractMethod(String[] lines, int lineBegin) {
        final Matcher methodBeginMatcher = methodBeginPattern.matcher(lines[lineBegin]);
        if (!methodBeginMatcher.find()) {
            return null;
        }
        final String[] methodSignature = methodBeginMatcher.group(1).split("\\s+");
        final String methodName = methodSignature[methodSignature.length - 1];

        final List<Token> methodTokens = new LinkedList<>();
        final int numberOfLines = lines.length;
        int currentLine = lineBegin + 1;

        Deque<Character> bracketsStack = new ArrayDeque<>();
        bracketsStack.push('{');
        methodTokens.add(new Border("{"));
        while (!bracketsStack.isEmpty() && currentLine < numberOfLines) {

            final List<Token> lineTokens = parseLine(lines[currentLine]);

            for (Token token : lineTokens) {
                if (token.getTokenType() == Token.TokenType.BORDER) {
                    Border border = (Border) token;
                    if (border.getBorderType() == Border.BorderType.OPENING) {
                        bracketsStack.push('{');
                    } else {
                        // if bracketsStack.isEmpty -> unexpected end of method.
                        bracketsStack.pop();
                    }
                }
            }
            methodTokens.addAll(lineTokens);
            currentLine++;
        }

        return new Method(methodName, methodTokens, lineBegin, currentLine);
    }

    private String removeComments(String text) {
        boolean isInsideComment = false;
        String lines[] = text.split(System.lineSeparator());
        for (int i = 0; i < lines.length; ++i) {
            if (!isInsideComment) {
                lines[i] = lines[i].replaceFirst(inlineCommentPattern.toString(), "");
                lines[i] = lines[i].replaceAll(multilineCommentPattern.toString(), "");
                if (lines[i].contains("/*")) {
                    lines[i] = lines[i].replaceFirst("/\\*.*", "");
                    isInsideComment = true;
                }
            } else { // isInsideComment
                if (lines[i].contains("*/")) {
                    isInsideComment = false;
                    lines[i] = lines[i].replaceFirst(".*\\*/", "");
                    lines[i] = lines[i].replaceFirst(inlineCommentPattern.toString(), "");
                    lines[i] = lines[i].replaceAll(multilineCommentPattern.toString(), "");
                } else {
                    lines[i] = "";
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public SourceCode parse(String text) {
        text = removeComments(text);
        List<Method> methods = new LinkedList<>();
        final String[] lines = text.split(System.lineSeparator());
        for (int i = 0; i < lines.length; ++i) {
            if (isMethodBegin(lines[i])) {
                final Method method = extractMethod(lines, i);
                i = method.getEndLine();
                methods.add(method);
            }
        }

        return new SourceCode(methods);
    }


}
