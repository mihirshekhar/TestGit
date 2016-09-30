/**
 *  Create feature files for given input files that can be stored for further use.
 */
package com.cde.cancer.generatefeatures;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.cde.Parameter;
import com.cde.cancer.generatefeatures.dataentities.MedicalTerm;
import com.google.gson.JsonSyntaxException;

/**
 * @author Mihir Shekhar
 * @date 13/12/2015
 * @emailId mihir.shekhar@research.iiit.ac.in
 * @version 1.0
 * 
 */
public class ProcessInputFile {

	private String inputPipedFolder;
	private String outputDescriptionPipedFolder;
	private BufferedWriter log;

	public ProcessInputFile() throws IOException {
		super();
		this.inputPipedFolder = Parameter.inputPipedFolder;
		this.outputDescriptionPipedFolder = Parameter.outputDescriptionPipedFolder;
		this.log = new BufferedWriter(new FileWriter(Parameter.logFile));
	}

	public void createFeatureDirectory() throws IOException, JsonSyntaxException, NumberFormatException, InterruptedException
	{
		Parameter.IntitialiseDictionary();
		File folder = new File(inputPipedFolder);
		File [] files = folder.listFiles();
		ProcessInputFile pif = new ProcessInputFile();
		for(int i=0;i<files.length;i++)
		{
			String outputFeatureFile = pif.createFeatureFile(files[i].getName());
		}
	}



	/**
	 * First creates a description file containing description for all files.
	 * This description is printed if write description flag is true and then
	 * convertDescriptionFileToFeatureFile is called to create feature files for various classsfiers. 
	 * @throws IOException
	 * @throws InterruptedException 
	 * @throws NumberFormatException 
	 * @throws JsonSyntaxException 
	 */
	public String createFeatureFile(String filename) throws IOException, JsonSyntaxException, NumberFormatException, InterruptedException
	{
		File f = new File(outputDescriptionPipedFolder+File.separator+filename);
		BufferedReader br = new  BufferedReader(new FileReader(inputPipedFolder+File.separator+filename));
		BufferedWriter bw = new BufferedWriter (new FileWriter(outputDescriptionPipedFolder+File.separator+filename));
		String line = "";
//		System.out.println(outputDescriptionPipedFolder+File.separator+filename+" Generating Description file ");

		while((line=br.readLine())!=null)
		{	
			ProcessLine(line,bw);

		}

		bw.close();
		br.close();
	//	System.out.println(outputDescriptionPipedFolder+File.separator+filename+" Generation of Description File Completed");
		return f.getAbsolutePath();


	}
/**
 * Process line for faster processing using cached description values from Annotation Description
 * @param line
 * @param bw
 * @throws IOException
 * @throws InterruptedException
 */
	private void ProcessLine(String line, BufferedWriter bw) throws IOException, InterruptedException {
		String arr [] = line.split("\\|");
		if(Parameter.termDescriptionValues.containsKey(arr[2].toLowerCase())==false)
		{
			try {
			//	System.out.println("Not Found |"+arr[2]);
				MedicalTerm medicalTerm = new MedicalTerm(line);
				String wrtStr = medicalTerm.toString();
				String arr1 [] = wrtStr.split("\\|",4);
				Parameter.termDescriptionValues.put(arr1[2].toLowerCase(), arr1[3]);
				bw.write(wrtStr);
				bw.newLine();
				
				bw.flush();
				Thread.sleep(2000);
				System.out.println(line+" done");
			} catch (Exception e) {
				log.write(e.getStackTrace().toString()); 
				log.newLine();
				log.write(line);
				log.flush();
				System.err.println(line+" Exception");
				e.printStackTrace();
				Thread.sleep(10000);
			}
		}
		else
		{
		//	System.out.println(" Found |"+arr[2]);

			String wrtStr = arr[0]+"|"+arr[1]+"|"+arr[2]+"|"+Parameter.termDescriptionValues.get(arr[2].toLowerCase());
			bw.write(wrtStr);
			bw.newLine();
			bw.flush();
		}

	

}

public static void main(String[] args) throws IOException, JsonSyntaxException, NumberFormatException, InterruptedException {
	ProcessInputFile pif = new ProcessInputFile();
	pif.createFeatureDirectory();
	//pif.createCorrectedFeatureDirectory();


}

}
