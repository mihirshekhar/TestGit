package com.cde.cancer.generatefeatures.dataentities;
/**
 * Represents given label of medical term.
 * @author Mihir Shekhar
 * @date 10/12/2015
 * @emailId mihir.shekhar@research.iiit.ac.in
 * @version 1.0
 */
public class Label {
	private boolean isCancer;
	private int cancerSubtype;
	public Label(boolean isCancer, int cancerSubtype) {
		super();
		this.isCancer = isCancer;
		this.cancerSubtype = cancerSubtype;
	}
	
	@Override
	public String toString() {
		return   "|"+ cancerSubtype ;
	}

	public boolean isCancer() {
		return isCancer;
	}
	public void setCancer(boolean isCancer) {
		this.isCancer = isCancer;
	}
	public int getCancerSubtype() {
		return cancerSubtype;
	}
	public void setCancerSubtype(int cancerSubtype) {
		this.cancerSubtype = cancerSubtype;
	}
	
	
}
