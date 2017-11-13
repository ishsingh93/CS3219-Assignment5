package cs3219.assignment5.CIRApp.CIRApp;

public class AuthorTrendObject {
	
	private int year;
	private String confName;
	private int authorCount;
	
	public AuthorTrendObject(int year, String confName, int authorCount) {
		setYear(year);
		setConfName(confName);
		setAuthorCount(authorCount);
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
		this.authorCount = authorCount;
	}
}