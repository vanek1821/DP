package OSM2Tramod;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class DataContainerHandler extends DefaultHandler {


	private StringBuilder currentValue = new StringBuilder();

	private DataContainer dataContainer;
	private OSMBounds osmBounds;
	private OSMNode osmNode;
	private OSMTag osmTag;
	private OSMBuilding osmBuilding;
	private OSMNode referencedNode;

	private boolean isInNode, isInWay;

	public DataContainerHandler(DataContainer dataContainer) {
		this.setDataContainer(dataContainer);
		this.isInNode = false;
		this.isInWay = false;
	}

	@Override
	public void startDocument() {
		System.out.println("Start Document");
	}

	@Override
	public void endDocument() {
		System.out.println("End Document");
	}

	@Override
	public void startElement(
			String uri,
			String localName,
			String qName,
			Attributes attributes) {

		// reset the tag value
		currentValue.setLength(0);

		//System.out.println(q);
		
		if (qName.equalsIgnoreCase("bounds")) {
			// get tag's attribute by name
			/*String minlat = attributes.getValue("minlat");
			String minlon = attributes.getValue("minlon");
			String maxlat = attributes.getValue("maxlat");
			String maxlon = attributes.getValue("maxlon");
			System.out.printf("Bounds id : %s, %s, %s, %s%n", minlat, minlon, maxlat, maxlon);
			 */
			this.setOsmBounds(new OSMBounds(Double.valueOf(attributes.getValue("minlon")), Double.valueOf(attributes.getValue("minlat")), Double.valueOf(attributes.getValue("maxlon")), Double.valueOf(attributes.getValue("maxlat"))));
			
		}


		if (qName.equalsIgnoreCase("node")) {
			
			
			this.setOsmNode(new OSMNode(Long.valueOf(attributes.getValue("id")), Double.valueOf(attributes.getValue("lon")), Double.valueOf(attributes.getValue("lat"))));
			//this.setOsmNode(new OSMNode(Long.valueOf(attributes.getValue("id")), Double.valueOf(attributes.getValue("lon")), Double.valueOf(attributes.getValue("lat")), Integer.valueOf(attributes.getValue("version")), attributes.getValue("timestamp"), Integer.valueOf(attributes.getValue("changeset"))));
			this.isInNode= true;
		}
		if (qName.equalsIgnoreCase("way")) {
			this.setOsmBuilding(new OSMBuilding(Long.valueOf(attributes.getValue("id"))));
			//this.setOsmBuilding(new OSMBuilding(Long.valueOf(attributes.getValue("id")),  Integer.valueOf(attributes.getValue("version")), attributes.getValue("timestamp"), Integer.valueOf(attributes.getValue("changeset"))));
			this.isInWay= true;
		}

		if (qName.equalsIgnoreCase("tag")) {

			this.setOsmTag(new OSMTag(attributes.getValue("k"),attributes.getValue("v")));
		}

		if (qName.equalsIgnoreCase("nd")) {

			this.setReferencedNode(dataContainer.getNode(attributes.getValue("ref")));
		}




	}

	@Override
	public void endElement(String uri,
			String localName,
			String qName) {

		//System.out.printf("End Element : %s%n", qName);

		if (qName.equalsIgnoreCase("bounds")) {
			this.getDataContainer().setBounds(this.getOsmBounds());
			System.out.println(this.getDataContainer().getBounds().toString());

		}

		if (qName.equalsIgnoreCase("node")) {
//			this.getDataContainer().getNodes().put(this.getOsmNode());
			this.getDataContainer().getNodes().put(this.getOsmNode().getId(), this.getOsmNode());
			this.isInNode = false;
			//System.out.println(this.getOsmNode().toString());

		}
		if (qName.equalsIgnoreCase("way")) {
			this.getDataContainer().getBuildings().add(this.getOsmBuilding());
			this.isInWay = false;
			//System.out.println(this.getOsmBuilding().toString());

		}
		if (qName.equalsIgnoreCase("tag")) {

			if(this.isInNode==true) { this.getOsmNode().getTagList().add(this.getOsmTag());	}
			if(this.isInWay==true) { this.getOsmBuilding().getTagList().add(this.getOsmTag());	}

		}
		if (qName.equalsIgnoreCase("nd")) {

			if(this.isInWay==true) { this.getOsmBuilding().getNodeList().add(this.getReferencedNode()); }

		}
		
	}

	@Override
	public void characters(char ch[], int start, int length) {

		// The characters() method can be called multiple times for a single text node.
		// Some values may missing if assign to a new string

		// avoid doing this
		// value = new String(ch, start, length);

		// better append it, works for single or multiple calls
		currentValue.append(ch, start, length);

	}

	public DataContainer getDataContainer() {
		return dataContainer;
	}

	public void setDataContainer(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}

	public OSMBounds getOsmBounds() {
		return osmBounds;
	}

	public void setOsmBounds(OSMBounds osmBounds) {
		this.osmBounds = osmBounds;
	}

	public OSMNode getOsmNode() {
		return osmNode;
	}

	public void setOsmNode(OSMNode osmNode) {
		this.osmNode = osmNode;
	}

	public OSMTag getOsmTag() {
		return osmTag;
	}

	public void setOsmTag(OSMTag osmTag) {
		this.osmTag = osmTag;
	}

	public OSMBuilding getOsmBuilding() {
		return osmBuilding;
	}

	public void setOsmBuilding(OSMBuilding osmBuilding) {
		this.osmBuilding = osmBuilding;
	}

	public OSMNode getReferencedNode() {
		return referencedNode;
	}

	public void setReferencedNode(OSMNode referencedNode) {
		this.referencedNode = referencedNode;
	}
}