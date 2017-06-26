package advance.codeComprators;

import advance.codeStructure.Method;
import advance.codeStructure.SourceCode;
import advance.codeStructure.tokens.*;

import java.util.List;

/**
 * Class performs code comparison based on the longest subsequence of tokens
 */
public class LCSCodeComparator implements CodeComparator {

    private int getLCSLength(List<Token> first, List<Token> second) {
        if (first.size() < second.size()) {
            List<Token> temp = first;
            first = second;
            second = temp;
        }

        final Token[] firstTokens = first.toArray(new Token[0]);
        final Token[] secondTokens = second.toArray(new Token[0]);

        int lcs[][] = new int[2][second.size() + 1];

        for (int i = 0; i < firstTokens.length; ++i) {
            lcs[1][0] = 0;
            for (int j = 1; j <= secondTokens.length; ++j) {
                lcs[0][j] = lcs[1][j];
                final int tokensSimilarity = firstTokens[i].compare(secondTokens[j - 1]);
                if (tokensSimilarity > 0) {
                    lcs[1][j] = lcs[0][j - 1] + 1;
                } else {
                    lcs[1][j] = Math.max(lcs[1][j - 1], lcs[0][j]);
                }
            }
        }
        return lcs[1][second.size() - 1];
    }

    @Override
    public CodeSimilarity compare(SourceCode first, SourceCode second) {
        final CodeSimilarity codeSimilarity = new CodeSimilarity();
        for (Method methodFromFirst : first.getMethods()) {
            for (Method methodFromSecond : second.getMethods()) {
                final int lcs_length = getLCSLength(methodFromFirst.getStructure(), methodFromSecond.getStructure());
                int minLength = Math.min(
                        methodFromFirst.getNumberOfTokens(),
                        methodFromSecond.getNumberOfTokens());
                if (lcs_length > 0.8 * minLength) {
                    codeSimilarity.addSimilarity(methodFromFirst, methodFromSecond, (lcs_length * 1.0) / minLength);
                }
            }
        }
        return codeSimilarity;
    }
}
