package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main{
	
	public static final int EXECUTIONS=1;
	public static Vector v_cig;
	public static Vector v_schaffers;
	public static Vector v_katsuura;
	
	public static void main(String args[]) throws InterruptedException, ExecutionException {
		v_cig=new Vector();
		v_schaffers=new Vector();
		v_katsuura=new Vector();
		
        double results_cigar[]= new double[EXECUTIONS];
        double results_schaffers[]= new double[EXECUTIONS];
        //double results_katsuura[]= new double[(EXECUTIONS/10)-1];
        double results_katsuura[]= new double[EXECUTIONS];
        
        
        long time_cigar=0;
        long time_schaffers=0;
        long time_katsuura=0;
        
		ExecutorService executor = Executors.newFixedThreadPool(50);
		for(int i =0;i<EXECUTIONS;i++) {
			System.out.println(""+i);
			long time=System.currentTimeMillis();
			 results_cigar[i] = (double) executor.submit(new Method("BentCigarFunction")).get();
			 time_cigar+=System.currentTimeMillis()-time;
			 time=System.currentTimeMillis();
			 results_schaffers[i] = (double) executor.submit(new Method("SchaffersEvaluation")).get();
			 time_schaffers+=System.currentTimeMillis()-time;
			 time=System.currentTimeMillis();
			 results_katsuura[i] = (double) executor.submit(new Method("KatsuuraEvaluation")).get();
			 time_katsuura+=System.currentTimeMillis()-time;
			// if(i%10==0 && i!=0) {
				// results_katsuura[(i/10)-1] = (double) executor.submit(new Method("KatsuuraEvaluation")).get();
			// }
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
			sum_katsuura+=results_katsuura[i];
/*			if(i%10==0 && i!=0) {
				sum_katsuura+=results_katsuura[(i/10)-1];
				
				if(results_katsuura[(i/10)-1]>max_katsuura) {
					max_katsuura=results_katsuura[(i/10)-1];
				}
				if(results_katsuura[(i/10)-1]<min_katsuura) {
					min_katsuura=results_katsuura[(i/10)-1];
				}
			}*/
			if(results_katsuura[i]>max_katsuura) {
				max_katsuura=results_katsuura[i];
			}
			if(results_katsuura[i]<min_katsuura) {
				min_katsuura=results_katsuura[i];
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
		System.out.println("Average time ("+EXECUTIONS+" runs) Cigar: "+time_cigar/EXECUTIONS);
		System.out.println("Max ("+EXECUTIONS+" runs) Cigar: "+max_cigar);
		System.out.println("Min ("+EXECUTIONS+" runs) Cigar: "+min_cigar);
		
		System.out.println(Arrays.toString(results_schaffers));
		System.out.println("Average ("+EXECUTIONS+" runs) Schaffers: "+sum_schaffers/EXECUTIONS);
		System.out.println("Average time ("+EXECUTIONS+" runs) Schaffers: "+time_schaffers/EXECUTIONS);
		System.out.println("Max ("+EXECUTIONS+" runs) Schaffers: "+max_schaffers);
		System.out.println("Min ("+EXECUTIONS+" runs) Schaffers: "+min_schaffers);
		
		System.out.println(Arrays.toString(results_katsuura));
/*		System.out.println("Average ("+((EXECUTIONS/10)-1)+" runs) Katsuura: "+sum_katsuura/((EXECUTIONS/10)-1));
		System.out.println("Max ("+((EXECUTIONS/10)-1)+" runs) Katsuura: "+max_katsuura);
		System.out.println("Min ("+((EXECUTIONS/10)-1)+" runs) Katsuura: "+min_katsuura);*/
		System.out.println("Average ("+EXECUTIONS+" runs) Katsuura: "+sum_katsuura/EXECUTIONS);
		System.out.println("Average time ("+EXECUTIONS+" runs) Katsuura: "+time_katsuura/EXECUTIONS);
		System.out.println("Max ("+EXECUTIONS+" runs) Katsuura: "+max_katsuura);
		System.out.println("Min ("+EXECUTIONS+" runs) Katsuura: "+min_katsuura);
		
        executor.shutdown();
        FileWriter fw;
        PrintWriter pw = null;
        try {
			fw = new FileWriter(new File("avgFitnessPerGen.txt"));
			pw=new PrintWriter(fw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.println("Average fitness per gen over 50 runs - Bent Cigar");
		for(int i=0;i<v_cig.size();i++) {
			v_cig.set(i, (double)v_cig.get(i)/EXECUTIONS);
			pw.println((double)v_cig.get(i));
		}
		pw.println("Average fitness per gen over 50 runs - Schaffers");
		for(int i=0;i<v_schaffers.size();i++) {
			v_schaffers.set(i, (double)v_schaffers.get(i)/EXECUTIONS);
			pw.println((double)v_schaffers.get(i));
		}
		pw.println("Average fitness per gen over 50 runs - Katsuura");
		for(int i=0;i<v_katsuura.size();i++) {
			v_katsuura.set(i, (double)v_katsuura.get(i)/EXECUTIONS);
			pw.println((double)v_katsuura.get(i));
		}
		pw.close();
	}


}

