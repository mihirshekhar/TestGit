package com.cde.cancer.generatefeatures.dataentities;
/**
 * Represents wiki Features.
 * @author Mihir Shekhar
 * @date 10/12/2015
 * @emailId mihir.shekhar@research.iiit.ac.in
 * @version 1.0
 */
import java.util.Arrays;


public class Wiki {
	private String text;
	private String category;
	private int [] dict;
	public Wiki(String text, String category, int[] dict) {
		super();
		this.text = text;
		this.category = category;
		this.dict = dict;
	}
	
	@Override
	public String toString() {
		return "|" + text + "|" + category + "|"
				+ dict[0]+"|"+ dict[1]+"|"+dict[2]+"|"+dict[3]+"|"+dict[4]+"|"+dict[5];
	}





	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int[] getDict() {
		return dict;
	}
	public void setDict(int[] dict) {
		this.dict = dict;
	}
	
        
}
