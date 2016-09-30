package com.cde.cancer.generatefeatures.dataentities;
/**
 * Represents UMLS Features.
 * @author Mihir Shekhar
 * @date 10/12/2015
 * @emailId mihir.shekhar@research.iiit.ac.in
 * @version 1.0
 */
public class UMLS {
	private String text;
	private int [] dict;
	private String semanticType;
	public UMLS(String text, int[] dict, String semanticType) {
		super();
		this.text = text;
		this.dict = dict;
		this.semanticType = semanticType;
	}
	public UMLS() {
		super();
		this.text = "";
		int arr[] = {0,0,0,0,0,0};
		this.dict = arr;
		this.semanticType = "";
	}
	
	@Override
	public String toString() {
		return "|" + text + "|" + semanticType+ "|"
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
	public String getSemanticType() {
		return semanticType;
	}
	public void setSemanticType(String semanticType) {
		this.semanticType = semanticType;
	}
	
	
    	
}
