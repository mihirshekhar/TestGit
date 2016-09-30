package com.cde.pruneVocab.infogain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Main Driver class for calculating gain value and sort attributes in descending order of gain value
 * @author mihirshekhar
 *
 */
public class CalculateGainValue {
	ArrayList<Attribute>attr_list;
	double pEntropy;
	public CalculateGainValue(String libSVMFile) throws IOException {
		super();
		ReadLibSVMFile rlsf = new ReadLibSVMFile(libSVMFile);
		 rlsf.extractClassDistribution();
		 attr_list = rlsf.getAttr_list();
		 pEntropy = getEntropy(attr_list.get(0));
		 System.out.println(pEntropy+"  a");
	}
	public CalculateGainValue(String libSVMFile,int maxLineToRead) throws IOException {
		super();
		ReadLibSVMFile rlsf = new ReadLibSVMFile(libSVMFile,maxLineToRead);
		 rlsf.extractClassDistribution();
		 attr_list = rlsf.getAttr_list();
		 pEntropy = getEntropy(attr_list.get(0));
		 System.out.println(pEntropy+"  a");
	}
	public void CalculateGainRatio()
	{
		for(int i=0;i<attr_list.size();i++)
		{
			Attribute atr = attr_list.get(i);
			atr.setGain_ratio(pEntropy);
		}
		Collections.sort(attr_list,new CompareUsingGainRatio());
	//	for(int i=0;i<attr_list.size();i++)
    //			System.out.println(attr_list.get(i).getGain_ratio()+" "+attr_list.get(i).getId());
	}
	
	
	private double getEntropy(Attribute attribute) {
		int [] null_class = attribute.getNull_classDist();
		
		int [] notNull_class = attribute.getNotNull_classDist();
		int [] class_total = new int [null_class.length];
		for(int i=0;i<class_total.length;i++)
		{
			class_total[i] = null_class[i]+notNull_class[i];
		}
	//	for(int i=0;i<class_total.length;i++)System.out.println(class_total[i]);
       pEntropy = Entropy.calEntropy(class_total);
       return pEntropy;
	}
	public ArrayList<Attribute> getAttr_list() {
		return attr_list;
	}
	public void setAttr_list(ArrayList<Attribute> attr_list) {
		this.attr_list = attr_list;
	}
	public double getpEntropy() {
		return pEntropy;
	}
	public void setpEntropy(double pEntropy) {
		this.pEntropy = pEntropy;
	}
	
	
	
	
	
	
	

}
