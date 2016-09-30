package com.cde.pruneVocab.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class FormatConverter {
	
	private String libsvmFilePath;
	private String csvFilePath;
	private int vocabSize;
	private int lineNumber;
	public FormatConverter(String libsvmFilePath, String csvFilePath, int vocabSize) {
		super();
		this.libsvmFilePath = libsvmFilePath;
		this.csvFilePath = csvFilePath;
		this.vocabSize = vocabSize;
		this.lineNumber  = Integer.MAX_VALUE;
	}
	
	public FormatConverter(String libsvmFilePath, String csvFilePath,
			int vocabSize, int lineNumber) {
		super();
		this.libsvmFilePath = libsvmFilePath;
		this.csvFilePath = csvFilePath;
		this.vocabSize = vocabSize;
		this.lineNumber = lineNumber;
	}

	public FormatConverter(String csvFilePath, String libsvmFilePath) {
		super();
		this.libsvmFilePath = libsvmFilePath;
		this.csvFilePath = csvFilePath;
		this.lineNumber  = Integer.MAX_VALUE;

	}
	
	
	
	public void ConvertLIBSVMtoCSV() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(libsvmFilePath));
		BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath));
		int t = 0;
		int s=0;
		String line = "";
		int lineNo = 0; 
		while((line=br.readLine())!=null && ++lineNo<=lineNumber)
		{
			String arr [] = line.split("\\s+");
//			String array [] = new String [vocabSize+1];
//			Arrays.fill(array, "0");
//			array[0]  = arr[0];
//			for(int i=1;i<arr.length;i++)
//			{
//				String arr2 [] = arr[i].split(":");
//				array[Integer.parseInt(arr2[0])] = arr2[1];
//			}
//			
//			String wrtStr = CreateCSVString(array);
//			bw.write(wrtStr);
//			bw.newLine();
//			
//			
//			if(++t%10000 == 0)
//			{
//				System.out.println((++s) * t);
//				t = 0;
//				}	
		}
		bw.close();
		br.close();
		
	}

	private String CreateCSVString(String[] array) {
		StringBuilder wrtStr = new StringBuilder().append("");
		wrtStr.append(array[0]);
		for(int i=1;i<array.length;i++)
		{
			wrtStr.append(",");
			wrtStr.append(array[i]);
		}
		
		return wrtStr.toString();
	}
	
	
	

}
