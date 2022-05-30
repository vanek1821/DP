package OSM2Tramod;

import java.util.ArrayList;


public class OSMBuilding {

	private long id;
	private int version;
	private String timestamp;
	private int changeset;
	private ArrayList<OSMNode> nodeList;
	private ArrayList<OSMTag> tagList;
	
	private double area;
	private double sphericArea;
	private Centroid centroid;
	public boolean flag;
	//private double centroidLat;
	//private double centroidLon;
	
	public OSMBuilding(long id) {
		this.setId(id);
		this.setNodeList(new ArrayList<OSMNode>());
		this.setTagList(new ArrayList<OSMTag>());
		flag=false;
	}
	
	public OSMBuilding(long id, int version, String timestamp, int changeset) {
		this.setId(id);
		this.setVersion(version);
		this.setTimestamp(timestamp);
		this.setChangeset(changeset);
		this.setNodeList(new ArrayList<OSMNode>());
		this.setTagList(new ArrayList<OSMTag>());
		flag=false;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public ArrayList<OSMNode> getNodeList() {
		return nodeList;
	}
	public void setNodeList(ArrayList<OSMNode> nodeList) {
		this.nodeList = nodeList;
	}
	public ArrayList<OSMTag> getTagList() {
		return tagList;
	}
	public void setTagList(ArrayList<OSMTag> tagList) {
		this.tagList = tagList;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public String toString() {
	
		return "Building -> id: " + id + /*", version: " + version + ", timestamp: " + timestamp +", changeset: " + changeset + /*this.referencedNodesToString() + this.tagListToString() + this.getCentroid().toString() + */" Area: " + this.getArea()+" m2" + this.centroid.toString();
		
	}
	
	private String referencedNodesToString() {
		String result="";
		for (OSMNode node : nodeList) {
			result += "\n\tref -> " + node.getId();
		}
		return result;
	}
	
	private String tagListToString() {
		String result="";
		for (OSMTag osmTag : tagList) {
			result += "\n\tTag -> k: " + osmTag.getKey() + ", v: " + osmTag.getValue();
		}
		return result;
	}


	public void calculateArea() {
		double xi, yi, xj, yj;
		double sum = 0;
		//System.out.println("Calculating area");
		for (int i = 0; i < this.getNodeList().size()-1; i++) {
			xi = this.getNodeList().get(i).getLonInM();
			yi = this.getNodeList().get(i).getLatInM();
			xj = this.getNodeList().get(i+1).getLonInM();
			yj = this.getNodeList().get(i+1).getLatInM();
			
			sum += (xi*yj - xj*yi);
			//System.out.println("xi: " + xi + ", yi " + yi + ", xj" + xj + ", yj: " + yj +", sum: " + sum);
		}
		
		this.setArea(Math.abs(sum/2));
		
	}
	
	public void calculateSphericArea() {
		double xi, yi, xj, yj;
		double sum = 0;
		//System.out.println("Calculating area");
		for (int i = 0; i < this.getNodeList().size()-1; i++) {
			xi = this.getNodeList().get(i).getLon();
			yi = this.getNodeList().get(i).getLat();
			xj = this.getNodeList().get(i+1).getLon();
			yj = this.getNodeList().get(i+1).getLat();
			
			sum += (xi*yj - xj*yi);
			//System.out.println("xi: " + xi + ", yi " + yi + ", xj" + xj + ", yj: " + yj +", sum: " + sum);
		}
		
		this.setSphericArea(Math.abs(sum/2));
		
	}


	public void setSphericArea(double sphericArea) {
		this.sphericArea = sphericArea;
		
	}

	public double getSphericArea() {
		return this.sphericArea;
	}

	public Centroid getCentroid() {
		return centroid;
	}


	public void setCentroid(Centroid centroid) {
		this.centroid = centroid;
	}


	public void calculateCentroid() {
		double xi, yi, xj, yj;
		double cx = 0.0, cy = 0.0;

		this.calculateSphericArea();
		for (int i = 0; i < this.getNodeList().size()-1; i++) {
			xi = this.getNodeList().get(i).getLon();
			yi = this.getNodeList().get(i).getLat();
			xj = this.getNodeList().get(i+1).getLon();
			yj = this.getNodeList().get(i+1).getLat();
			
			cx += ((xi+xj)*(xi*yj - xj*yi));
			cy += ((yi+yj)*(xi*yj - xj*yi));
		}
		
		this.setCentroid(new Centroid(Math.abs((1/(6*this.getSphericArea()))*cx), Math.abs((1/(6*this.getSphericArea()))*cy)));
		
	}
}
