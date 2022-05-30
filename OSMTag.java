package OSM2Tramod;

public class OSMTag {
	private String key;
	private String value;
	
	
	public OSMTag(String k, String v) {
		this.setKey(k);
		this.setValue(v);
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

}
