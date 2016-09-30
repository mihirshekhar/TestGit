/**
 *  Generates combined feature values for each term, converts it into SVM format for both cancer/noncancer classifier and 
 *  multicancer classifier using vocab files.
 *  
 */
package com.cde.test;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;

/**
 * @author Mihir Shekhar
 * @date 7/01/2016
 * @emailId mihir.shekhar@research.iiit.ac.in
 * @version 1.0
 * 
 */

public class CancerTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * @throws JsonSyntaxException 
	 */
	public static void main(String[] args) throws JsonSyntaxException, NumberFormatException, IOException, InterruptedException {
		
          
		OutputDiseaseTag odt = new OutputDiseaseTag();
		odt.getDiseaseTag();
		
		
		

	}

}
