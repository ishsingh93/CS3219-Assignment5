package cs3219.assignment5.CIRApp.CIRApp;

import java.util.Comparator;

import assignment3_CIR.app.AuthorObj;

public class CitTrendObj {

	private int year;
	private int numCitations;
	private String conference;
	
	public CitTrendObj (int yr, int cit, String confName) {
		setYear(yr);
		setNumCitations(cit);
		setConference(confName);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getNumCitations() {
		return numCitations;
	}

	public void setNumCitations(int numCitations) {
		this.numCitations = numCitations;
	}
	
	public String getConference() {
		return conference;
	}
	
	public void setConference(String conf) {
		this.conference = conf;
	}
	
    public static Comparator<CitTrendObj> yearComparator = new Comparator<CitTrendObj>() {

	public int compare(CitTrendObj ct1, CitTrendObj ct2) {

	   int ctYear1 = ct1.getYear();
	   int ctYear2 = ct2.getYear();

	   /*For ascending order*/
	   return ctYear1 - ctYear2;
   }};
}