package uk.ac.glos.ct5055.assignment.s1609415.population;

import javafx.application.Platform;
import javafx.util.Pair;
import org.encog.ml.MLMethod;
import org.encog.ml.CalculateScore;
import org.encog.neural.neat.NEATNetwork;
import uk.ac.glos.ct5055.assignment.s1609415.ui.Config;
import uk.ac.glos.ct5055.assignment.s1609415.ui.Progress;
import uk.ac.glos.ct5055.assignment.s1609415.ui.SimulationController;

import java.util.ArrayList;

/**
 * This class calculates the life of a single creature
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class Life implements CalculateScore {

    private Progress progress;
    private Status status;
    private Food food;

    private int stepsPerLife;
    private int creatureRadius;
    private int foodRadius;
    private int stepLength;

    private int screenYMin;
    private int screenYMax;
    private int screenXMin;
    private int screenXMax;

    public Life(Config config, Food food, Progress progress, Status status) {
        this.progress = progress;
        this.status = status;
        this.food = food;

        this.stepsPerLife = config.getStepsPerLife();
        this.creatureRadius = config.getCreatureRadius();
        this.foodRadius = config.getFoodRadius();
        this.stepLength = config.getStepLength();

        this.screenYMin = config.getScreenYMin();
        this.screenYMax = config.getScreenYMax();
        this.screenXMin = config.getScreenXMin();
        this.screenXMax = config.getScreenXMax();
    }

    @Override
    public double calculateScore( MLMethod neuralNetwork ) {
        Creature creature = new Creature( (NEATNetwork)neuralNetwork );
        int foodIndex = 0;
        double nextStep;
        ArrayList<Double> steps = new ArrayList<>();
        Pair<Double, Double> foodLocation = food.getFood(foodIndex);
        Pair<Double, Double> creatureLocation = centerCreature();

        // run all creature steps using neural network
        for (int i = 0; i < stepsPerLife; i++) {

            nextStep = creature.chooseDirection( foodAngle(creatureLocation, foodLocation), foodDistance(creatureLocation, foodLocation), foodRadius );
            steps.add(nextStep);
            creatureLocation = takeStep( creatureLocation, nextStep );
            foodIndex = checkFood( creatureLocation, foodLocation, foodIndex );
            foodLocation = food.getFood(foodIndex);

            if (!status.getRunStatus()) {
                return 0;
            }
        }

        double result = calculateResult( creatureLocation, foodLocation, foodIndex );
        progress.incrementProgress( new Pair<>(result, steps) );

        // Slow code to smooth ui
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            return 0;
        }

        return result;
    }

    public void uiRun( Pair<Double, ArrayList<Double>> creature, SimulationController uiReference, int msWait, boolean first ) {
        int foodIndex = 0;
        double nextStep;
        Pair<Double, Double> foodLocation = food.getFood(foodIndex);
        Pair<Double, Double> creatureLocation = centerCreature();
        int multiple = stepsPerLife / 200;
        ArrayList<Double> steps = creature.getValue();
        boolean alternate = false;

        if (first) {
            // show creature viewer
            uiReference.drawCreatureLocation( creatureLocation );
            uiReference.drawFoodLocation( foodLocation );
            uiReference.drawSceneVisibility(true);
        }

        // replay all creature steps from stored values
        for (int i = 0; i < steps.size(); i++) {

            nextStep = steps.get(i);
            creatureLocation = takeStep( creatureLocation, nextStep );
            foodIndex = checkFood( creatureLocation, foodLocation, foodIndex );
            foodLocation = food.getFood(foodIndex);

            if (i%multiple == 0) {
                uiReference.drawCreatureLocation(creatureLocation);
                alternate = !alternate;
                if (alternate) {
                    uiReference.drawFoodLocation(foodLocation);
                }

                // Slow display for viewing
                try {
                    Thread.sleep(msWait);
                } catch (InterruptedException e) {
                    return;
                }
            }

            if (!status.getRunStatus()) {
                return;
            }
        }

        System.out.println(creature.getKey() + " " + calculateResult( creatureLocation, foodLocation, foodIndex ));
    }

    private Pair<Double, Double> takeStep( Pair<Double, Double> creatureLocation, double direction ) {
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

        return new Pair<>(xPos, yPos);
    }

    private int checkFood( Pair<Double, Double> creatureLocation, Pair<Double, Double> foodLocation, int foodIndex ) {
        // check if the food circle intersects the creature circle
        if ((creatureRadius + foodRadius) >= foodDistance(creatureLocation, foodLocation)) {
            foodIndex +=1;
        }
        return foodIndex;
    }

    private double calculateResult( Pair<Double, Double> creatureLocation, Pair<Double, Double> foodLocation, int foodIndex ) {
        // get the previous food location
        Pair<Double, Double> lastLocation;
        if (foodIndex > 0) {
            lastLocation = food.getFood(foodIndex - 1);
        } else {
            lastLocation = new Pair<>(0.0, 0.0);
        }

        // get the distance to the next piece of food from the last piece of food
        double xDistance = Math.abs( lastLocation.getKey() - foodLocation.getKey() );
        double yDistance = Math.abs( lastLocation.getValue() - foodLocation.getValue() );
        double originalDistance = Math.sqrt( Math.pow(xDistance, 2) + Math.pow(yDistance, 2) );

        // get the proportion of the distance that the creature has travelled and coerce to >0
        double distanceToNext = foodDistance(creatureLocation, foodLocation);
        double proportionToNext;
        if (distanceToNext > originalDistance) {
            proportionToNext = 0;
        } else {
            proportionToNext = (originalDistance - distanceToNext) / originalDistance;
        }

        return foodIndex + proportionToNext;
    }

    private static double foodDistance( Pair<Double, Double> creatureLocation, Pair<Double, Double> foodLocation ) {
        // calculate the absolute distance to the food
        double xDistance = Math.abs( creatureLocation.getKey() - foodLocation.getKey() );
        double yDistance = Math.abs( creatureLocation.getValue() - foodLocation.getValue() );
        return Math.sqrt( Math.pow(xDistance, 2) + Math.pow(yDistance, 2) );
    }

    private static double foodAngle( Pair<Double, Double> creatureLocation, Pair<Double, Double> foodLocation ) {
        // calculate the absolute angle of the food compared to the creatures location not the direction it is facing
        double xDistance = Math.abs( creatureLocation.getKey() - foodLocation.getKey() );
        double yDistance = Math.abs( creatureLocation.getValue() - foodLocation.getValue() );

        double theta = Math.toDegrees( Math.atan( yDistance/xDistance ) );
        double foodAngle;

        // coerce angle of food from creature between -180 & 180
        if (xDistance > 0) {
            if (yDistance > 0) {
                // first quadrant
                foodAngle = theta;
            } else if (yDistance < 0) {
                // second quadrant
                foodAngle = 180 - theta;
            } else {
                foodAngle = 90.0;
            }
        } else if (xDistance == 0) {
            if (yDistance < 0) {
                foodAngle = 180;
            } else {
                foodAngle = 0;
            }
        } else {
            if (yDistance > 0) {
                // third quadrant
                foodAngle = -theta;
            } else if (yDistance < 0) {
                // fourth quadrant
                foodAngle = -180 + theta;
            } else {
                foodAngle = -90.0;
            }
        }

        return foodAngle;
    }

    private Pair<Double, Double> centerCreature() {
        // returns the pixel location for the center of the viewer
        return new Pair<>( (double)((screenXMax-screenXMin)/2 + screenXMin), (double)((screenYMax-screenYMin)/2 + screenYMin) );
    }


    @Override
    public boolean shouldMinimize() {
        return false;
    }

    @Override
    public boolean requireSingleThreaded() {
        return false;
    }
}
