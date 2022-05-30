package OSM2Tramod;

public class OSMBounds {
	
	private double minlon;
	private double minlat;
	private double maxlon;
	private double maxlat;
	
	
	public OSMBounds(double minlon, double minlat, double maxlon, double maxlat) {
		this.setMinlon(minlon);
		this.setMinlat(minlat);
		this.setMaxlon(maxlon);
		this.setMaxlat(maxlat);
	}

	public double getMinlat() {
		return minlat;
	}

	public void setMinlat(double minlat) {
		this.minlat = minlat;
	}

	public double getMinlon() {
		return minlon;
	}

	public void setMinlon(double minlon) {
		this.minlon = minlon;
	}

	public double getMaxlat() {
		return maxlat;
	}

	public void setMaxlat(double maxlat) {
		this.maxlat = maxlat;
	}

	public double getMaxlon() {
		return maxlon;
	}

	public void setMaxlon(double maxlon) {
		this.maxlon = maxlon;
	}
	
	public String toString() {
		return "Bounds -> MinLon: " + minlon + ", MinLat: " + minlat + ", maxLon: " + maxlon + ", maxLat: " + maxlat;
	}

}
