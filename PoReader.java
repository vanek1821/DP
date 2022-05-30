package OSM2Tramod;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

public class PoReader {
	
	private FileReader frLines;
	private BufferedReader brLines;
	private FileReader frVertexes;
	private BufferedReader brVertexes;
	//private Records records;
	private DataContainer dataContainer;
	private ArrayList<String> readLines;
	private ArrayList<String> readVertexes;
	
	public PoReader(String frLinesName, String frVertexesName, DataContainer dataContainer) {
		try {
			this.setFrLines(new FileReader(frLinesName));
			this.brLines = new BufferedReader(frLines);
			this.readLines = new ArrayList<String>();
			System.out.println("File: " + frLinesName + " successfully loaded");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File: " + frLinesName + " not Found.");
			System.exit(0);
		}
		try {
			this.setFrVertexes(new FileReader(frVertexesName));
			this.brVertexes = new BufferedReader(frVertexes);
			this.readVertexes = new ArrayList<String>();
			System.out.println("File: " + frVertexesName + " successfully loaded");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File: " + frVertexesName + " not Found.");
			System.exit(0);
		}
		
		this.setDataContainer(dataContainer);
		
		
	}

	public void executeReadingLines() {
		String lineRead;
		int lineCounter = 0;
		try {
			
			
			while((lineRead = this.getBrLines().readLine()) !=  null) {
				if(isLine(lineRead)) {
					readLines.add(lineRead);
					lineCounter++;
				}
				else {
					//System.out.println("invalid: " + lineRead);
				}
			}
			
			System.out.println("Read " + lineCounter + " lines.");
			fillLines();
		
		
		} catch (IOException e) {
			System.out.println("Cannot read from Line File");
			e.printStackTrace();
		}
		
	}
	
	public void executeReadingVertexes() {
		String lineRead;
		int vertexCounter = 0;
		
		try {
					
			while((lineRead = this.getBrVertexes().readLine()) !=  null) {
				if(isVertex(lineRead)) {
					readVertexes.add(lineRead);
					vertexCounter++;
				}
				else {
					//System.out.println("invalid: " + lineRead);
				}
			}
			
			System.out.println("Read " + vertexCounter + " vertexes.");
			fillVertexes();
		
		
		} catch (IOException e) {
			System.out.println("Cannot read from Vertex File");
			e.printStackTrace();
		}
		
	}

	

	private void fillLines() {

		String[] splitLine;
		for (String line : readLines) {
			line = line.replaceAll("[(]", "");
			line = line.replaceAll("[)]", "");
			line = line.replaceAll("\\s", "");
			line = line.replaceAll(";", "");
			splitLine = line.split(",");

			LineOSM newLine = new LineOSM(Integer.valueOf(splitLine[0]), splitLine[1], splitLine[2], splitLine[3], splitLine[4], splitLine[5], splitLine[6], splitLine[7], splitLine[8],splitLine[9], splitLine[10], splitLine[11], splitLine[12], splitLine[13], splitLine[14], splitLine[15], splitLine[16], splitLine[17], splitLine[18]);
			dataContainer.getLines().getLineList().add(newLine);
		}
		System.out.println("Lines successfully loaded.");
	}
	
	private void fillVertexes() {
		
		String[] splitLine;
		for (String line : readVertexes) {
			line = line.replaceAll("[(]", "");
			line = line.replaceAll("[)]", "");
			line = line.replaceAll("\\s", "");
			line = line.replaceAll(";", "");
			splitLine = line.split(",");

			Vertex newVertex = new Vertex(Integer.valueOf(splitLine[0]), splitLine[1], splitLine[2], splitLine[3], splitLine[4], splitLine[5], splitLine[6]);
			dataContainer.getVertexes().getVertexList().add(newVertex);
		}
		
		System.out.println("Vertexes successfully loaded.");
	}

	private boolean isLine(String lineRead) {
		String regex = "[(].+[)],?;?";
		if(lineRead.matches(regex))
			return true;
		else
			return false;
	}

	private boolean isVertex(String lineRead) {
		String regex = "[(].+[)],?;?";
		if(lineRead.matches(regex))
			return true;
		else
			return false;
	}
	
	public FileReader getFrLines() {
		return frLines;
	}

	public void setFrLines(FileReader frLines) {
		this.frLines = frLines;
	}

	public FileReader getFrVertexes() {
		return frVertexes;
	}

	public void setFrVertexes(FileReader frVertexes) {
		this.frVertexes = frVertexes;
	}


	public BufferedReader getBrLines() {
		return this.brLines;
	}
	public BufferedReader getBrVertexes() {
		return this.brVertexes;
	}

	public DataContainer getDataContainer() {
		return dataContainer;
	}

	public void setDataContainer(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}
	

}
