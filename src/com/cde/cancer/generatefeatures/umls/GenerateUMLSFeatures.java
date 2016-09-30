/**
 * Use apis to generate UMLS object.
 */
package com.cde.cancer.generatefeatures.umls;

import java.io.IOException;
import java.util.ArrayList;

import com.cde.Parameter;
import com.cde.cancer.generatefeatures.dataentities.UMLS;

/**
 * @author Mihir Shekhar
 * @date 10/12/2015
 * @emailId mihir.shekhar@research.iiit.ac.in
 * @version 1.0
 */
public class GenerateUMLSFeatures {
	private String term;

	public GenerateUMLSFeatures(String term) {
		super();
		this.term = term.replaceAll("-", " ").toLowerCase();
		this.term = term.replaceAll("[^a-zA-Z0-9]", "");
		//System.out.println(term);
		//System.out.println(term);
	}



	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		Parameter.IntitialiseDictionary();
		GenerateUMLSFeatures guf = new GenerateUMLSFeatures("abagovomab");
		System.out.println(guf.getUMLSObject());
	}
	/**
	 * Gets UMLS object from term
	 * 
	 * @return
	 * @throws IOException 
	 */
	public UMLS getUMLSObject() throws IOException
	{
		UMLS umls=new UMLS();
		try {
			
	
		if(term.length()>1)
		{
			umls = Java2MySql.getUMLSObject(term);
			if(umls.getText().length()<1 && term.split("\\s+").length>1)
			{
				String arr [] =  term.split("\\s+");
				for(int i=0;i<arr.length;i++)
				{

					arr[i] = arr[i].replaceAll("[^a-zA-Z0-9\\.]","");
					if(arr[i].length()>1)
					{
						UMLS temp =Java2MySql.getUMLSObject(arr[i]);
						if(temp.getText().length()>1)
						{
							umls.setText(umls.getText()+Parameter.delimiterInPipedOutput+temp.getText());
							umls.setSemanticType(umls.getSemanticType()+Parameter.delimiterInPipedOutput+temp.getSemanticType());
							int dict1 []  = umls.getDict();
							int dict2 [] = temp.getDict();
							int dict3 [] = Merge(dict1,dict2);
							umls.setDict(dict3);
						}
					}
				}
			}
		}
		} catch (Exception e) {
			System.out.println(term+" UMLS exception");
		}
		return umls;

	}


	/**
	 * Merges contents of two arrays.
	 * @param dict1
	 * @param dict2
	 * @return
	 */
	private int[] Merge(int[] dict1, int[] dict2) {
		int dict3 [] = new int [dict1.length];
		for(int i=0;i<dict1.length;i++)
		{
			dict3[i] = dict1[i]+dict2[i];
		}
		return dict3;
	}



}
