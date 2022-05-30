package OSM2Tramod;

import java.util.ArrayList;

public class Lines {
	
	private ArrayList<LineOSM> lineList;
	
	public Lines() {
		setLineList(new ArrayList<LineOSM>());
		System.out.println("LineList succesfully initialized.");
	}

	public ArrayList<LineOSM> getLineList() {
		return lineList;
	}

	public void setLineList(ArrayList<LineOSM> lineList) {
		this.lineList = lineList;
	}

}
