package tuning;


import java.util.Random;
import java.util.Arrays;
import java.util.Properties;

public class Main
{
    public static void main(String[] args) {
    	GeneticAlgorithm.POPULATION_SIZE=10;
    	GeneticAlgorithm.CHILD_MUTATION_RATE = 1;
    	GeneticAlgorithm.MUTATION_RATE = 0.5;
    	GeneticAlgorithm.KEEPBEST=1;
    	GeneticAlgorithm.TOURNAMENT_SELECTION_SIZE=8;
    	GeneticAlgorithm.TOURNAMENT_SELECTION_PROBABILITY=0.5;
    	
    	GeneticAlgorithm.tau = 1/Math.sqrt(2*Math.sqrt(10));
    	GeneticAlgorithm.tauPrime = 1/Math.sqrt(20);
    	GeneticAlgorithm.sigmaBoundary = 0.1;
    	GeneticAlgorithm.BLEND_CROSSOVER_ALPHA = 0.5;
    	GeneticAlgorithm.Func="SchaffersEvaluation";
    	
    	
        int gen = 1;
        Population population = new Population(GeneticAlgorithm.POPULATION_SIZE).initializePopulation().evalFitness();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        while(true){
        	population=geneticAlgorithm.evolve(population);
            population.evalFitness();
            gen++;
			System.out.println("evcount:"+Population.evcount+" gen:"+gen+" best fitness:"+population.getChildren()[0].fitness+"\n"+Arrays.toString(population.getChildren()[0].sequence));
        }
    }
}
