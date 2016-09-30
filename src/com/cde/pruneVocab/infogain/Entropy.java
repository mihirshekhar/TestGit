package com.cde.pruneVocab.infogain;

import java.util.Arrays;

public class Entropy {
	
	
	public static double calEntopy(int posIns, int negIns)
	{
		double entropy= 0;
		double total = posIns+negIns;
		if(total<=0)
		{
			System.out.println("File is empty");
			System.exit(0);
		}
		double posRatio = (double)posIns/total;
		double negRatio = (double)negIns/total;

		entropy = calIndEntropy(posRatio)+calIndEntropy(negRatio);
		return entropy;
	}
	
	public static double calEntropy(int [] ins)
	{
		
		double entropy= 0;
		
		double [] ratio = new double[ins.length];
		Arrays.fill(ratio, 0);
		ratio = findRatio(ins);
		for(int i=0;i<ins.length;i++)
		{
		//	System.out.println(calIndEntropy(ratio[i]));
			entropy+=calIndEntropy(ratio[i]);
		}
		return entropy;
	}
	private static double calIndEntropy(double d) {
		return -1*d*logarithm(d);
	}

	private static double[] findRatio(int[] ins) {
		
		double total = sum(ins);
		if(total<=0)
		{
			total = 1;
		//	System.out.println("File is empty");
		//	System.exit(0);
		}
		double arr[] = new double[ins.length];
		for(int i=0;i<arr.length;i++)
			arr[i] = (double)ins[i]/total;
		
		
		
		return arr;
	}

	public static double sum(int[] ins) {
	double sum = 0;
	for(int i=0;i<ins.length;i++)
	{
		sum+=ins[i];
	}
		return sum;
	}
	private static double sum(double[] ins) {
		double sum = 0;
		for(int i=0;i<ins.length;i++)
		{
			sum+=ins[i];
		}
			return sum;
	}

	private static double logarithm(double val)
	{  
		double log = 0;
		if(val>0)
		{
	      log = Math.log(val)/Math.log(2);
		}
		return log;
	}

}
