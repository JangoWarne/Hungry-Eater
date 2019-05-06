package uk.ac.glos.ct5055.assignment.s1609415.population;

import org.encog.ml.ea.train.EvolutionaryAlgorithm;
import org.encog.neural.neat.NEATPopulation;
import org.encog.neural.neat.NEATUtil;
import org.encog.neural.neat.training.species.OriginalNEATSpeciation;
import uk.ac.glos.ct5055.assignment.s1609415.ui.Config;
import uk.ac.glos.ct5055.assignment.s1609415.ui.Progress;

/**
 * This class processes a generation of creatures
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class Population {

    private Status status;
    private Progress progress;
    private Config config;
    private Food food;

    public Population(Status status, Progress progress, Config config) {
        this.status = status;
        this.progress = progress;
        this.config = config;
    }

    protected void start() {
        food = new Food(config);
        EvolutionaryAlgorithm train = newPopulation();

        while (status.getRunStatus()) {
            train.iteration();
            progress.incrementGeneration( food );

            // Slow code to smooth ui
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                return;
            }

            food = new Food(config);
        }
    }

    private EvolutionaryAlgorithm newPopulation() {
        NEATPopulation population = new NEATPopulation( 5, 1, config.getPopulationSize() );
        population.setSurvivalRate( config.getSurvivalRate() );
        population.reset();
        Life life = new Life( config, food, progress, status );
        EvolutionaryAlgorithm train = NEATUtil.constructNEATTrainer( population, life );
        train.setSpeciation( new OriginalNEATSpeciation() );

        return train;
    }
}
