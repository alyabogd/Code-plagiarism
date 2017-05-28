package advance.codeStructure.tokens;

import java.util.*;

/**
 * Represents operators in source code.
 */
public class Operator extends Token {

    private static Map<String, OperatorType> rules;
    private OperatorType operatorType;

    public Operator(String actualString) {
        super(actualString);
        this.tokenType = TokenType.OPERATOR;
        this.operatorType = determineOperatorType(actualString);
    }

    public static void setRules(Map<String, OperatorType> rules) {
        Operator.rules = rules;
    }

    public static void setDefaultRules() {
        rules = new HashMap<>();

        final String[] arithmetic = new String[]{"+", "-", "*", "/", "%", "++", "--", "+=", "-=", "*=", "/=", "%="};
        final String[] relational = new String[]{"==", "!=", ">", "<", ">=", "<="};
        final String[] logical = new String[]{"&&", "||", "^", "!"};
        final String[] assignment = new String[]{"="};

        Arrays.stream(arithmetic)
                .forEach(op -> rules.put(op, OperatorType.ARITHMETIC));
        Arrays.stream(relational)
                .forEach(op -> rules.put(op, OperatorType.RELATIONAL));
        Arrays.stream(logical)
                .forEach(op -> rules.put(op, OperatorType.LOGICAL));
        Arrays.stream(assignment)
                .forEach(op -> rules.put(op, OperatorType.ASSIGNMENT));

        setRules(rules);
    }

    public static OperatorType determineOperatorType(String operator) {
        if (rules == null) {
            setDefaultRules();
        }
        return rules.getOrDefault(operator, OperatorType.OTHER);
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "operatorType=" + operatorType +
                "} \"" + actualString + "\"";
    }

    /**
     * ARITHMETIC = {+, -, *, /, ++, --}
     * RELATIONAL = {==, !=, >, <, >=, <= }
     * LOGICAL = {&&, ||, ^, !}
     * ASSIGNMENT = {=}
     * OTHER - stands for all other misc operators i.e. sizeof(), (type), ->, *, ',', break, continue, and others.
     * <p>
     * Operators, which represent assignment AND some other operator
     * should be considered as 2 different ones.
     * For example, += stands for '=, +'
     */
    public enum OperatorType {
        OTHER,
        ARITHMETIC,
        RELATIONAL,
        LOGICAL,
        ASSIGNMENT,
    }
}
