package cs3219.assignment5.CIRApp.CIRApp;

public class AuthorObj {
	
	private int year;
	private String confName;
	private String authorName;
	
	public AuthorObj(int year, String confName, String authorName) {
		setYear(year);
		setConfName(confName);
		setAuthorName(authorName);
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

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
}