package uk.ac.glos.ct5055.assignment.s1609415.ml;

import java.util.ArrayList;

/**
 * This class contains the genome of single creature that defines its neural network
 *
 * @author  Joshua Walker
 * @version 1.0
 */
public class Genome {

    public static ArrayList<Genome> nextGeneration(ArrayList<Genome> genomes, int genesPerMember) {
        ...
    }

    public static ArrayList<Genome> newGenomes(int polulationSize, int genesPerMember) {
        ...
    }

    public Genome(int genesPerMember) {
        ...
    }

    public ArrayList<Double> getGenes() {
        ...
    }
}
