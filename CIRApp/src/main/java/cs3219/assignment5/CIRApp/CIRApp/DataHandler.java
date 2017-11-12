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
		xmlToJSON(inputObj.getDataLocation(), inputObj.getConferences());
		// parseJSONFileIntoObjArrList();
		execute();
	}

	private void execute() throws IOException {

		String queryType = inputObj.getQueryType();
		String queryCommand = inputObj.getQueryCommand();
		String venue = inputObj.getVenue();
		String[] query = queryType.split(" ");

		switch (queryType) {
		case "cited documents": // cited documents trend

			System.out.println("cited document trend executed");
			break;
		case "authors": // author trend
			if (queryCommand.equalsIgnoreCase("for")) {
				getAuthorsFOR();
			} else {
				// getAuthorsTOP();
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

	private void getAuthorsFOR() throws IOException {
		// JSONObject test = dataset.get(7);
		// jsonToTxtFile(test, "test7.json");
		ArrayList<String> authors = new ArrayList<String>();
		for (JSONObject jo : dataset) {
			if (jo.equals(dataset.get(0))) {
				continue;
			} else {
				JSONObject a = jo.getJSONObject("algorithms");
				System.out.println("first level okay");
				JSONArray b = a.getJSONArray("algorithm");
				System.out.println("second level okay");
				JSONObject c = getAuthorObjFromJSONArray(b);
				System.out.println("third level okay");
				JSONObject d = c.getJSONObject("variant");
				System.out.println("fourth level okay");
				if (d.has("author")) {
					if (d.getJSONArray("author") == null) {
						JSONObject e = d.getJSONObject("author");
						System.out.println("fifth level okay");
						String authorName = e.getString("content");
						System.out.println("sixth level okay");
						authors.add(authorName);
					} else {
						JSONArray e = d.getJSONArray("author");
						System.out.println("fifth level okay");
						for (int i = 0; i < e.length(); i++) {
							JSONObject obj = e.getJSONObject(i);
							String authorName = obj.getString("content");
							authors.add(authorName);
						}						
						System.out.println("sixth level okay");
					}
				}
			}
		}

		System.out.println("These are the authors: " + authors);
	}


	private JSONObject getAuthorObjFromJSONArray(JSONArray b) {
		for (int i = 0; i < b.length(); i++) {
			JSONObject test = b.getJSONObject(i);
			String section = test.getString("name");
			if (section.equalsIgnoreCase("SectLabel")) {
				return b.getJSONObject(i);
			}
		}
		return null;
	}

	public static void xmlToJSON(String fileName, ArrayList<String> confArr)
			throws FileNotFoundException, IOException, UnsupportedEncodingException {
		// ArrayList<JSONObject> finalDataset = new ArrayList<JSONObject>();
		for (int i = 0; i < confArr.size(); i++) {
			Path fullPath = Paths.get(fileName + "\\" + confArr.get(i).toString() + "\\"
					+ confArr.get(i).substring(0, 1) + "\\" + confArr.get(i).toString());

			if (Files.isDirectory(fullPath)) {
				try (DirectoryStream<Path> files = Files.newDirectoryStream(fullPath)) {
					for (Path file : files) {
						if (Files.isRegularFile(file) || Files.isSymbolicLink(file)) {
							File xmlFile = new File(file.toString());
							FileInputStream fin = new FileInputStream(xmlFile);
							byte[] xmlData = new byte[(int) xmlFile.length()];
							fin.read(xmlData);
							fin.close();
							TEST_XML_STRING = new String(xmlData, "UTF-8");
							JSONObject xmlJSONObj = new JSONObject();

							try {
								xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
								dataset.add(xmlJSONObj);
							} catch (JSONException e) {
								System.out.println(e.toString());
							}
						}
					}
				}
			} else {
				System.out.println(fullPath.toString() + "is not a directory");
			}

		}
		System.out.println("Number of JSON Objects : " + dataset.size());
		// System.out.println(dataset.get(0).toString());
		// jsonToTxtFile(dataset.get(0), "jsonOutput");
	}

	public static void jsonToTxtFile(JSONObject xmlJSONObj, String outputFileName) throws IOException {
		try (FileWriter outputFile = new FileWriter(outputFileName)) {
			outputFile.write(xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR));
			System.out.println("Successfully Copied JSON Object to File...");
			// System.out.println("\nJSON Object: " + xmlJSONObj);
		}
	}

	public void setInputObj(Input inputObj) {
		this.inputObj = inputObj;
	}

}
