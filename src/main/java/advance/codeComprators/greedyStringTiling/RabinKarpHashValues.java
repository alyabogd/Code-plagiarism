package advance.codeComprators.greedyStringTiling;

import advance.codeStructure.tokens.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Rabin-Karp algorithm implementation for calculating hash values of sublists
 */
public class RabinKarpHashValues {
    /**
     * Multiplier for hash values
     */
    private static final int N = 31;
    /**
     * Modulus for hash values
     */
    private static final int MOD = 222926543;
    //private static final long MOD = Long.MAX_VALUE;

    private List<Token> tokens;

    private List<Long> hashValues;
    private int currentHashLength;

    private List<Long> powers;

    public RabinKarpHashValues(List<Token> tokens) {
        this.tokens = new ArrayList<>(tokens);
        powers = new ArrayList<>(tokens.size());

        powers.add(0, 1L);
        long lastPower = 1;
        for (int i = 1; i < tokens.size(); ++i) {
            long power = (lastPower * N) % MOD;
            powers.add(power);
            lastPower = power;
        }
    }

    /**
     * Assigns hash values for sublists of length 'length'
     * First value is calculated naive
     * Next values are calculated using recurrent formula
     */
    public void assignHashValuesForLength(int length) {
        /*currentHashLength = length;
        hashValues = new ArrayList<>(tokens.size() - length);
        long hash = 0;
        for (int i = 0; i < length; ++i) {
            hash += ((tokens.get(i).hashCode()) % MOD * powers.get(i)) % MOD;
        }
        hashValues.add(hash);
        for (int l = 1, r = length; r < tokens.size(); ++l, ++r) {
            hash = (hash - (tokens.get(l - 1).hashCode()) % MOD);
            long rightIndexHash = ((tokens.get(r).hashCode()) % MOD * powers.get(r)) % MOD;
            hash = (hash + rightIndexHash);
            hash /= N;
            hash %= MOD;
            hashValues.add(hash);
        }*/

        currentHashLength = length;
        hashValues = new ArrayList<>(tokens.size() - length);
        for (int l = 0, r = length - 1; r < tokens.size(); ++l, ++r) {
            long hash = 0;
            for(int i = 0; l + i < r; ++i) {
                hash += ((tokens.get(l + i).hashCode()) % MOD * powers.get(i)) % MOD;
            }
            hashValues.add(hash);
        }

    }

    /**
     * Returns hash for sublist [l, r]
     */
    public long getHash(int l, int r) {
        if (r - l + 1 != currentHashLength) {
            throw new IllegalArgumentException("incorrect range for hash");
        }
        return hashValues.get(l);
    }

    public List<Long> getHashValues() {
        return hashValues;
    }
}
