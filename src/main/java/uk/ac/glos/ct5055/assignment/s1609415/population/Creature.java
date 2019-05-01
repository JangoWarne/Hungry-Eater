package uk.ac.glos.ct5055.assignment.s1609415.population;

import uk.ac.glos.ct5055.assignment.s1609415.ml.Brain;
import uk.ac.glos.ct5055.assignment.s1609415.ml.Genome;

import java.util.ArrayList;

/**
 * This class calculates the behaviour of a single creature
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class Creature {

    private double creatureDirection;
    private Brain brain;
    private Genome genome;

    public Creature(Genome genome, ArrayList<Integer> hiddenLayerNodes) {
        this.genome = genome;
        this.brain = new Brain(genome.getGenes(), hiddenLayerNodes);
        this.creatureDirection = 0.0;
    }

    protected Genome getGenome() {
        return genome;
    }

    protected double chooseDirection(double foodAngle, double foodDistance, int foodRadius) {

        updateDirection( brain.chooseStep( calculateSight(foodAngle, foodDistance, foodRadius) ) );

        return getCreatureDirection();
    }

    private ArrayList<Double> calculateSight(double foodAngle, double foodDistance, int foodRadius) {
        // calculate relative direction of food
        double relativeAngle = foodAngle - getCreatureDirection();
        double foodHalfAngle = Math.toDegrees( Math.atan( foodRadius/foodDistance ) );
        double foodMinAngle = relativeAngle - foodHalfAngle;
        double foodMaxAngle = relativeAngle + foodHalfAngle;

        // sight inputs
        ArrayList<Double> vision = new ArrayList<>();
        double halfConeAngle = 5.0; // defines the half field of view for a cone
        ArrayList<Double> coneAngles = new ArrayList<>() {{
            add(-20.0);
            add(-10.0);
            add(0.0);
            add(10.0);
            add(20.0);
        }};

        // calculate sight inputs
        double visionCone, coneMin, coneMax;
        for (double coneAngle: coneAngles) {
            coneMin = coneAngle - halfConeAngle;
            coneMax = coneAngle + halfConeAngle;

            // calculate fraction of the vision cone that can see the food
            if (foodMinAngle < coneMin && foodMaxAngle >= coneMax) {
                // food fills field of view
                visionCone = 1.0;
            } else if ((foodMaxAngle >= coneMin && foodMaxAngle < coneMax) && (foodMinAngle >= coneMin && foodMinAngle < coneMax)) {
                // food is fully within field of view
                visionCone = foodHalfAngle * 2;
            } else if (foodMaxAngle >= coneMin && foodMaxAngle < coneMax){
                // food partially fills cone view from right
                visionCone = (foodMaxAngle - coneMin)/(halfConeAngle * 2);
            } else if (foodMinAngle >= coneMin && foodMinAngle < coneMax){
                // food partially fills cone view from left
                visionCone = (coneMax - foodMinAngle)/(halfConeAngle * 2);
            } else {
                // food is outside field of view
                visionCone = 0.0;
            }

            // coerce values to within limits
            if (visionCone < 0) {
                visionCone = 0.0;
            } else if (visionCone > 1){
                visionCone = 1.0;
            }

            // update vision
            vision.add(visionCone);
        }
        return vision;
    }

    private double getCreatureDirection() {
        return creatureDirection;
    }

    private void updateDirection(double chosenStep) {

        double newDirection = getCreatureDirection() + chosenStep;

        // Wrap direction to between -180 & 180
        while (newDirection <= -180) {
            newDirection += 360;
        }
        while (newDirection > 180) {
            newDirection -= 360;
        }

        this.creatureDirection = newDirection;
    }
}
