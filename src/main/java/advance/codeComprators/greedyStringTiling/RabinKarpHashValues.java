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
    private static final long N = 31;
    /**
     * Modulus for hash values
     */
    private static final long MOD_F = 222926543;
    private static final long MOD_S = 153618461;

    private List<Token> tokens;

    // {1, N, N^2, N^3, ...}
    private List<SafeLong> powers;
    // {0, s[0], s[0]*P + s[1], s[0]*P^2 + s[1]*P + s[2], ...}
    private List<SafeLong> prefixHashes_f;

    public RabinKarpHashValues(List<Token> tokens) {
        this.tokens = new ArrayList<>(tokens);
        powers = new ArrayList<>(tokens.size());
        prefixHashes_f = new ArrayList<>(tokens.size());

        powers.add(new SafeLong(1));
        prefixHashes_f.add(new SafeLong(0));

        SafeLong lastPower = powers.get(0);
        SafeLong lastPrefixHash_f = prefixHashes_f.get(0);

        for (int i = 0; i < tokens.size(); ++i) {
            powers.add(lastPower.mult(N));
            lastPower = powers.get(i + 1);

            final long tokenHashcode = tokens.get(i).hashCode();

            prefixHashes_f.add(
                    lastPrefixHash_f
                            .mult(N)
                            .add(tokenHashcode)
            );

            lastPrefixHash_f = prefixHashes_f.get(i + 1);
        }
    }


    /**
     * Returns hash for sublist [l, r]
     */
    public long getHash(int l, int r) {
        SafeLong hash = prefixHashes_f.get(r + 1);
        hash = hash.substract(
                prefixHashes_f.get(l).mult(powers.get(r - l + 1))
        );
        return hash.getValueF();
    }

    /**
     * Class is modulus safe on basic arithmetic operations.
     * Modulus is equal to parent's module
     */
    private class SafeLong {

        private Long value_f;
        private Long value_s;

        public SafeLong(Long value) {
            this.value_f = value % MOD_F;
            this.value_s = value % MOD_S;
        }

        public SafeLong(Integer value) {
            this.value_f = value % MOD_F;
            this.value_s = value % MOD_S;
        }

        public SafeLong(Long value_f, Long value_s) {
            this.value_f = value_f;
            this.value_s = value_s;
        }

        public SafeLong add(SafeLong safeLong) {
            return new SafeLong(
                    (value_f + safeLong.value_f) % MOD_F,
                    (value_s + safeLong.value_s) % MOD_S);
        }

        public SafeLong add(Long term) {
            term %= MOD_F;
            return new SafeLong(
                    (value_f + term) % MOD_F,
                    (value_s + term) % MOD_S);
        }

        public SafeLong substract(SafeLong safeLong) {
            return new SafeLong(
                    (value_f + MOD_F - safeLong.value_f) % MOD_F,
                    (value_s + MOD_S - safeLong.value_s) % MOD_S);
        }

        public SafeLong substract(Long sub) {
            sub %= MOD_F;
            return new SafeLong(
                    (value_f + MOD_F - sub) % MOD_F,
                    (value_s + MOD_S - sub) % MOD_S);
        }

        public SafeLong mult(SafeLong safeLong) {
            return new SafeLong(
                    (value_f * safeLong.value_f) % MOD_F,
                    (value_s * safeLong.value_s) % MOD_S);
        }

        public SafeLong mult(Long factor) {
            factor %= MOD_F;
            return new SafeLong(
                    (value_f * factor) % MOD_F,
                    (value_s * factor) % MOD_S);
        }

        public Long getValueF() {
            return value_f;
        }

        public Long getValueS() {
            return value_s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SafeLong safeLong = (SafeLong) o;

            if (!value_f.equals(safeLong.value_f)) return false;
            return value_s.equals(safeLong.value_s);
        }

        @Override
        public int hashCode() {
            int result = value_f.hashCode();
            result = 31 * result + value_s.hashCode();
            return result;
        }
    }
}
