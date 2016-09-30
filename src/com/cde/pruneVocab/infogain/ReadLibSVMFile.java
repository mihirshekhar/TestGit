package com.cde.pruneVocab.infogain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * Reads an libsvm file and returns a arraylist of attribute types
 * @author mihirshekhar
 *
 */
public class ReadLibSVMFile {
	private String libSVMFile;
	int noOfClasses;
	int noOfData;
	int maxDimension;
	ArrayList<Attribute>attr_list;
	TreeMap<String,Integer>classVal;
	int maxLineToRead;
	public ReadLibSVMFile(String libSVMFile) throws IOException {
		super();
		this.libSVMFile = libSVMFile;
		noOfData = 0;
		noOfClasses = 0;
		maxDimension = 0;
		maxLineToRead = Integer.MAX_VALUE;
		setFileSpecificVals();
		System.out.println(noOfData+"  "+noOfClasses+"  "+maxDimension);
		if(noOfData<1 || noOfClasses<1)
		{
			System.out.println("File does not contain data row or there is only one class or File is not in proper libsvm format ");
			System.exit(0);
		}
		
		initialiseAttrList();
	    System.out.println("File specific parameters set");
	    
	}
	
	
	
	public ReadLibSVMFile(String libSVMFile, int maxLineToRead) throws IOException {
		super();
		this.libSVMFile = libSVMFile;
		this.maxLineToRead = maxLineToRead;
		noOfData = 0;
		noOfClasses = 0;
		maxDimension = 0;
		maxLineToRead = Integer.MAX_VALUE;
		setFileSpecificVals();
		System.out.println(noOfData+"  "+noOfClasses+"  "+maxDimension);
		if(noOfData<1 || noOfClasses<1)
		{
			System.out.println("File does not contain data row or there is only one class or File is not in proper libsvm format ");
			System.exit(0);
		}
		
		initialiseAttrList();
	    System.out.println("File specific parameters set");
	}



	private void initialiseAttrList() {
        attr_list = new ArrayList<Attribute>();
        int id=0;
		for(int i=0;i<maxDimension;i++)
		{
			Attribute atb = new Attribute(++id,noOfClasses);
			attr_list.add(atb);
		}
	}

	
	public void extractClassDistribution() throws IOException
	{
		System.out.println("Extracting class Distribution of File: "+libSVMFile);
		BufferedReader br = new BufferedReader(new FileReader(libSVMFile));
		String line="";
		int t=0;
		while((line=br.readLine())!=null && t<=maxLineToRead)
		{			
			++t;
			String arr[] =  line.split("\\s+");
			int cval = classVal.get(arr[0]);
			HashSet<Integer>set = new  HashSet<Integer>();
			for(int i=1;i<arr.length;i++)
			{
				String arr2 [] = arr[i].split(":");
				set.add(Integer.parseInt(arr2[0]));
			}
			
			for(int i=0;i<attr_list.size();i++)
			{
				if(!set.contains(i+1))
				{
					Attribute atb = attr_list.get(i);
					atb.incNull_classDist(cval);
				}
			}
		    
			for(Integer dim : set)
			{
				Attribute atb = attr_list.get(dim-1);
				atb.incNotNull_classDist(cval);
			}
			if(t%10000==0)
			{
				System.out.println(t);
			}
			
		}
		br.close();
		
//		for(int i=0;i<attr_list.size();i++)
//		{
//			System.out.println(attr_list.get(i).toString());
//		}
	}
	/**
	 * Computes no of classes in file
	 * @throws IOException
	 */
	private void setFileSpecificVals() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(libSVMFile));
		String line="";
		int classId = -1;
		classVal = new TreeMap<String,Integer>();
		
		while((line=br.readLine())!=null )
		{
			String arr[] =  line.split("\\s+");
			if(!classVal.containsKey(arr[0]))
			{
			classVal.put(arr[0],++classId);
			}
			noOfData++;
			for(int i=1;i<arr.length;i++)
			{
				String arr2[] = arr[i].split(":");
				int tempMaxDim = Integer.parseInt(arr2[0]);
				if(tempMaxDim>maxDimension)
				{
					maxDimension = tempMaxDim;
					
				}
			}
		}
		br.close();
		noOfClasses = classVal.size();
	}
	public int getNoOfClasses() {
		return noOfClasses;
	}
	public void setNoOfClasses(int noOfClasses) {
		this.noOfClasses = noOfClasses;
	}
	public int getNoOfData() {
		return noOfData;
	}
	public void setNoOfData(int noOfData) {
		this.noOfData = noOfData;
	}
	public int getMaxDimension() {
		return maxDimension;
	}
	public void setMaxDimension(int maxDimension) {
		this.maxDimension = maxDimension;
	}
	public ArrayList<Attribute> getAttr_list() {
		return attr_list;
	}
	public void setAttr_list(ArrayList<Attribute> attr_list) {
		this.attr_list = attr_list;
	}
	public TreeMap<String, Integer> getClassVal() {
		return classVal;
	}
	public void setClassVal(TreeMap<String, Integer> classVal) {
		this.classVal = classVal;
	}
	
	
	
	

}
