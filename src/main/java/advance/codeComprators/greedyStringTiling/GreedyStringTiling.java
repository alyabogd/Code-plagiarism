package advance.codeComprators.greedyStringTiling;

import advance.codeStructure.tokens.Token;

import java.util.*;

/**
 * Class performs lise of tokens comparison based on Rabin-Karp Greedy String Tiling algorithm
 * <p>
 * Greedy String Tiling algorithm searches for maximal tiling of pattern P and text T
 * (i.e. coverage of non-overlapping substrings of T with non-overlapping substrings of P
 * which maximises number of tokens, that have been covered by tiles)
 */
public class GreedyStringTiling {

    /**
     * Matches, that are smaller than MINIMUM_MATCH_LENGTH are ignored.
     */
    private static final int MINIMUM_MATCH_LENGTH = 6;

    private RabinKarpHashValues textHashValues;
    private RabinKarpHashValues patternHashValues;

    /**
     * Tile is a permanent association of substring from P with a matching substring from P
     * List stores tiles, which are included into final answer
     */
    private List<Match> tiles;

    /**
     * Structure is used to record maximal matches.
     * Each queue records maximal matches of the same length (which is stored as KEY)
     * Map is ordered by decreasing lengths of maximal matches in queues.
     */
    private TreeMap<Integer, Deque<Match>> maximalMatches;

    private int lengthOfTokensTiled;

    private List<Token> pattern;
    private List<Token> text;

    private TreeSet<Integer> markedIndexesPattern;
    private TreeSet<Integer> markedIndexesText;

    private GSTHashMap textHashesMap;

    public GreedyStringTiling() {
        tiles = new ArrayList<>();
        textHashesMap = new GSTHashMap();
        markedIndexesPattern = new TreeSet<>();
        markedIndexesText = new TreeSet<>();
        maximalMatches = new TreeMap<>();
        lengthOfTokensTiled = 0;
    }

    public TiledMatches getStringTiling(List<Token> first, List<Token> second) {
        if (first.size() < second.size()) {
            pattern = new ArrayList<>(first);
            text = new ArrayList<>(second);
        } else {
            pattern = new ArrayList<>(second);
            text = new ArrayList<>(first);
        }

        clear();

        if (pattern.size() <= MINIMUM_MATCH_LENGTH) {
            return new TiledMatches(new LinkedList<>(), 0);
        }

        textHashValues = new RabinKarpHashValues(text);
        patternHashValues = new RabinKarpHashValues(pattern);

        searchMatches(pattern.size());
        return new TiledMatches(tiles, lengthOfTokensTiled);
    }

    private void clear() {
        tiles.clear();
        lengthOfTokensTiled = 0;
        textHashesMap.clear();
        markedIndexesPattern.clear();
        markedIndexesText.clear();
        maximalMatches.clear();
    }

    /**
     * Remove all found matches in maxMatches map and
     * assign empty queues for all possible lengths
     */
    private void clearMaxMatches() {
        maximalMatches = new TreeMap<>();
        for (int i = 0; i <= text.size(); ++i) {
            maximalMatches.put(i, new LinkedList<>());
        }
    }

    /**
     * Implements Rabin-Karp Greedy-String-Tiling algorithm
     */
    private void searchMatches(int initSearchLength) {
        int searchLength = initSearchLength;

        while (searchLength != -1) {

            clearMaxMatches();

            // maxLength is length of the longest maximal-matching found in this scan
            int maxLength = scanPattern(searchLength);

            if (maxLength > 2 * searchLength) {
                searchLength = maxLength;
            } else {
                markStrings();
                searchLength = getNextSearchLength(searchLength);
            }
        }
    }

    /**
     * Returns next appropriate value for search length
     * Returns -1 in case current search length is the least one
     */
    private int getNextSearchLength(int searchLength) {
        if (searchLength > 2 * MINIMUM_MATCH_LENGTH) {
            return searchLength / 2;
        }

        if (searchLength > MINIMUM_MATCH_LENGTH) {
            return MINIMUM_MATCH_LENGTH;
        }
        return -1;
    }

    /**
     * Method calculates textHashesMap for each substring of Text of length searchLength
     * ans stores them into GSTHashMap textHashesMap.
     * <p>
     * Karp-Rabin algorithm is used to calculate textHashesMap of sublists.
     */
    private void putHashesInTextForLengthInMap(int searchLength) {
        textHashesMap.clear();
        for (int l = 0, r = searchLength - 1; r < text.size(); ++l, ++r) {
            Integer nextMarkedToken = markedIndexesText.ceiling(l);
            if (nextMarkedToken == null || nextMarkedToken > r) {
                // if there is no marked elements inside [l, r]
                textHashesMap.add(textHashValues.getHash(l, r), l);
            }
        }
    }

    private boolean isTextTokenUnmarked(int index) {
        return !markedIndexesText.contains(index);
    }

    private boolean isPatternTokenUnmarked(int index) {
        return !markedIndexesPattern.contains(index);
    }

    /**
     * All maximum matches of size searchLength or greater are collected into maximal-matches map,
     * where they are sorted by decreasing lengths.
     *
     * @return size of the longest maximum matching found
     */
    private int scanPattern(int searchLength) {

        int longestMaxMatch = 0;

        putHashesInTextForLengthInMap(searchLength);

        for (int l = 0, r = searchLength - 1; r < pattern.size(); ++l, ++r) {
            if (isIntervalUnmarked(l, r, markedIndexesPattern)) {
                // if there is no marked elements in [l, r]
                final long patternSubstringHash = patternHashValues.getHash(l, r);
                final List<Integer> tokensWithEqualHash = textHashesMap.get(patternSubstringHash);
                for (Integer beginIndex : tokensWithEqualHash) {
                    int currentMatchLength = searchLength;

                    int patternIndex = l + currentMatchLength;
                    int textIndex = beginIndex + currentMatchLength;
                    while (patternIndex < pattern.size() &&
                            textIndex < text.size() &&
                            pattern.get(patternIndex).compare(text.get(textIndex)) ==2 &&
                            isTextTokenUnmarked(textIndex) &&
                            isPatternTokenUnmarked(patternIndex)) {
                        // extend match until EOL or first non-match or element marked
                        currentMatchLength++;
                        patternIndex++;
                        textIndex++;
                    }

                    if (currentMatchLength > 2 * searchLength) {
                        return currentMatchLength;
                    }
                    longestMaxMatch = Math.max(longestMaxMatch, currentMatchLength);
                    maximalMatches.get(currentMatchLength).add(new Match(l, beginIndex, currentMatchLength));
                }
            }
        }
        return longestMaxMatch;
    }

    /**
     * Checks if part of match is already marked. It it is, returns false
     */
    private boolean isNotOccluded(Match match) {
        int patternPosition = match.getPatternPosition();
        int textPosition = match.getTextPosition();
        int len = match.getLenght();
        return isIntervalUnmarked(patternPosition, patternPosition + len - 1, markedIndexesPattern) &&
                isIntervalUnmarked(textPosition, textPosition + len - 1, markedIndexesText);
    }

    /**
     * Returns not occluded part of given match or null if there isn't any
     * <p>
     * Since longer matches are processed before smaller ones,
     * occluded tokens might be in the beginning or (and) in the end of given match.
     */
    private Match getNotOccludedPart(Match match) {

        int textIndex = match.getTextPosition();
        int patternIndex = match.getPatternPosition();
        int len = match.getLenght();

        // move left border to right while possible
        while (markedIndexesText.contains(textIndex) ||
                markedIndexesPattern.contains(patternIndex)) {
            textIndex++;
            patternIndex++;
            len--;
            if (textIndex >= text.size() || patternIndex >= pattern.size() || len < 0) {
                return null;
            }
        }

        // move right border to left while possible
        while (markedIndexesText.contains(textIndex + len - 1) ||
                markedIndexesPattern.contains(patternIndex + len - 1)) {
            len--;
            if (len < 0) {
                return null;
            }
        }

        return new Match(patternIndex, textIndex, len);
    }

    /**
     * Returns true is there is no marked tokens on [l, r]
     * <p>
     * Since longer matches are processed before smaller ones,
     * occluded tokens might be in the beginning or (and) in the end of given match.
     */
    private boolean isIntervalUnmarked(int l, int r, TreeSet<Integer> markedIndexes) {
        Integer ceil = markedIndexes.ceiling(l);
        return !(ceil != null && ceil <= r);
    }

    private void markTokensInsideMatch(Match match) {
        int len = match.getLenght();
        for (int i = 0; i < len; ++i) {
            markedIndexesPattern.add(match.getPatternPosition() + i);
            markedIndexesText.add(match.getTextPosition() + i);
        }
    }

    /**
     * Maximal matches, found in scanPattern are taken one per time starting with the longest ones,
     * tested if they have been occluded by a sibling tile (i.e. part of the maximal-match is already marked).
     * If it's not, the tile is created by marking tokens from two sublists.
     */
    private void markStrings() {
        for (Map.Entry<Integer, Deque<Match>> queueOfMatches : maximalMatches.descendingMap().entrySet()) {
            if (queueOfMatches.getKey() < MINIMUM_MATCH_LENGTH) {
                break;
            }
            for (Match match : queueOfMatches.getValue()) {
                if (isNotOccluded(match)) {
                    markTokensInsideMatch(match);
                    lengthOfTokensTiled += match.getLenght();
                    tiles.add(match);
                } else {
                    // put non-occluded part of this match in correct queue
                    final Match nonOccludedMatch = getNotOccludedPart(match);
                    if (nonOccludedMatch == null) {
                        continue;
                    }
                    maximalMatches.get(nonOccludedMatch.getLenght()).add(nonOccludedMatch);
                }
            }
        }
    }
}
