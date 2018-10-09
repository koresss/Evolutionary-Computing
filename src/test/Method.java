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
		ProcessBuilder builder = new ProcessBuilder("java","-jar","C:\\Users\\Orestis\\Desktop\\MSC\\Evolutionary Computing\\assignmentfiles_2018\\assignmentfiles_2017\\testrun.jar","-submission=main.player30","-evaluation="+method,"-seed=1");
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
                output+=line;
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
        System.out.println(output.split("java.lang.NullPointerException")[0]);
        System.out.println(output.split("Score: ")[1].split("Runtime")[0].trim());
        return Double.parseDouble(output.split("Score: ")[1].split("Runtime")[0].trim());
	}

}
