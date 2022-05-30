package OSM2Tramod;

import java.util.ArrayList;
import java.util.HashMap;

public class ModifiedLines {

	//private ArrayList<LineChangesTree> modifiedLines;
	private HashMap<Integer, LineChangesTree> modifiedLines;
	
	public ModifiedLines() {
		//this.setModifiedLines(new ArrayList<LineChangesTree>());
		this.setModifiedLines(new HashMap<Integer, LineChangesTree>());
		System.out.println("ModifiedLines succesfully initialized.");
	}

	public HashMap<Integer, LineChangesTree> getLines() {
		return modifiedLines;
	}

	public void setModifiedLines(HashMap<Integer, LineChangesTree> modifiedLines) {
		this.modifiedLines = modifiedLines;
	}

	/*public ArrayList<LineChangesTree> getLines() {
		return modifiedLines;
	}

	public void setModifiedLines(ArrayList<LineChangesTree> modifiedLines) {
		this.modifiedLines = modifiedLines;
	}
	*/
}
