package com.cde.pruneVocab.infogain;


import java.util.Comparator;


public class CompareUsingGainRatio implements Comparator<Attribute> {
	@Override
	public int compare(Attribute p1, Attribute p2) {
		   
		   if(p1.getGain_ratio()>p2.getGain_ratio())
		   {
			   return -1;
		   }
		   else if(p1.getGain_ratio()<p2.getGain_ratio())
		   return 1;
		   else
			   return 0;
	}

}