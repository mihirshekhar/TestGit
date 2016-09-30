package com.cde.cancer.generatefeatures.dataentities;
/**
 * Represents term.
 * @author Mihir Shekhar
 * @date 10/12/2015
 * @emailId mihir.shekhar@research.iiit.ac.in
 * @version 1.0
 */
public class Baseline {
	private String term;
    private int dict[];
    
    

	public Baseline(String term, int[] dict) {
		super();
		this.term = term;
		this.dict = dict;
	}

	@Override
	public String toString() {
		return "|"
				+ dict[0]+"|"+ dict[1]+"|"+dict[2]+"|"+dict[3]+"|"+dict[4]+"|"+dict[5];
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int[] getDict() {
		return dict;
	}

	public void setDict(int[] dict) {
		this.dict = dict;
	}
    
}
