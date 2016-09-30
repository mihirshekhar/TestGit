package com.cde.pruneVocab.converter;

import java.io.IOException;
import java.util.PriorityQueue;

public class Test {

	private static String libsvmFilePath="/home/mihirshekhar/Desktop/FinalVocab/Null_OutputFeatureFiles/wiki/01.txt";
	private static int vocabSize=50000;
	private static String csvFilePath="/home/mihirshekhar/Desktop/test/01.csv";

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//FormatConverter fc = new FormatConverter(libsvmFilePath, csvFilePath, vocabSize,230000);
		//fc.ConvertLIBSVMtoCSV();
		
		PriorityQueue<Integer>pq = new PriorityQueue<Integer>();
		pq.add(1);
		pq.add(10);
		pq.add(12);
		
		while(!pq.isEmpty())
			System.out.println(pq.poll());
		
	}
}
