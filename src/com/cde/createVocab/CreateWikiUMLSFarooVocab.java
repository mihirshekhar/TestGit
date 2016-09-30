package com.cde.createVocab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.TreeSet;

import com.StopWord;
import com.cde.Stemmer;

public class CreateWikiUMLSFarooVocab {

	static TreeSet<String>vocab;
	static HashSet<String>stopword;
	static String PathToDescriptionFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifierMMihr/Description_Phase2_Data_Corrected";
	static String pathToVocabOutputFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifierMMihr/completeVocab";
	static int [] indices = {3,4,11,12,19};
	//3,4,11,12,19
	static String [] filename = {"wiki.txt","wikiclass.txt","umls.txt","semanticTypes.txt","faroo.txt"}; 
	public static void main(String args[]) throws IOException
	{
		stopword = StopWord.InitialiseHash("/home/cde/mihir/CancerTermClassfier/CancerClassifierMMihr/StopWord");
		for(int i=0;i<indices.length;i++)
		{
			ProcessVocab(indices[i],i);
		}
	}
	private static void ProcessVocab(int indexOfArr,int index) throws IOException {
		vocab = new TreeSet<String>();
		File folder = new File(PathToDescriptionFolder);
		File files[] = folder.listFiles();
		BufferedWriter bw = new BufferedWriter(new FileWriter(pathToVocabOutputFolder+File.separator+filename[index]));

		for(int i=0;i<files.length;i++)
		{
			BufferedReader br = new BufferedReader(new FileReader(files[i].getAbsolutePath()));

			String line = "";
			while((line = br.readLine())!=null)
			{
				//System.out.println(line.split("\\|")[3]);
				try {

					String desc = (line.split("\\|")[indexOfArr]);
					//System.out.println(desc);
					String arr [] = desc.split("\\s+");
					for(int j=0;j<arr.length;j++)
					{
						String str = Stemmer.StemWords(arr[j]).toLowerCase();
						str = str.replaceAll("[^a-z]", "");
						if(stopword.contains(str)==false && str.length()>2)
						{
							System.out.println(str);
							vocab.add(str);
						}
					}

				}
				catch (Exception e) {
					System.out.println(line);
				}

			}
			br.close();
		}
		for(String s: vocab)
		{
			bw.write(s);
			bw.newLine();
		}
		bw.close();
		vocab.clear();
	}



}
