package OSM2Tramod;

public class Tile {
	
	private int id;
	private double minLon;
	private double minLat;
	private double maxLon;
	private double maxLat;
	private Centroid centroid;
	private double trafficGenerator;
	private Vertex vertex;
	
	public Tile(double minLon, double minLat, double maxLon, double maxLat) {
		
		this.setMinLon(minLon);
		this.setMinLat(minLat);
		
		this.setMaxLon(maxLon);
		this.setMaxLat(maxLat);
		
		this.trafficGenerator = 0.0;
	}
	
	public double getMinLat() {
		return minLat;
	}
	public void setMinLat(double minLat) {
		this.minLat = minLat;
	}
	public double getMinLon() {
		return minLon;
	}
	public void setMinLon(double minLon) {
		this.minLon = minLon;
	}
	public double getMaxLat() {
		return maxLat;
	}
	public void setMaxLat(double maxLat) {
		this.maxLat = maxLat;
	}

	public double getMaxLon() {
		return maxLon;
	}

	public void setMaxLon(double maxLon) {
		this.maxLon = maxLon;
	}

	public void calculateCentroid() {
		
		double xi, yi, xj, yj;
		double cx = 0.0, cy = 0.0;
		
		OSMNode nodes[] = {
				new OSMNode(this.minLon, this.minLat),
				new OSMNode(this.maxLon, this.minLat),
				new OSMNode(this.maxLon, this.maxLat),
				new OSMNode(this.minLon, this.maxLat),
				new OSMNode(this.minLon, this.minLat)

		};

		double sphericArea = this.calculateSphericArea();
		for (int i = 0; i < nodes.length-1; i++) {
			xi = nodes[i].getLon();
			yi = nodes[i].getLat();
			xj = nodes[i+1].getLon();
			yj = nodes[i+1].getLat();
			
			cx += ((xi+xj)*(xi*yj - xj*yi));
			cy += ((yi+yj)*(xi*yj - xj*yi));
		}
		
		this.setCentroid(new Centroid(Math.abs((1/(6*sphericArea))*cx), Math.abs((1/(6*sphericArea))*cy)));
		
	}

	public double calculateSphericArea() {
		double xi, yi, xj, yj;
		double sum = 0;
		//System.out.println("Calculating area");
		
		OSMNode nodes[] = {
				new OSMNode(this.minLon, this.minLat),
				new OSMNode(this.maxLon, this.minLat),
				new OSMNode(this.maxLon, this.maxLat),
				new OSMNode(this.minLon, this.maxLat),
				new OSMNode(this.minLon, this.minLat)

		};
		
		for (int i = 0; i < nodes.length-1; i++) {
			xi = nodes[i].getLon();
			yi = nodes[i].getLat();
			xj = nodes[i+1].getLon();
			yj = nodes[i+1].getLat();
			
			sum += (xi*yj - xj*yi);
			//System.out.println("xi: " + xi + ", yi " + yi + ", xj" + xj + ", yj: " + yj +", sum: " + sum);
		}
		
		return (Math.abs(sum/2));
		
	}

	public Centroid getCentroid() {
		return centroid;
	}

	public void setCentroid(Centroid centroid) {
		this.centroid = centroid;
	}

	public double getTrafficGenerator() {
		return trafficGenerator;
	}

	public void setTrafficGenerator(double trafficGenerator) {
		this.trafficGenerator = trafficGenerator;
	}
	
	public String toString() {
		return "Tile - minLon: " + minLon + ", maxLon: " + maxLon + ", minLat: " + minLat + ", maxLat: " + maxLat + ", traffic: " + this.getTrafficGenerator();
	}

	public Vertex getVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	public boolean isCloser(Vertex vertex) {
		if(this.vertex == null) {
			return true;
		}
		if(distance(vertex.getLon(), this.centroid.getLon(), vertex.getLat(), this.centroid.getLat()) < distance(this.vertex.getLon(), this.centroid.getLon(), this.vertex.getLat(), this.centroid.getLat())) {
			return true;
		}
		return false;
	}
	
	public static double distance(double lon1, double lat1, double lon2,
	        double lat2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meter
	    
	    distance = Math.pow(distance, 2);

	    return Math.sqrt(distance);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toTramodSQLString() {
		return "(" + this.getId()
		+ ", " + this.getVertex().getID()
		+ ", " + this.getTrafficGenerator()
		+ ", " + this.getCentroid().getAddGeometryColumn()
		+ ")";
	}

}
