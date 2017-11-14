package cs3219.assignment5.CIRApp.CIRApp;

import java.util.Comparator;

public class CitedDocObj {

	private int citedYear;
	private String confName;
	private String docName;
	private int citedCount;

	public CitedDocObj() {
		// TODO Auto-generated constructor stub
	}

	public int getCitedYear() {
		return citedYear;
	}

	public void setCitedYear(int citedYr) {
		this.citedYear = citedYr;
	}

	public String getConfName() {
		return confName;
	}

	public void setConfName(String conference) {
		this.confName = conference;
	}

	public int getCitedCount() {
		return citedCount;
	}

	public void setCitedCount(int count) {
		this.citedCount = count;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String name) {
		this.docName = name;
	}

	public static Comparator<CitedDocObj> topDocCounter = new Comparator<CitedDocObj>() {

		public int compare(CitedDocObj cd1, CitedDocObj cd2) {

			int cdCount1 = cd1.getCitedCount();
			int cdCount2 = cd2.getCitedCount();

			/* For descending order */
			return cdCount2 - cdCount1;
		}
	};

}
