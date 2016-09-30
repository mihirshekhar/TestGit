package com.cde.createVocab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import com.cde.Stemmer;
/**
 *  * @author cde
 *
 */
public class CreateVocabDictioanary {
	
	static HashSet<String>vocab;
	static String PathToInputFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifierMMihr/dictionary";
	static String pathToOutputFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifierMMihr/completeVocab/dictionary";
	public static void main(String args[]) throws IOException
	{
		vocab = new HashSet<String>();
		File folder = new File(PathToInputFolder);
		File files[] = folder.listFiles();
		for(int i=0;i<files.length;i++)
		{
			BufferedReader br = new BufferedReader(new FileReader(files[i].getAbsolutePath()));
			BufferedWriter bw = new BufferedWriter(new FileWriter(pathToOutputFolder+File.separator+files[i].getName()));

			String line = "";
			while((line = br.readLine())!=null)
			{
				String str = Stemmer.StemWords(line).toLowerCase();
				str = str.replaceAll("[^a-z]", "");
				if(str.length()>2)
				{
					System.out.println(str);
					vocab.add(str);
				}
				
			}
			for(String s: vocab)
			{
				bw.write(s);
				bw.newLine();
			}
			vocab.clear();
			br.close();
			bw.close();
			
		}

	
		
		
	}
	

}
