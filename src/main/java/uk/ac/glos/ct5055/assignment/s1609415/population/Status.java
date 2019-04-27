package uk.ac.glos.ct5055.assignment.s1609415.population;

/**
 * This class stores the stop/run status of the simulation
 * All methods are thread-safe
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class Status {

    private final Object lockStatus = new Object();
    private boolean run = true;

    /**
     * Sets the run status of the simulation to stop
     */
    public void stopSimulation() {

        synchronized (lockStatus) {
            this.run = false;
        }
    }

    /**
     * Returns if the simulation should run
     * @return This is the run status of simulation
     */
    public boolean getRunStatus() {

        synchronized (lockStatus) {
            return this.run;
        }
    }
}
