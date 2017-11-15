package cs3219.assignment5.CIRApp.CIRApp;

public class AuthorTrendObject {
	
	private int year;
	private String confName;
	private int authorCount;
	private int ArXiv;
	private int ICSE;
	
	public AuthorTrendObject() {
		
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getConfName() {
		return confName;
	}

	public void setConfName(String confName) {
		this.confName = confName;
	}

	public int getAuthorCount() {
		return authorCount;
	}

	public void setAuthorCount(int authorCount) {
		if (this.getConfName().equalsIgnoreCase("ArXiv")) {
			setArXiv(authorCount);
		} else if (this.getConfName().equalsIgnoreCase("ICSE")){
			setICSE(authorCount);
		}
		this.authorCount = authorCount;
	}

	public int getArXiv() {
		return ArXiv;
	}

	public void setArXiv(int arXiv) {
		ArXiv = arXiv;
	}

	public int getICSE() {
		return ICSE;
	}

	public void setICSE(int iCSE) {
		ICSE = iCSE;
	}
}