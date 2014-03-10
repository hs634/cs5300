package edu.cornell.cs5300s14.project1a.model;

import java.io.Serializable;
import java.util.ArrayList;

public class LocationMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1098101274909516335L;
	
	private ArrayList<String> wqaddresses;

	public ArrayList<String> getWqaddresses() {
		return wqaddresses;
	}

	public void setWqaddresses(ArrayList<String> wqaddresses) {
		this.wqaddresses = wqaddresses;
	}
	
	public String toString(){
		StringBuilder lmStr = new StringBuilder();
		for(String st: this.wqaddresses){
			lmStr.append(st);
			lmStr.append(";");
		}
		return lmStr.toString();
	}
	

}
