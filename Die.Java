import java.util.Random;

/* 
 * Represents a single six sided die
 * Author: Darrell Cenido
*/

public class Die {
    private int val;
    Random rand = new Random();

    /**
     * Constructor
     */
    public Die() {
        roll();
    }

    /**
     * Gets random val between 1 and 6
     */
    public void roll() {
        this.val = rand.nextInt(6) + 1;
    }

    /**
     * Gets value of the die
     * Returns value of die
     */
    public int getValue() {
        return val;
    }

    /**
     * Sets value of the die
     * Param is new value to set
     */
    public void setValue(int val) {
        this.val = val;
    }
}