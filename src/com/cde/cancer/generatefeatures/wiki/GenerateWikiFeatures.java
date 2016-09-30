/**
 * Use apis to generate Wiki object.
 */
package com.cde.cancer.generatefeatures.wiki;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xml.sax.SAXException;

import com.cde.Parameter;
import com.cde.cancer.generatefeatures.dataentities.Wiki;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class GenerateWikiFeatures {

	private String term;
	private ArrayList<String>category;
	private ArrayList<String>description;
	public GenerateWikiFeatures(String term) {
		super();
		category = new ArrayList<String>();
		description = new ArrayList<String>();
		this.term  = term;

	}
	/**
	 * Use apis to generate wiki object.
	 * @return
	 * @throws JsonSyntaxException
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ParseException 
	 */
	public Wiki getWikiObject() throws JsonSyntaxException, NumberFormatException, IOException, ParseException
	{

		Wiki wiki ;
		String desc="";
		String cat="";
		try {
			getDescriptionAndCategories();
			desc = Parameter.MergeUsingDelimiter(description);
			cat = Parameter.MergeUsingDelimiter(category);
		} catch (Exception e) {
			System.out.println(term+ " Wiki Exception");
		}

		int [] dict = Parameter.getDict(desc);

		wiki = new com.cde.cancer.generatefeatures.dataentities.Wiki(desc, cat, dict);
		return wiki;
	}
	


/**
 * Get  description and classes of terms using tag me and DBpedia api
 * @return
 * @throws IOException 
 * @throws NumberFormatException 
 * @throws JsonSyntaxException 
 * @throws ParseException 
 * @throws LoginException 
 * @throws SAXException 
 * @throws ParserConfigurationException 
 */
private void getDescriptionAndCategories() throws JsonSyntaxException, NumberFormatException, IOException, ParseException
{
	ArrayList<String>titles = tagme();

	if(titles.size()>0)
	{            
		for(int i=0;i<titles.size();i++)
			getDescriptionAndCategoriesFromDBPedia(titles.get(i));
	}
	else
	{
		String query = "text:"+term+" AND cancer";		
		SearchIndex si = new SearchIndex(query);
		String title =si.searching();
		if(title .length()>1)
		{
			getDescriptionAndCategoriesFromDBPedia(title);
		}

	}
}
/**
 * Calls DBpedia api to get description and other information for wiki entities found by tag me
 * @param term
 * @throws IOException
 */
private void getDescriptionAndCategoriesFromDBPedia(String term) throws IOException {

	System.setProperty("http.proxyHost", "proxy.iiit.ac.in");
	System.setProperty("http.proxyPort", "8080");
	String url = "http://lookup.dbpedia.org/api/search.asmx/KeywordSearch?QueryString="+URLEncoder.encode(term) +" &MaxHits=1";

	String result = makeRestCall(url);//Obtain DBPedia XML object

	getDescriptionFromXML(result);
	getDBPediaCategories(result);
}
/**
 * Uses jsoup to obtain DBPedia category information from Xml content obtained from wikipedia.
 * @param result
 * @throws IOException
 */
private void getDBPediaCategories(String result) throws IOException{

	Document doc =  Jsoup.parse( result, "utf-8" );

	for (Element e : doc.select("Class")) 
	{
		for (Element ee : doc.select("Label"))
		{
			category.add(ee.text());	//  System.out.println(ee.text());
		}
	}

}
/**
 * Uses jsoup to parse XML content from DBPedia to get description.
 * @param result
 * @throws IOException
 */
private void getDescriptionFromXML(String result) throws IOException {

	Document doc =  Jsoup.parse( result, "utf-8" );
	// System.out.println(result);
	for (Element e : doc.select("Description")) 
	{
		description.add(e.text().replaceAll("\\|", ""));
		// System.out.println(e.text());
	}

}
/**
 * Demo to call this class.
 * @param args
 * @throws JsonSyntaxException
 * @throws NumberFormatException
 * @throws IOException
 * @throws ParseException 
 */
public static void main(String args[]) throws JsonSyntaxException, NumberFormatException, IOException, ParseException
{
	Parameter.IntitialiseDictionary();
	GenerateWikiFeatures gwf = new GenerateWikiFeatures("lignocaine");
	System.out.println(gwf.getWikiObject());
}
/**
 * 
 * 
 * Calls tag me to identify wiki entities in a given word. Get Rest Api is used
 * @return Arraylist of entities
 * @throws JsonSyntaxException
 * @throws NumberFormatException
 * @throws IOException
 */
private ArrayList<String> tagme() throws JsonSyntaxException, NumberFormatException, IOException
{
	ArrayList<String>titles = new ArrayList<String>();
	System.setProperty("http.proxyHost", "proxy.iiit.ac.in");
	System.setProperty("http.proxyPort", "8080");
	//	System.out.println(term);
	String text = term.replaceAll("-", " ");

	String url = "http://tagme.di.unipi.it/tag?key="+Parameter.TagMeKey+"&tweet=false&text="+URLEncoder.encode(text);
	String result = null;
	result = makeRestCall(url);

	JsonParser parser = new JsonParser();
	JsonObject o = (JsonObject)parser.parse(result);
	for(int k=0;k<o.getAsJsonArray("annotations").size();k++)
	{
		try {


			String title = o.getAsJsonArray("annotations").get(k).getAsJsonObject().get("title").getAsString();
			titles.add(title);
			//System.out.println(title);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	return titles;
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

}
