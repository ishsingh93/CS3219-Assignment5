package cs3219.assignment5.CIRApp.CIRApp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class Output {

	public Output() {
		
	}
	
	public void writeCSVFileAuthorFOR(String fileName, ArrayList<AuthorTrendObject> authorTrend, Input inputObj) {
		ICsvBeanWriter beanWriter = null;
		List<AuthorTrendObject> authorList = authorTrend;
		System.out.println("Size of authorArr = " + authorTrend.size());
		
		try {
			beanWriter = new CsvBeanWriter(
					new FileWriter(
							inputObj.getDataLocation() + fileName + ".csv"),
					CsvPreference.STANDARD_PREFERENCE);
			
			final String[] header = new String[] { "year", "ArXiv", "ICSE" };			
			final CellProcessor[] processors = getProcessors();

			// Write the CSV file header
			beanWriter.writeHeader(header);

			// Write statistics
			for (AuthorTrendObject authObject : authorList) {
				beanWriter.write(authObject, header, processors);
			}

			System.out.println("CSV file created successfully");

		} catch (Exception e) {
			System.out.println("Error in CSVFileWriter");
			e.printStackTrace();
		} finally {

			try {
				beanWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter");
				e.printStackTrace();
			}
		}
	}
	
	private CellProcessor[] getProcessors() {
		CellProcessor[] PROCESSORS = new CellProcessor[] { null, null, null };
		return PROCESSORS;
	}
}
