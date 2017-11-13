package cs3219.assignment5.CIRApp.CIRApp;

public class AuthorTrendObj {

	private String authorName;
	private int year;
	
	public AuthorTrendObj(String name, int yr) {
		setAuthorName(name);
		setYear(yr);
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
