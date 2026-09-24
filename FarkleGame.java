import java.util.Scanner;
import java.util.ArrayList;

/**
 * Handles game play, general printing, and user prompting
 * Author: Darrell Cenido
 */

public class FarkleGame {

    /**
     * starts round of Farkle
     */
    public void play() {
        Scanner scanner = new Scanner(System.in);
        Hand hand = new Hand();

        hand.printHand();
        int[] counts = hand.getValQuantities();

        System.out.print("Quantity of each die value: ");
        for (int i = 1; i < 7; i++) {
            System.out.print(counts[i] + " ");
        }
        System.out.println();

        int totalScore = 0;

        if (FarkleScorer.isFarkle(counts)) {
            System.out.println("Farkle! Points: 0");
        } else {
            String userInput = "";
            int meldScore = 0;
            int[] meldValues = {0, 0, 0, 0, 0, 0};
            int[] activeDiceValues = new int[6];

            ArrayList<Die> diceList = hand.getDice();
            for (int i = 0; i < 6; i++) {
                activeDiceValues[i] = diceList.get(i).getValue();
            }

            boolean done = false;

            while (!done) {
                System.out.println();
                System.out.println("*************************** Current hand and meld *******************");
                System.out.println(" Die   Hand |   Meld");
                System.out.println("------------+---------------");
                for (int i = 0; i < 6; i++) {
                    char option = (char) ('A' + i);
                    System.out.print(" (" + option + ")    ");
                    if (activeDiceValues[i] != 0) {
                        System.out.print(activeDiceValues[i]);
                    } else {
                        System.out.print(" ");
                    }
                    System.out.print("   |     ");
                    if (meldValues[i] != 0) {
                        System.out.print(meldValues[i]);
                    } else {
                        System.out.print(" ");
                    }
                    System.out.println();
                }
                System.out.println("------------+---------------");

                // Prepare meld array for scoring calculation
                int meldDiceCount = 0;
                int[] meldDiceArray = {0, 0, 0, 0, 0, 0};
                for (int i = 0; i < 6; i++) {
                    if (meldValues[i] != 0) {
                        meldDiceArray[meldDiceCount] = meldValues[i];
                        meldDiceCount++;
                    }
                }

                int[] meldCounts = {0, 0, 0, 0, 0, 0, 0};
                for (int i = 0; i < 6; i++) {
                    meldCounts[meldDiceArray[i]]++;
                }

                meldScore = FarkleScorer.calculateMeldScore(meldCounts);

                System.out.println("                Meld Score: " + meldScore);
                System.out.println();
                System.out.println(" (K) BanK Meld & End Round");
                System.out.println(" (Q) Quit game");
                System.out.println();
                System.out.print("Enter letters for your choice(s): ");
                userInput = scanner.next();

                for (int i = 0; i < userInput.length(); i++) {
                    char letter = Character.toUpperCase(userInput.charAt(i));
                    if (letter >= 'A' && letter <= 'F') {
                        int index = letter - 'A';
                        if (activeDiceValues[index] != 0) {
                            meldValues[index] = activeDiceValues[index];
                            activeDiceValues[index] = 0;
                        } else {
                            activeDiceValues[index] = meldValues[index];
                            meldValues[index] = 0;
                        }
                    } else if (letter == 'Q') {
                        done = true;
                    } else if (letter == 'K') {
                        done = true;
                        totalScore += meldScore;
                    }
                }
            }
        }

        System.out.println();
        System.out.println("Round over. Total score is now: " + totalScore);
        System.out.println();
    }
}