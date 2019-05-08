package uk.ac.glos.ct5055.assignment.s1609415.ui;

/**
 * This class stores the user defined settings from Menu.fxml
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class Config {

    private int creatureRadius;
    private int foodRadius;
    private int stepLength;
    private int stepsPerLife;
    private int populationSize;
    private double survivalRate;

    private int screenYMin;
    private int screenYMax;
    private int screenXMin;
    private int screenXMax;

    /**
     * Initialises all values for the object
     * @param sizeMultiplier This is used to scale the creature and food from their default sizes
     * @param stepsPerLife This is the number of steps each creature can take in a life
     * @param populationSize This is the number of creatures in the population every generation
     * @param survivalRate This is fraction of creatures that will survive each generation
     */
    public Config( float sizeMultiplier, int stepsPerLife, int populationSize, double survivalRate) {

        final int DEFAULT_CREATURE_RADIUS = 24;
        final int DEFAULT_FOOD_RADIUS = 8;
        final int DEFAULT_STEP_LENGTH = 12;

        final int SCREEN_WIDTH = 1400;
        final int SCREEN_HEIGHT = 687;

        this.creatureRadius = Math.round( DEFAULT_CREATURE_RADIUS * sizeMultiplier );
        this.foodRadius = Math.round( DEFAULT_FOOD_RADIUS * sizeMultiplier );
        this.stepLength = Math.round( DEFAULT_STEP_LENGTH * sizeMultiplier );
        this.stepsPerLife = stepsPerLife;
        this.populationSize = populationSize;
        this.survivalRate = survivalRate;

        this.screenYMin = this.creatureRadius;
        this.screenYMax = SCREEN_HEIGHT - this.creatureRadius;
        this.screenXMin = this.creatureRadius;
        this.screenXMax = SCREEN_WIDTH - this.creatureRadius;
    }

    /**
     * Returns the pixel radius of the creature
     * @return This is the pixel radius
     */
    public int getCreatureRadius() {
        return creatureRadius;
    }

    /**
     * Returns the pixel radius of the food
     * @return This is the pixel radius
     */
    public int getFoodRadius() {
        return foodRadius;
    }

    /**
     * Returns the pixes step length of the creature
     * @return This is the pixel step length
     */
    public int getStepLength() {
        return stepLength;
    }

    /**
     * Returns the number of steps each creature can take in a life
     * @return This is the number of steps per life
     */
    public int getStepsPerLife() {
        return stepsPerLife;
    }

    /**
     * Returns the number of creatures in the population every generation
     * @return This is the population size
     */
    public int getPopulationSize() {
        return populationSize;
    }

    /**
     * Returns the survival rate fraction of creatures each generation
     * @return This is the survival rate
     */
    public double getSurvivalRate() {
        return survivalRate;
    }

    /**
     * Returns the creature viewer screen maxY value
     * @return This maximum Y pixel value
     */
    public int getScreenYMax() {
        return screenYMax;
    }

    /**
     * Returns the creature viewer screen minY value
     * @return This minimum Y pixel value
     */
    public int getScreenYMin() {
        return screenYMin;
    }

    /**
     * Returns the creature viewer screen maxX value
     * @return This maximum X pixel value
     */
    public int getScreenXMax() {
        return screenXMax;
    }

    /**
     * Returns the creature viewer screen minX value
     * @return This minimum X pixel value
     */
    public int getScreenXMin() {
        return screenXMin;
    }
}
