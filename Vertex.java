package OSM2Tramod;

import java.util.List;

public class Vertex {
	
	private int ID;
	private String clazz;
	private String osm_id;
	private String osm_name;
	private String ref_count;
	private String restrictions;
	private String addGeometryColumn;
	private double lon;
	private double lat;
	
	public Vertex() {
		System.out.println("Vertex was Created. Empty properties.");
	}
	
	public Vertex(int id, String clazz, String osm_id, String osm_name, String ref_count, String restrictions, String addGeometryColumn) {
		this.setID(id);
		this.setClazz(clazz);
		this.setOsm_id(osm_id);
		this.setOsm_name(osm_name);
		this.setRef_count(ref_count);
		this.setRestrictions(restrictions);
		this.addGeometryColumn = addGeometryColumn;
		
		//System.out.println("Vertex was Created. Filled properties. " + this.toString());
	}

	public int getID() {
		return ID;
	}

	public void setID(int id) {
		ID = id;
	}

	private String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	private String getOsm_id() {
		return osm_id;
	}

	private void setOsm_id(String osm_id) {
		this.osm_id = osm_id;
	}

	private String getOsm_name() {
		return osm_name;
	}

	public void setOsm_name(String osm_name) {
		this.osm_name = osm_name;
	}

	private String getRef_count() {
		return ref_count;
	}

	public void setRef_count(String ref_count) {
		this.ref_count = ref_count;
	}

	public String getRestrictions() {
		return restrictions;
	}
	/*public List<String> getRestrictionList(){
		
	}*/

	public void setRestrictions(String restrictions) {
		this.restrictions = restrictions;
	}
	public String getAddGeometryColumn() {
		return addGeometryColumn;
	}
	
	public boolean hasTurnRestriction() {
		if (this.getRestrictions().equals("null")) return false;
		else return true;
	}
	
	@Override
	public String toString() {
		return "Vertex: " + this.getID() + " Restrictions: " + this.getRestrictions();
	}
	
	
	
	public void print() {
		System.out.println(this.toString());
	}
	public String toSQLString() {
		return "("+this.getID()
		+ ", "+this.getClazz()
		+ ", "+this.getOsm_id()
		+ ", "+this.getOsm_name()
		+ ", "+this.getRef_count()
		+ ", "+this.getRestrictions()
		+ ", "+this.getAddGeometryColumn()
		+ ")";
	}

	public String toTramodSQLString() {
		return "(" + this.getID()
		+ ", " + this.getAddGeometryColumn()
		+ ")";
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

}
