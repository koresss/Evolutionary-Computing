
import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Arrays;
import java.util.Properties;

public class player30 implements ContestSubmission
{
	Random rnd_;
	static ContestEvaluation evaluation_;
    private int evaluations_limit_;
	
    public static void main(String[] args) {
    	System.out.println(1/Math.pow(10, 0.5));
    }
	public player30()
	{
		rnd_ = new Random();
	}
	
	public void setSeed(long seed)
	{
		// Set seed of algortihms random process
		rnd_.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation)
	{
		// Set evaluation problem used in the run
		evaluation_ = evaluation;
		
		// Get evaluation properties
		Properties props = evaluation.getProperties();
        // Get evaluation limit
        evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));
		// Property keys depend on specific evaluation
		// E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));
        //System.out.println("Function Properties");
        //System.out.println(evaluation.getProperties());
		// Do sth with property values, e.g. specify relevant settings of your algorithm
        if(isMultimodal){
        	if(!hasStructure) {//Katsuura
        		/* Average (19 runs) Katsuura: 5.1022263865815525
					Max (19 runs) Katsuura: 9.67494009196859
					Min (19 runs) Katsuura: 0.6668800872017767 */
        		GeneticAlgorithm.POPULATION_SIZE=225;
        		GeneticAlgorithm.CHILD_MUTATION_RATE = 1;
        		GeneticAlgorithm.MUTATION_RATE = 0.5;
        		GeneticAlgorithm.KEEPBEST=90;
        		GeneticAlgorithm.TOURNAMENT_SELECTION_SIZE=8;
        		GeneticAlgorithm.TOURNAMENT_SELECTION_PROBABILITY=0.5;
        		        		
        		GeneticAlgorithm.tau = 1/Math.sqrt(2*Math.sqrt(10));
        		GeneticAlgorithm.tauPrime = 1000/Math.sqrt(20);
        		GeneticAlgorithm.sigmaBoundary = 0.01;
        		GeneticAlgorithm.BLEND_CROSSOVER_ALPHA = 0.5;
        		GeneticAlgorithm.FITNESS_SHARING_SIGMA = 5;
        	}else {	//Schaffers
/*        		GeneticAlgorithm.POPULATION_SIZE=225;
        		GeneticAlgorithm.CHILD_MUTATION_RATE = 1;
        		GeneticAlgorithm.MUTATION_RATE = 0.5;
        		GeneticAlgorithm.KEEPBEST=90;
        		GeneticAlgorithm.TOURNAMENT_SELECTION_SIZE=8;
        		GeneticAlgorithm.TOURNAMENT_SELECTION_PROBABILITY=0.5;
        		
        		GeneticAlgorithm.tau = 1/Math.sqrt(2*Math.sqrt(10));
        		GeneticAlgorithm.tauPrime = 1/Math.sqrt(20);
        		GeneticAlgorithm.sigmaBoundary = 0.01; 
        		GeneticAlgorithm.BLEND_CROSSOVER_ALPHA = 0.5;
        		GeneticAlgorithm.FITNESS_SHARING_SIGMA = 4;*/

        		GeneticAlgorithm.POPULATION_SIZE=229;
        		GeneticAlgorithm.CHILD_MUTATION_RATE = 1;
        		GeneticAlgorithm.MUTATION_RATE = 0.06158542005132886;
        		GeneticAlgorithm.KEEPBEST=82;
        		GeneticAlgorithm.TOURNAMENT_SELECTION_SIZE=3;
        		GeneticAlgorithm.TOURNAMENT_SELECTION_PROBABILITY=0.8944528038597799;
        		
        		GeneticAlgorithm.tau = -60.169595077925976;
        		GeneticAlgorithm.tauPrime = 181.61666921979605;
        		GeneticAlgorithm.sigmaBoundary = 0; 
        		GeneticAlgorithm.BLEND_CROSSOVER_ALPHA = 0.8548500198446678;
        		GeneticAlgorithm.FITNESS_SHARING_SIGMA = 0;
        		
        	}
        }else{	//BentCigar
/*        	GeneticAlgorithm.POPULATION_SIZE=100;
        	GeneticAlgorithm.CHILD_MUTATION_RATE = 1;
        	GeneticAlgorithm.MUTATION_RATE = 0.5;
        	GeneticAlgorithm.KEEPBEST=40;
        	GeneticAlgorithm.TOURNAMENT_SELECTION_SIZE=8;
        	GeneticAlgorithm.TOURNAMENT_SELECTION_PROBABILITY=0.5;
        	
        	GeneticAlgorithm.tau = 1/Math.sqrt(2*Math.sqrt(10));
        	GeneticAlgorithm.tauPrime = 1/Math.sqrt(20);
        	GeneticAlgorithm.sigmaBoundary = 0.1;
        	GeneticAlgorithm.BLEND_CROSSOVER_ALPHA = 0.5;
        	GeneticAlgorithm.FITNESS_SHARING_SIGMA = 15;*/
        	

        	GeneticAlgorithm.POPULATION_SIZE=163;
        	GeneticAlgorithm.CHILD_MUTATION_RATE = 1;
        	GeneticAlgorithm.MUTATION_RATE = 0.9419578714094395;
        	GeneticAlgorithm.KEEPBEST=0;
        	GeneticAlgorithm.TOURNAMENT_SELECTION_SIZE=17;
        	GeneticAlgorithm.TOURNAMENT_SELECTION_PROBABILITY=0.7927369563555049;
        	
        	GeneticAlgorithm.tau = 6.526531030259164;
        	GeneticAlgorithm.tauPrime = -186.97383318820718;
        	GeneticAlgorithm.sigmaBoundary = 0;
        	GeneticAlgorithm.BLEND_CROSSOVER_ALPHA = 0.8080570819197654;
        	GeneticAlgorithm.FITNESS_SHARING_SIGMA = 0;
        }
        //Parameter overrides
        try {
	        if(Boolean.parseBoolean(System.getProperty("override"))) {
		    	GeneticAlgorithm.POPULATION_SIZE=Integer.parseInt(System.getProperty("popsize"));
		    	//GeneticAlgorithm.CHILD_MUTATION_RATE = Double.parseDouble(System.getProperty("cmutationrate"));
		    	GeneticAlgorithm.MUTATION_RATE = Double.parseDouble(System.getProperty("mutationrate"));
		    	GeneticAlgorithm.KEEPBEST=Integer.parseInt(System.getProperty("elitism"));
		    	GeneticAlgorithm.TOURNAMENT_SELECTION_SIZE=Integer.parseInt(System.getProperty("tournamentsize"));
		    	GeneticAlgorithm.TOURNAMENT_SELECTION_PROBABILITY=Double.parseDouble(System.getProperty("tournamentprob"));
		    	
		    	GeneticAlgorithm.tau = Double.parseDouble(System.getProperty("tau"));
		    	GeneticAlgorithm.tauPrime = Double.parseDouble(System.getProperty("tauprime"));
		    	GeneticAlgorithm.sigmaBoundary = Double.parseDouble(System.getProperty("sigmaboundary"));
		    	GeneticAlgorithm.BLEND_CROSSOVER_ALPHA = Double.parseDouble(System.getProperty("blendcrossoveralpha"));
		    	GeneticAlgorithm.FITNESS_SHARING_SIGMA = Double.parseDouble(System.getProperty("fitnesssigma"));
	        }
        }catch(Exception e) {
        	//
        }
    }
	public void run()
	{
        int gen = 1;
        Population population = new Population(GeneticAlgorithm.POPULATION_SIZE).initializePopulation().evalFitness();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        while(true){
        	population=geneticAlgorithm.evolve(population);
            population.evalFitness();
            gen++;
			//System.out.println("evcount:"+Population.evcount+" gen:"+gen+" best fitness:"+population.getChildren()[0].fitness+"\n");
			System.out.println(population.getBestChild().fitness);
        }

	}
}
