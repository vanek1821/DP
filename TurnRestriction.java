package OSM2Tramod;

public class TurnRestriction {

	private String nodeID;
	private String edgeFromID;
	private String edgeToID;
	private String cost;
	
	public TurnRestriction(String nodeID, String edgeFromID, String edgeToID, String cost) {
		this.setNodeID(nodeID);
		this.setEdgeFromID(edgeFromID);
		this.setEdgeToID(edgeToID);
		this.setCost(cost);
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getEdgeFromID() {
		return edgeFromID;
	}

	public void setEdgeFromID(String edgeFromID) {
		this.edgeFromID = edgeFromID;
	}

	public String getEdgeToID() {
		return edgeToID;
	}

	public void setEdgeToID(String edgeToID) {
		this.edgeToID = edgeToID;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String toTramodSQLString() {
		
		return "(" + this.getNodeID()
		+ ", " + this.getEdgeFromID()  
		+ ", " + this.getEdgeToID()
		+ ", " + this.getCost()
		+ ")" ;
	}

	
}
