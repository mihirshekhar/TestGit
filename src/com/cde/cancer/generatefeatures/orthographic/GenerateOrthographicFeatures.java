/**
 * Generates orthographic features from the term
 */
package com.cde.cancer.generatefeatures.orthographic;

import com.cde.cancer.generatefeatures.dataentities.Orthograph;

/**
 * @author Mihir Shekhar
 * @date 10/12/2015
 * @emailId mihir.shekhar@research.iiit.ac.in
 * @version 1.0
 */
public class GenerateOrthographicFeatures {

	private String term;
	private int startsWithCapital;
	private int containsNumeric;
	private int containsDash;
	private int containsComma;
	private int lengthOfTerm;
	private  int containsOnlyUpperCase;
	private int containsDot;
	private int startsWithNumber;
	private int endsWithNumber;
	private int containsBracket;
	
	
	public GenerateOrthographicFeatures(String term) {
		super();
		this.term = term;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		GenerateOrthographicFeatures ogf = new GenerateOrthographicFeatures("22dimethyln(s)6oxo67dihydro5hdibenzo( b d)azepin7yl)n'(22333pentafluoropropyl)malonamide");
		System.out.println(ogf.generateOrthographicFeatures());;
	}
   
	public Orthograph  generateOrthographicFeatures()
	{
		startsWithCapital = getCapitalLetter(term);
		containsNumeric = getNumeric(term);
		containsDash = getDash(term);
		containsComma = getComma(term);
		lengthOfTerm = term.length();
		containsOnlyUpperCase = getAllUppercase(term);
		containsDot = getDot(term);
		startsWithNumber = getStartNumber(term);
		endsWithNumber = getEndNumber(term);
		containsBracket = getBracket(term);
		Orthograph o = new Orthograph(startsWithCapital, containsNumeric, containsDash, containsComma, lengthOfTerm, containsOnlyUpperCase, containsDot, startsWithNumber, endsWithNumber, containsBracket);
		
		return o;

	}

	private int getBracket(String term) {
		char [] arr = term.toCharArray();
		int t= 0;
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i]==40 || arr[i]==41||arr[i]==123 || arr[i]==125 ||arr[i]==91 || arr[i]==93 )
			{
				++t;
			}
		}
		return t;
		
	}

	private int getEndNumber(String term) {
		char [] arr = term.toCharArray();
		int t= 0;

		if(47<arr[arr.length-1] && arr[arr.length-1]<58)
		{
			return 1;
		}

		return 0;
	}

	private int getStartNumber(String term) {
		char [] arr = term.toCharArray();
		int t= 0;

		if(47<arr[0] && arr[0]<58)
		{
			return 1;
		}

		return 0;
	}

	private int getDot(String term) {
		char [] arr = term.toCharArray();
		int t= 0;
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i]==46)
			{
				++t;
			}
		}
		return t;
	}

	private int getAllUppercase(String term) {
		if(term.toUpperCase().compareTo(term)==0)
		{
			return 1;
		}
		return 0;
	}

	private int getComma(String term2) {
		char [] arr = term.toCharArray();
		int t= 0;
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i]==39 || arr[i]==44)
			{
				++t;
			}
		}
		return t;
	}

	private int getDash(String term2) {
		char [] arr = term.toCharArray();
		int t= 0;
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i]==45)
			{
				++t;
			}
		}
		return t;
	}

	private int getNumeric(String term) {
		char [] arr = term.toCharArray();
		int t= 0;
		for(int i=0;i<arr.length;i++)
		{
			if(47<arr[i] && arr[i]<58)
			{
				++t;
			}
		}
		return t;
	}

	private int getCapitalLetter(String term) {
		char [] arr = term.toCharArray();
		if(64<arr[0] && arr[0]<91)
		{
			return 1;
		}
		return 0;
	}




}
