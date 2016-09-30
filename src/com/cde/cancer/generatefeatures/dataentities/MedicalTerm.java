package com.cde.cancer.generatefeatures.dataentities;
/**
 * Represents medical term from training and testing dataset.
 * @author Mihir Shekhar
 * @date 10/12/2015
 * @emailId mihir.shekhar@research.iiit.ac.in
 * @version 1.0
 */
import java.io.IOException;
import java.util.Dictionary;

import org.apache.lucene.queryparser.classic.ParseException;

import com.cde.Parameter;
import com.cde.cancer.generatefeatures.Faroo.GenerateFarooFeatures;
import com.cde.cancer.generatefeatures.orthographic.GenerateOrthographicFeatures;
import com.cde.cancer.generatefeatures.umls.GenerateUMLSFeatures;
import com.cde.cancer.generatefeatures.wiki.GenerateWikiFeatures;
import com.google.gson.JsonSyntaxException;


public class MedicalTerm {
	private String inputString;
	private Baseline baseline;	
	private Label label;
	private Wiki wiki;
	private UMLS umls;
	//private DuckDuckGo duckduckgo;
	private Faroo faroo;
	private Orthograph orthographic;
	public MedicalTerm(String line) throws JsonSyntaxException, NumberFormatException, IOException, InterruptedException, ParseException {
		super();
		this.inputString = line;
		String arr[] = line.split("\\|");
		if(arr.length ==4 && arr[2].replaceAll("[^a-zA-Z]", "").length()>1)
		{
			this.label = setLabel(arr[arr.length-1]);
			int dict [] = Parameter.getDict(arr[2]);
			this. baseline = new Baseline(arr[2],dict);
			GenerateWikiFeatures gwf = new GenerateWikiFeatures(arr[2]);
			this.wiki = gwf.getWikiObject();
			GenerateUMLSFeatures guf = new GenerateUMLSFeatures(arr[2]);
			this.umls = guf.getUMLSObject();
			GenerateFarooFeatures gff = new GenerateFarooFeatures(arr[2]);
			this.faroo = gff.getFarooObject();
			GenerateOrthographicFeatures gof = new GenerateOrthographicFeatures(arr[2]);
			this.orthographic = gof.generateOrthographicFeatures();

		}
		else if(arr.length ==3 && arr[2].replaceAll("[^a-zA-Z]", "").length()>1)
		{
			this.label = setLabel("-111");
			int dict [] = Parameter.getDict(arr[2]);
			this. baseline = new Baseline(arr[2],dict);
			GenerateWikiFeatures gwf = new GenerateWikiFeatures(arr[2]);
			this.wiki = gwf.getWikiObject();
			GenerateUMLSFeatures guf = new GenerateUMLSFeatures(arr[2]);
			this.umls = guf.getUMLSObject();
			GenerateFarooFeatures gff = new GenerateFarooFeatures(arr[2]);
			this.faroo = gff.getFarooObject();
			GenerateOrthographicFeatures gof = new GenerateOrthographicFeatures(arr[2]);
			this.orthographic = gof.generateOrthographicFeatures();
		}
	}
	public static void main(String args[]) throws JsonSyntaxException, NumberFormatException, IOException, InterruptedException, ParseException
	{
		Parameter.IntitialiseDictionary();
		MedicalTerm mterm = new MedicalTerm("7094393.txt|186-193|aaaaaaaaa|3");
	//	System.out.println(mterm);
	}

	@Override
	public String toString() {

		String p[]= inputString.split("\\|");
		String r = p[0]+"|"+p[1]+"|"+p[2];
		//System.out.println(r);
	//	System.out.println("Wikipedia :" +wiki);
		r+=wiki.toString();
	//	System.out.println("UMLS :"+ umls);
		r+=umls.toString();
	//	System.out.println("Faroo :"+faroo);
		r+=faroo.toString();
	//	System.out.println("Orthograph: "+orthographic);
		r+=orthographic.toString();
		r+=label.toString();
		return r;
	}

	/**
	 * Set lable object
	 * @param str
	 * @return
	 */
	private Label setLabel(String str) {
		Label label;
		if(str.compareToIgnoreCase("genericcancer")==0)
		{
			label = new Label(true, 100);

		}
		else if(str.compareToIgnoreCase("braincancer")==0)
		{
			label = new Label(true, 111);

		}
		else if(str.compareToIgnoreCase("breastcancer")==0)
		{
			label = new Label(true, 112);

		}
		else if(str.compareToIgnoreCase("headandneckcancer")==0)
		{
			label = new Label(true, 113);

		}
		else if(str.compareToIgnoreCase("lungcancer")==0)
		{
			label = new Label(true,114);

		}
		else if(str.compareToIgnoreCase("prostatecancer")==0)
		{
			label = new Label(true, 115);

		}
		else
		{	    	 
			label = new Label(false, -111);
		}
		return label;
	}



}
