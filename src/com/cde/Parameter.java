/**
 * Contains parameter.
 */
package com.cde;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Parameter {
	public static String annotationDescriptionPipedFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifier/OutputDescription";
	public static HashMap<String,String>termDescriptionValues;
	public static String FeatureFolder = "/home/cde/mihir/CancerTermClassfier/CancerClassifier/feature";
	public static String CancerVsNonCancerFeaturePath=FeatureFolder+File.separator+"1.libsvm";// directory where features files for cancer vs non cancer are kept.
	public static String BrainCancerFeaturePath= FeatureFolder+File.separator+"2.libsvm";
	public static String LungCancerFeaturePath =FeatureFolder+File.separator+"5.libsvm";;
	public static String HeadandNeckCancerFeaturePath = FeatureFolder+File.separator+"4.libsvm";;
	public static String BreastCancerFeaturePath = FeatureFolder+File.separator+"3.libsvm";;
	public static String ProstateCancerFeaturePath=FeatureFolder+File.separator+"6.libsvm";;
	public static String DictionaryDirectoryPath="/home/cde/mihir/CancerTermClassfier/CancerClassifier/dictionary";
	public static String TrainLibsvmExe = "svm-train";
	public static String DictionaryVocabPath = "/home/cde/mihir/CancerTermClassfier/CancerClassifier/completeVocab/dictionary";
	public static String outputDescriptionPipedFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifier/OutputDescription";
	public static String WikiVocabPath = "/home/cde/mihir/CancerTermClassfier/CancerClassifier/completeVocab/wiki.txt";
	public static String WikiClassVocabPath = "/home/cde/mihir/CancerTermClassfier/CancerClassifier/completeVocab/wikiclass.txt";
	public static String UMLSVocabPath = "/home/cde/mihir/CancerTermClassfier/CancerClassifier/completeVocab/umls.txt";
	public static String FarooVocabPath= "/home/cde/mihir/CancerTermClassfier/CancerClassifier/completeVocab/faroo.txt";
	public static String TermVocabPath = "/home/cde/mihir/CancerTermClassfier/CancerClassifier/completeVocab/term.txt";
	public static String SemanticVocabPath="/home/cde/mihir/CancerTermClassfier/CancerClassifierMMihr/completeVocab/sem.txt";

	public static String StopWord = "/home/cde/mihir/CancerTermClassfier/CancerClassifier/StopWord";
	public static String OutputFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifier/OutputFolder";
	public static String ModelFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifier/model";
	public static String SVmOutputFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifier/tempSVMOutput";
	public static String LIBSVMTestPath="svm-predict";
	
	public static String InputFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifier/TrainTest/Test";
	public static String PreprocessedInputFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifier/preprocessed_discharge_summary";
	public static String TaggedOutputFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifier/tagged_output";
	public static String UMLSOutputFolder="/home/cde/mihir/CancerTermClassfier/CancerClassifier/umls_output";
	public static String PosIdentifedEntitiesFolder = "/home/cde/mihir/CancerTermClassfier/CancerClassifier/pos_identified_entities";
	public static String UmlsIdentifiedEntitiesFolder = "/home/cde/mihir/CancerTermClassfier/CancerClassifier/umls_identified_entities";
	public static String ExtraneousSyllableFile="conf/ExtraneousSyllables";
	public static String ClefSemanticTypes = "conf/ClefSemanticTypes.txt";
	public static String GoldSemanticTypes = "conf/GoldSemanticTypes.txt";
	public static String AbbreviationFile="conf/AbbreviationList.txt";
	public static String StopWord2 = "conf/StopWord";
	public static String inputPipedFolder=UMLSOutputFolder;

	public static String Url="jdbc:mysql://localhost:3306/";
	public static String VerbSemanticType = "conf/VerbGoldSemanticTypes.txt";
	public static boolean BifurcateNewLineseparatedWords=true;
	/**
	 * DBNAME of UMLS database
	 */
	public static String DbName="umls2014aa";
	/**
	 * Driver name of UMLS database.
	 */
	public static String Driver= "com.mysql.jdbc.Driver";
	/**
	 * Username of UMLS database
	 */
	public static String UserName="rag";
	/**
	 * Password of UMLS database
	 */
	public static String Password="datacopy";
	
	
	public static final String delimiterInPipedOutput = " $$##^^##$$ ";

	public static ArrayList<String>genericCancerDict;
	public static ArrayList<String>breastCancerDict ;
	public static ArrayList<String>lungCancerDict;
	public static ArrayList<String>prostateCancerDict;
	public static ArrayList<String>headAndNeckCancerDict ;
	public static ArrayList<String>brainCancerDict ;
	public static String url = "jdbc:mysql://localhost:3306/";
	public static String dbName = "umls2014aa";
	public static String driver= "com.mysql.jdbc.Driver";
	public static String userName="rag";
	public static String password="datacopy";
	public static String WikiIndexPath="/home/cde/Wikipedia/wikiindex";
    public static String logFile = "/home/cde/mihir/CancerTermClassfier/CancerClassifierMMihr/log.txt";
    public static String FarooKey = "N@2njpiIOCySycjEIgA4b8VPNbU_";
	public static String TagMeKey="2456ee0fb677f0bc6af3181776cd8412";

	


    public static void  IntitialiseDictionary() throws IOException
	{
		genericCancerDict = Load(DictionaryDirectoryPath+File.separator+"cancer.csv");
		breastCancerDict = Load(DictionaryDirectoryPath+File.separator+"breast.csv");
		brainCancerDict = Load(DictionaryDirectoryPath+File.separator+"brain.csv");
		prostateCancerDict = Load(DictionaryDirectoryPath+File.separator+"prostate.csv");
		lungCancerDict = Load(DictionaryDirectoryPath+File.separator+"lung.csv");
		headAndNeckCancerDict = Load(DictionaryDirectoryPath+File.separator+"hn.csv");
		
		IntialiseDescriptionMap(annotationDescriptionPipedFolder);

	}
	private static void IntialiseDescriptionMap(
			String annotationDescription) throws IOException {
		
		termDescriptionValues = new HashMap<String, String>();
		File folder = new File(annotationDescription);
	    File files[] = folder.listFiles();
	    
	    for(int i=0;i<files.length;i++)
	    {
	    	BufferedReader br = new BufferedReader (new FileReader(files[i].getAbsolutePath()));
	    	String line = "";
	    	while((line=br.readLine())!=null)
	    	{
	    		String arr[] = line.split("\\|",4);
	    		termDescriptionValues.put(arr[2].toLowerCase(), arr[3]);
	    	}
	    		
	    	br.close();
	    }
		System.out.println("HashMap loaded"+termDescriptionValues.size());
	}
	private static ArrayList<String> Load(String path) throws IOException{
		BufferedReader br = new BufferedReader ( new FileReader(path));
		String line ="";
		HashSet<String>hash = new HashSet<String>();
		while((line=br.readLine())!=null)
		{
			line = line.toLowerCase().replaceAll("^[a-z]", "");
			line = Stemmer.StemWords(line);
			hash.add(line);
		}
		ArrayList<String>arr = new ArrayList<String>();
		for(String s:hash)
		{
			arr.add(s);
		}

		return arr;
	}
	public  static int[] getDict(String description) {
		int t [] = new int[6];
		t[0] = getSubStringCount(description,genericCancerDict);
		t[2] = getSubStringCount(description,breastCancerDict);
		t[1] = getSubStringCount(description,brainCancerDict);
		t[3] = getSubStringCount(description,headAndNeckCancerDict);
		t[4] = getSubStringCount(description,lungCancerDict);
		t[5] = getSubStringCount(description,prostateCancerDict);

		return t;
	}
	private static int getSubStringCount(String description,
			ArrayList<String> token) {
		int t=0;
		for(int i=0;i<token.size();i++)
		{
			if(description.contains(token.get(i)))
			{
				++t;
			}
		}
		return t;
	}
	public static String MergeUsingDelimiter(ArrayList<String> arr) {
		String str = "";
		if(arr!=null && arr.size()>0 )
		{
			str+=arr.get(0).toLowerCase().replaceAll("\n", " ");
			for(int i=1;i<arr.size();i++)
			{
				str+=Parameter.delimiterInPipedOutput+arr.get(i).toLowerCase().replaceAll("\n", " ");
			}
		}
		return str;
	}




}
