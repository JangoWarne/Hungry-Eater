package uk.ac.glos.ct5055.assignment.s1609415.population;

import uk.ac.glos.ct5055.assignment.s1609415.ui.Config;
import uk.ac.glos.ct5055.assignment.s1609415.ui.Progress;

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

    public void startSimulation() {
        status = new Status();

        Thread thread = new Thread(() -> {
            boolean first = true;

            while (status.getRunStatus()) {
                new Generation(status, progress, config).start( first );
                first = false;
            }
        });

        thread.start();
    }

    public void stopSimulation() {
        status.stopSimulation();
    }
}
