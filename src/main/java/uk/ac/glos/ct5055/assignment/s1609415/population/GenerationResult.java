package uk.ac.glos.ct5055.assignment.s1609415.population;

/**
 * This class stores the results for a generation
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class GenerationResult {

    private int generation;
    private double bestResult;
    private double meanResult;
    private Creature bestCreature;

    /**
     * Stores the best creature from the generation
     * @param bestCreature This is the most effective creature in the generation
     */
    public void setBestCreature(Creature bestCreature) {
        this.bestCreature = bestCreature;
    }

    public Creature getBestCreature() {
        return bestCreature;
    }

    /**
     * Stores the largest amount of food eaten
     * @param bestResult This is the most food any creature in the generation ate
     */
    public void setBestResult(double bestResult) {
        this.bestResult = bestResult;
    }

    /**
     * Returns the largest amount of food eaten
     * @return This is the most food any creature in the generation ate
     */
    public double getBestResult() {
        return bestResult;
    }

    /**
     * Stores the mean amount of food eaten
     * @param meanResult This is the mean amount food the creatures in the generation ate
     */
    public void setMeanResult(double meanResult) {
        this.meanResult = meanResult;
    }

    /**
     * Returns the mean amount of food eaten
     * @return This is the mean amount food the creatures in the generation ate
     */
    public double getMeanResult() {
        return meanResult;
    }

    /**
     * Stores the value of the recorded generation
     * @param generation This is the value of the recorded generation
     */
    public void setGeneration(int generation) {
        this.generation = generation;
    }

    /**
     * Returns the value of the recorded generation
     * @return This is the value of the recorded generation
     */
    public int getGeneration() {
        return generation;
    }
}
