package uk.ac.glos.ct5055.assignment.s1609415.ui;

import javafx.application.Platform;
import uk.ac.glos.ct5055.assignment.s1609415.population.Creature;
import uk.ac.glos.ct5055.assignment.s1609415.population.Food;
import uk.ac.glos.ct5055.assignment.s1609415.population.GenerationResult;

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
    private GenerationResult result;
    private int generation;
    private int progress;
    private ArrayList<Creature> creatures;
    private int populationSize;

    /**
     * Initialises the values for the object;
     * @param uiReference This is a reference to the SimulationController UI
     */
    public Progress( SimulationController uiReference, int populationSize ) {
        this.uiReference = uiReference;
        this.generation = 0;
        this.progress = 0;
        this.creatures = new ArrayList<>();
        this.result = new GenerationResult();
        this.populationSize = populationSize;
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
     * Returns the last completed generation
     * @return This contains most recent completed generation
     */
    public int getGeneration() {

        synchronized (lockCreature) {
            return this.generation;
        }

    }

    /**
     * Increments the number of the current generation
     * Resets progress for generation
     * Updates values in UI
     */
    public void incrementGeneration( Food food ) {

        synchronized (lockProgress) {
            this.generation++;
            this.progress = 0;

            Creature bestCreature = null;
            double bestResult = -1;
            double total = 0;
            double current;

            // Calculate generation statistics
            for (Creature creature: creatures) {
                current = creature.getResult();

                if (current > bestResult) {
                    bestResult = current;
                    bestCreature = creature;
                }
                total = total + current;
            }

            double mean = total/creatures.size();

            this.result.setBestCreature( bestCreature );
            this.result.setFood( (Food) food.clone() );

            // Update UI
            final int gen = getGeneration();
            final int progress = this.progress;
            final double best = bestResult;

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    uiReference.drawProgressGeneration( gen );
                    uiReference.drawProgressValue( progress );
                    uiReference.drawCompletedGen( gen, best, mean );
                }
            });
        }

    }

    /**
     * Increments progress for current generation
     * Updates value in UI
     */
    public void incrementProgress(Creature creature) {

        synchronized (lockProgress) {
            this.creatures.add( (Creature) creature.clone() );
            this.progress++;

            // Update UI
            final int progress = this.progress;

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    uiReference.drawProgressValue( progress );
                }
            });
        }
    }
}
