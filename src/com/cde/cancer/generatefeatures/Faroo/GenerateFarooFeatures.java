/**
 * Extracts description from Faroo Search Engine using Faroo api. 
 */
package com.cde.cancer.generatefeatures.Faroo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.cde.Parameter;
import com.cde.cancer.generatefeatures.dataentities.Faroo;

/**
 * @author Mihir Shekhar
 * @date 10/12/2015
 * @emailId mihir.shekhar@research.iiit.ac.in
 * @version 1.0
 */
public class GenerateFarooFeatures {

	private String term;
	private ArrayList<String>description;
    
	public GenerateFarooFeatures(String term) {
		super();
		this.term = term;
		this.description = new ArrayList<String>();
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
	Parameter.IntitialiseDictionary();
       GenerateFarooFeatures gff = new GenerateFarooFeatures("Amoxycillin");
       System.out.println(gff.getFarooObject());
       
	}
	public Faroo getFarooObject() throws IOException, InterruptedException
	{
		Faroo faroo = new Faroo();
		try {
			
	
		getDescriptionFromFarooApi(term);
		String desc = Parameter.MergeUsingDelimiter(description);
		int [] dict = Parameter.getDict(desc);
		faroo = new Faroo(desc, dict);
		} catch (Exception e) {
			System.out.println(term+" faroo Exception");
		}
		return faroo;
	}
	
	private void getDescriptionFromFarooApi(String term) throws IOException {

		System.setProperty("http.proxyHost", "proxy.iiit.ac.in");
		System.setProperty("http.proxyPort", "8080");
		String url = "http://www.faroo.com/api?q="+URLEncoder.encode(term)+"&start=1&length=10&l=en&src=web&i=false&f=xml&key="+Parameter.FarooKey;
		
		String result = makeRestCall(url);//Obtain DBPedia XML object
               getDescriptionFromXML(result);
        
	}
	
	/**
	 * Given a url, dies a rest call to obtain DBpedia XM l content and tagme output in this case
	 * @param urlString
	 * @return
	 */
		private String makeRestCall(String urlString){
			URL url;
			HttpURLConnection conn=null;
			StringBuffer result = new StringBuffer();
			try {
				url = new URL(urlString);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
				}
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				String line;

				while ((line = br.readLine()) != null) {
					result.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			conn.disconnect();
			return result.toString();
		}
		
		private void getDescriptionFromXML(String result) throws IOException {
			
			  Document doc =  Jsoup.parse( result, "utf-8" );
			// System.out.println(result);
			  for (Element e : doc.select("kwic")) 
			  {
				  description.add(e.text().replaceAll("\\|", ""));
				 // System.out.println(e.text());
			  }
			
		}
	

}
