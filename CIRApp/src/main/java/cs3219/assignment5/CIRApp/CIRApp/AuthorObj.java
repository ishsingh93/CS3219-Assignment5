package cs3219.assignment5.CIRApp.CIRApp;

import java.util.Comparator;


public class AuthorObj {

	private int year;
	private String confName;
	private String authorName;
	private int count;

	public AuthorObj() {
		
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public static Comparator<AuthorObj> authorCount = new Comparator<AuthorObj>() {

		public int compare(AuthorObj au1, AuthorObj au2) {

			int auCount1 = au1.getCount();
			int auCount2 = au2.getCount();

			/* For descending order */
			return auCount2 - auCount1;
		}
	};
}