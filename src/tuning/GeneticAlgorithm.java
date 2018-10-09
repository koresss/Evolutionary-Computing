package tuning;
import java.util.Arrays;
import java.util.Random;




public class GeneticAlgorithm {
	
	public static int POPULATION_SIZE;
	public static double CHILD_MUTATION_RATE;
	public static double MUTATION_RATE;
	public static int KEEPBEST;
	public static int TOURNAMENT_SELECTION_SIZE;
	public static double TOURNAMENT_SELECTION_PROBABILITY;
	
	public static double tau;
	public static double tauPrime;
	public static double sigmaBoundary;
	public static double BLEND_CROSSOVER_ALPHA;


	
	public Population evolve(Population population){
		return mutatePopulation(crossoverPopulation(population));
	}
	
	private Population crossoverPopulation(Population population){
		Population crossoverPopulation = new Population(population.getChildren().length);
		for(int x=0;x<population.getChildren().length;x++){
			if(population.getChildren()[x].elitist) {
				crossoverPopulation.getChildren()[x]=population.getChildren()[x];
				continue;	
			}
			Child chromosome1=doTournament(population);
			Child chromosome2=doTournament(population);
			crossoverPopulation.getChildren()[x]=crossoverChromosome(chromosome1,chromosome2);
			crossoverPopulation.getChildren()[x].sigmas=chromosome1.sigmas;
		}
		return crossoverPopulation;
	}
	
	private Population mutatePopulation(Population population){
		Population mutatePopulation  = new Population(population.getChildren().length);
		for(int x=0;x<population.getChildren().length;x++){
			if(population.getChildren()[x].elitist) {
				mutatePopulation.getChildren()[x]=population.getChildren()[x];
				continue;
			}
			mutatePopulation.getChildren()[x]=mutateChromosome(population.getChildren()[x]);
		}
		return mutatePopulation;
	}
	
	private Child crossoverChromosome(Child chromosome1,Child chromosome2){	
		Child crossoverChromosome = new Child();
		for(int x=0;x<chromosome1.getSequence().length;x++){
				double d=chromosome2.getSequence()[x]-chromosome1.getSequence()[x];
				double min=chromosome1.getSequence()[x]-BLEND_CROSSOVER_ALPHA*d;
				double max=chromosome1.getSequence()[x]+BLEND_CROSSOVER_ALPHA*d;
				double amount=min + Math.random() * (max - min);
					crossoverChromosome.getSequence()[x]=amount;
			
		} 
		return crossoverChromosome;
	}
	
	private Child mutateChromosome(Child chromosome){
		Child mutateChromosome = new Child();
		Random r=new Random();
		if(r.nextDouble()<CHILD_MUTATION_RATE) {
			double N=r.nextGaussian();
			for(int x=0;x<chromosome.getSequence().length;x++) {
				double tauGaussian=tau*r.nextGaussian();
				mutateChromosome.sigmas[x]=chromosome.sigmas[x]*Math.exp(tauPrime*N+tauGaussian);
				if(mutateChromosome.sigmas[x]<sigmaBoundary) {
					mutateChromosome.sigmas[x]=sigmaBoundary;
				}
				if(r.nextDouble()<MUTATION_RATE) {
					double xPrime=chromosome.getSequence()[x]+mutateChromosome.sigmas[x]*tauGaussian;
						mutateChromosome.getSequence()[x]=xPrime;
				}else {
					mutateChromosome.getSequence()[x]=chromosome.getSequence()[x];
				}
			}
		}else {
			return chromosome;
		}
		return mutateChromosome;


	}
	
	private Child doTournament(Population population){
		int fitness_indexes[]=new int[TOURNAMENT_SELECTION_SIZE];
		Random r=new Random();
		for(int x=0;x<TOURNAMENT_SELECTION_SIZE;x++){
			fitness_indexes[x]=r.nextInt(population.getChildren().length);
		}
		java.util.Arrays.sort(fitness_indexes);
		double weightedpickprob=r.nextDouble();
		if(weightedpickprob>=TOURNAMENT_SELECTION_PROBABILITY) {
			return population.getChildren()[fitness_indexes[0]];
		}else {
			for(int y=0;y<TOURNAMENT_SELECTION_SIZE-1;y++) {
				if(weightedpickprob>=TOURNAMENT_SELECTION_PROBABILITY*Math.pow((1-TOURNAMENT_SELECTION_PROBABILITY),y+1)) {
					return population.getChildren()[fitness_indexes[y+1]];
				}
			}
			return population.getChildren()[fitness_indexes[TOURNAMENT_SELECTION_SIZE-1]];
		}
	}

}
