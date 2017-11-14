package cs3219.assignment5.CIRApp.CIRApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class DataHandler {

	private Input inputObj;
	private static ArrayList<JSONObject> dataset = new ArrayList<JSONObject>();
	private static Output output = new Output();
	public static String TEST_XML_STRING = null;
	public static int PRETTY_PRINT_INDENT_FACTOR = 4;

	public DataHandler(Input input) throws IOException {
		this.setInputObj(input);
		parseJSONFileIntoObjArrList();
		execute();
	}

	private void parseJSONFileIntoObjArrList() throws IOException {
		File f = new File(inputObj.getDataLocation());
		if (f.exists()) {
			InputStream is = new FileInputStream(inputObj.getDataLocation());
			ArrayList<String> jsonTxt = (ArrayList<String>) IOUtils.readLines(is, "UTF-8");
			for (String i : jsonTxt) {
				JSONObject jo = new JSONObject(i);
				dataset.add(jo);
			}
		}
		System.out.println("Number of JSONObjects in dataset is: " + dataset.size());
	}

	private void execute() throws IOException {

		String queryType = inputObj.getQueryType();
		String queryCommand = inputObj.getQueryCommand();
		String venue = inputObj.getVenue();
		String[] query = queryType.split(" ");

		switch (queryType) {
		case "cited documents": // cited documents trend
			if (queryCommand.equalsIgnoreCase("for")) {
				getCitedDocumentsFOR();
			} else {
				getTOP(queryType);
			}
			System.out.println("cited document trend executed");
			break;
		case "authors": // author trend
			if (queryCommand.equalsIgnoreCase("for")) {
				getAuthorsFOR();
			} else {
				getTOP(queryType);
			}
			System.out.println("author trend executed");
			break;
		case "citations": // citations trend

			System.out.println("citation trend executed");
			break;
		case "top": // top (no.) Y of X

			System.out.println("top trend executed");
			break;
		case "network": // citation network

			System.out.println("map citation executed");
			break;
		default:
			break;
		}

	}

	private void getCitedDocumentsFOR() {
		ArrayList<CitTrendObj> citations = new ArrayList<CitTrendObj>();
		ArrayList<Integer> numYrs = inputObj.getNumYrs();
		ArrayList<String> confNames = inputObj.getConferences();
		
		for (String confName : confNames) {
			for (JSONObject jo : dataset) {
				if (jo.getString("venue").contains(confName) && jo.has("year")) {
					extractCitedDocuments(numYrs, jo, confName, citations);
				}
			}
		}
		
		System.out.println("These are the number of citations per year " + "(" + citations.size() + ")");
		for (CitTrendObj citOb : citations) {
			System.out.println(citOb.getConference() + ", " + citOb.getYear() + ", " + citOb.getNumCitations());
		}
	}

	private void extractCitedDocuments(ArrayList<Integer> numYrs, JSONObject jo, String confName, ArrayList<CitTrendObj> citations) {
		int jsonYr = jo.getInt("year");
		int yrRange = numYrs.size();
		int numCitations = jo.getJSONArray("inCitations").length();
		if (numYrs.get(0) < jsonYr && jsonYr < numYrs.get(yrRange - 1)) { //within the range specified by the user
			if (citations.size() == 0) { //add element into new array to avoid IndexOutOfBoundException
				citations.add(new CitTrendObj(jsonYr, numCitations, confName));
			}
			else {
				for (int j=0; j< citations.size(); j++) {
					if (citations.get(j).getYear() == jsonYr && citations.get(j).getConference().contains(confName)) {
						citations.get(j).setNumCitations(citations.get(j).getNumCitations() + numCitations);
						break;
					} 
					
					if (j == citations.size() - 1 && numCitations != 0) {
						CitTrendObj citationYear = new CitTrendObj(jsonYr, numCitations, confName);
						citations.add(citationYear);									
						break;
					}
				}
			}
		}
	}

	private void getTOP(String searchTerm) {
		String[] searchLocArr = inputObj.getSearchLoc().split(" ");
		
		if (searchTerm.equalsIgnoreCase("authors")) {
			switch (searchLocArr[0]) {
			case "conference":
				ArrayList<AuthorObj> topAuthorsConf = topAuthorsFromConf(searchLocArr);
				System.out.println("Top authors for conference : ");
				for (int topAuthorsSize = 0; topAuthorsSize < inputObj.getNum(); topAuthorsSize++) {
					AuthorObj topAuthor = topAuthorsConf.get(topAuthorsSize);
					int order = topAuthorsSize + 1;
					System.out.println(order + ". " + topAuthor.getAuthorName() + " - " + topAuthor.getCount() + " times. ");
				}
				break;
			case "year":
				ArrayList<AuthorObj> topAuthorsYr = topAuthorsFromYr(searchLocArr);
				System.out.println("Top authors for year : ");
				for (int topAuthorsSize = 0; topAuthorsSize < inputObj.getNum(); topAuthorsSize++) {
					AuthorObj topAuthor = topAuthorsYr.get(topAuthorsSize);
					int order = topAuthorsSize + 1;
					System.out.println(order + ". " + topAuthor.getAuthorName() + " - " + topAuthor.getCount() + " times. ");
				}
				break;
			case "years":
				
				break;
			default:
				break;
			}
		}
		
		else if (searchTerm.equalsIgnoreCase("cited documents")) {
			switch (searchLocArr[0]) {
			case "conference":
				ArrayList<CitedDocObj> topCitedDocConf = topCitedDocFromConf(searchLocArr);
				System.out.println("Top cited documents for conference : ");
				for (int topCitedDocSize = 0; topCitedDocSize < inputObj.getNum(); topCitedDocSize++) {
					CitedDocObj topCitedDoc = topCitedDocConf.get(topCitedDocSize);
					int order = topCitedDocSize + 1;
					System.out.println(order + ". " + topCitedDoc.getDocName() + " - " + topCitedDoc.getCitedCount() + " times. ");
				}
				break;
			case "year":
				ArrayList<CitedDocObj> topCitedDocYr = topCitedDocFromYr(searchLocArr);
				System.out.println("Top cited documents for year : ");
				for (int topCitedDocSize = 0; topCitedDocSize < inputObj.getNum(); topCitedDocSize++) {
					CitedDocObj topCitedDoc = topCitedDocYr.get(topCitedDocSize);
					int order = topCitedDocSize + 1;
					System.out.println(order + ". " + topCitedDoc.getDocName() + " - " + topCitedDoc.getCitedCount() + " times. ");
				}
				break;
			case "years":
				
				break;
			default:
				break;
			}
		}
	}
		
	private ArrayList<CitedDocObj> topCitedDocFromYr(String[] searchLocArr) {
		String yr = searchLocArr[1];
		int specifiedYear = Integer.valueOf(yr);
		ArrayList<CitedDocObj> citedArr = new ArrayList<CitedDocObj>();
		for (JSONObject jo : dataset) {
			if (jo.has("year")) {
				if (jo.getInt("year") == specifiedYear) {
					String paperTitle = jo.getString("title");
					JSONArray inCitedArr = jo.getJSONArray("inCitations");
					int inCitedCount = inCitedArr.length();
					CitedDocObj citedDoc = new CitedDocObj();
					citedDoc.setCitedCount(inCitedCount);
					citedDoc.setDocName(paperTitle);
					//citedDoc.setCitedYr(specifiedYear);
					citedArr.add(citedDoc);
				}
			}
			sortCitedDocs(citedArr);
		}
		return citedArr;
	}

	private ArrayList<CitedDocObj> topCitedDocFromConf(String[] searchLocArr) {
		String conf = searchLocArr[1];
		ArrayList<CitedDocObj> citedArr = new ArrayList<CitedDocObj>();
		for (JSONObject jo : dataset) {
			if (jo.getString("venue").equalsIgnoreCase(conf)) {
				String paperTitle = jo.getString("title");
				JSONArray inCitedArr = jo.getJSONArray("inCitations");
				int inCitedCount = inCitedArr.length();
				CitedDocObj citedDoc = new CitedDocObj();
				citedDoc.setCitedCount(inCitedCount);
				citedDoc.setDocName(paperTitle);
				//citedDoc.setConfName(conf);
				citedArr.add(citedDoc);
			}
			sortCitedDocs(citedArr);
		}
		return citedArr;
	}

	private void sortCitedDocs(ArrayList<CitedDocObj> citedArr) {
		Collections.sort(citedArr, CitedDocObj.topDocCounter);
	}

	private ArrayList<AuthorObj> topAuthorsFromYr(String[] searchLocArr) {
		String yearStr = searchLocArr[1];
		int yearInt = Integer.parseInt(yearStr);
		ArrayList<AuthorObj> aoArr = new ArrayList<AuthorObj>();
		for (JSONObject jo : dataset) {
			if (jo.has("year") && jo.getInt("year") == yearInt) {
				JSONArray authorArr = jo.getJSONArray("authors");
				for (int j = 0; j < authorArr.length(); j++) {
					JSONObject author = authorArr.getJSONObject(j);
					String authorName = author.getString("name");

					if (aoArrHasAuthor(authorName, aoArr)) {
						incrementAuthorCount(authorName, aoArr);
					} else {
						AuthorObj newAuthor = new AuthorObj();
						newAuthor.setAuthorName(authorName);
						newAuthor.setCount(1);
						aoArr.add(newAuthor);
					} 
				}
			}
		} 
		
		//System.out.println("Size of authorArr = " + aoArr.size());
		sortAuthorArr(aoArr);
		//output.writeCSVFileAuthor("authors", aoArr, numTop, inputObj.getDataLocation());
		//ArrayList<String> nameArr = extractAuthorNamesFromAoArr(aoArr, inputObj.getNum());
		return aoArr;
	}

	private ArrayList<AuthorObj> topAuthorsFromConf(String[] searchLocArr) {
		String conf = searchLocArr[1];
		ArrayList<AuthorObj> aoArr = new ArrayList<AuthorObj>();
		for (JSONObject jo : dataset) {
			if (jo.getString("venue").equalsIgnoreCase(conf)) {
				JSONArray authorArr = jo.getJSONArray("authors");
				for (int j = 0; j < authorArr.length(); j++) {
					JSONObject author = authorArr.getJSONObject(j);
					String authorName = author.getString("name");
					
					if (aoArrHasAuthor(authorName, aoArr)) {
						incrementAuthorCount(authorName, aoArr);
					} else {
						AuthorObj newAuthor = new AuthorObj();
						newAuthor.setAuthorName(authorName);
						newAuthor.setCount(1);
						aoArr.add(newAuthor);
					}
				}
			}
		}
		System.out.println("Size of authorArr = " + aoArr.size());
		sortAuthorArr(aoArr);
		//output.writeCSVFileAuthor("authors", aoArr, numTop, inputObj.getDataLocation());
		//ArrayList<String> nameArr = extractAuthorNamesFromAoArr(aoArr, inputObj.getNum());
		return aoArr;
	}
	
	/*private ArrayList<String> extractAuthorNamesFromAoArr(ArrayList<AuthorObj> aoArr, int numTop) {
		ArrayList<String> topAuthArr = new ArrayList<String>();
		if (!aoArr.isEmpty()) {
			for (int authIndex = 0; authIndex < numTop; authIndex++) {
				String topAuthor = aoArr.get(authIndex).getAuthorName();
				topAuthArr.add(topAuthor);
			}
		}
		return topAuthArr;
	}*/
	
	private void sortAuthorArr(ArrayList<AuthorObj> aoArr) {
		Collections.sort(aoArr, AuthorObj.authorCount);
	}

	private boolean aoArrHasAuthor(String authorName, ArrayList<AuthorObj> aoArr) {
		boolean hasAuthor = false;
		for (AuthorObj ao : aoArr) {
			if (ao.getAuthorName().equalsIgnoreCase(authorName)) {
				hasAuthor = true;
			} else {
				hasAuthor = false;
			}
		}
		return hasAuthor;
	}
	
	private void incrementAuthorCount(String authorName, ArrayList<AuthorObj> aoArr) {
		for (AuthorObj ao : aoArr) {
			if (ao.getAuthorName().equalsIgnoreCase(authorName)) {
				ao.setCount(ao.getCount() + 1);
				System.out.println("duplicate found : " + ao.getAuthorName());
			}
		}
	}
	
	private void getAuthorsFOR() throws IOException {
		ArrayList<AuthorObj> authors = new ArrayList<AuthorObj>();
		ArrayList<AuthorTrendObject> authorTrend = new ArrayList<AuthorTrendObject>();
		ArrayList<Integer> numYrs = inputObj.getNumYrs();
		ArrayList<String> confNames = inputObj.getConferences();

		authorsToAuthorObj(authors, numYrs, confNames);
		removeDuplicates(authors);
		updateAuthorTrendObj(authors, authorTrend);
		System.out.println("These are the authors (" + authors.size() + ")");
/*		for (AuthorObj ato : authors) {
			System.out.println(ato.getAuthorName() + ", " + ato.getConfName() + ", " + ato.getYear());
		}
*/	}

	private void authorsToAuthorObj(ArrayList<AuthorObj> authors, ArrayList<Integer> numYrs,
			ArrayList<String> confNames) {
		for (String confName : confNames) {
			for (int i = 0; i < numYrs.size(); i++) {
				Integer integerYr = numYrs.get(i);
				int intYr = integerYr.intValue();
				for (JSONObject jo : dataset) {
					if (jo.getString("venue").contains(confName) && jo.getInt("year") == intYr) {
						extractAuthors(intYr, jo, confName, authors);
					}
				}

			}
		}
	}

	private void updateAuthorTrendObj(ArrayList<AuthorObj> authors, ArrayList<AuthorTrendObject> authorTrend) {
		ArrayList<Integer> numYrs = inputObj.getNumYrs();
		ArrayList<String> confList = inputObj.getConferences();

		for (int j = 0; j < confList.size(); j++) {
			String conf = confList.get(j);
			for (int i = 0; i < numYrs.size(); i++) {
				AuthorTrendObject ato = new AuthorTrendObject();
				ato.setConfName(conf);
				int yr = numYrs.get(i);
				//System.out.println("year is " + yr);
				ato.setYear(yr);
				int count = 0;
				for (AuthorObj ao : authors) {
					if (ao.getYear() == yr) {
						//System.out.println("First statement passed");
						String authorConf = ao.getConfName();
						//System.out.println("authorconference name is: " + authorConf);
						if (authorConf.equalsIgnoreCase(conf)) {
							//System.out.println("There's an author! Wew!");
							count++;
						}
					}
				}
				ato.setAuthorCount(count);
				//System.out.println("Author count for conference " + conf + ", in year " + yr + " is: " + count);
				authorTrend.add(ato);
			}
		}
	}

	private void removeDuplicates(ArrayList<AuthorObj> authors) {
		List<AuthorObj> al = authors;
		// add elements to al, including duplicates
		Set<AuthorObj> hs = new LinkedHashSet<>();
		hs.addAll(al);
		al.clear();
		al.addAll(hs);
	}

	private void extractAuthors(int intYr, JSONObject jo, String confName, ArrayList<AuthorObj> authors) {
		if (jo.has("authors")) {
			JSONArray authorArr = jo.getJSONArray("authors");
			for (int i = 0; i < authorArr.length(); i++) {
				JSONObject ao = authorArr.getJSONObject(i);
				AuthorObj atobj = new AuthorObj();
				atobj.setAuthorName(ao.getString("name"));
				atobj.setConfName(confName);
				atobj.setYear(intYr);
				authors.add(atobj);
			}
		}
	}

	public static void jsonToTxtFile(JSONObject xmlJSONObj, String outputFileName) throws IOException {
		try (FileWriter outputFile = new FileWriter(outputFileName)) {
			outputFile.write(xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR));
			System.out.println("Successfully Copied JSON Object to File...");
		}
	}

	public void setInputObj(Input inputObj) {
		this.inputObj = inputObj;
	}
}
