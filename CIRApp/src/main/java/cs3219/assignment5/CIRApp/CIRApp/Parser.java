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

	Input inputObj;

	public Parser() {
		inputObj = new Input();
	}

	public void parseInput(String input, String dataLoc) throws IOException {
		String[] inputArr = input.split(" ");
		printArr(inputArr);
		// parseDataLocation(dataLoc);
		// parseCommand(inputArr[0]);
		// parseQueryType(inputArr);
		// parseLocation(inputArr);
		parseQuery(inputArr);
		// InputHandler handler = new InputHandler(inputObj);
	}

	private void parseQuery(String[] inputArr) {
		extractConferences(inputArr);

	}

	private void parseDataLocation(String dataLoc) {
		inputObj.setDataLocation(dataLoc);
	}

	private void parseLocation(String[] inputArr) {
		String location = concatLocationParameter(inputArr);
		String[] locArr = location.split("\"");
		// System.out.println(location);
		switch (locArr[0]) {
		case AUTHOR:
			extractAuthors(locArr);
			break;
		case AUTHORS:
			extractAuthors(locArr);
			break;
		case YEAR:
			extractYears(locArr);
			break;
		case YEARS:
			extractYears(locArr);
			break;
		case CONFERENCE:
			extractConferences(locArr);
			break;
		case CONFERENCES:
			extractConferences(locArr);
			break;
		case ALL:
			break;
		case VENUE:
			extractVenue(locArr);
			break;
		default:
			break;
		}

	}

	private void extractVenue(String[] locArr) {
		inputObj.setVenue(locArr[1]);
	}

	private void extractConferences(String[] locArr) {
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
				String confName = string.substring(0, 3);
				isConf = true;
				System.out.println(confName + " is a conference name");
			} else {
				isConf = false;
			}
		} else if (string.length() == 3) {
			if (Character.isLetter(string.charAt(0)) && Character.isDigit(string.charAt(1))
					&& Character.isDigit(string.charAt(2))) {
				System.out.println(string + " is a conference name");
				isConf = true;
			}
		} else {
			isConf = false;
		}
		return isConf;
	}

	public void extractYears(String[] locArr) {
		ArrayList<Integer> numList = new ArrayList<Integer>();
		if (locArr.length == 2) {
			String[] yrRange = locArr[1].split(DASH);
			if (yrRange.length > 0) {
				int startYr = Integer.valueOf(yrRange[0]);
				int endYr = Integer.valueOf(yrRange[1]);
				// int numYrs = endYr - startYr;
				for (int i = startYr; i <= endYr; i++) {
					numList.add(Integer.valueOf(i));
				}
			} else {
				numList.add(Integer.parseInt(locArr[1]));
			}

		} else {
			for (String i : locArr) {
				if (!i.equalsIgnoreCase(YEAR) && !i.equalsIgnoreCase(YEARS) && !i.equalsIgnoreCase(DASH)) {
					numList.add(Integer.parseInt(i));
				}
			}
		}
		System.out.println("the list of years is: ");
		for (Integer i : numList) {
			System.out.println(i);
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

	private void parseQueryType(String[] inputArr) {
		String fullQuery = inputArr[1];
		for (int i = 2; i < inputArr.length; i++) {
			if (inputArr[i].equals("where")) {
				fullQuery.trim();
				break;
			} else {
				// fullQuery.concat(inputArr[i]);
				fullQuery = fullQuery + " " + inputArr[i];
			}
		}

		inputObj.setQueryType(fullQuery);

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
