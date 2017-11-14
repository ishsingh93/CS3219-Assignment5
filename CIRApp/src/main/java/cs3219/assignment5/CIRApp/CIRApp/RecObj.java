package cs3219.assignment5.CIRApp.CIRApp;

import java.util.Comparator;

public class RecObj {
	
	private int docYr;
	private String docID;
	private int yrDiff;
	
	public RecObj() {
		
	}

	public int getDocYr() {
		return docYr;
	}

	public void setDocYr(int docYr) {
		this.docYr = docYr;
	}

	public String getDocID() {
		return docID;
	}

	public void setDocID(String docID) {
		this.docID = docID;
	}
	
	public int getYrDiff() {
		return yrDiff;
	}

	public void setYrDiff(int yrDiff) {
		this.yrDiff = yrDiff;
	}
	
	public static Comparator<RecObj> yearDiff = new Comparator<RecObj>() {

		public int compare(RecObj ro1, RecObj ro2) {

			int roYrDiff1 = ro1.getYrDiff();
			int roYrDiff2 = ro2.getYrDiff();

			/* For ascending order */
			return roYrDiff1 - roYrDiff2;
		}
	};
}
