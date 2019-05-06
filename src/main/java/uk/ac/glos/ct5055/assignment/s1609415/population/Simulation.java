package uk.ac.glos.ct5055.assignment.s1609415.population;

import javafx.application.Platform;
import uk.ac.glos.ct5055.assignment.s1609415.ui.Config;
import uk.ac.glos.ct5055.assignment.s1609415.ui.Progress;
import uk.ac.glos.ct5055.assignment.s1609415.ui.SimulationController;

/**
 * This class is the main class to simulate an entire population of creatures through many generations
 * it runs in a separate processor thread
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class Simulation {

    private Status status;
    private Progress progress;
    private Config config;

    public Simulation(Progress progress, Config config) {
        status = new Status();
        this.progress = progress;
        this.config = config;
    }

    public void startSimulation( SimulationController uiReference, Status status ) {
        this.status = status;

        // Run background simulation of creatures
        Thread simulationThread = new Thread(() -> {
            new Population(status, progress, config).start();
        });
        simulationThread.start();


        // Display best of generation on UI
        Thread displayThread = new Thread(() -> {

            int displayGen = 1;
            boolean first = true;
            while (status.getRunStatus()) {

                // Wait for new completed generation
                if (progress.getGeneration() > displayGen) {

                    final int gen = displayGen;
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            uiReference.drawVisibleGen( gen );
                        }
                    });

                    displayGen = progress.getGeneration();

                    // Display generation on UI
                    GenerationResult result = progress.getResult();
                    Creature creature = result.getBestCreature();
                    Food food = result.getFood();
                    Life life = new Life( config, food, progress, status );
                    life.uiRun( creature, uiReference, 10, first );
                    first = false;

                } else {
                    // Look again in 100ms
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        return;
                    }
                }

            }
        });
        displayThread.start();
    }

    public void stopSimulation() {
        status.stopSimulation();
    }


}
