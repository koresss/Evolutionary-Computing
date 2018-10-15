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
    	GeneticAlgorithm.TOURNAMENT_SELECTION_SIZE=3;//8
    	GeneticAlgorithm.TOURNAMENT_SELECTION_PROBABILITY=0.5;
    	
    	GeneticAlgorithm.tau = 1/Math.sqrt(2*Math.sqrt(10));
    	GeneticAlgorithm.tauPrime = 1/Math.sqrt(20);
    	GeneticAlgorithm.sigmaBoundary = 0.5;
    	GeneticAlgorithm.BLEND_CROSSOVER_ALPHA = 0.5;
    	GeneticAlgorithm.Func="KatsuuraEvaluation";
    	
    	
        int gen = 1;
        Population population = new Population(GeneticAlgorithm.POPULATION_SIZE).initializePopulation();
        //HOT-PLUG 1 (to continue tuning)
    	population.getChildren()[0].sequence[0]=225; //popsize
    	population.getChildren()[0].sequence[1]=0.5; //mutationrate
    	population.getChildren()[0].sequence[2]=90; //elitism
    	population.getChildren()[0].sequence[3]=8; //tournament size
    	population.getChildren()[0].sequence[4]=0.5;//tournament prob
    	
    	population.getChildren()[0].sequence[5]=1/Math.sqrt(2*Math.sqrt(10)); //tau
    	population.getChildren()[0].sequence[6]=1000/Math.sqrt(20); //tauprime
    	population.getChildren()[0].sequence[7]=0.01; //sigmaboundary
    	population.getChildren()[0].sequence[8]=0.5; //blend crossover alpha
    	population.getChildren()[0].sequence[9]=5;//fitness sharing sigma
    	
    	//HOT-PLUG 2 (optional)
/*    	population.getChildren()[1].sequence[0]=186.9640773651363; //popsize
    	population.getChildren()[1].sequence[1]=0.7469000855387562; //mutationrate
    	population.getChildren()[1].sequence[2]=30.681961556844477; //elitism
    	population.getChildren()[1].sequence[3]=4.648181401859613; //tournament size
    	population.getChildren()[1].sequence[4]=0.7630035620066529;//tournament prob
    	
    	population.getChildren()[1].sequence[5]=117.69645735760128; //tau
    	population.getChildren()[1].sequence[6]=198.68061677955583; //tauprime
    	population.getChildren()[1].sequence[7]=0; //sigmaboundary
    	population.getChildren()[1].sequence[8]=0.9912152161949537; //blend crossover alpha
    	population.getChildren()[1].sequence[9]=10.810764815409758;//fitness sharing sigma
*/        //
        population.evalFitness();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        while(true){
        	population=geneticAlgorithm.evolve(population);
            population.evalFitness();
            gen++;
			System.out.println("evcount:"+Population.evcount+" gen:"+gen+" best fitness:"+population.getChildren()[0].fitness+"\n"+Arrays.toString(population.getChildren()[0].sequence));
        }
    }
}
