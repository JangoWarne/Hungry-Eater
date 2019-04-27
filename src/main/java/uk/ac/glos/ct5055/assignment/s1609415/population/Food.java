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
public class Food {

    private final Object lockFood = new Object();
    private Config config;
    private ArrayList<Pair<Double, Double>> locations;
    private Random xPositions;
    private Random yPositions;

    public Food(Config config) {
        this.config = config;
        this.xPositions = new Random();
        this.yPositions = new Random();
        this.locations = new ArrayList<>();
        addLocation();
    }

    protected Pair<Double, Double> getFood(int index){

        synchronized (lockFood) {
            while (locations.size() < index + 1) {
                addLocation();
            }

            return locations.get(index);
        }
    }

    private void addLocation() {
        Double xPos = (this.xPositions.nextDouble() * (config.getScreenXMax() - config.getScreenXMin())) - config.getScreenXMin();
        Double yPos = (this.yPositions.nextDouble() * (config.getScreenYMax() - config.getScreenYMin())) - config.getScreenYMin();
        this.locations.add(new Pair<>(xPos, yPos));
    }
}
