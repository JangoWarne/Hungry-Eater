package uk.ac.glos.ct5055.assignment.s1609415.ui;

import uk.ac.glos.ct5055.assignment.s1609415.population.Creature;
import uk.ac.glos.ct5055.assignment.s1609415.population.GenerationResult;
import uk.ac.glos.ct5055.assignment.s1609415.population.Life;

import java.util.ArrayList;

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
    private ArrayList<Life> lives;
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
        this.lives = new ArrayList<>();
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
    public ArrayList<Life> incrementGeneration() {

        synchronized (lockProgress) {
            this.generation++;
            this.progress = 0;

            Creature bestCreature = null;
            double bestResult = Double.MAX_VALUE;
            double total = 0;
            double current;

            // Calculate generation statistics
            for (Life life: lives) {
                current = life.getResult();

                if (current < bestResult) {
                    bestResult = current;
                    bestCreature = life.getCreature();
                    total = total + current;
                }
            }

            this.result.setBestCreature( bestCreature );
            this.result.setBestResult( bestResult );
            this.result.setMeanResult( total/lives.size() );
            ArrayList<Life> completedLives = this.lives;

            this.lives = new ArrayList<>();

            // Update UI
            uiReference.drawProgressGeneration(this.generation);
            uiReference.drawProgressValue(this.progress);

            return completedLives;
        }

    }

    /**
     * Increments progress for current generation
     * Updates value in UI
     */
    public void incrementProgress(Life life) {

        synchronized (lockProgress) {
            this.lives.add(life);
            this.progress++;

            // Update UI
            uiReference.drawProgressValue(this.progress);
        }
    }

    /**
     * Returns the progress of the current generation
     * @return This contains the progress of the current generation
     */
    public int getProgress() {

        synchronized (lockCreature) {
            return this.progress;
        }

    }
}
