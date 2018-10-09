package main;
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
	public static double FITNESS_SHARING_SIGMA;

	
	public Population evolve(Population population){
		//double[][] C = buildStandardCovarianceMatrix(population);
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
				if(amount<=5 && amount>=-5) {
					crossoverChromosome.getSequence()[x]=amount;
				}else {
					crossoverChromosome.getSequence()[x]=(chromosome1.getSequence()[x]+chromosome2.getSequence()[x])/2;
				}
			
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
					if(xPrime<=5 && xPrime>=-5) {
						mutateChromosome.getSequence()[x]=xPrime;
					}
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
	private double[][] buildStandardCovarianceMatrix(Population population) { //https://stattrek.com/matrix-algebra/covariance-matrix.aspx
		double[][] allgenomes= new double[POPULATION_SIZE][10];
		double[][] deviation= new double[POPULATION_SIZE][10];
		double[][] deviationPrime=new double[10][POPULATION_SIZE];
		double[][] deviationScoreSumsOfSquares= new double[10][10];
		double[][] covarianceMatrix= new double[10][10];
		for(int i=0;i<POPULATION_SIZE;i++) {
			allgenomes[i]=population.getChildren()[i].sequence;
		}
		for(int i=0;i<10;i++) {
			double currentsum=0;
			for(int j=0;j<POPULATION_SIZE;j++) {
				currentsum+=allgenomes[j][i];
			}
			double currentmean=currentsum/POPULATION_SIZE;
			for(int j=0;j<POPULATION_SIZE;j++) {
				deviation[j][i]=allgenomes[j][i]-currentmean;
			}
		}
		for(int i=0;i<10;i++) {
			double[] currentRow=new double[POPULATION_SIZE];
			for(int j=0;j<POPULATION_SIZE;j++) {
				currentRow[j]=deviation[j][i];
			}
			deviationPrime[i]=currentRow;
		}
		for(int i=0;i<10;i++) {
			for(int k=0;k<10;k++) {
				double currentsum=0;
				for(int j=0;j<POPULATION_SIZE;j++) {
					currentsum+=deviationPrime[i][j]*deviation[j][k];
				}
				deviationScoreSumsOfSquares[i][k]=currentsum;
			}
		}
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				covarianceMatrix[i][j]=deviationScoreSumsOfSquares[i][j]/225;
			}
		}

/*	    double[][] newArray = new double[covarianceMatrix.length][covarianceMatrix[0].length];
	    for(int i = 0; i < covarianceMatrix.length; i++){
	        for(int y = 0; y < covarianceMatrix[0].length; y++){
	            newArray[i][y] = (double)Math.round(covarianceMatrix[i][y] * 100) / 100;
	        }
	    }
	    for(int i=0;i<10;i++) {
			System.out.println(Arrays.toString(newArray[i]));
	    }
		System.out.println("");*/
		return covarianceMatrix;
	}
}
