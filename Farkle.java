import java.util.Random;
import java.util.Scanner;

public class Farkle {
    public static void main(String[] args) {
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);

        // initialize an array of 6 dice rolls
        int[] dice = new int[6]; 

        // generate 6 random roll values
        for (int i = 0; i < dice.length; i++) {
            dice[i] = rand.nextInt(6) + 1;
            System.out.print(dice[i] + " ");
        }

        // sort the hand the hard way
        for (int i = 0; i < 6 - 1; i++) {
            for (int j = 0; j < 6 - i - 1; j++) {
                if (dice[j] > dice[j + 1]) {
                    int temp = dice[j];
                    dice[j] = dice[j + 1];
                    dice[j + 1] = temp;
                }
            }
        }

        // print sorted dice
        System.out.println();
        System.out.print("Hand: ");
        for (int i = 0; i < dice.length; i++) {
            System.out.print(dice[i] + " ");
        }

        System.out.println();
        int[] diceNumCount = new int[7];
        for (int i = 0; i < 7; i++) {
            diceNumCount[i] = 0;
        }

        for (int i = 0; i < 6; i++) {
            diceNumCount[dice[i]]++;
        }

        System.out.print("Quantity of each die value: ");
        for (int i = 0; i < 7; i++) {
            System.out.print(diceNumCount[i] + " ");
        }
        System.out.println();

        boolean isFarkle = true;

        // GAME LOGIC
        if (diceNumCount[1] != 0 || diceNumCount[5] != 0) {
            isFarkle = false;
        }

        for (int i = 2; i < 7; i++) {
            if(diceNumCount[i] >= 3) {
                isFarkle = false;
            }
        }

        int pairCount = 0;
        for( int i = 1; i < 7; i++ ) {
            if( diceNumCount[i] == 2 ) {
                pairCount++;
            }
        }

        if (pairCount == 3) {
            isFarkle = false;
        }

        int totalScore = 0;
        if(isFarkle) {
            System.out.println("Farkle! Points: 0");
        } else {
            String userInput = "";
            int meldScore = 0;
            int[] meld = {0, 0, 0, 0, 0, 0};
            boolean done = false;

            while(!done) {
                System.out.println();
                System.out.println("******************* Current hand and meld *******************");
                System.out.println(" Die   Hand |   Meld");
                System.out.println("------------+---------------");
                for (int i = 0; i < 6; i++) {
                    char option = (char) ('A' + i);
                    System.out.print(" (" + option + ")    ");
                    if (dice[i] != 0) {
                        System.out.print(dice[i]);
                    } else {
                        System.out.print(" ");
                    }
                    System.out.print("   |     ");
                    if (meld[i] != 0) {
                        System.out.print(meld[i]);
                    } else {
                        System.out.print(" ");
                    }
                    System.out.println();
                }

                // done = true;
                System.out.println("------------+---------------");
        boolean isValidMeld = false;

        // Calculate the meld
        meldScore = 0; // Reset each time user changes meld dice
        int meldDiceCount = 0;
        int[] meldDice = {0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 6; i++) {
            if (meld[i] != 0) {
                meldDice[meldDiceCount] = meld[i];
                meldDiceCount++;
            }
        }

        int[] meldDiceSizesCount = {0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 6; i++) {
            meldDiceSizesCount[meldDice[i]]++;
        }
        
        boolean isStraight = true;
                for (int i = 1; i < 7; i++) {
                    if (meldDiceSizesCount[i] != 1) {
                        isStraight = false;
                    }
                }
                if (isStraight) {
                    meldScore += 1000;
                } else {
                    // Check 3 pairs
                    int meldPairCount = 0;
                    for (int i = 1; i < 7; i++) {
                        if (meldDiceSizesCount[i] == 2) {
                            meldPairCount++;
                        }
                    }
                    if (meldPairCount == 3) {
                        meldScore += 750;
                    } else {
                        // Check triples +
                        for (int i = 1; i < 7; i++) {
                            if (meldDiceSizesCount[i] >= 3) {
                                int tripleSetPoints = 0;
                                if (i == 1) {
                                    tripleSetPoints = 1000;
                                } else {
                                    tripleSetPoints = i * 100;
                                }
                                if (meldDiceSizesCount[i] > 3) {
                                    tripleSetPoints += (meldDiceSizesCount[i] - 3) * 100 * i;
                                }
                                meldScore += tripleSetPoints;
                            }
                        }

                        // Add 1's & 5's if unused
                        if (meldDiceSizesCount[1] < 3) {
                            meldScore += meldDiceSizesCount[1] * 100;
                        }

                        if (meldDiceSizesCount[5] < 3) {
                            meldScore += meldDiceSizesCount[5] * 50;
                        }
                    }
                }

                System.out.println("                Meld Score: " + meldScore);
                System.out.println();
                System.out.println(" (K) BanK Meld & End Round");
                System.out.println(" (Q) Quit game");
                System.out.println();

                System.out.print("Enter letters for your choice(s): ");
                userInput = scanner.next();

                for( int i = 0; i < userInput.length(); i++ ) {
                    char letter = Character.toUpperCase(userInput.charAt(i));
                    if( letter >= 'A' && letter <= 'F' ) {
                        int index = letter - 'A';
                        if(dice[index] != 0) {
                            meld[index] = dice[index];
                            dice[index] = 0;
                        } else {
                            dice[index] = meld[index];
                            meld[index] = 0;
                        }
                    } else if(letter == 'Q') {
                        done = true;
                    } else if(letter == 'K') {
                        done = true;
                        totalScore += meldScore;
                    }
                }
            }
            System.out.println();
            System.out.println("Round over. Total score is now: " + totalScore);
            System.out.println();
        }
    }
}