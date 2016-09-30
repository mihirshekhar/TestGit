package com.cde.pruneVocab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import com.cde.pruneVocab.calculatetopFeatures.TopFeatures;

public class PruneFeatureFile {
	public String outputFolder;
	public String inputFolder;
	public String old_Vocab_path;
	public String new_Vocab_path;
	public int noOfPrunedFeatures;
	public static final double  percentDistribution[] = {.16,.14,.14,.14,.14,.14};
    HashMap<Integer,Integer>map;
	public PruneFeatureFile(String outputFolder, String inputFolder,
			String old_Vocab_path, String new_Vocab_path, int noOfPrunedFeatures,int maxLineToRead) throws IOException {
		super();
		System.out.println("Feature Pruning Started");
		this.outputFolder = outputFolder;
		this.inputFolder = inputFolder;
		this.old_Vocab_path = old_Vocab_path;
		this.new_Vocab_path = new_Vocab_path;
		this.noOfPrunedFeatures = noOfPrunedFeatures;
		TopFeatures tf = new TopFeatures(inputFolder, noOfPrunedFeatures, percentDistribution, maxLineToRead);
		TreeSet<Integer>id = tf.CreateTopFeaturesSet();
		System.out.println("Top Features HashMap Created");

        GenerateVocabularyAndMap(id);
        System.out.println("New VOcabulary generated at :"+new_Vocab_path);
	}
	private void GenerateVocabularyAndMap(TreeSet<Integer>Id) throws IOException {
       BufferedReader br = new BufferedReader(new FileReader(old_Vocab_path));
       BufferedWriter bw = new BufferedWriter(new FileWriter(new_Vocab_path));
       ArrayList<String>old_vocab = new ArrayList<String>();
       map = new HashMap<Integer, Integer>();
       String line = "";
       while((line=br.readLine())!=null)
       {
    	   old_vocab.add(line);
       }
       br.close();
       int index = 0;
       while(!Id.isEmpty())
       {
    	  int t = Id.pollFirst();
    	  String str = old_vocab.get(t-1);
    	  bw.write(str);
    	  bw.newLine();
    	  map.put(t,++index);
       }
		bw.close();
	}
    
	
	public void GeneratePrunedOutputFile() throws IOException
	{
		String path1 = inputFolder+File.separator+"1.libsvm";
		String path2 = inputFolder+File.separator+"2.libsvm";
		String path3 = inputFolder+File.separator+"3.libsvm";
		String path4 = inputFolder+File.separator+"4.libsvm";
		String path5 = inputFolder+File.separator+"5.libsvm";
		String path01 = inputFolder+File.separator+"6.libsvm";
	//	String pathMultiClass = inputFolder+File.separator+"multiclass.libsvm";
		
		
		String path1_0 = outputFolder+File.separator+"1.libsvm";
		String path2_0 = outputFolder+File.separator+"2.libsvm";
		String path3_0 = outputFolder+File.separator+"3.libsvm";
		String path4_0 = outputFolder+File.separator+"4.libsvm";
		String path5_0 = outputFolder+File.separator+"5.libsvm";
		String path01_0 = outputFolder+File.separator+"6.libsvm";
	//	String pathMultiClass_0 = outputFolder+File.separator+"multiclass.libsvm";
		Convert(path1,path1_0);
		System.out.println("New pruned Feature File Generated at :"+path1_0);
		Convert(path2,path2_0);
		System.out.println("New pruned Feature File Generated at :"+path2_0);

		Convert(path3,path3_0);
		System.out.println("New pruned Feature File Generated at :"+path3_0);

		Convert(path4,path4_0);
		System.out.println("New pruned Feature File Generated at :"+path4_0);

		Convert(path5,path5_0);
		System.out.println("New pruned Feature File Generated at :"+path5_0);

		Convert(path01,path01_0);
		System.out.println("New pruned Feature File Generated at :"+path01_0);

		System.out.println("Feature Pruning Completed");

	}
	private void Convert(String inputPath, String outputPath) throws IOException {
        
		BufferedReader br = new BufferedReader(new FileReader (inputPath));
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath));
		String line = "";
		while((line= br.readLine())!=null)
		{
			String arr[] = line.split("\\s+");
			StringBuilder wrtStr = new StringBuilder();
			wrtStr.append(arr[0]);
			for(int i=1;i<arr.length;i++)
			{
				String arr2[] = arr[i].split(":");
				int dim = Integer.parseInt(arr2[0]);
				if(map.containsKey(dim))
				{
					wrtStr.append(" ");
					wrtStr.append(map.get(dim));
					wrtStr.append(":");
					wrtStr.append(arr2[1]);
				}
			}
			
			bw.write(wrtStr.toString());
			bw.newLine();
		}
		
		bw.close();
		br.close();
	}
    
	
	
	

}
