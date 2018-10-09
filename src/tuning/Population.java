package tuning;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



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
				try {
					children[i].fitness=0;
					System.out.println(i+Arrays.toString(children[i].getSequence()));
					for(int k=0;k<5;k++) {
						children[i].fitness+=(double) executor.submit(new Method(GeneticAlgorithm.Func,children[i].getSequence()[0],children[i].getSequence()[1],children[i].getSequence()[2],children[i].getSequence()[3],children[i].getSequence()[4],children[i].getSequence()[5],children[i].getSequence()[6],children[i].getSequence()[7],children[i].getSequence()[8],children[i].getSequence()[9])).get();
					}
					children[i].fitness=children[i].fitness/5;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	}
}
