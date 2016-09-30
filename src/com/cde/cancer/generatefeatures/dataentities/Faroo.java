package com.cde.cancer.generatefeatures.dataentities;

import java.util.Arrays;

/**
 * Represents faroo description 
 * @author Mihir Shekhar
 * @date 10/12/2015
 * @emailId mihir.shekhar@research.iiit.ac.in
 * @version 1.0
 */
public class Faroo {
	private String text;
	private int [] dict;
	public Faroo(String text, int[] dict) {
		super();
		this.text = text;
		this.dict = dict;
	}
	
	public Faroo() {
		super();
		this.text ="";
		int arr[] = {0,0,0,0,0,0};
		this.dict =arr ;
	}
	
	@Override
	public String toString() {
		return "|" + text + "|"
				+ dict[0]+"|"+ dict[1]+"|"+dict[2]+"|"+dict[3]+"|"+dict[4]+"|"+dict[5];
	}



	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int[] getDict() {
		return dict;
	}
	public void setDict(int[] dict) {
		this.dict = dict;
	}
    
	
}
