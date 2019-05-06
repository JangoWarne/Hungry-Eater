package uk.ac.glos.ct5055.assignment.s1609415.population;

import uk.ac.glos.ct5055.assignment.s1609415.ui.Config;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class calculates where the food will be for every Life in a Generation
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class Food implements Cloneable {

    private final Object lockFood = new Object();
    private Config config;
    private ArrayList<Pair<Double, Double>> locations;
    private Random xPositions;
    private long xSeed;
    private Random yPositions;
    private long ySeed;

    public Food(Config config) {
        this.config = config;
        Random rand = new Random();
        this.xSeed = rand.nextLong();
        this.ySeed = rand.nextLong();
        this.xPositions = new Random( this.xSeed );
        this.yPositions = new Random( this.ySeed );
        this.locations = new ArrayList<>();
        addLocation();
    }

    public Food(Config config, long xSeed, long ySeed) {
        this.config = config;
        this.xSeed = xSeed;
        this.ySeed = ySeed;
        this.xPositions = new Random( xSeed );
        this.yPositions = new Random( ySeed );
        this.locations = new ArrayList<>();
        addLocation();
    }

    protected Pair<Double, Double> getFood(int index){

        synchronized (lockFood) {

            while (locations.size() < index+1) {
                addLocation();
            }

            return locations.get(index);
        }
    }

    private void addLocation() {
        Double xPos = (this.xPositions.nextDouble() * (config.getScreenXMax() - config.getScreenXMin())) + config.getScreenXMin();
        Double yPos = (this.yPositions.nextDouble() * (config.getScreenYMax() - config.getScreenYMin())) + config.getScreenYMin();

        this.locations.add(new Pair<>(xPos, yPos));
    }

    @Override
    public Object clone() {
        return new Food(config, xSeed, ySeed );
    }
}
