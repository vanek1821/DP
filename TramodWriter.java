package OSM2Tramod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class TramodWriter {
	
	private String lineFileName;
	private String restrictionFileName;
	private String vertexesFileName;
	private String zonesFileName;
	//private Records records;
	private DataContainer dataContainer;
	private String projectName;

	public TramodWriter(String lineFileName, String restrictionFileName, String vertexesFileName, String zonesFileName, DataContainer dataContainer, String projectName) {
		this.setLineFileName(lineFileName);
		this.setRestrictionFileName(restrictionFileName);
		this.setVertexesFileName(vertexesFileName);
		this.setZonesFileName(zonesFileName);
		this.setDataContainer(dataContainer);
		this.setProjectName(projectName);
		
	}

	

	public void executeWritingLines() {
		try {
			File myObj = new File(lineFileName);
			HashMap<Integer, LineChangesTree> modifiedLines = this.dataContainer.getModifiedLines().getLines();
			if(!myObj.exists()) myObj.createNewFile();
			
			FileWriter lineWriter = new FileWriter(myObj);
			lineWriter.write(
					"-- Created by  : osm2tramod\n" + 
					"-- Version     : 3.0.00\n" + 
					"-- Author (c)  : Jakub Vanek\n" +  
					"\n" + 
					"SET client_encoding = 'UTF8';\n" + 
					"\n" + 
					"DROP TABLE IF EXISTS edge;\n" +  
					"\n" + 
					"CREATE TABLE edge\n" +
					"(\n" +
					  "edge_id serial PRIMARY KEY,\n" +
					  "source int NOT NULL REFERENCES node(node_id),\n" +
					  "target int NOT NULL REFERENCES node(node_id),\n"	+
					  "capacity real NOT NULL,\n" + 
					  "cost real NOT NULL,\n" +
					  "isvalid boolean NOT NULL,\n" +
					  "turn_restriction text NOT NULL,\n" +
					  "speed real NOT NULL,\n" +
					  "road_type int,\n" +
					  "geometry public.geometry(MultiLineString,4326) NOT NULL\n"+
					");\n"
					);

			int i = 0;
			for (LineOSM line : this.dataContainer.getLines().getLineList()) {
				if(i%50 == 0) lineWriter.write("\nINSERT INTO edge VALUES\n");
				
				lineWriter.write(line.toTramodSQLString());
				
				//if(((i+1)%50 == 0)||(i == (this.records.getLines().getLineList().size()-1))) lineWriter.write(";");
				if(((i+1)%50 == 0)) lineWriter.write(";");
				else lineWriter.write(",");
				lineWriter.write("\n");
				i++;
			}
			
			for (LineChangesTree lct : modifiedLines.values()) {
				if(i%50 == 0) lineWriter.write("\nINSERT INTO edge VALUES\n");
				
				lineWriter.write(lct.getNewLine().toTramodSQLString());
				if(((i+1)%50 == 0)||(i == (this.dataContainer.getLines().getLineList().size() + modifiedLines.size() - 1))) lineWriter.write(";");
				else lineWriter.write(",");
				lineWriter.write("\n");
				i++;
			}
			
			lineWriter.write(
					"ALTER TABLE edge ADD COLUMN geometry2 geometry(LineString, 4326)" +
					"UPDATE edge" +
					"SET geometry2=ST_LineMerge(geometry)" +
					"WHERE true;"+
					"ALTER TABLE edge RENAME COLUMN geometry TO geometry_mls;"+
					"ALTER TABLE edge RENAME COLUMN geometry2 TO geometry;"+
					");\n"
					);
			
			lineWriter.close();
			System.out.println("Successfully wrote to the file" + lineFileName + "...");
			
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
	}
	
	public void executeWritingTurnRestrictions() {
		try {
			File myObj = new File(restrictionFileName);
			if(!myObj.exists()) myObj.createNewFile();
			
			FileWriter turnRestrictionWriter = new FileWriter(myObj);
			turnRestrictionWriter.write(
					"-- Created by  : osm2tramod\n" + 
					"-- Version     : 3.0.00\n" + 
					"-- Author (c)  : Jakub Vanek\n" + 
					 
					"\n" + 
					"SET client_encoding = 'UTF8';\n" + 
					"\n" + 
					"DROP TABLE IF EXISTS turn_restriction;\n" +  
					"\n" + 
					"CREATE TABLE turn_restriction (\n" + 
					"    node_id integer NOT NULL REFERENCES node(node_id),\n" + 
					"    from_edge_id integer NOT NULL REFERENCES edge(edge_id),\n" + 
					"    to_edge_id integer NOT NULL REFERENCES edge(edge_id),\n" + 
					"    cost real NOT NULL,\n" + 
					"    PRIMARY KEY (node_id, from_edge_id, to_edge_id)\n" + 
					");\n" + 
					"\n"
					);
			int i = 0;
			for (TurnRestriction turnRestriction : this.dataContainer.getTurnRestrictions().getTurnRestrictionList()) {
				if(i%50 == 0) turnRestrictionWriter.write("\nINSERT INTO turn_restriction VALUES\n");
				turnRestrictionWriter.write(turnRestriction.toTramodSQLString());
				if(((i+1)%50 == 0)||(i == (this.dataContainer.getTurnRestrictions().getTurnRestrictionList().size()-1))) turnRestrictionWriter.write(";");
				else turnRestrictionWriter.write(",");
				turnRestrictionWriter.write("\n");
				i++;
			}
			
			
			turnRestrictionWriter.close();
			System.out.println("Successfully wrote to the file " + restrictionFileName + "...");
			
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public void executeWritingNodes() {
		try {
			File myObj = new File(vertexesFileName);
			if(!myObj.exists()) myObj.createNewFile();
			
			FileWriter vertexWriter = new FileWriter(myObj);
			vertexWriter.write(
					"-- Created by  : osm2tramod\n" + 
					"-- Version     : 3.0.00\n" + 
					"-- Author (c)  : Jakub Vanek\n" + 
					 
					"\n" + 
					"SET client_encoding = 'UTF8';\n" + 
					"\n" + 
					"DROP TABLE IF EXISTS node;\n" +  
					"\n" + 
					"CREATE TABLE node\n" + 
					"( \n" +
					 "node_id int primary key,\n" +
					 "geometry public.geometry(Point, 4326) NOT NULL\n" +
					")"
					);
			int i = 0;
			for (Vertex vertex : this.dataContainer.getVertexes().getVertexList()) {
				if(i%50 == 0) vertexWriter.write("\nINSERT INTO node VALUES\n");
				vertexWriter.write(vertex.toTramodSQLString());
				if(((i+1)%50 == 0)||(i == (this.dataContainer.getVertexes().getVertexList().size()-1))) vertexWriter.write(";");
				else vertexWriter.write(",");
				vertexWriter.write("\n");
				i++;
			}
			
			
			vertexWriter.close();
			System.out.println("Successfully wrote to the file " + vertexesFileName + "...");
			
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public void executeWritingZones() {
		try {
			File myObj = new File(zonesFileName);
			if(!myObj.exists()) myObj.createNewFile();
			
			FileWriter zoneWriter = new FileWriter(myObj);
			zoneWriter.write(
					"-- Created by  : osm2tramod\n" + 
					"-- Version     : 3.0.00\n" + 
					"-- Author (c)  : Jakub Vanek\n" + 
					"\n" + 
					"SET client_encoding = 'UTF8';\n" + 
					"\n" + 
					"DROP TABLE IF EXISTS zone;\n" +  
					"\n" + 
					"CREATE TABLE zone\n" + 
					"( \n" +
					"zone_id int primary key,\n" +
					"node_id int NOT NULL,\n" +
					"trips real NOT NULL,\n" +
					"geometry public.geometry(Point, 4326) NOT NULL\n" +
					")"
					);

			
			
			int i = 0;
			Iterator<HashMap.Entry<GridLocator, Tile>> it = this.dataContainer.getGrid().getTiles().entrySet().iterator();
			
			while (it.hasNext()) {
				HashMap.Entry<GridLocator, Tile> pair = it.next();
				
				if(pair.getValue().getVertex()!=null) {
					if(i%50 == 0) zoneWriter.write("\nINSERT INTO zone VALUES\n");
					zoneWriter.write(pair.getValue().toTramodSQLString());
					if(((i+1)%50 == 0)||(!it.hasNext())) zoneWriter.write(";");
					else zoneWriter.write(",");
					zoneWriter.write("\n");
					i++;
				}
				
			}
			
			zoneWriter.close();
			System.out.println("Successfully wrote to the file "+zonesFileName+"..." );
			
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
	}


	
	
	public String getLineFileName() {
		return lineFileName;
	}

	public void setLineFileName(String lineFileName) {
		this.lineFileName = lineFileName;
	}
	public void setVertexesFileName(String vertexesFileName) {
		this.vertexesFileName = vertexesFileName;
	}

	public String getRestrictionFileName() {
		return restrictionFileName;
	}

	public void setRestrictionFileName(String vertexFileName) {
		this.restrictionFileName = vertexFileName;
	}

	/*public Records getRecords() {
		return records;
	}

	public void setRecords(Records records) {
		this.records = records;
	}*/
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectName() {
		return this.projectName;
	}



	public DataContainer getDataContainer() {
		return dataContainer;
	}



	public void setDataContainer(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}



	public String getZonesFileName() {
		return zonesFileName;
	}



	public void setZonesFileName(String zonesFileName) {
		this.zonesFileName = zonesFileName;
	}


}
