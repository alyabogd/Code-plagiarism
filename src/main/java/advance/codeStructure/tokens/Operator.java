package advance.codeStructure.tokens;

import java.util.*;

/**
 * Represents operators in source code.
 */
public class Operator extends Token {

    /**
     * ARITHMETIC = {+, -, *, /, ++, --}
     * RELATIONAL = {==, !=, >, <, >=, <= }
     * LOGICAL = {&&, ||, ^, !}
     * ASSIGNMENT = {=}
     * OTHER - stands for all other misc operators i.e. sizeof(), (type), ->, *, ',', break, continue, and others.
     *
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

    private OperatorType operatorType;

    private static Map<String, OperatorType> rules;

    public static void setRules(Map<String, OperatorType> rules) {
        Operator.rules = rules;
    }

    public static void setDefaultRules() {
        rules = new HashMap<>();

        final String[] arithmetic = new String[] {"+", "-", "*", "/", "++", "--"};
        final String[] relational = new String[] {"==", "!=", ">", "<", ">=", "<="};
        final String[] logical = new String[] {"&&", "||", "^", "!"};
        final String[] assignment = new String[] {"="};

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

    public Operator(String actualString) {
        super(actualString);
        this.tokenType = TokenType.OPERATOR;
        this.operatorType = determineOperatorType(actualString);
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }
}
