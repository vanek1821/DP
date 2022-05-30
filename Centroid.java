package OSM2Tramod;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ByteOrderValues;
import org.locationtech.jts.io.WKBWriter;

public class Centroid {
	
	private double lon;
	private double lat;
	
	public Centroid(double lon, double lat) {	
		this.setLon(lon);
		this.setLat(lat);
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
	
	
	public String toString() {
		return "Centroid - lon: " + this.getLon() +", lat: " + this.getLat();
	}

	public String getAddGeometryColumn() {
		
		return "'" + this.convertToWKBR()+"'";
	}
	
	public String convertToWKBR() {
		PrecisionModel precisionModel = new PrecisionModel();
		GeometryFactory geometryFactory = new GeometryFactory(precisionModel, 4326);

		WKBWriter wkbWriter = new WKBWriter(2, ByteOrderValues.LITTLE_ENDIAN, true);
		byte[] b = wkbWriter.write(geometryFactory.createPoint(new Coordinate(lon, lat)));
		String hex = WKBWriter.toHex(b);
		return hex;
	}

}
