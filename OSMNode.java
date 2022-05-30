
package OSM2Tramod;

import java.util.ArrayList;

public class OSMNode {

	private long id;
	private double lon;
	private double lat;
	private double lonInM;
	private double latInM;
	private int version;
	private String timestamp;
	private int changeset;
	private ArrayList<OSMTag> tagList;
	
	public OSMNode(double lon, double lat) {
		this.lon = lon;
		this.lat = lat;
	}
	
	public OSMNode(long id, double lon, double lat) {
		this.setId(id);
		this.setLon(lon);
		this.setLat(lat);
		this.setTagList(new ArrayList<OSMTag>());
		this.degreesToMeters(this.getLon(), this.getLat());
	}
	
	public OSMNode(long id, double lon, double lat, int version, String timestamp, int changeset) {
		this.setId(id);
		this.setLon(lon);
		this.setLat(lat);
		this.setVersion(version);
		this.setTimestamp(timestamp);
		this.setChangeset(changeset);
		this.setTagList(new ArrayList<OSMTag>());
		this.degreesToMeters(this.getLon(), this.getLat());
	}

	private void degreesToMeters(double lon, double lat) {
		
		this.setLonInM( lon * 20037508.34 / 180);
		double y = Math.log(Math.tan((90 + lat) * Math.PI / 360)) / (Math.PI / 180);
		this.setLatInM(y * 20037508.34 / 180);

		
	}

	public void setLatInM(double d) {
		this.latInM = d;
		
	}

	public void setLonInM(double d) {
		this.lonInM = d;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getChangeset() {
		return changeset;
	}

	public void setChangeset(int changeset) {
		this.changeset = changeset;
	}

	public ArrayList<OSMTag> getTagList() {
		return tagList;
	}

	public void setTagList(ArrayList<OSMTag> tagList) {
		this.tagList = tagList;
	}
	
	public String toString() {
		return "Node -> id: " + id + ", lon: " + lon + ", lat: "  + lat + ", version: "+ version +", timestamp:" + timestamp + ", changeset " + changeset + this.tagListToString();
	}
	
	private String tagListToString() {
		String result="";
		for (OSMTag osmTag : tagList) {
			result += "\n\tTag -> k: " + osmTag.getKey() + ", v: " + osmTag.getValue();
		}
		return result;
	}

	public double getLonInM() {
		return this.lonInM;
	}
	public double getLatInM() {
		return this.latInM;
	}
	
	
}
