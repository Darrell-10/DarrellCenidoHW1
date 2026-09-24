/**
 * Handles game logic and scoring
 * Author: Darrell Cenido
 */

public class FarkleScorer{

    /**
     * Checks for Farkle
     * Param is array of counts of each val
     * returns true if Farkle, false otherwise
     */
    public static boolean isFarkle(int[] counts) {
        // if there are vals 1 or 5, then its not a Farkle
        if (counts[1] != 0 || counts[5] != 0) {
            return false;
        }

        // if there are any triples, then its not a Farkle
        for (int i = 2; i < 7; i++) {
            if (counts[i] >= 3) {
                return false;
            }
        }

        // if there are any three pairs, then its not a Farkle
        int pairCount = 0;
        for (int i = 1; i < 7; i++) {
            if(counts[i] == 2) {
                pairCount++;
            }
        }
        if (pairCount == 3) {
            return false;
        }

        return true;
    }

    /**
     * Calculates score of meld
     * Param val quantities
     * Returns score of meld vals
     */
    public static int calculateMeldScore(int[] counts) {
        int meldScore = 0;

        // Check straight (1 through 6)
        boolean isStraight = true;
        for (int i = 1; i < 7; i++) {
            if (counts[i] != 1) {
                isStraight = false;
            }
        }

        if (isStraight) {
            return 1000;
        } else {
            // Check 3 pairs
            int meldPairCount = 0;
            for (int i = 1; i < 7; i++) {
                if (counts[i] == 2) {
                    meldPairCount++;
                }
            }
            if (meldPairCount == 3) {
                return 750;
            } else {
                // Check triples +
                for (int i = 1; i < 7; i++) {
                    if (counts[i] >= 3) {
                        int tripleSetPoints = 0;
                        if (i == 1) {
                            tripleSetPoints = 1000;
                        } else {
                            tripleSetPoints = i * 100;
                        }
                        if (counts[i] > 3) {
                            tripleSetPoints += (counts[i] - 3) * 100 * i;
                        }
                        meldScore += tripleSetPoints;
                    }
                }

                // Add 1's & 5's if unused in triples
                if (counts[1] < 3) {
                    meldScore += counts[1] * 100;
                }

                if (counts[5] < 3) {
                    meldScore += counts[5] * 50;
                }
            }
        }
        return meldScore;
    }
}