package OSM2Tramod;

public class LineChangesTree {
	
	private LineOSM originalLine;
	//private LineOSM copiedLine;
	private LineOSM newLine;
	
	public LineChangesTree() {
		this.setOriginalLine(new LineOSM());
		//this.setCopiedLine(new LineOSM());
		this.setNewLine(new LineOSM());
		System.out.println("LineChangesTree was Initialized.");
	}
	
	public LineChangesTree(LineOSM originalLine) {
		this.setOriginalLine(originalLine);
	}

	public LineOSM getOriginalLine() {
		return originalLine;
	}

	public void setOriginalLine(LineOSM originalLine) {
		this.originalLine = originalLine;
	}

	/*public LineOSM getCopiedLine() {
		return copiedLine;
	}

	public void setCopiedLine(LineOSM copiedLine) {
		this.copiedLine = copiedLine;
	}*/

	public LineOSM getNewLine() {
		return newLine;
	}

	public void setNewLine(LineOSM newLine) {
		this.newLine = newLine;
	}

	public void modify(int newID) {
		//this.getOriginalLine().print();	
		
		/*LineOSM copiedLine = new LineOSM(this.originalLine.getID(),
				this.getOriginalLine().getOsm_id(), 
				this.getOriginalLine().getOsm_name(), 
				this.getOriginalLine().getOsm_meta(), 
				this.getOriginalLine().getOsm_source_id(),
				this.getOriginalLine().getOsm_target_id(),
				this.getOriginalLine().getClazz(), 
				this.getOriginalLine().getFlags(), 
				this.getOriginalLine().getSource(),
				this.getOriginalLine().getTarget(), 
				this.getOriginalLine().getKm(), 
				this.getOriginalLine().getKmh(), 
				this.getOriginalLine().getCost(),
				"1000000.0", 
				this.getOriginalLine().getX1(), 
				this.getOriginalLine().getY1(), 
				this.getOriginalLine().getX2(), 
				this.getOriginalLine().getY2(),
				this.getOriginalLine().getAddGeometryColumn());
		
		this.setCopiedLine(copiedLine);*/
		
		LineOSM newLine = new LineOSM(//this.originalLine.getID(),
				newID,
				this.getOriginalLine().getOsm_id(), 
				this.getOriginalLine().getOsm_name(), 
				this.getOriginalLine().getOsm_meta(), 
				this.getOriginalLine().getOsm_target_id(),
				this.getOriginalLine().getOsm_source_id(),
				this.getOriginalLine().getClazz(), 
				this.getOriginalLine().getFlags(), 
				this.getOriginalLine().getTarget(), 
				this.getOriginalLine().getSource(),
				this.getOriginalLine().getKm(), 
				this.getOriginalLine().getKmh(), 
				this.getOriginalLine().getReverse_cost(), 
				"1000000.0",
				this.getOriginalLine().getX2(), 
				this.getOriginalLine().getY2(), 
				this.getOriginalLine().getX1(), 
				this.getOriginalLine().getY1(),
				"ST_REVERSE("+this.getOriginalLine().getAddGeometryColumn()+")");	
		
		this.setNewLine(newLine);
		
	}
	
	public void printChanges() {
		System.out.println("Original: " + this.getOriginalLine().toString());
		//System.out.println("Copied:   " + this.getCopiedLine().toString());
		System.out.println("newLine:  " + this.getNewLine().toString());
		System.out.println();
	}

}
