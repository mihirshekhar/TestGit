package com.cde.pruneVocab.infogain;

public class Attribute {

	
	int id;
    int[]notNull_classDist;
    int[]null_classDist;
    double gain_ratio;
    public Attribute(int id, int noOfClasses) {
     this.id = id;
     notNull_classDist = new int [noOfClasses];
     null_classDist = new int[noOfClasses];
    }
    
    public String toString()
    {
    	StringBuilder str =new StringBuilder();
    	str.append(id+"\t");
    	for(int i=0;i<null_classDist.length;i++)
    	{
    		str.append(" ");
    		str.append(null_classDist[i]);
    	}
    	str.append("\t");
    	for(int i=0;i<notNull_classDist.length;i++)
    	{
    		str.append(" ");
    		str.append(notNull_classDist[i]);
    	}
    	return str.toString();
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int[] getNotNull_classDist() {
		return notNull_classDist;
	}
	public void setNotNull_classDist(int[] notNull_classDist) {
		this.notNull_classDist = notNull_classDist;
	}
	public void incNotNull_classDist(int classIndex) {
		notNull_classDist[classIndex]+=1;
	}
	public int[] getNull_classDist() {
		return null_classDist;
	}
	public void setNull_classDist(int[] null_classDist) {
		this.null_classDist = null_classDist;
	}
	public void incNull_classDist(int classIndex) {
		null_classDist[classIndex]+=1;
	}
	public double getGain_ratio() {
		return gain_ratio;
	}
	public void setGain_ratio(double entropy) {
		
		double null_sum = Entropy.sum(null_classDist);
		double not_null_sum = Entropy.sum(notNull_classDist);
		double total = null_sum+not_null_sum;
		double nullSubEntropy = (null_sum/total)*Entropy.calEntropy(null_classDist);
		double notnullsubentropy = (not_null_sum/total)*Entropy.calEntropy(notNull_classDist);
		
	    this.gain_ratio =  entropy-notnullsubentropy -  nullSubEntropy;
     
	}
    
   
    
}
