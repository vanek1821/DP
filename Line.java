package OSM2Tramod;

public class Line {

	private int ID;
	private String source;
	private String target;
	private String capacity;
	private String cost;
	private String reverseCost;
	private boolean valid;
	private String turn_restrictions;
	private String speed;
	private String roadType;
	private String geom_way;
	
	public Line(int id, String source, String target, String cost, String reverseCost, String speed, String roadType, String geom_way) {
		
		this.setID(id);
		this.setSource(source);
		this.setTarget(target);
		this.setCapacity("1800");
		this.setCost(cost);
		this.setReverseCost(reverseCost);
		this.setValid(true);
		this.setTurn_restrictions("");
		this.setSpeed(speed);
		this.setRoadType(roadType);
		this.setGeom_way(geom_way);
	}
	
	public Line() {
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getTurn_restrictions() {
		return turn_restrictions;
	}

	public void setTurn_restrictions(String turn_restrictions) {
		this.turn_restrictions = turn_restrictions;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getRoadType() {
		return roadType;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	public String getGeom_way() {
		return geom_way;
	}

	public void setGeom_way(String geom_way) {
		this.geom_way = geom_way;
	}
	
	public String getValid() {
		if (this.isValid())
			return "true";
		else
			return "false";
	}
	
	public String toString() {

		return "Line - ID: " + this.getID() + 
				" source: " + this.getSource() +
				" target: " + this.getTarget() +
				" capacity: " + this.getCapacity() +
				" cost: " + this.getCost() +
				" reverse_cost: " + this.getReverseCost() +
				" valid: " + this.getValid() +
				" turn_restrictions: " + this.getTurn_restrictions() +
				" speed: " + this.getSpeed() +
				" roadType: " + this.getRoadType() +
				" geom_way: " + this.getGeom_way();
		
	}
	
	public void print() {
		System.out.println(this.toString());
	}

	public String getReverseCost() {
		return reverseCost;
	}

	public void setReverseCost(String reverseCost) {
		this.reverseCost = reverseCost;
	}
	
}
