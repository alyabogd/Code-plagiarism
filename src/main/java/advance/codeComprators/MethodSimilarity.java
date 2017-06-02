package advance.codeComprators;

import advance.codeStructure.Method;

/**
 * Class represents similarity of two methods
 */
public class MethodSimilarity {

    private Method first;
    private Method second;

    private double measure;

    public MethodSimilarity(Method first, Method second, double measure) {
        this.first = first;
        this.second = second;
        this.measure = measure;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(first.getName())
                //.append("(").append(first.getNumberOfTokens()).append(")")
                .append(" and ").append(second.getName())
                //.append("(").append(second.getNumberOfTokens()).append(")")
                .append("\t measure:  \t").append(measure);
        return sb.toString();
    }
}
