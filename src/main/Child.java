package main;
import java.util.Arrays;
import java.util.Random;

public class Child {
	double sequence[]={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
	double fitness=0;
	double sharedFitness=0;
	boolean elitist=false;
	double sigmas[]={5,5,5,5,5,5,5,5,5,5}; //3s
	Random r = new Random();
	

	public Child() {

	}
	public Child initializeChild() {
		for(int x=0;x<sequence.length;x++){
			Random positive_or_negative = new Random();
			if(positive_or_negative.nextBoolean()) {
				sequence[x]=r.nextDouble()*5;
			}else {
				sequence[x]=r.nextDouble()*(-5);
			}
		}
		return this;
	}
	public String toString(){
		return Arrays.toString(this.sequence);
	}

	public double[] getSequence() {
		return sequence;
	}
}
