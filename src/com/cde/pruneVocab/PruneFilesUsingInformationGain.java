package com.cde.pruneVocab;

import java.io.IOException;

public class PruneFilesUsingInformationGain {

	private static String old_Vocab_path="/home/cde/mihir/CancerTermClassfier/CancerClassifierMMihr/completeVocab/faroo.txt";
	private static String inputFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifierMMihr/VocabPruning/CompleteFeatureForPruningVocab/Faroo";
	private static String new_Vocab_path="/home/cde/mihir/CancerTermClassfier/CancerClassifierMMihr/completeVocab/PrunedVocab/faroo.txt";
	private static String outputFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifierMMihr/VocabPruning/PrunedFeature/Faroo";
	private static int maxLineToRead=275652;
	private static int noOfPrunedFeatures = 25000;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
     if(args.length==5)
     {
     inputFolder = args[0];
     outputFolder = args[1];
     old_Vocab_path = args[2];
     new_Vocab_path = args[3];
     noOfPrunedFeatures = Integer.parseInt(args[4]);
     PruneFeatureFile pff = new PruneFeatureFile(outputFolder, inputFolder, old_Vocab_path, new_Vocab_path, noOfPrunedFeatures, maxLineToRead);
     pff.GeneratePrunedOutputFile();
     }
     else
     {
    	 System.out.println("inputFolder,outputFolder,old_vocab_path,new_vocab_path,no_Of_Features_required");
     }
	}

}
