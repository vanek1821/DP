package OSM2Tramod;

import java.util.ArrayList;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ByteOrderValues;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;


public class Vertexes {
	
	private ArrayList<Vertex> vertexList;
	
	public Vertexes() {
		setVertexList(new ArrayList<Vertex>());
		System.out.println("Vertexes succesfully initialized.");
	}

	public ArrayList<Vertex> getVertexList() {
		return vertexList;
	}

	public void setVertexList(ArrayList<Vertex> vertexList) {
		this.vertexList = vertexList;
	}
	
	public void convertToCoordinates() throws ParseException{
		
		GeometryFactory gm = new GeometryFactory(new PrecisionModel(), 4326);
        WKBReader wkbr = new WKBReader(gm);
        byte[] wkbBytes;
        
        for (Vertex vertex : vertexList) {

			wkbBytes = wkbr.hexToBytes(vertex.getAddGeometryColumn().replace("'", ""));
        	Geometry geom = wkbr.read(wkbBytes);
        	vertex.setLon(geom.getCoordinate().x);
        	vertex.setLat(geom.getCoordinate().y);

         	//System.out.println(vertex.getID()+": " + vertex.getAddGeometryColumn());
        	//System.out.println("Vertex" + vertex.getID() + ": Geometry: " + vertex.getAddGeometryColumn() + " ->  Long: " + vertex.getLongtitude()  + " Lat: " + vertex.getLatitude());
		}
	}
	

}
