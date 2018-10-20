package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class Method implements Callable{
	String method="";
	
	public Method(String m){
		method=m;
	}

	@Override
	public Object call() throws Exception {
		ProcessBuilder builder = new ProcessBuilder("java","-jar","C:\\Users\\Orestis\\Desktop\\MSC\\Evolutionary Computing\\assignmentfiles_2018\\assignmentfiles_2017\\testrun.jar","-submission=player30","-evaluation="+method,"-seed=1");
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
        try {
            while ((line = input.readLine()) != null) {
                output+=line+"\n";
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
        //System.out.println(output.split("java.lang.NullPointerException")[0]);
        String scorePerGen[]=output.split("java.lang.")[0].split("\n");
        for(int i=0;i<scorePerGen.length;i++) {
        	try {
	        	if(method.equals("BentCigarFunction")) {
	        		Main.v_cig.set(i, (double)Main.v_cig.get(i)+Double.parseDouble(scorePerGen[i]));
	        	}else if(method.equals("SchaffersEvaluation")) {
	        		Main.v_schaffers.set(i, (double)Main.v_schaffers.get(i)+Double.parseDouble(scorePerGen[i]));
	        	}else {
	        		Main.v_katsuura.set(i, (double)Main.v_katsuura.get(i)+Double.parseDouble(scorePerGen[i]));
	        	}
        	}catch(ArrayIndexOutOfBoundsException e) {
	        	if(method.equals("BentCigarFunction")) {
	        		Main.v_cig.add(i, Double.parseDouble(scorePerGen[i]));
	        	}else if(method.equals("SchaffersEvaluation")) {
	        		Main.v_schaffers.add(i, Double.parseDouble(scorePerGen[i]));
	        	}else {
	        		Main.v_katsuura.add(i, Double.parseDouble(scorePerGen[i]));
	        	}
        	}
        }
        System.out.println(output.split("Score: ")[1].split("Runtime")[0].trim());
        return Double.parseDouble(output.split("Score: ")[1].split("Runtime")[0].trim());
	}

}
