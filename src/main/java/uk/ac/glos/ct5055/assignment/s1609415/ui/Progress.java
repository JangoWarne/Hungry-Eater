package uk.ac.glos.ct5055.assignment.s1609415.ui;

import uk.ac.glos.ct5055.assignment.s1609415.population.GenerationResult;

/**
 * This class stores the progress of the generation currently being processed
 * All methods are thread-safe
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class Progress {

    private final Object lockCreature = new Object();
    private final Object lockProgress = new Object();

    private SimulationController uiReference;
    private GenerationResult result;
    private int generation;
    private int progress;

    /**
     * Initialises the values for the object;
     * @param uiReference This is a reference to the SimulationController UI
     */
    public Progress( SimulationController uiReference ) {
        this.uiReference = uiReference;
        this.generation = 0;
        this.progress = 0;
    }

    /**
     * Stores results of the finished generation
     * Outputs statistics about the generation to the UI
     * @param result This contains the results for the finished generation
     */
    public void setGenerationResult(GenerationResult result) {

        synchronized (lockCreature) {
            this.result = result;
        }

    }

    /**
     * Returns the results of the last finished generation
     * @return This contains the results for the finished generation
     */
    public GenerationResult getResult() {

        synchronized (lockCreature) {
            return this.result;
        }

    }

    /**
     * Increments the number of the current generation
     * Resets progress for generation
     * Updates values in UI
     */
    public void incrementGeneration() {

        synchronized (lockProgress) {
            this.generation++;
            this.progress = 0;

            // Update UI
            uiReference.drawProgressGeneration(this.generation);
            uiReference.drawProgressValue(this.progress);
        }

    }

    /**
     * Increments progress for current generation
     * Updates value in UI
     */
    public void incrementProgress() {

        synchronized (lockProgress) {
            this.progress++;

            // Update UI
            uiReference.drawProgressValue(this.progress);
        }
    }
}
