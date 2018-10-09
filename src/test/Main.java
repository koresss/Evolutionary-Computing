package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main{
	
	public static final int EXECUTIONS=60;
	
	public static void main(String args[]) throws InterruptedException, ExecutionException {
		
        double results_cigar[]= new double[EXECUTIONS];
        double results_schaffers[]= new double[EXECUTIONS];
        double results_katsuura[]= new double[(EXECUTIONS/10)-1];
        
		ExecutorService executor = Executors.newFixedThreadPool(50);
		for(int i =0;i<EXECUTIONS;i++) {
			System.out.println(""+i);
			 //results_cigar[i] = (double) executor.submit(new Method("BentCigarFunction")).get();
			 results_schaffers[i] = (double) executor.submit(new Method("SchaffersEvaluation")).get();
			 if(i%10==0 && i!=0) {
				 //results_katsuura[(i/10)-1] = (double) executor.submit(new Method("KatsuuraEvaluation")).get();
			 }
		}
		double sum_cigar=0;
		double sum_schaffers=0;
		double sum_katsuura=0;
		
		double min_cigar=Double.MAX_VALUE;
		double min_schaffers=Double.MAX_VALUE;
		double min_katsuura=Double.MAX_VALUE;
		
		double max_cigar=Double.MIN_VALUE;
		double max_schaffers=Double.MIN_VALUE;
		double max_katsuura=Double.MIN_VALUE;
		
		for(int i=0;i<EXECUTIONS;i++) {
			sum_cigar+=results_cigar[i];
			sum_schaffers+=results_schaffers[i];
			if(i%10==0 && i!=0) {
				sum_katsuura+=results_katsuura[(i/10)-1];
				
				if(results_katsuura[(i/10)-1]>max_katsuura) {
					max_katsuura=results_katsuura[(i/10)-1];
				}
				if(results_katsuura[(i/10)-1]<min_katsuura) {
					min_katsuura=results_katsuura[(i/10)-1];
				}
			}
			
			if(results_cigar[i]>max_cigar) {
				max_cigar=results_cigar[i];
			}
			if(results_cigar[i]<min_cigar) {
				min_cigar=results_cigar[i];
			}
			
			if(results_schaffers[i]>max_schaffers) {
				max_schaffers=results_schaffers[i];
			}
			if(results_schaffers[i]<min_schaffers) {
				min_schaffers=results_schaffers[i];
			}

		}
		System.out.println(Arrays.toString(results_cigar));
		System.out.println("Average ("+EXECUTIONS+" runs) Cigar: "+sum_cigar/EXECUTIONS);
		System.out.println("Max ("+EXECUTIONS+" runs) Cigar: "+max_cigar);
		System.out.println("Min ("+EXECUTIONS+" runs) Cigar: "+min_cigar);
		
		System.out.println(Arrays.toString(results_schaffers));
		System.out.println("Average ("+EXECUTIONS+" runs) Schaffers: "+sum_schaffers/EXECUTIONS);
		System.out.println("Max ("+EXECUTIONS+" runs) Schaffers: "+max_schaffers);
		System.out.println("Min ("+EXECUTIONS+" runs) Schaffers: "+min_schaffers);
		
		System.out.println(Arrays.toString(results_katsuura));
		System.out.println("Average ("+((EXECUTIONS/10)-1)+" runs) Katsuura: "+sum_katsuura/((EXECUTIONS/10)-1));
		System.out.println("Max ("+((EXECUTIONS/10)-1)+" runs) Katsuura: "+max_katsuura);
		System.out.println("Min ("+((EXECUTIONS/10)-1)+" runs) Katsuura: "+min_katsuura);
        executor.shutdown();
	}


}

