package tuning;
import java.util.Arrays;
import java.util.Random;

import main.GeneticAlgorithm;

public class Child {
	double sequence[]={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
	double fitness=0;
	boolean elitist=false;
	double sigmas[]={5,5,5,5,5,5,5,5,5,5}; //3s
	Random r = new Random();
	

	public Child() {

	}
	public Child initializeChild() {
		Random r=new Random();
    	sequence[0]=30+r.nextInt(196); //popsize=30-225
    	sequence[1]=0.01+r.nextDouble()*0.99; //mutationrate
    	sequence[2]=r.nextInt(150); //elitism
    	sequence[3]=2+r.nextInt(19); //tournament size=2-20
    	sequence[4]=0.1+r.nextDouble()*0.41;//tournament prob
    	
    	sequence[5]=(1+r.nextInt(1000))/Math.sqrt(2*Math.sqrt(10)); //tau
    	sequence[6]=(1+r.nextInt(1000))/Math.sqrt(20); //tauprime
    	sequence[7]=0.001+r.nextDouble()*2; //sigmaboundary
    	sequence[8]=0.3+r.nextDouble()*0.7; //blend crossover alpha
    	sequence[9]=0.1+r.nextDouble()*15;//fitness sharing sigma
		return this;
	}
	public String toString(){
		return Arrays.toString(this.sequence);
	}

	public double[] getSequence() {
		return sequence;
	}
}
