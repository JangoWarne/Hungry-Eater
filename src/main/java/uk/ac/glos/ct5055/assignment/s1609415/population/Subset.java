package uk.ac.glos.ct5055.assignment.s1609415.population;

import uk.ac.glos.ct5055.assignment.s1609415.ui.Progress;

import java.util.ArrayList;

/**
 * This class handles a subset of a generation
 * it runs in a new processor thread
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class Subset {

    private Status status;
    private Progress progress;
    private ArrayList<Life> lives;

    public Subset(Status status, Progress progress, ArrayList<Life> lives) {
        this.status = status;
        this.progress = progress;
        this.lives = lives;
    }

    protected void start() {

        Thread thread = new Thread(() -> {
            while (status.getRunStatus() && (lives.size() > 0)) {

                Life life = lives.remove( 0 );
                life.run( status );
                progress.incrementProgress( life );
            }
        });

        thread.start();
    }
}
