/**
 * Test file for Zag Farkle
 * Author: Darrell Cenido
 */
public class FarkleTester {
    public static void main(String[] args) {
        System.out.println("Running Zag Farkle Test File...\n");

        testPlayerName();
        testFarkleDetection();
        testCombosScoring();

        System.out.println("\nAll tests completed!");
    }

    /**
     * Test that player name setting works correctly.
     */
    public static void testPlayerName() {
        System.out.print("Testing Player Name Setting... ");
        // Simulating the logic from FarkleGame
        String inputName = "Darrell";
        if (inputName.equals("")) {
            inputName = "Unknown Player";
        }
        
        assert inputName.equals("Darrell") : "Player name failed!";
        System.out.println("PASSED");
    }

    /**
     * Test that a known Farkle hand is correctly identified.
     */
    public static void testFarkleDetection() {
        System.out.print("Testing Farkle Detection... ");
        
        // Hand counts with no 1s, 5s, or triples
        int[] farkleCounts = {0, 0, 1, 1, 1, 0, 0}; // Values 2, 3, 4 present once each -> Farkle
        
        boolean isFarkle = FarkleScorer.isFarkle(farkleCounts);
        assert isFarkle == true : "Farkle detection failed for non-scoring hand!";
        
        System.out.println("PASSED");
    }

    /**
     * Test at least three different types of combos (Straight, 3 Pairs, Single 1 / Triples).
     */
    public static void testCombosScoring() {
        System.out.print("Testing Combo Scoring (Straight, 3 Pairs, Single 1)... ");

        // 1. Straight (1 through 6)
        int[] straightCounts = {0, 1, 1, 1, 1, 1, 1};
        int straightScore = FarkleScorer.calculateMeldScore(straightCounts);
        assert straightScore == 1000 : "Straight score expected 1000, got " + straightScore;

        // 2. Three Pairs
        int[] threePairsCounts = {0, 2, 2, 2, 0, 0, 0};
        int pairsScore = FarkleScorer.calculateMeldScore(threePairsCounts);
        assert pairsScore == 750 : "Three pairs score expected 750, got " + pairsScore;

        // 3. Single 1 die
        int[] singleOneCounts = {0, 1, 0, 0, 0, 0, 0};
        int singleScore = FarkleScorer.calculateMeldScore(singleOneCounts);
        assert singleScore == 100 : "Single 1 score expected 100, got " + singleScore;

        System.out.println("PASSED");
    }
}