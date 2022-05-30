package OSM2Tramod;

import java.util.ArrayList;

public class TurnRestrictions {

	
private ArrayList<TurnRestriction> turnRestrictionList;
	
	public TurnRestrictions() {
		setTurnRestrictionList(new ArrayList<TurnRestriction>());
		System.out.println("TurnRestrictionList succesfully initialized.");
	}

	public ArrayList<TurnRestriction> getTurnRestrictionList() {
		return turnRestrictionList;
	}

	public void setTurnRestrictionList(ArrayList<TurnRestriction> turnRestrictionList) {
		this.turnRestrictionList = turnRestrictionList;
	}

	public boolean isAlreadyRestricted(String id, String from, String to) {
		for (TurnRestriction tr : turnRestrictionList) {
			if(tr.getNodeID().equals(id) && tr.getEdgeFromID().equals(from) && tr.getEdgeToID().equals(to)) return true;
		}
		return false;
	}
	
	
}
