package com.cde.createVocab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.TreeSet;

import com.cde.Stemmer;

public class CreateTermVocab {
	
	static TreeSet<String>vocab;
	static String PathToInputFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifierMMihr/Description_Phase2_Data_Corrected";
    static String PathToOutputFile ="/home/cde/mihir/CancerTermClassfier/CancerClassifierMMihr/completeVocab/term.txt";
	public static void main(String args[]) throws IOException
	{
		vocab = new TreeSet<String>();
		File folder = new File(PathToInputFolder);
		File files[] = folder.listFiles();
		for(int i=0;i<files.length;i++)
		{
		//	System.out.println(files[i].getAbsolutePath()+"  "+i);
			BufferedReader br = new BufferedReader(new FileReader(files[i].getAbsolutePath()));
			String line = "";
			while((line = br.readLine())!=null)
			{
				String arr[] = line.split("\\|");
				arr[2] = arr[2].replaceAll("[^a-z]", " ");
				//System.out.println(arr[2]);
				String arr2[] = arr[2].split("\\s+");
				
				for(int j=0;j<arr2.length;j++)
				{
					String str = Stemmer.StemWords(arr2[j]).toLowerCase();
					if(str.length()>2)
					{
						vocab.add(str);
						System.out.println(str);
					}
				}
				
			}
			br.close();

			
		}

		BufferedWriter bw = new BufferedWriter(new FileWriter(PathToOutputFile));
		for(String s: vocab)
		{
			bw.write(s);
			bw.newLine();
		}
		bw.close();
		
		
	}
	

}
