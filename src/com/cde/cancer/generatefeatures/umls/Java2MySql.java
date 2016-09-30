package com.cde.cancer.generatefeatures.umls;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.cde.Parameter;
import com.cde.cancer.generatefeatures.dataentities.UMLS;



/**
 * Java2MySql is a java class used to query MySQL database.
 * @author raghavendra
 */
public class Java2MySql { 
	String url;
	String dbName;
	String driver;
	String userName;
	String password;
	Connection conn;
	static Statement st;

	public static Java2MySql instance = null;

	/**
	 * Java2MySql Constructor
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Java2MySql() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		url = Parameter.url;
		dbName = Parameter.dbName;
		driver = Parameter.driver;
		userName = Parameter.userName;
		password = Parameter.password;
		Class.forName(driver).newInstance();
		conn = DriverManager.getConnection(url+dbName,userName,password);
		st = conn.createStatement();
		readSemanticTypes();
	}

	/**
	 * Returns the instance of Java2MySql object.
	 * @return Java2MySql 
	 * @throws IOException
	 */
	public static Java2MySql getInstance(){
		if(Java2MySql.instance == null) {
			try {
				Java2MySql.instance = new Java2MySql();
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Java2MySql.instance;
	}

	/**
	 * Closes the connection with MySQL database.
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		conn.close();
	}


	HashMap<String,String> semanticTypesMap = new HashMap<String,String> ();
	HashSet<String> goldSemanticTypes = new HashSet<String>();
	/**
	 * Loads the UMLS semantic types from the configuration files.
	 * @throws IOException
	 */
	void readSemanticTypes() throws IOException {
		List<String> lines = FileUtils.readLines(new File("conf_classifier"+File.separator+"clefSemanticTypes.txt"));
		for (String line : lines) {
			semanticTypesMap.put(StringUtils.substringBetween(line, "|").toLowerCase(),StringUtils.substringBefore(line, "|"));
		}
		lines = FileUtils.readLines(new File("conf_classifier"+File.separator+"GoldSemanticTypes"));
		for (String line : lines) {
			goldSemanticTypes.add(StringUtils.substringBefore(line, "|"));
		}
	}

	/**
	 * Returns the UMLS definitions of the CUI from MRDEF table.
	 * @param cui Concept Unique Identifier
	 * @return definitions UMLS definitions of CUI
	 */
	public List<String> getUMLSDefinitionsOfCUI (String cui) {
		List<String> definitions = new ArrayList<String>();
		try { 

			ResultSet res = st.executeQuery("SELECT * FROM MRDEF WHERE CUI=\"" +cui+ "\"");
			while (res.next()) { 
				//				String id = res.getString("CUI");
				//				String msg = res.getString("DEF");
				//System.out.println(id + "\t" + msg);
				definitions.add(res.getString("DEF"));
				//				System.out.println(res.getString("SAB"));
			} 

		} catch (Exception e) { 
			e.printStackTrace();
		} 
		return definitions;
	} 





	/**
	 * Returns the cui of the medical term, if the medical term is present in UMLS.
	 * Else returns null.
	 * @param term Medical term
	 * @return CUI Concept Unique Identifier
	 */
	public String getCUIOfMedicalTerm (String term) {
		String cui = searchCUIForTerm(term.trim());
		if(cui != null)
			if(cui.isEmpty()) {
				if(term.endsWith("s")) {
					cui = searchCUIForTerm(term.replaceAll("s$", "").trim());
				}
			}
		if(cui != null) {
			if(cui.isEmpty()) {
				cui = searchCUIForTerm(term.replaceAll("s$", "").replaceAll("^(\\d|,|:|\\.|;|-|\\*)*", "").replaceAll("(\\d|,|:|\\.|;|-|\\*)*$", "").trim());
			}
		}

		return cui;
	}

	/**
	 * Returns the all cuis of the medical term, if the medical term is present in UMLS.
	 * Else returns null.
	 * @param term Medical term
	 * @return CUI Concept Unique Identifier
	 */
	public String getCUIsOfMedicalTerm (String term) {
		String cui = searchCUIForTerm(term.trim());
		if(cui != null)
			if(cui.isEmpty()) {
				if(term.endsWith("s")) {
					cui = searchCUIForTerm(term.replaceAll("s$", "").trim());
				}
			}
		if(cui != null) {
			if(cui.isEmpty()) {
				cui = searchCUIForTerm(term.replaceAll("s$", "").replaceAll("^(\\d|,|:|\\.|;|-|\\*)*", "").replaceAll("(\\d|,|:|\\.|;|-|\\*)*$", "").trim());
			}
		}

		return cui;
	}

	/**
	 * Searches the term in UMLS and return its CUI.
	 * @param term
	 * @return CUI
	 */
	public String searchCUIForTerm (String term) {
		TreeMap<String, Integer> CUICountMap = new TreeMap<String, Integer>();
		try { 
			ResultSet res = st.executeQuery("SELECT * FROM MRCONSO WHERE STR=\"" + term+ "\"");
			while (res.next()) {
				HashMapUtilsCustom.incrementKeyFreqCount(CUICountMap, res.getString("CUI"));
			} 
		} catch (Exception e) { 
			//			e.printStackTrace();
		} 

		/*for (Entry<String, Integer> entry : CUICountMap.entrySet()) {
			System.out.println(entry.getKey() + "\t" + entry.getValue() + "\tterm = " + getTermsOfCUI(entry.getKey()));
		}*/
		String predictedCui = HashMapUtilsCustom.getKeyWithTopFreq(CUICountMap);
		String predictedTerm = getTermsOfCUI(predictedCui).toString();

		//		System.out.println("predicted = " + predictedCui + "\t predicted term = " + predictedTerm);
		if(predictedTerm.contains(term.toLowerCase()))
			return predictedCui;
		else 
			return "";
	}

	/**
	 * Searches the term in UMLS and return its all CUI.
	 * @param term
	 * @return CUI
	 */
	public HashSet<String> searchCUIsForTerm (String term) {
		HashSet<String> cuis = new HashSet<>();
		try { 
			ResultSet res = st.executeQuery("SELECT * FROM MRCONSO WHERE STR=\"" + term+ "\"");
			while (res.next()) {
				cuis.add(res.getString("CUI"));
			} 
		} catch (Exception e) { 
			//			e.printStackTrace();
		} 
		return cuis;
	}

	/**
	 * Get semantic type of medical term. 
	 * @param term
	 * @return semantic Type of the medical term
	 */
	public String getSemanticTypeOfTerm (String term) {

		String cui = getCUIOfMedicalTerm(term);
		//		System.out.println("2term and cui  =" + term  + " \t "+ cui);
		if(cui !=null) {
			if(!cui.isEmpty()) {
				return getMedicalTermSemanticType(cui);
			}
		}

		return "";
	}


	//
	/**
	 * Get gold semantic type (from predefined list) of the medical term.  
	 * @param cui
	 * @return
	 */
	String getMedicalTermSemanticType(String cui) {
		String semanticType = getSemanticTypeOfCUI(cui);
		if (semanticType != null)
			if(!semanticType.isEmpty())
				if(goldSemanticTypes.contains(semanticType))
					return semanticType;
		return "";
	}

	/**
	 * Get all the terms related to the cui.
	 * @param cui
	 * @return list of terms
	 */
	public HashSet<String> getTermsOfCUI (String cui) {
		HashSet<String> list = new HashSet<String>();
		try { 
			ResultSet res = st.executeQuery("SELECT * FROM MRCONSO WHERE CUI=\"" + cui+ "\"");
			while (res.next()) {
				list.add(res.getString("STR").toLowerCase());
			} 
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Returns the UMLS semantic type of CUI
	 * @param cui
	 * @return SemanticType UMLS semantic tyoe
	 */
	public String getSemanticTypeOfCUI (String cui) {
		try { 
			ResultSet res = st.executeQuery("SELECT * FROM MRSTY WHERE CUI=\"" +cui+ "\"");

			while (res.next()) { 
				return semanticTypesMap.get(res.getString("TUI").toLowerCase());
			} 

		} catch (Exception e) { 
			e.printStackTrace();
		} 
		return "";
	}

	/**
	 * Returns all UMLS semantic types of CUI
	 * @param cui
	 * @return SemanticType UMLS semantic tyoe
	 */
	public HashSet<String> getAllSemanticTypesOfCUI (String cui) {
		HashSet<String> semanticTypes = new HashSet<>();
		try { 
			String query = "SELECT * FROM MRSTY WHERE CUI= ?";
			//ResultSet res = st.executeQuery("SELECT * FROM MRSTY WHERE CUI= ? ");
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, cui);
			ResultSet res = stmt.executeQuery();
			while (res.next()) { 
				//System.out.println(res.getString("TUI").toLowerCase());
				semanticTypes.add(semanticTypesMap.get(res.getString("TUI").toLowerCase()));
			} 

		} catch (Exception e) { 
			e.printStackTrace();
		} 
		return semanticTypes;
	}


	/**
	 * Checks whether the token is in UMLS dictionary.
	 * Returns true if the token is in UMLS, else false.
	 * @param token
	 * @return true/false
	 */
	public boolean isInUMLS(String token) {
		if(token.length()<3)
			return false;
		String cui = getCUIOfMedicalTerm(StringUtils.strip(token,"(\\d|,|:|\\.|;|-|\\*)*"));
		//System.out.println("1term and cui  =" + token + " \t "+ cui);
		if(cui !=null) {
			if(!cui.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public boolean isDiseaseDisorderSemanticTypeTerm (String term) {
		if(!getSemanticTypeOfTerm(StringUtils.strip(term,"(\\d|,|:|\\.|;|-|\\*)*")).isEmpty()) {
			return true;
		}
		return false;
	}


	public static UMLS getUMLSObject(String term) throws IOException
	{
		ArrayList<String>sem = new ArrayList<String>();
		Java2MySql java2MySql;
		UMLS umls = new UMLS() ;
		try {
			java2MySql = new Java2MySql();
			String cui = java2MySql.getCUIOfMedicalTerm(term);
			List<String> desc = java2MySql.getUMLSDefinitionsOfCUI(cui);
			//	System.out.println(desc);
			HashSet<String>semantic = java2MySql.getAllSemanticTypesOfCUI(cui);
			   // System.out.println(semantic);
			for(String s : semantic)
			{
				sem.add(s);
			}

			String description = "";
			for(String s: desc)
			{
				description+=" "+s.replaceAll("\\|", " ").toLowerCase();
			}
			description = description.trim().replaceAll("\n", " ");
			//System.out.println(description);
			String semanticType ="";
			for(String s: sem)
			{
				semanticType+=" "+s;
			}
			int dict [] = Parameter.getDict(description);
			umls = new UMLS(description, dict, semanticType );
			java2MySql.closeConnection();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return umls; 
	}

	public static void main(String[] args) throws IOException, InstantiationException { 

		Parameter.IntitialiseDictionary();
		UMLS umls =  Java2MySql.getUMLSObject("heart attack");
			System.out.println(umls);
		

	}




} 
