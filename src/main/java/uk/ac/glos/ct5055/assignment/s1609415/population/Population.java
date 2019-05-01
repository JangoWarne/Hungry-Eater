package uk.ac.glos.ct5055.assignment.s1609415.population;

import javafx.util.Pair;
import uk.ac.glos.ct5055.assignment.s1609415.ui.Config;
import uk.ac.glos.ct5055.assignment.s1609415.ui.Progress;
import uk.ac.glos.ct5055.assignment.s1609415.ui.SimulationController;

/**
 * This class is the main class to process/optimise an entire population of creatures through many generations
 * it runs in a separate processor thread
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class Population {

    private Status status;
    private Progress progress;
    private Config config;

    public Population(Progress progress, Config config) {
        status = new Status();
        this.progress = progress;
        this.config = config;
    }

    public void startSimulation( SimulationController uiReference ) {
        status = new Status();

        // Run background simulation of creatures
        Thread simulationThread = new Thread(() -> {
            boolean first = true;

            while (status.getRunStatus()) {
                new Generation(status, progress, config).start( first );
                first = false;
            }
        });
        simulationThread.start();


        // Display best of generation on UI
        Thread displayThread = new Thread(() -> {

            int displayGen = 0;
            boolean first = true;
            while (status.getRunStatus()) {

                // Wait for new completed generation
                if (progress.getGeneration() > displayGen) {

                    uiReference.drawVisibleGen( displayGen );
                    displayGen = progress.getGeneration();

                    // Display generation on UI
                    progress.getResult().getBestLife().uiRun( status, uiReference, 100, first );
                    first = false;

                } else {
                    try {
                        wait(500);
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
