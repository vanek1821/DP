package OSM2Tramod;

public class LineOSM {
	
	private int ID;
	private String osm_id;
	private String osm_name;
	private String osm_meta;
	private String osm_source_id;
	private String osm_target_id;
	private String clazz;
	private String flags;
	private String source;
	private String target;
	private String km;
	private String kmh;
	private String cost;
	private String reverse_cost;
	private String x1;
	private String y1;
	private String x2;
	private String y2;
	private String addGeometryColumn;
	
	public LineOSM() {
		System.out.println("Line was Created. Empty properties.");
	}
	
	public LineOSM(int id, String osm_id, String osm_name, String osm_meta, String osm_source_id, String osm_target_id, String clazz, String flags, String source, String target, String km, String kmh, String cost, String reverseCost, String x1, String y1, String x2, String y2, String addGeometryColumn) {
		
		this.setID(id);
		this.setOsm_id(osm_id);
		this.setOsm_name(osm_name);
		this.setOsm_meta(osm_meta);
		this.setOsm_source_id(osm_source_id);
		this.setOsm_target_id(osm_target_id);
		this.setClazz(clazz);
		this.setFlags(flags);
		this.setSource(source);
		this.setTarget(target);
		this.setKm(km);
		this.setKmh(kmh);
		this.setCost(cost);
		this.setReverse_cost(reverseCost);
		this.setX1(x1);
		this.setY1(y1);
		this.setX2(x2);
		this.setY2(y2);
		this.setAddGeometryColumn(addGeometryColumn);
		//System.out.println("Line was Created. Filled properties. " + this.toString());
		
		
	}



	@Override
	public String toString() {
		return "Line: " + this.getID() + " Cost: " + this.getCost() + ", ReverseCost: "+ this.getReverse_cost() + ", x1: " + this.getX1() + ", y1: " + this.getY1() + ", x2: " + this.getX2() + ", y2: " + this.getY2();
		
	}
	
	public void print() {
		System.out.println(this.toString());
	}
	
	public String toSQLString() {
		return "(" + this.getID()
				+ ", " + this.getOsm_id() 
				+ ", "+ this.getOsm_name() 
				+ ", " + this.getOsm_meta() 
				+ ", " + this.getOsm_source_id() 
				+ ", " + this.getOsm_target_id() 
				+ ", " + this.getClazz()
				+ ", " + this.getFlags()
				+ ", " + this.getSource()
				+ ", " + this.getTarget() 
				+ ", " + this.getKm() 
				+ ", " + this.getKmh() 
				+ ", " + this.getCost()
				+ ", " + this.getReverse_cost() 
				+ ", " + this.getX1()
				+ ", " + this.getY1()
				+ ", " + this.getX2()
				+ ", " + this.getY2()
				+ ", " + this.getAddGeometryColumn()
				+ ")" ;
	}
	
	public String toTramodSQLString() {
		/*CREATE TABLE edge
		(
		  edge_id serial PRIMARY KEY,					//id
		  source int NOT NULL REFERENCES node(node_id),		//source
		  target int NOT NULL REFERENCES node(node_id),		//target	
		  capacity real NOT NULL,						//1800 ( bude se měnit )
		  cost real NOT NULL,						//cost
		  isvalid boolean NOT NULL,						//true
		  turn_restriction text NOT NULL,					//prázdnej řetězec
		  speed real NOT NULL,						//kmh
		  road_type int,							//clazz
		  geometry public.geometry(LineString,4326) NOT NULL		//geom_way
		);*/
		return "(" + this.getID()
		+ ", " + this.getSource() 
		+ ", "+ this.getTarget() 
		+ ", 1800"  
		+ ", " + this.getCost() 
		+ ", true" 
		+ ",''"
		+ ", " + this.getKmh()
		+ ", " + this.getClazz() 
		+ ", " + this.getAddGeometryColumn()
		+ ")" ;

	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getOsm_id() {
		return osm_id;
	}

	public void setOsm_id(String osm_id) {
		this.osm_id = osm_id;
	}

	public String getOsm_name() {
		return osm_name;
	}

	public void setOsm_name(String osm_name) {
		this.osm_name = osm_name;
	}

	public String getOsm_meta() {
		return osm_meta;
	}

	public void setOsm_meta(String osm_meta) {
		this.osm_meta = osm_meta;
	}

	public String getOsm_source_id() {
		return osm_source_id;
	}

	public void setOsm_source_id(String osm_source_id) {
		this.osm_source_id = osm_source_id;
	}

	public String getOsm_target_id() {
		return osm_target_id;
	}

	public void setOsm_target_id(String osm_target_id) {
		this.osm_target_id = osm_target_id;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public String getKmh() {
		return kmh;
	}

	public void setKmh(String kmh) {
		this.kmh = kmh;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getReverse_cost() {
		return reverse_cost;
	}

	public void setReverse_cost(String reverse_cost) {
		this.reverse_cost = reverse_cost;
	}

	public String getX1() {
		return x1;
	}

	public void setX1(String x1) {
		this.x1 = x1;
	}

	public String getY1() {
		return y1;
	}

	public void setY1(String y1) {
		this.y1 = y1;
	}

	public String getX2() {
		return x2;
	}

	public void setX2(String x2) {
		this.x2 = x2;
	}

	public String getY2() {
		return y2;
	}

	public void setY2(String y2) {
		this.y2 = y2;
	}
	
	public boolean isDuplex() {
		
		//if(this.getCost().equals(this.getReverse_cost())) {
		if(!this.getReverse_cost().equals("1000000.0")) {
			//System.out.println("ID: " + this.getID() + ", Cost: " + this.getCost() + " - ReverseCost: " + this.getReverse_cost());
			return true;
		}
		else {
			//System.out.println("ID: " + this.getID() + ", Cost: " + this.getCost() + " - ReverseCost: " + this.getReverse_cost());
			return false;
		}
	}

	public String getAddGeometryColumn() {
		return addGeometryColumn;
	}

	public void setAddGeometryColumn(String addGeometryColumn) {
		this.addGeometryColumn = addGeometryColumn;
	}
	
}
