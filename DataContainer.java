package OSM2Tramod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DataContainer {

	//	private ArrayList<OSMNode> nodes;
	private Lines lines;
	private Vertexes vertexes;
	private ModifiedLines modifiedLines;
	private TurnRestrictions turnRestrictions;

	
	private HashMap<Long, OSMNode> nodes;
	private ArrayList<OSMBuilding> buildings;
	private OSMBounds bounds;
	private Grid grid;

	DataContainer() {
		this.setLines(new Lines());
		this.setVertexes(new Vertexes());
		this.setModifiedLines(new ModifiedLines());
		this.setTurnRestrictions(new TurnRestrictions());
		
		this.setNodes(new HashMap<Long, OSMNode>());
		this.setBuildings(new ArrayList<OSMBuilding>());
		this.setGrid(new Grid());
		
		System.out.println("Data succesfully initialized.");
	}


	public ArrayList<OSMBuilding> getBuildings() {
		return buildings;
	}
	public void setBuildings(ArrayList<OSMBuilding> buildings) {
		this.buildings = buildings;
	}
	public OSMBounds getBounds() {
		return bounds;
	}
	public void setBounds(OSMBounds bounds) {
		this.bounds = bounds;
	}

	public HashMap<Long, OSMNode> getNodes() {
		return nodes;
	}

	public void setNodes(HashMap<Long, OSMNode> nodes) {
		this.nodes = nodes;
	}

	public OSMNode getNode(String ref) {
		return this.getNodes().get(Long.valueOf(ref));
	}

	public Vertexes getVertexes() {
		return vertexes;
	}

	public void setVertexes(Vertexes vertexes) {
		this.vertexes = vertexes;
	}

	public Lines getLines() {
		return lines;
	}

	public void setLines(Lines lines) {
		this.lines = lines;
	}

	public ModifiedLines getModifiedLines() {
		return modifiedLines;
	}

	public void setModifiedLines(ModifiedLines modifiedLines) {
		this.modifiedLines = modifiedLines;
	}

	public TurnRestrictions getTurnRestrictions() {
		return turnRestrictions;
	}

	public void setTurnRestrictions(TurnRestrictions turnRestrictions) {
		this.turnRestrictions = turnRestrictions;
	}


	public void printBuildings() {
		for (OSMBuilding osmBuilding : buildings) {
			System.out.println(osmBuilding.toString());
		}

	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	public Grid getGrid(){
		return this.grid;
	}


	public void printBuildingsArea() {
		double result = 0.0;
		for (OSMBuilding osmBuilding : buildings) {
			result += osmBuilding.getArea();
		}
		System.out.println("Total Area of buildings: " + result);
	}


	public Double getBuildingsArea() {
		double result = 0.0;
		for (OSMBuilding osmBuilding : buildings) {
			result += osmBuilding.getArea();
		}
		return result;
	}


	public int countTilesWithoutVertex() {
		int counter = 0;
		Iterator<HashMap.Entry<GridLocator, Tile>> it = this.getGrid().getTiles().entrySet().iterator();
		
		while (it.hasNext()) {
			HashMap.Entry<GridLocator, Tile> pair = it.next();
			
			if(pair.getValue().getVertex() == null) {
				counter++;
			
			}
		}
		return counter;
	}



	public void assignZoneIDs() {
		Iterator<HashMap.Entry<GridLocator, Tile>> it = this.getGrid().getTiles().entrySet().iterator();
		int zoneIDCounter =0; 
		while (it.hasNext()) {
			HashMap.Entry<GridLocator, Tile> pair = it.next();
			pair.getValue().setId(zoneIDCounter);
			zoneIDCounter++;
		}
		
	}

}
