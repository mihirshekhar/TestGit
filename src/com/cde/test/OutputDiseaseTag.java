package com.cde.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import com.SVM;
import com.StopWord;
import com.cde.Parameter;
import com.google.gson.JsonSyntaxException;

public class OutputDiseaseTag {

	private HashMap<String,Integer>wikiVocab;
	private HashMap<String,Integer>wikiClassVocab;
	private HashMap<String, Integer>umlsVocab;
	private HashMap<String,Integer> farooVocab;
	private HashMap<String,Integer>semanticVocab;
	private HashMap<String, Integer>termVocab;

	private HashMap<String,Integer>cancerDictVocab;
	private HashMap<String,Integer>brainDictVocab;
	private HashMap<String,Integer>lungDictVocab;
	private HashMap<String,Integer>breastDictVocab;
	private HashMap<String,Integer>prostateDictVocab;
	private HashMap<String, Integer>headAndNeckDictVocab;


	private String inputPipedFolder;
	private HashSet<String>stopword;
	private int index;
	private String featureFolder;
	private String testLibsvmExe;
	private String modelFolder;
	private String SVMOutputFolder;
	private String outputFolder;

	private String dictionaryVocabFolder;

	public OutputDiseaseTag(
			) throws IOException {
		super();
		index = 28;
		this.modelFolder = Parameter.ModelFolder;
		this.SVMOutputFolder = Parameter.SVmOutputFolder;
		this.outputFolder = Parameter.OutputFolder;
		this.testLibsvmExe = Parameter.LIBSVMTestPath;

		this.cancerDictVocab = LoadVocabulary(Parameter.DictionaryVocabPath+File.separator+"cancer.csv");
		this.lungDictVocab = LoadVocabulary(Parameter.DictionaryVocabPath+File.separator+"lung.csv");
		this.brainDictVocab = LoadVocabulary(Parameter.DictionaryVocabPath+File.separator+"brain.csv");
		this.breastDictVocab = LoadVocabulary(Parameter.DictionaryVocabPath+File.separator+"breast.csv");
		this.prostateDictVocab = LoadVocabulary(Parameter.DictionaryVocabPath+File.separator+"prostate.csv");
		this.headAndNeckDictVocab = LoadVocabulary(Parameter.DictionaryVocabPath+File.separator+"hn.csv");
		this.wikiVocab = LoadVocabulary(Parameter.WikiVocabPath);
		this.termVocab = LoadVocabulary(Parameter.TermVocabPath);
		this.wikiClassVocab = LoadVocabulary(Parameter.WikiClassVocabPath);;
		this.umlsVocab = LoadVocabulary(Parameter.UMLSVocabPath);
		this.farooVocab = LoadVocabulary(Parameter.FarooVocabPath);
		this.semanticVocab= LoadVocabulary(Parameter.SemanticVocabPath);
		this.stopword = StopWord.InitialiseHash(Parameter.StopWord);
		this.inputPipedFolder = Parameter.inputPipedFolder;
		this.outputFolder = Parameter.OutputFolder;
		this.featureFolder = Parameter.FeatureFolder;
	}

	public void getDiseaseTag() throws IOException, JsonSyntaxException, NumberFormatException, InterruptedException
	{
		Parameter.IntitialiseDictionary();
		File folder = new File(inputPipedFolder);
		File files[] = folder.listFiles();

		
		for(int i=0;i<files.length;i++)
		{
			GenerateFeatures gf = new GenerateFeatures(
					featureFolder+File.separator+files[i].getName(), 
					cancerDictVocab,
					brainDictVocab,
					lungDictVocab, 
					breastDictVocab,
					prostateDictVocab,
					headAndNeckDictVocab,
					wikiVocab,
					wikiClassVocab, 
					umlsVocab, 
					farooVocab,
					semanticVocab,
					termVocab, 
					stopword,index);

			gf.generateFeatures(files[i].getAbsolutePath());
			SVM svmtest = new SVM(modelFolder, SVMOutputFolder, testLibsvmExe, featureFolder+File.separator+files[i].getName());
			svmtest.outputTestResults();

			ReadAndWriteSVMTestOutput(SVMOutputFolder, Parameter.outputDescriptionPipedFolder+File.separator+files[i].getName()
					,outputFolder+File.separator+files[i].getName());
			System.out.println("File "+files[i].getName()+" processed");

		}
	}

	/**
	 * Reads output generated by SVM biclassifiers and generates output in CSV format
	 * @param tempOutputFolder
	 * @param pipedArr
	 * @throws NumberFormatException 
	 * @throws IOException
	 */
	private void ReadAndWriteSVMTestOutput(String svmOutputFolder,
			String descriptionFile, String outputFile) throws NumberFormatException, IOException{
		BufferedReader br1 = new BufferedReader(new FileReader(svmOutputFolder+File.separator+"1.output")); 
		BufferedReader br2 = new BufferedReader(new FileReader(svmOutputFolder+File.separator+"2.output")); 
		BufferedReader br3 = new BufferedReader(new FileReader(svmOutputFolder+File.separator+"3.output")); 
		BufferedReader br4 = new BufferedReader(new FileReader(svmOutputFolder+File.separator+"4.output")); 
		BufferedReader br5 = new BufferedReader(new FileReader(svmOutputFolder+File.separator+"5.output"));
		BufferedReader br6 = new BufferedReader(new FileReader(svmOutputFolder+File.separator+"6.output"));
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		BufferedReader desc = new BufferedReader(new FileReader(descriptionFile));
		String line1,line2, line3 ,line4 ,line5,line6;

		while((line1=br1.readLine())!=null)
		{
			line2 = br2.readLine();
			line3 = br3.readLine();
			line4 = br4.readLine();
			line5 = br5.readLine();
			line6 = br6.readLine();
			int d1 = Integer.parseInt(line1);
			int d2 = Integer.parseInt(line2);
			int d3 = Integer.parseInt(line3);
			int d4 = Integer.parseInt(line4);
			int d5 = Integer.parseInt(line5);
			int d6 = Integer.parseInt(line6);
			String arr[] = desc.readLine().split("\\|");

			String disease = getDisease(d1,d2,d3,d4,d5,d6,arr[2]);


			String wrtStr = arr[0]+"|" +arr[1]+"|"+arr[2]+"|"+disease;

			bw.write(wrtStr);
			bw.newLine();



		}
		br1.close();
		br2.close();
		br3.close();
		br4.close();
		br5.close();
		br6.close();
		bw.close();
		desc.close();


	}
	private void ReadAndWriteRandomForestTestOutput(String svmOutputFolder,
			String descriptionFile, String outputFile) throws NumberFormatException, IOException{
		BufferedReader br1 = new BufferedReader(new FileReader(svmOutputFolder+File.separator+"rf1.output")); 
		BufferedReader br2 = new BufferedReader(new FileReader(svmOutputFolder+File.separator+"rf2.output")); 
		BufferedReader br3 = new BufferedReader(new FileReader(svmOutputFolder+File.separator+"rf3.output")); 
		BufferedReader br4 = new BufferedReader(new FileReader(svmOutputFolder+File.separator+"rf4.output")); 
		BufferedReader br5 = new BufferedReader(new FileReader(svmOutputFolder+File.separator+"rf5.output"));
		BufferedReader br6 = new BufferedReader(new FileReader(svmOutputFolder+File.separator+"rf6.output"));
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		BufferedReader desc = new BufferedReader(new FileReader(descriptionFile));
		String line1,line2, line3 ,line4 ,line5,line6;

		while((line1=br1.readLine())!=null)
		{
			line2 = br2.readLine();
			line3 = br3.readLine();
			line4 = br4.readLine();
			line5 = br5.readLine();
			line6 = br6.readLine();
			int d1 = ProcessProbability(line1);
			int d2 = ProcessProbability(line2);
			int d3 = ProcessProbability(line3);
			int d4 = ProcessProbability(line4);
			int d5 = ProcessProbability(line5);
			int d6 = ProcessProbability(line6);
			String arr[] = desc.readLine().split("\\|");
			String disease = getDisease(d1,d2,d3,d4,d5,d6,arr[2]);


			String wrtStr = arr[0]+"|" +arr[1]+"|"+arr[2]+"|"+disease;

			bw.write(wrtStr);
			bw.newLine();



		}
		br1.close();
		br2.close();
		br3.close();
		br4.close();
		br5.close();
		br6.close();
		bw.close();
		desc.close();


	}



	private int ProcessProbability(String prob) {
		
		return (int)(Double.parseDouble(prob)+0.1);
	}

	private String getDisease(int d1, int d2, int d3, int d4, int d5, int d6,String term) {

		if(term.toLowerCase().contains("imrt")||term.toLowerCase().contains("mesotheliom")|| term.toLowerCase().contains("dysplas")||term.toLowerCase().contains("carcin")|| term.toLowerCase().contains("cancer"))
		{
			d1 = 1;
		}
		if(term.toLowerCase().contains("brachytherapy")||term.toLowerCase().contains("angiogen")||term.toLowerCase().contains("myelom")||term.toLowerCase().contains("lymphom")||term.toLowerCase().contains("malignanc")||term.toLowerCase().contains("tumo")||term.toLowerCase().contains("chemo")|| term.toLowerCase().contains("hodgkin")||term.toLowerCase().contains("leukemi")|| term.toLowerCase().contains("melanom"))
		{
			d1 = 1;
		}

		if(term.toLowerCase().contains("cytotox")||term.toLowerCase().contains("neoplasm")||term.toLowerCase().contains("adenom")||term.toLowerCase(
				 ).contains("granulom")||term.toLowerCase().contains("hyperlasi")||term.toLowerCase().contains("biop")|| term.toLowerCase().contains("neoplas")|| term.toLowerCase().contains("blastom")||term.toLowerCase().contains("sarcom"))
		{
			
			d1 = 1;
		}
		if(term.toLowerCase().contains("breast cancer") || term.toLowerCase().contains("breastcancer")||term.toLowerCase().contains("lumpectomy")
				|| term.toLowerCase().contains("goserelin") )
		{
		//	System.out.println(term+" breast");

			return "breastcancer";
		}
		if(term.toLowerCase().contains("mammary") && d1==1)
		{
		//	System.out.println(term+" breast");


			return "breastcancer";
		}
		if(term.toLowerCase().contains("ductal") && d1==1)
		{
		//	System.out.println(term+" breast");

			return "breastcancer";
		}
		if(term.toLowerCase().contains("breast") && d1==1)
		{
			//System.out.println(term+" breast");

			return "breastcancer";
		}
		if(term.toLowerCase().contains("brca") ||term.toLowerCase().contains("megestrol"))
		{
			//System.out.println(term+" breast");

			return "breastcancer";
		}
		if(term.toLowerCase().contains("hnscc") || term.toLowerCase().contains("scchn"))
		{
			//System.out.println(term+" hn");

			return "headandneckcancer";
		}
		if(term.toLowerCase().contains("thyroid") && d1==1)
		{
		//	System.out.println(term+" hn");

			return "headandneckcancer";
		}
		if(term.toLowerCase().contains("salivary") && d1==1)
		{
		//	System.out.println(term+" hn");

			return "headandneckcancer";
		}
		if(term.toLowerCase().contains("tongue") && d1==1)
		{
			//System.out.println(term+" hn");

			return "headandneckcancer";
		}
		if(term.toLowerCase().contains("oral") && d1==1)
		{
//			System.out.println(term+" hn");

			return "headandneckcancer";
		}
		if(term.toLowerCase().contains("phary") && d1==1)
		{
			//System.out.println(term+" hn");

			return "headandneckcancer";
		}
		if(term.toLowerCase().contains("neck") && d1==1)
		{
		//	System.out.println(term+" hn");

			return "headandneckcancer";
		}
		if(term.toLowerCase().contains("thora") && d1==1)
		{
		//	System.out.println(term+" hn");

			return "headandneckcancer";
		}
		if(term.toLowerCase().contains("nsclc"))
		{
		//	System.out.println(term+" lung");

			return "lungcancer";
		}
		if(term.toLowerCase().contains("lung") && d1==1)
		{
		//	System.out.println(term+" lung");

			return "lungcancer";
		}
		if(term.toLowerCase().contains("pleomorphic") && d1==1)
		{
		//	System.out.println(term+" lung");

			return "lungcancer";
		}
		if(term.toLowerCase().contains("bronch") && d1==1)
		{
		//	System.out.println(term+" lung");

			return "lungcancer";
		}
		if(term.toLowerCase().contains("transperineal") && d1==1)
		{
		//	System.out.println(term+" prostate");

			return "prostatecancer";
		}
		if(term.toLowerCase().contains("prostat") && d1==1)
		{
		//	System.out.println(term+" prostat");

			return "prostatecancer";
		}
		
		if(term.toLowerCase().contains("castration-resistant")||term.toLowerCase().contains("lapc4")||term.toLowerCase().contains("lncap") || term.toLowerCase().contains("gleason")||term.toLowerCase().contains("cyp17"))
		{
	//		System.out.println(term+" prostate");
			return "prostatecancer";
		}

		if(term.toLowerCase().contains("meningiom")|| term.toLowerCase().contains("glioma") )
		{
	//		System.out.println(term+" brain");
			return "braincancer";
		}
        
		if(d1<1 && term.toLowerCase().contains("prost"))
		{
			d6 = 0;
		}
		if(d1<1 && term.toLowerCase().contains("castration"))
		{
			d6 = 0;
		}
		if(d1<1 && term.toLowerCase().contains("lung"))
		{
			d5 = 0;
		}
		if(d1<1 && term.toLowerCase().contains("neck"))
		{
			d4 = 0;
		}
		if(d1>0 || d2>0 || d3>0 || d4>0 ||d5 >0||d6>0)
		{
			if(d6>0)
			{
				return "prostatecancer";
			}
			else if(d3>0)
			{
				return "breastcancer";
			}
			else if(d4>0)
			{
				return "headandneckcancer";
			}
			else if(d2>0)
			{
				return "braincancer";
			}
			else if(d5>0)
			{
				return "lungcancer";
			}
			else
				return "genericcancer";

		}
		else
		{
			return "0";
		}
	}

	/**
	 * Loads vocabulary
	 * @param vocabPath
	 * @param stem
	 * @return
	 * @throws IOException
	 */
	private  HashMap<String, Integer> LoadVocabulary(String vocabPath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(vocabPath));
		String line = "";
		HashMap<String, Integer>vocab = new HashMap<String,Integer>();
		while((line=br.readLine())!=null)
		{
			vocab.put(line.toLowerCase(), ++index);
		}
		br.close();
		return vocab;
	}

	public static void main(String[] args) throws IOException, JsonSyntaxException, NumberFormatException, InterruptedException
	{
		OutputDiseaseTag odt = new OutputDiseaseTag();
		odt.getDiseaseTag();
	}


}
