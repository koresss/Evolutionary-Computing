package tuning;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.vu.contest.ContestEvaluation;

import test.Method;

public class Population {
	private Child[] children;
	public static int evcount=0;
	
	public Child[] getChildren() {
		return children;
	}

	public Population(int length) {
		children = new Child[length];
	}

	public Population initializePopulation(){
		for(int x=0;x<children.length;x++){
			children[x]=new Child().initializeChild();
		}
		return this;
	}
	
	public Population evalFitness(){
		ExecutorService executor = Executors.newFixedThreadPool(100);
		for(int i=0;i<children.length;i++) {
			if(!children[i].elitist) {
				children[i].fitness=(double) executor.submit(new Method("KatsuuraEvaluation")).get();
			}
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
