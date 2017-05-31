package advance.codeComprators;

import advance.codeStructure.SourceCode;

public interface CodeComparator {

    /**
     * Method should return the similarity measure based on the way the class compares source codes.
     */
    CodeSimilarity compare(SourceCode first, SourceCode second);
}
