package cs3219.assignment5.CIRApp.CIRApp;

import java.io.IOException;

public class InputHandler {

	private static final String FILEOP = "count";
	private static final String DATAOP = "select";

	private Input inputObj;

	public InputHandler(Input inputObj) throws IOException {
		this.setInputObj(inputObj);
		assignToManager();
	}

	private void assignToManager() throws IOException {
		String command = inputObj.getQueryCommand();
		switch (command) {
		case FILEOP:
			DataManager dmFile = new DataManager(inputObj);
			break;
		case DATAOP:
			DataManager dmData = new DataManager(inputObj);
			break;
		default:
			break;
		}

	}

	public Input getInputObj() {
		return inputObj;
	}

	public void setInputObj(Input inputObj) {
		this.inputObj = inputObj;
	}
}
