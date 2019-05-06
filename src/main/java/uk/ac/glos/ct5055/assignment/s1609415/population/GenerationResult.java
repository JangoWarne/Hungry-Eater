package uk.ac.glos.ct5055.assignment.s1609415.population;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 * This class stores the results for a generation
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class GenerationResult {

    private Food food;
    private Pair<Double, ArrayList<Double>> bestCreature;

    /**
     * Stores the best Creature from the generation
     * @param bestCreature This is the Creature of the most effective creature in the generation
     */
    public void setBestCreature(Pair<Double, ArrayList<Double>> bestCreature) {
        this.bestCreature = bestCreature;
    }

    /**
     * Returns the best Creature from the generation
     * @return This is the creature of the most effective creature in the generation
     */
    public Pair<Double, ArrayList<Double>> getBestCreature() {
        return bestCreature;
    }

    /**
     * Stores the food positions for creatures in the generation
     * @param food This is the food positions
     */
    public void setFood(Food food) {
        this.food = food;
    }

    /**
     * Returns the food position information used for this Generation
     * @return This is the food positions
     */
    public Food getFood() {
        return food;
    }
}
