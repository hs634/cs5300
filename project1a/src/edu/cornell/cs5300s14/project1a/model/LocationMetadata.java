package edu.cornell.cs5300s14.project1a.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author harsh
 * Class to represent Location Metadeta
 */
public class LocationMetadata implements Serializable {

	/**
	 * This class is a represention for storing location metadata
	 */
	private static final long serialVersionUID = 1098101274909516335L;
	
	// arraylist of string
	private ArrayList<String> wqaddresses;

	public ArrayList<String> getWqaddresses() {
		return wqaddresses;
	}

	public void setWqaddresses(ArrayList<String> wqaddresses) {
		this.wqaddresses = wqaddresses;
	}
	
	//toString implementation to generate string representation of object
	public String toString(){
		StringBuilder lmStr = new StringBuilder();
		for(String st: this.wqaddresses){
			lmStr.append(st);
			lmStr.append(";");
		}
		return lmStr.toString();
	}
	

}
