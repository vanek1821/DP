package OSM2Tramod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Grid {

	//private ArrayList<Tile> tiles;
	private HashMap <GridLocator, Tile> tiles;
	private int userGridCountRequest;
	private OSMBounds bounds;
	private double sphericArea;
	private double dLon, dLat;

	public Grid() {
		this.setTiles(new HashMap<GridLocator, Tile>());
	}

	public void init(int useGridRequest, OSMBounds bounds) {
		this.setUserGridCountRequest(useGridRequest);
		this.setBounds(bounds);
		this.sphericArea = this.calculateSphericArea();

		this.setdLon(Math.sqrt(this.getSphericArea()/this.getUserGridCountRequest()));
		this.setdLat(Math.sqrt(this.getSphericArea()/this.getUserGridCountRequest()));

	}

	private double calculateSphericArea() {
		double xi, yi, xj, yj;
		double sum = 0;
		//System.out.println("Calculating area");

		OSMNode nodes[] = {

				new OSMNode(this.bounds.getMinlon(), this.bounds.getMinlat()),
				new OSMNode(this.bounds.getMaxlon(), this.bounds.getMinlat()),
				new OSMNode(this.bounds.getMaxlon(), this.bounds.getMaxlat()),
				new OSMNode(this.bounds.getMinlon(), this.bounds.getMaxlat()),
				new OSMNode(this.bounds.getMinlon(), this.bounds.getMinlat())

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


	public void calculateGenerators(ArrayList<OSMBuilding> buildings) {

		Centroid buildingCentroid;
		double trafficGenerator;
		
		int x,y,lonCounter, latCounter;
		GridLocator gl;
		Tile tile;
		double minLon, minLat, maxLon, maxLat;

		for (OSMBuilding osmBuilding : buildings) {
			
			buildingCentroid = osmBuilding.getCentroid();
			
			if(Double.isNaN(buildingCentroid.getLat())||Double.isNaN(buildingCentroid.getLon())) {
				continue;
			}
			
			lonCounter = 1;
			latCounter = 1;

			if(buildingCentroid.getLon()<this.bounds.getMinlon() || buildingCentroid.getLat()<this.bounds.getMinlat() || buildingCentroid.getLon()>this.bounds.getMaxlon() || buildingCentroid.getLat()>this.bounds.getMaxlat()) {

				osmBuilding.flag = false;
				continue;
			}

			//System.out.println("OSMBuilding" + osmBuilding.toString());

			while( this.getBounds().getMinlon() + lonCounter * this.getdLon() < buildingCentroid.getLon()) {		
				lonCounter++;

			}

			while( this.getBounds().getMinlat() + latCounter * this.getdLat() < buildingCentroid.getLat()) {
				latCounter++;
			}
			x = lonCounter - 1;
			y = latCounter - 1;

			gl = new GridLocator(x,y);



			if(!tiles.containsKey(gl)) {
				
				minLon = x * this.getdLon() + this.getBounds().getMinlon();
				minLat = y * this.getdLat() + this.getBounds().getMinlat();

				maxLon = lonCounter * this.getdLon() + this.getBounds().getMinlon();
				if(maxLon > this.getBounds().getMaxlon()) maxLon = this.getBounds().getMaxlon();

				maxLat = latCounter * this.getdLat() + this.getBounds().getMinlat();
				if(maxLat > this.getBounds().getMaxlat()) maxLat = this.getBounds().getMaxlat();

				tile = new Tile(minLon, minLat, maxLon, maxLat);

				tile.calculateCentroid();

				trafficGenerator = tile.getTrafficGenerator() + osmBuilding.getArea();
				tile.setTrafficGenerator(trafficGenerator);

				tiles.put(gl, tile);

				if(osmBuilding.getArea() == 0.0) osmBuilding.flag = false;
				else  osmBuilding.flag = true;
			}
			else {

				trafficGenerator = tiles.get(gl).getTrafficGenerator();
				tiles.get(gl).setTrafficGenerator(trafficGenerator+ osmBuilding.getArea());

				if(osmBuilding.getArea() == 0.0) osmBuilding.flag = false;
				else  osmBuilding.flag = true;
			}





		}

	}

	public void printGenerators() {

		Iterator<HashMap.Entry<GridLocator, Tile>> it = tiles.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry<GridLocator, Tile> pair = it.next();
			System.out.println(pair.getValue().toString());
		}

	}

	public HashMap<GridLocator, Tile> getTiles() {
		return tiles;
	}

	public void setTiles(HashMap<GridLocator, Tile> tiles) {
		this.tiles = tiles;
	}

	public OSMBounds getBounds() {
		return bounds;
	}

	public void setBounds(OSMBounds bounds) {
		this.bounds = bounds;
	}

	public double getSphericArea() {
		return sphericArea;
	}

	public void setSphericArea(double sphericArea) {
		this.sphericArea = sphericArea;
	}

	public double getdLon() {
		return dLon;
	}

	public void setdLon(double dLon) {
		this.dLon = dLon;
	}

	public double getdLat() {
		return dLat;
	}

	public void setdLat(double dLat) {
		this.dLat = dLat;
	}

	public int getUserGridCountRequest() {
		return userGridCountRequest;
	}

	public void setUserGridCountRequest(int userGridCountRequest) {
		this.userGridCountRequest = userGridCountRequest;
	}

	public Double getTotalTraffic() {
		double result = 0.0;

		Iterator<HashMap.Entry<GridLocator, Tile>> it = tiles.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry<GridLocator, Tile> pair = it.next();
			result += pair.getValue().getTrafficGenerator();
		}

		return result;
	}

	public void assignGenerators(ArrayList<Vertex> vertexList) {

		int lonCounter, latCounter, x, y;
		double minLon, minLat, maxLon, maxLat;
		Tile tile;
		GridLocator gl;

		for (Vertex vertex : vertexList) {

			lonCounter = 1;
			latCounter = 1;

			if(vertex.getLon()<this.bounds.getMinlon() || vertex.getLat()<this.bounds.getMinlat() || vertex.getLon()>this.bounds.getMaxlon() || vertex.getLat()>this.bounds.getMaxlat()) {
				System.out.println("Vertex out of grid?");
				System.out.println("VertexID: " + vertex.getID() + " Lon: " + vertex.getLon() + " Lat: " + vertex.getLat());
				continue;
			}

			//System.out.println("OSMBuilding" + osmBuilding.toString());

			while( this.getBounds().getMinlon() + lonCounter * this.getdLon() < vertex.getLon()) {		
				lonCounter++;

			}
			while( this.getBounds().getMinlat() + latCounter * this.getdLat() < vertex.getLat()) {
				latCounter++;
			}

			x = lonCounter - 1;
			y = latCounter - 1;

			gl = new GridLocator(x,y);

			if(!tiles.containsKey(gl)) {

				minLon = x * this.getdLon() + this.getBounds().getMinlon();
				minLat = y * this.getdLat() + this.getBounds().getMinlat();

				maxLon = lonCounter * this.getdLon() + this.getBounds().getMinlon();
				if(maxLon > this.getBounds().getMaxlon()) maxLon = this.getBounds().getMaxlon();

				maxLat = latCounter * this.getdLat() + this.getBounds().getMinlat();
				if(maxLat > this.getBounds().getMaxlat()) maxLat = this.getBounds().getMaxlat();

				tile = new Tile(minLon, minLat, maxLon, maxLat);

				tile.calculateCentroid();

				if(tile.isCloser(vertex)) {
					tile.setVertex(vertex);
				}

				tiles.put(gl, tile);

			}
			else {
				tile = tiles.get(gl);
				
				if(tile.isCloser(vertex)) {
					tile.setVertex(vertex);
				}

				tiles.put(gl, tile);
			}
		}
	}
}
