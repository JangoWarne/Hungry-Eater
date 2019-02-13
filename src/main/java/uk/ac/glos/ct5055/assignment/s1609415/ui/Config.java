package uk.ac.glos.ct5055.assignment.s1609415.ui;

import java.util.ArrayList;

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
    private ArrayList<Integer> hiddenLayerNodes;

    /**
     * Initialises all values for the object
     * @param sizeMultiplier This is used to scale the creature and food from their default sizes
     * @param stepsPerLife This is the number of steps each creature can take in a life
     * @param populationSize This is the number of creatures in the population every generation
     * @param hiddenLayerNodes This is the number of nodes in the hidden layers in every creature's neural network
     */
    public Config( int sizeMultiplier, int stepsPerLife, int populationSize, ArrayList<Integer> hiddenLayerNodes) {

        final int DEFAULT_CREATURE_RADIUS = 24;
        final int DEFAULT_FOOD_RADIUS = 8;
        final int DEFAULT_STEP_LENGTH = 12;

        this.creatureRadius = DEFAULT_CREATURE_RADIUS * sizeMultiplier;
        this.foodRadius = DEFAULT_FOOD_RADIUS * sizeMultiplier;
        this.stepLength = DEFAULT_STEP_LENGTH * sizeMultiplier;
        this.stepsPerLife = stepsPerLife;
        this.populationSize = populationSize;
        this.hiddenLayerNodes = hiddenLayerNodes;
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
     * Returns the number of nodes in the hidden layers in every creature's neural network
     * @return This is an list of the number of nodes per hidden layer
     */
    public ArrayList<Integer> getHiddenLayerNodes() {
        return hiddenLayerNodes;
    }
}
