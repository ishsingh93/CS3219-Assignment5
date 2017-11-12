package cs3219.assignment5.CIRApp.CIRApp;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {

	private static final String AUTHORS = "authors=";
	private static final String AUTHOR = "author=";
	private static final String COMMA = ",";
	private static final String DATASET = "dataset=";
	private static final String DATASETS = "datasets=";
	private static final String YEAR = "year=";
	private static final String YEARS = "years=";
	private static final String CONFERENCE = "conference";
	private static final String CONFERENCES = "conferences";
	private static final String VENUE = "venue=";
	private static final String ALL = "all";
	private static final String DASH = "-";
	private static final String GIVE = "give";
	private static final String IN = "in";
	private static final String FOR = "for";
	private static final String TOP = "top";

	Input inputObj;

	public Parser() {
		inputObj = new Input();
	}

	public void parseInput(String input, String dataLoc) throws IOException {
		String[] inputArr = input.split(" ");
		// printArr(inputArr);
		parseQuery(inputArr);
		inputObj.setDataLocation(dataLoc);
		InputHandler handler = new InputHandler(inputObj);
	}

	private void parseQuery(String[] inputArr) {
		if (inputArr[0].equalsIgnoreCase(FOR)) {
			extractConferencesFOR(inputArr);
			extractQueryTypeFOR(inputArr);
			extractYearsFOR(inputArr);
		}

		if (inputArr[0].equalsIgnoreCase(TOP)) {
			extractNumber(inputArr);
			extractSearchContent(inputArr);
			extractSearchLoc(inputArr);
		}

	}

	private void extractNumber(String[] inputArr) {
		int num = Integer.parseInt(inputArr[1]);
		inputObj.setNum(num);
		System.out.println("Number is: " + num);
	}

	private void extractSearchLoc(String[] inputArr) {
		String loc = inputArr[inputArr.length - 1];
		for (int i = inputArr.length - 2; i > 3; i--) {
			if (inputArr[i].equalsIgnoreCase(FOR)) {
				break;
			} else {
				loc = inputArr[i] + " " + loc;
			}
		}
		inputObj.setDataLocation(loc);
		System.out.println("Query location is: " + loc);
	}

	private void extractSearchContent(String[] inputArr) {
		String content = inputArr[2];
		for (int i = 3; i < inputArr.length; i++) {
			if (inputArr[i].equalsIgnoreCase(FOR)) {
				break;
			} else {
				content = content + " " + inputArr[i];
			}
		}
		inputObj.setQueryType(content);
		System.out.println("Query type is: " + content);
	}

	private void extractQueryTypeFOR(String[] inputArr) {
		int startIndex = 0;
		int endIndex = 0;

		for (int i = 0; i < inputArr.length; i++) {
			if (inputArr[i].equalsIgnoreCase(GIVE)) {
				startIndex = i + 1;
			}
			if (inputArr[i].equalsIgnoreCase(IN)) {
				endIndex = i;
			}
		}

		String queryType = inputArr[startIndex];

		if (endIndex - startIndex > 1) {
			for (int j = startIndex + 1; j <= endIndex - 1; j++) {
				queryType = queryType + " " + inputArr[j];
			}
			inputObj.setQueryType(queryType.trim());
			System.out.println("Query type is: " + queryType);
		} else {
			inputObj.setQueryType(queryType);
			System.out.println("Query type is: " + queryType);
		}

	}

	private void extractConferencesFOR(String[] locArr) {
		ArrayList<String> confList = new ArrayList<String>();
		for (int i = 0; i < locArr.length; i++) {
			if (locArr[i].equalsIgnoreCase(CONFERENCE) || locArr[i].equalsIgnoreCase(CONFERENCES)) {
				for (int j = i + 1; j < locArr.length; j++) {
					if (isConference(locArr[j])) {
						String confName = removeCommas(locArr[j]);
						confList.add(confName);
					}
				}
			}
		}
		System.out.println("This is the confList: " + confList);
		inputObj.setConferences(confList);
	}

	private String removeCommas(String string) {
		if (string.length() == 4) {
			if (string.charAt(3) == ',') {
				return string.substring(0, 3);
			} else {
				return string;
			}
		} else {
			return string;
		}
	}

	private boolean isConference(String string) {
		boolean isConf = false;
		if (string.length() == 4) {
			if (string.charAt(3) == ',') {
				// String confName = string.substring(0, 3);
				isConf = true;
				// System.out.println(confName + " is a conference name");
			} else {
				isConf = false;
			}
		} else if (string.length() == 3) {
			if (Character.isLetter(string.charAt(0)) && Character.isDigit(string.charAt(1))
					&& Character.isDigit(string.charAt(2))) {
				// System.out.println(string + " is a conference name");
				isConf = true;
			}
		} else {
			isConf = false;
		}
		return isConf;
	}

	public void extractYearsFOR(String[] inputArr) {
		ArrayList<Integer> numList = new ArrayList<Integer>();
		for (int i = 0; i < inputArr.length; i++) {
			if (inputArr[i].equalsIgnoreCase(YEAR) || inputArr[i].equalsIgnoreCase(YEARS)) {
				if (i + 1 == inputArr.length) {
					int yr = Integer.valueOf(inputArr[i + 1]);
					numList.add(Integer.valueOf(yr));
				} else {
					String[] yrRange = inputArr[i + 1].split(DASH);
					int startYr = Integer.valueOf(yrRange[0]);
					int endYr = Integer.valueOf(yrRange[1]);
					// int numYrs = endYr - startYr;
					for (int j = startYr; j <= endYr; j++) {
						numList.add(Integer.valueOf(j));
					}
				}
			}
		}
		inputObj.setNumYrs(numList);
	}

	private void extractAuthors(String[] locArr) {
		ArrayList<String> authors = new ArrayList<String>();
		for (String i : locArr) {
			if (!i.equalsIgnoreCase(AUTHORS) && !i.equalsIgnoreCase(COMMA) && !i.equalsIgnoreCase(AUTHOR)) {
				authors.add(i);
			}
		}
		printArrList(authors);
		inputObj.setAuthors(authors);
	}

	private void parseCommand(String string) {
		inputObj.setQueryCommand(string);
	}

	private void printArr(String[] inputArr) {
		for (String i : inputArr) {
			System.out.println(i);
		}
	}

	private void printArrList(ArrayList<String> authors) {
		for (String i : authors) {
			System.out.println(i);
		}
	}

	private String concatLocationParameter(String[] inputArr) {
		String locPar = inputArr[3];
		if (inputArr.length > 4) {
			for (int i = 4; i < inputArr.length; i++) {
				locPar = locPar + " " + inputArr[i];
			}
			return locPar;
		} else {
			return locPar;
		}

	}
}
