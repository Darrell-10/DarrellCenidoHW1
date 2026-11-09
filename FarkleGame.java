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

        // Header Banner
        System.out.println("************************************************************************");
        System.out.println("*                    Zag Farkle by Darrell Cenido!                     *");
        System.out.println("*                           Copyright: 2026                            *");
        System.out.println("************************************************************************");
        System.out.println();

        System.out.print("How many points would you like to play in this game? [10000] ");
        String pointGoalInput = scanner.nextLine().trim();
        int targetPoints = 10000;

        if (!pointGoalInput.isEmpty()) {
            targetPoints = Integer.parseInt(pointGoalInput);
        }

        System.out.print("How many players are going to be playing? [1] ");
        String numPlayersInput = scanner.nextLine().trim();
        int numPlayers = 1;

        if (numPlayersInput.equals("2")) {
            numPlayers = 2;
        } else if (numPlayersInput.equals("3")) {
            numPlayers = 3;
        } else if (numPlayersInput.equals("4")) {
            numPlayers = 4;
        } else if (numPlayersInput.equals("5")) {
            numPlayers = 5;
        } else if (numPlayersInput.equals("6")) {
            numPlayers = 6;
        } else if (numPlayersInput.equals("7")) {
            numPlayers = 7;
        } else if (numPlayersInput.equals("8")) {
            numPlayers = 8;
        } else if (numPlayersInput.equals("9")) {
            numPlayers = 9;
        }

        String[] defaultNamePool = {"Unknown player #1", "Unknown player #2", "Unknown player #3", 
                                    "Unknown player #4", "Unknown player #5", "Unknown player #6", 
                                    "Unknown player #7", "Unknown player #8", "Unknown player #9"};

        ArrayList<String> playerNames = new ArrayList<>();
        int[] playerScores = new int[numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            int playerNum = i + 1;
            String defaultName = (i < defaultNamePool.length) ? defaultNamePool[i] : "Unknown Player #" + playerNum;
            
            System.out.print("What is the player #" + playerNum + "'s name? [" + defaultName + "] ");
            String inputName = scanner.nextLine().trim();
            
            if (inputName.isEmpty()) {
                playerNames.add(defaultName);
            } else {
                playerNames.add(inputName);
            }
        }

        System.out.println("----------------------------------");
        System.out.println();

        int roundNumber = 1;
        int currentPlayerIndex = 0;
        boolean hasQuit = false;
        boolean gameWon = false;

        boolean endgameTriggered = false;
        int endgameTurnsRemaining = 0;

        while (!gameWon && !hasQuit) {
            String currentName = playerNames.get(currentPlayerIndex);

            System.out.println("--- Round " + roundNumber + ": " + currentName + "'s Turn ---");
            System.out.println(currentName + ", it is your turn! Rolling dice...");

            Hand hand = new Hand();
            int turnScore = 0;
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

                boolean meldPhaseDone = false;

                while (!meldPhaseDone) {
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
                    System.out.println("                Turn Points: " + (turnScore + meldScore));
                    System.out.println();
                    System.out.println(" (K) Bank Meld & End Round");
                    System.out.println(" (R) Reroll unmelded dice");
                    System.out.println(" (Q) Quit game");
                    System.out.println();
                    System.out.print("Enter letters for your choice(s): ");
                    userInput = scanner.nextLine().trim();

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
                            hasQuit = true;
                            turnOver = true;
                            meldPhaseDone = true;
                            break;
                        } else if (letter == 'K') {
                            if (meldScore > 0 || turnScore > 0) {
                                turnScore += meldScore;

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
                                    System.out.println("You used all 6 dice! Rolling 6 new dice.");
                                    System.out.println("*********************");
                                    hand = new Hand();
                                    diceList = hand.getDice();
                                    for (int d = 0; d < 6; d++) {
                                        activeDiceValues[d] = diceList.get(d).getValue();
                                        meldValues[d] = 0;
                                    }
                                    meldPhaseDone = true;
                                } else {
                                    playerScores[currentPlayerIndex] += turnScore;
                                    meldPhaseDone = true;
                                    turnOver = true;
                                }
                            }
                        } else if (letter == 'R') {
                            if (meldScore > 0) {
                                turnScore += meldScore;

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
                                    System.out.println("You used all 6 dice! Rolling 6 new dice.");
                                    System.out.println("*********************");
                                    hand = new Hand();
                                } else {
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
                                meldPhaseDone = true;
                            } else {
                                System.out.println("Select scoring dice before rerolling!");
                            }
                        }
                    }
                }
            }

            if (!hasQuit) {
                System.out.println();
                System.out.println("Current total points:");
                for (int p = 0; p < numPlayers; p++) {
                    System.out.println(playerNames.get(p) + "\t" + playerScores[p]);
                }
                System.out.println();

                if (!endgameTriggered && playerScores[currentPlayerIndex] >= targetPoints) {
                    endgameTriggered = true;
                    endgameTurnsRemaining = numPlayers - 1;

                    if (numPlayers > 1) {
                        System.out.println("========================================================================");
                        System.out.println(" ENDGAME TRIGGERED! " + currentName + " reached " + playerScores[currentPlayerIndex] + " points!");
                        System.out.println(" All other players get ONE final turn to try to beat their score!");
                        System.out.println("========================================================================\n");
                    }
                } else if (endgameTriggered) {
                    endgameTurnsRemaining--;
                }

                // Check if all endgame turns have completed
                if (endgameTriggered && endgameTurnsRemaining <= 0) {
                    gameWon = true;
                } else {
                    // Advance turn to next player
                    currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
                    if (currentPlayerIndex == 0) {
                        roundNumber++;
                    }
                }
            }
        }

        if (hasQuit) {
            System.out.println();
            System.out.println(playerNames.get(currentPlayerIndex) + " quit the game!");
            System.out.println();
        } else {
            int winningIndex = 0;
            int highestScore = playerScores[0];
            for (int p = 1; p < numPlayers; p++) {
                if (playerScores[p] > highestScore) {
                    highestScore = playerScores[p];
                    winningIndex = p;
                }
            }

            String winner = playerNames.get(winningIndex);
            System.out.println("************************************************************************");
            System.out.println(" CONGRATULATIONS " + winner.toUpperCase() + "! YOU WON!");
            System.out.println(" Final Scores:");
            for (int p = 0; p < numPlayers; p++) {
                System.out.println(" " + playerNames.get(p) + "\t" + playerScores[p]);
            }
            System.out.println("************************************************************************");
        }
    }
}