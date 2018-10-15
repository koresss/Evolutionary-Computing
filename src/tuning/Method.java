package tuning;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class Method implements Callable{
	String method="";
	public static int POPULATION_SIZE;
	public static double MUTATION_RATE;
	public static int KEEPBEST;
	public static int TOURNAMENT_SELECTION_SIZE;
	public static double TOURNAMENT_SELECTION_PROBABILITY;
	
	public static double tau;
	public static double tauPrime;
	public static double sigmaBoundary;
	public static double BLEND_CROSSOVER_ALPHA;
	public static double FITNESS_SIGMA;
	
	public Method(String m,double d,double mutation,double e,double f,double tournprob,double tau,double tauPrime,double sigmaBound,double blendAlpha,double fitnessSigma){
		method=m;
		POPULATION_SIZE=(int) d;
		MUTATION_RATE=mutation;
		KEEPBEST=(int) e;
		TOURNAMENT_SELECTION_SIZE=(int) f;
		TOURNAMENT_SELECTION_PROBABILITY=tournprob;
		this.tau=tau;
		this.tauPrime=tauPrime;
		sigmaBoundary=sigmaBound;
		BLEND_CROSSOVER_ALPHA=blendAlpha;
		FITNESS_SIGMA=fitnessSigma;
	}

	@Override
	public Object call() throws Exception {
		ProcessBuilder builder = new ProcessBuilder("java","-Doverride=true","-Dpopsize="+POPULATION_SIZE,"-Dmutationrate="+MUTATION_RATE,"-Delitism="+KEEPBEST,"-Dtournamentsize="+TOURNAMENT_SELECTION_SIZE,"-Dtournamentprob="+TOURNAMENT_SELECTION_PROBABILITY,"-Dtau="+tau,"-Dtauprime="+tauPrime,"-Dsigmaboundary="+sigmaBoundary,"-Dblendcrossoveralpha="+BLEND_CROSSOVER_ALPHA,"-Dfitnesssigma="+FITNESS_SIGMA,"-jar","C:\\Users\\Orestis\\Desktop\\MSC\\Evolutionary Computing\\assignmentfiles_2018\\assignmentfiles_2017\\testrun.jar","-submission=player30","-evaluation="+method,"-seed=1");
		builder.redirectErrorStream(true);
		builder.directory(new File("C:\\\\Users\\\\Orestis\\\\Desktop\\\\MSC\\\\Evolutionary Computing\\\\assignmentfiles_2018\\\\assignmentfiles_2017\\\\"));
		Process process=null;
		try {
			process = builder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String output="";
        String line = null; 
		//System.out.println(builder.command());
        try {
            while ((line = input.readLine()) != null) {
                output+=line;
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
        input.close();
        process.destroy();
        //System.out.println(output);
        return Double.parseDouble(output.split("Score: ")[1].split("Runtime")[0].trim());
	}

}
