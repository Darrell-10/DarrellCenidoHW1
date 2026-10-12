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

        // Header Banner
        System.out.println("************************************************************************");
        System.out.println("*                    Zag Farkle by Darrell Cenido!                     *");
        System.out.println("*                           Copyright: 2026                            *");
        System.out.println("************************************************************************");
        System.out.println();

        System.out.print("Enter player 1 name: ");
        String player1Name = scanner.nextLine();

        if(player1Name.equals("")){
            player1Name = "Unknown Player";
        }

        System.out.println();
        System.out.println(player1Name + ", it is your turn! Rolling dice...");
        System.out.println();

        int totalScore = 0;
        boolean turnOver = false;
        
        while (!turnOver) {
        
            hand.printHand();
            int[] counts = hand.getValQuantities();

            System.out.print("Quantity of each die value: ");
            for (int i = 1; i < 7; i++) {
                System.out.print(counts[i] + " ");
            }
            System.out.println();

            if (FarkleScorer.isFarkle(counts)) {
                System.out.println("Farkle! Points: 0");
                turnOver = true;
                break;
            } 
            
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
                System.out.println("******************* Current hand and meld *******************");
                System.out.println();
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
                System.out.println(" (R) Reroll unmelded dice"); // Added missing semicolon here
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
                        turnOver = true;
                    } else if (letter == 'K') {
                        done = true;
                        totalScore += meldScore;
                    } else if (letter == 'R') {
                        if (meldScore > 0) {
                            totalScore += meldScore;
                            done = true;

                            // Check Hot Hand
                            boolean hotHand = true;
                            for (int dVal : activeDiceValues) {
                                if (dVal != 0) {
                                    hotHand = false;
                                    break;
                                }
                            }

                            if (hotHand) {
                                System.out.println();
                                System.out.println("***** HOT HAND! *****");
                                System.out.println("Would you like to roll 6 new dice, or bank and end your turn?");
                                System.out.println("*********************");
                                hand = new Hand();
                            } else {
                                // Reroll unmelded dice
                                ArrayList<Die> currentDice = hand.getDice();
                                for (int dIdx = 0; dIdx < 6; dIdx++) {
                                    if (activeDiceValues[dIdx] != 0) {
                                        currentDice.get(dIdx).roll();
                                    } else {
                                        currentDice.get(dIdx).setValue(0);
                                    }
                                }
                                hand.sort();
                            }
                        } else {
                            System.out.println("Select scoring dice before rerolling!");
                        }
                    }
                }
            }
        }

        System.out.println();
        System.out.println("Round over. Total score is now: " + totalScore);
        System.out.println();
    }
}