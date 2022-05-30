package OSM2Tramod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.locationtech.jts.io.ParseException;

public class Modifier {

	//private Records records;
	private DataContainer dataContainer;
	
	public Modifier(DataContainer dataContainer) {
		this.setDataContainer(dataContainer);
		//this.setModifiedLines(new ModifiedLines());
		
		System.out.println("Modifier succesfully initialized.");
		
	}

	public void execute(){
		int duplex = 0, nonDuplex = 0;
		
		
		for (LineOSM line : this.getDataContainer().getLines().getLineList()) {
			if(line.isDuplex()) {
				//this.getModifiedLines().getLines().add(new LineChangesTree(line));
				this.dataContainer.getModifiedLines().getLines().put(line.getID() ,new LineChangesTree(line));
				duplex++;
			}
			else {
				nonDuplex++;
			}	
		}
		this.processLines();
		this.processVertexes();
		this.convertVertexesCoordinates();
	}

	private void convertVertexesCoordinates() {
		try {
			this.dataContainer.getVertexes().convertToCoordinates();
		} catch (ParseException e) {
			System.out.println("Something went wrong in Coordinates conversion");
			e.printStackTrace();
		}
		
	}

	private void processLines() {
		int newIDcounter = this.getDataContainer().getLines().getLineList().size();
		
		for (Map.Entry<Integer, LineChangesTree> line : this.dataContainer.getModifiedLines().getLines().entrySet()) {
            line.getValue().modify(++newIDcounter);
            //line.getValue().printChanges();
        }
		
		System.out.println("Lines successfully modified. Modified " + this.dataContainer.getModifiedLines().getLines().size() + " lines.");
		
	}
	
	private void processVertexes() {
		String restrictionFullString;
		String originalRestriction;
		String newRestriction;
		String regexRestriction = "[-]\\d*_\\d*";
		Pattern patternRestriction = Pattern.compile(regexRestriction);
		String regexOrder = "[+]\\d*_\\d*";
		Pattern patternOrder = Pattern.compile(regexOrder);
		
		for (Vertex vertex : this.dataContainer.getVertexes().getVertexList()) {
			if(vertex.hasTurnRestriction()) {
				
				restrictionFullString = vertex.getRestrictions().replaceAll("'", "");
				originalRestriction = vertex.getRestrictions().replaceAll("'", "");
				
				//THIS PART CREATES RESTRICTIONS OUT OF ORDERS
				Matcher matcherOrder = patternOrder.matcher(restrictionFullString);
				
				List<String> orders = new ArrayList<>();
				while(matcherOrder.find()) {
					orders.add(matcherOrder.group());
				}
				
				for (String order : orders) {
					newRestriction = orderToRestriction(vertex, order, originalRestriction);
					restrictionFullString = restrictionFullString.replace(order, newRestriction);
				}
				
				
				//THIS PART SWITCHES ALL RESTRICTIONS IF NEEDED
				Matcher matcherRestriction = patternRestriction.matcher(restrictionFullString);
				
				List<String> restrictions = new ArrayList<>();
				while (matcherRestriction.find()) {
				    restrictions.add(matcherRestriction.group());
				}
				
				String resTmp;
				String[] resSplit;
				for (String restriction : restrictions) {
					restriction = turnRestrictionSwitch(vertex, restriction);
					resTmp = restriction.replaceAll("-", "");
					resSplit = resTmp.split("_");
					
					if(!resSplit[0].equals("0") && !resSplit[1].equals("0")) {
						TurnRestriction tmpTR = new TurnRestriction(String.valueOf(vertex.getID()), resSplit[0], resSplit[1], "-1");
						if(!dataContainer.getTurnRestrictions().isAlreadyRestricted(String.valueOf(vertex.getID()), resSplit[0], resSplit[1])) { // nepřidávání duplicit
							dataContainer.getTurnRestrictions().getTurnRestrictionList().add(tmpTR);
						}
						
					}
				}
				
				
				
				String newRestrictionString = "'";
				
				for (String restriction : restrictions) {
					newRestrictionString += restriction;
				}
				
				newRestrictionString += "'";
				
				vertex.setRestrictions(newRestrictionString);
				
			}	
		}
	}

	private String orderToRestriction(Vertex vertex, String order, String restrictionFullString) {
		String orderSplit[];
		String newRestrictions = "";
		String potentialRestriction;
		String orderToCheck;
		
		//System.out.println("______________" + "\nrestrictionFullString: " +restrictionFullString + "\norder: " + order );
		
		order = order.replaceAll("\\+", "");
		orderSplit = order.split("_");
		
		
		for (LineOSM line : this.dataContainer.getLines().getLineList()) {
			if((vertex.getID()==Integer.valueOf(line.getSource()))||(vertex.getID()==Integer.valueOf(line.getTarget()))) {
				orderToCheck = "+" + orderSplit[0] + "_" + Integer.toString(line.getID());
				potentialRestriction = "-" + orderSplit[0] + "_" + Integer.toString(line.getID());
				if(!restrictionFullString.contains(orderToCheck)) {
					newRestrictions += potentialRestriction;
				}
			}
		}
		//System.out.println(newRestrictions);
		//System.out.println(restrictionFullString);
		return newRestrictions;
	}

	//Otočí turn_restrictions z důvodu předem neznámému orientovanému grafu
	private String turnRestrictionSwitch(Vertex vertex, String restriction) {
		
		String restrictionSplit[];
		String oldLine, newLine;
		LineChangesTree line;
		
		restriction = restriction.replaceAll("-", "");
		restrictionSplit = restriction.split("_");
		
		//odkud -> kam ----> hledam, jestli existuje lajna v modified, ktera ma tenhle vertex jako targe
		if (this.dataContainer.getModifiedLines().getLines().containsKey(Integer.valueOf(restrictionSplit[0]))) {
			line = this.dataContainer.getModifiedLines().getLines().get(Integer.valueOf(restrictionSplit[0]));
			if(vertex.getID() == Integer.valueOf(line.getNewLine().getTarget())) {
				restrictionSplit[0] = Integer.toString(line.getNewLine().getID());
			}
			else {
				//restrictionSplit[0] = Integer.toString(line.getCopiedLine().getID());
				restrictionSplit[0] = Integer.toString(line.getOriginalLine().getID());
			}
			
		}
		//odkud -> kam ----> hledam, jestli existuje lajna v modified, ktera ma tenhle vertex jako source
		if (this.dataContainer.getModifiedLines().getLines().containsKey(Integer.valueOf(restrictionSplit[1]))) {
			line = this.dataContainer.getModifiedLines().getLines().get(Integer.valueOf(restrictionSplit[1]));
			if(vertex.getID() == Integer.valueOf(line.getNewLine().getSource())) {
				restrictionSplit[1] = Integer.toString(line.getNewLine().getID());
			}
			else {
				//restrictionSplit[1] = Integer.toString(line.getCopiedLine().getID());
				restrictionSplit[1] = Integer.toString(line.getOriginalLine().getID());
			}
			
		}
		restriction = "-" + restrictionSplit[0] + "_" + restrictionSplit[1];
		return restriction;
	}

	/*public Records getRecords() {
		return records;
	}

	public void setRecords(Records records) {
		this.records = records;
	}
*/
	public DataContainer getDataContainer() {
		return dataContainer;
	}

	public void setDataContainer(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}


}
