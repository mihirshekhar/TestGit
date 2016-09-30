package com.cde.cancer.generatefeatures.dataentities;
/**
 * Represents orthographic features.
 * @author Mihir Shekhar
 * @date 10/12/2015
 * @emailId mihir.shekhar@research.iiit.ac.in
 * @version 1.0
 */
public class Orthograph {
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
	
	
	
	public Orthograph(int startsWithCapital, int containsNumeric,
			int containsDash, int containsComma, int lengthOfTerm,
			int containsOnlyUpperCase, int containsDot, int startsWithNumber,
			int endsWithNumber, int containsBracket) {
		super();
		this.startsWithCapital = startsWithCapital;
		this.containsNumeric = containsNumeric;
		this.containsDash = containsDash;
		this.containsComma = containsComma;
		this.lengthOfTerm = lengthOfTerm;
		this.containsOnlyUpperCase = containsOnlyUpperCase;
		this.containsDot = containsDot;
		this.startsWithNumber = startsWithNumber;
		this.endsWithNumber = endsWithNumber;
		this.containsBracket = containsBracket;
	}
	
	
	@Override
	public String toString() {
		return "|" + startsWithCapital
				+ "|" + containsNumeric + "|"
				+ containsDash + "|" + containsComma
				+ "|" + lengthOfTerm + "|"
				+ containsOnlyUpperCase + "|" + containsDot
				+ "|" + startsWithNumber
				+ "|" + endsWithNumber + "|"
				+ containsBracket;
	}


	public int getStartsWithCapital() {
		return startsWithCapital;
	}
	public void setStartsWithCapital(int startsWithCapital) {
		this.startsWithCapital = startsWithCapital;
	}
	public int getContainsNumeric() {
		return containsNumeric;
	}
	public void setContainsNumeric(int containsNumeric) {
		this.containsNumeric = containsNumeric;
	}
	public int getContainsDash() {
		return containsDash;
	}
	public void setContainsDash(int containsDash) {
		this.containsDash = containsDash;
	}
	public int getContainsComma() {
		return containsComma;
	}
	public void setContainsComma(int containsComma) {
		this.containsComma = containsComma;
	}
	public int getLengthOfTerm() {
		return lengthOfTerm;
	}
	public void setLengthOfTerm(int lengthOfTerm) {
		this.lengthOfTerm = lengthOfTerm;
	}
	public int getContainsOnlyUpperCase() {
		return containsOnlyUpperCase;
	}
	public void setContainsOnlyUpperCase(int containsOnlyUpperCase) {
		this.containsOnlyUpperCase = containsOnlyUpperCase;
	}
	public int getContainsDot() {
		return containsDot;
	}
	public void setContainsDot(int containsDot) {
		this.containsDot = containsDot;
	}
	public int getStartsWithNumber() {
		return startsWithNumber;
	}
	public void setStartsWithNumber(int startsWithNumber) {
		this.startsWithNumber = startsWithNumber;
	}
	public int getEndsWithNumber() {
		return endsWithNumber;
	}
	public void setEndsWithNumber(int endsWithNumber) {
		this.endsWithNumber = endsWithNumber;
	}
	public int getContainsBracket() {
		return containsBracket;
	}
	public void setContainsBracket(int containsBracket) {
		this.containsBracket = containsBracket;
	}


 
 
	
}
