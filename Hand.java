import java.util.ArrayList;

/* 
 * Represents six values of die
 * Author: Darrell Cenido
*/

public class Hand {
    private ArrayList<Die> dice;

    /**
     * Constructor
     */
    public Hand() {
        dice = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            dice.add(new Die());
        }
        sort();
    }

    /**
     * Sorts the hand
     */
    public void sort() {
        for (int i = 0; i < dice.size() - 1; i++) {
            for (int j = 0; j < dice.size() - i - 1; j++) {
                if (dice.get(j).getValue() > dice.get(j + 1).getValue()) {
                    Die temp = dice.get(j);
                    dice.set(j, dice.get(j + 1));
                    dice.set(j+1, temp);
                }
            }
        }
    }

    /**
     * Gets the frequency of each die value in the hand
     * Returns int array with 7 counts
     */
    public int[] getValQuantities() {
        int[] counts = new int[7];
        for (Die d : dice) {
            if (d.getValue() != 0) {
                counts[d.getValue()]++;
            }
        }
        return counts;
    }

    /**
     * Gets list of dice
     * Returns array of Die objects
     */
    public ArrayList<Die> getDice() {
        return dice;
    }

    /**
     * Prints sorted hand
     */
    public void printHand() {
        System.out.print("Hand: ");
        for (Die d : dice) {
            System.out.print(d.getValue() + " ");
        }
        System.out.println();
    }
}