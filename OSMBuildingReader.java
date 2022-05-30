package OSM2Tramod;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;


public class OSMBuildingReader {

	private FileReader fr;
	private BufferedReader br;
	private ArrayList<String> readLines;
	private SAXParserFactory fact;
	private SAXParser saxParser;
	private DataContainerHandler handler;
	private String inputFileName;
	//private Document doc;
	//private DocumentBuilder db;
	//private DefaultHandler handle;
	
	public OSMBuildingReader(String inputFileName, DataContainer dataContainer) {
			
		try {
			this.fact = SAXParserFactory.newInstance();
			this.saxParser = fact.newSAXParser();
			this.handler = new DataContainerHandler(dataContainer);
			this.inputFileName = inputFileName;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			System.out.println("Couldn`t load input file " + inputFileName);
		}
        
		
	}

	public FileReader getFr() {
		return fr;
	}

	public void setFr(FileReader fr) {
		this.fr = fr;
	}

	public BufferedReader getBr() {
		return br;
	}

	public void setBr(BufferedReader br) {
		this.br = br;
	}

	public ArrayList<String> getReadLines() {
		return readLines;
	}

	public void setReadLines(ArrayList<String> readLines) {
		this.readLines = readLines;
	}

	public void load() {
		try {
			this.saxParser.parse(this.inputFileName, this.handler);
		} catch (SAXException e) {
			System.out.println("File couldn`t be parsed");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File not found");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

