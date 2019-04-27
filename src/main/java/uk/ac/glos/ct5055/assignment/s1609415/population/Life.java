package uk.ac.glos.ct5055.assignment.s1609415.population;

import javafx.util.Pair;
import uk.ac.glos.ct5055.assignment.s1609415.ml.Genome;
import uk.ac.glos.ct5055.assignment.s1609415.ui.Config;

/**
 * This class calculates the life of a single creature
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class Life {

    private Config config;
    private Food food;
    private Creature creature;
    private double result;

    private Pair<Double, Double> foodLocation;
    private Pair<Double, Double> creatureLocation;
    private int foodIndex;

    private int creatureRadius ;
    private int foodRadius;
    private int stepLength;

    private int screenYMin;
    private int screenYMax;
    private int screenXMin;
    private int screenXMax;

    public Life(Config config, Food food, Genome genome) {
        this.config = config;
        this.food = food;
        this.creature = new Creature( genome, config.getHiddenLayerNodes() );

        this.foodIndex = 0;
        getNextFood();
        this.creatureLocation = new Pair<>(0.0, 0.0);

        this.creatureRadius = config.getCreatureRadius();
        this.foodRadius = config.getFoodRadius();
        this.stepLength = config.getStepLength();

        this.screenYMin = config.getScreenYMin();
        this.screenYMax = config.getScreenYMax();
        this.screenXMin = config.getScreenXMin();
        this.screenXMax = config.getScreenXMax();
    }

    public void run( Status status ) {
        int stepsPerLife = config.getStepsPerLife();

        for (int i = 0; i < stepsPerLife; i++) {

            takeStep( creature.chooseStep( foodLocation ) );
            checkFood();

            if (status.getRunStatus()) {
                return;
            }
        }

        calculateResult();
    }

    private void takeStep( double direction ) {
        double xPos = creatureLocation.getKey();
        double yPos = creatureLocation.getValue();

        yPos = yPos + ( Math.sin( Math.toRadians(direction) ) * stepLength );
        xPos = xPos + ( Math.cos( Math.toRadians(direction) ) * stepLength );

        if (xPos > screenXMax) {
            xPos = screenXMax;
        } else if (xPos < screenXMin) {
            xPos = screenXMin;
        }

        if (yPos > screenYMax) {
            yPos = screenYMax;
        } else if (yPos < screenYMin) {
            yPos = screenYMin;
        }

        creatureLocation = new Pair<>(xPos, yPos);
    }

    private void checkFood() {
        if ((creatureRadius + foodRadius) >= foodDistance()) {
            getNextFood();
        }
    }

    private void calculateResult() {
        Pair<Double, Double> lastLocation;
        if (foodIndex > 0) {
            lastLocation = food.getFood(foodIndex - 1);
        } else {
            lastLocation = new Pair<>(0.0, 0.0);
        }

        double xDistance = Math.abs( lastLocation.getKey() - foodLocation.getKey() );
        double yDistance = Math.abs( lastLocation.getValue() - foodLocation.getValue() );
        double originalDistance = Math.sqrt( Math.pow(xDistance, 2) + Math.pow(yDistance, 2) );

        result = foodIndex + (1 - (foodDistance() / originalDistance));
    }

    private double foodDistance() {
        double xDistance = Math.abs( creatureLocation.getKey() - foodLocation.getKey() );
        double yDistance = Math.abs( creatureLocation.getValue() - foodLocation.getValue() );
        return Math.sqrt( Math.pow(xDistance, 2) + Math.pow(yDistance, 2) );
    }

    private void getNextFood() {
        foodLocation = food.getFood(foodIndex);
    }

    public double getResult() {
        return result;
    }

    public Creature getCreature() {
        return creature;
    }
}
