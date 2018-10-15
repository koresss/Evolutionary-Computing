
import java.util.Arrays;

import org.vu.contest.ContestEvaluation;

public class Population {
	ContestEvaluation evaluation_=player30.evaluation_;
	private Child[] children;
	public static int evcount=0;
	private static Child bestChild=new Child().initializeChild();
	
	public Child[] getChildren() {
		return children;
	}

	public Population(int length) {
		children = new Child[length];
	}
	public Child getBestChild() {
		return bestChild;
	}
	public Population initializePopulation(){
		for(int x=0;x<children.length;x++){
			children[x]=new Child().initializeChild();
		}
		return this;
	}
	
	public Population evalFitness(){
		for(int i=0;i<children.length;i++) {
			double sumFitShare=0;
			for(int j=0;j<children.length;j++) {
				double euclideanDistanceSum=0;
				for(int k=0;k<10;k++) {
					euclideanDistanceSum+=Math.pow(children[i].getSequence()[k]-children[j].getSequence()[k],2);
				}
				double euclideanDistance=Math.sqrt(euclideanDistanceSum);
				if(euclideanDistance<=GeneticAlgorithm.FITNESS_SHARING_SIGMA) {
					sumFitShare+=1-euclideanDistance/GeneticAlgorithm.FITNESS_SHARING_SIGMA;
				}
			}
			if(!children[i].elitist) {
				children[i].fitness=(double)(evaluation_.evaluate(children[i].getSequence()));
			}
			children[i].sharedFitness=children[i].fitness/sumFitShare;
			evcount++;
		}
		sortByFitness();
		return this;
	}
	public void sortByFitness() {
		Arrays.sort(children,(child1,child2) -> {
			int flag=0;
			double child1fit=child1.fitness;
			double child2fit=child2.fitness;
			if(child1fit > child2fit){
				flag=-1;
			}else if(child1fit < child2fit){
				flag=1;
			}
			return flag;
		});
		bestChild=children[0];
		for(int i=0;i<children.length;i++) {
			if(i<GeneticAlgorithm.KEEPBEST) {
				children[i].elitist=true;
			}else {
				children[i].elitist=false;
			}
		}
		sortBySharedFitness();
	}
	public void sortBySharedFitness() {
		Arrays.sort(children,(child1,child2) -> {
			int flag=0;
			double child1fit=child1.sharedFitness;
			double child2fit=child2.sharedFitness;
			if(child1fit > child2fit){
				flag=-1;
			}else if(child1fit < child2fit){
				flag=1;
			}
			return flag;
		});
	}
}
