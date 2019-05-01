package uk.ac.glos.ct5055.assignment.s1609415.population;

import uk.ac.glos.ct5055.assignment.s1609415.ml.Genome;
import uk.ac.glos.ct5055.assignment.s1609415.ui.Config;
import uk.ac.glos.ct5055.assignment.s1609415.ui.Progress;

import java.util.ArrayList;

/**
 * This class processes a generation of creatures
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class Generation {

    private Status status;
    private Progress progress;
    private Config config;

    public Generation(Status status, Progress progress, Config config) {
        this.status = status;
        this.progress = progress;
        this.config = config;
    }

    protected void start(boolean first) {
        int subsets = Runtime.getRuntime().availableProcessors() - 1;
        if (subsets < 1) {
            subsets = 1;
        }
        int creaturesPerSubset = (int) Math.ceil(config.getPopulationSize() / subsets);

        ArrayList<Life> completedLives;
        if (first) {
            completedLives = null;
        } else {
            completedLives = progress.incrementGeneration();
        }
        ArrayList<Life> nextGen = nextGeneration( completedLives );

        for (int i = 0; i < subsets; i++) {
            ArrayList<Life> subsetCreatures = (ArrayList<Life>) nextGen.subList(i*creaturesPerSubset, (i*creaturesPerSubset - 1));
            new Subset(status, progress, subsetCreatures).start();
        }

        while ( status.getRunStatus() && (progress.getProgress() < config.getPopulationSize()) ) {
            try {
                wait(100);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private ArrayList<Life> nextGeneration( ArrayList<Life> lastGen ) {
        Food food = new Food(config);
        ArrayList<Life> lives = new ArrayList<>();
        ArrayList<Genome> genomes = new ArrayList<>();

        int genesPerMember = //config.getHiddenLayerNodes()

        if (lastGen != null) {
            for (Life life:lives) {
                genomes.add(life.getCreature().getGenome());
            }
            genomes = Genome.nextGeneration( genomes, genesPerMember );
        } else {
            genomes = Genome.newGenomes( config.getPopulationSize(), genesPerMember );
        }

        for (int i = 0; i < config.getPopulationSize(); i++) {
            lives.add( new Life(config, food, genomes.remove(0)) );
        }

        return lives;
    }
}
