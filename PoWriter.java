package OSM2Tramod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class PoWriter {
	
	private String lineFileName;
	private String vertexFileName;
	//private Records records;
	private DataContainer dataContainer;
	private String projectName;

	public PoWriter(String lineFileName, String vertexFileName, DataContainer dataContainer, String projectName) {
		this.setLineFileName(lineFileName);
		this.setVertexFileName(vertexFileName);
		this.setDataContainer(dataContainer);
		this.setProjectName(projectName);
	}

	




	public void executeWritingLines() {
		try {
			File myObj = new File("Lines.sql");
			HashMap<Integer, LineChangesTree> modifiedLines = this.dataContainer.getModifiedLines().getLines();
			if(!myObj.exists()) myObj.createNewFile();
			
			FileWriter lineWriter = new FileWriter(myObj);
			lineWriter.write(
					"-- Created by  : osm2po-core\n" + 
					"-- Version     : 5.2.43\n" + 
					"-- Author (c)  : Jakub Vanek\n" + 
					"-- Date        : Sat Sep 05 13:22:29 CEST 2020\n" + 
					"\n" + 
					"SET client_encoding = 'UTF8';\n" + 
					"\n" + 
					"DROP TABLE IF EXISTS "+this.projectName+"_2po_4pgr;\n" + 
					"-- SELECT DropGeometryTable('"+this.projectName+"_2po_4pgr');\n" + 
					"\n" + 
					"CREATE TABLE "+this.projectName+"_2po_4pgr(id integer, osm_id bigint, osm_name character varying, osm_meta character varying, osm_source_id bigint, osm_target_id bigint, clazz integer, flags integer, source integer, target integer, km double precision, kmh integer, cost double precision, reverse_cost double precision, x1 double precision, y1 double precision, x2 double precision, y2 double precision);\n" + 
					"SELECT AddGeometryColumn('"+this.projectName+"_2po_4pgr', 'geom_way', 4326, 'MULTILINESTRING', 2);\n" 
					);

			int i = 0;
			for (LineOSM line : this.dataContainer.getLines().getLineList()) {
				if(i%50 == 0) lineWriter.write("\nINSERT INTO "+this.projectName+"_2po_vertex VALUES\n");
				
				if(modifiedLines.containsKey(line.getID())) {
					//lineWriter.write(modifiedLines.get(line.getID()).getCopiedLine().toSQLString());
					lineWriter.write(modifiedLines.get(line.getID()).getOriginalLine().toSQLString());
				}
				else {
					lineWriter.write(line.toSQLString());
				}
				//if(((i+1)%50 == 0)||(i == (this.records.getLines().getLineList().size()-1))) lineWriter.write(";");
				if(((i+1)%50 == 0)) lineWriter.write(";");
				else lineWriter.write(",");
				lineWriter.write("\n");
				i++;
			}
			
			for (LineChangesTree lct : modifiedLines.values()) {
				if(i%50 == 0) lineWriter.write("\nINSERT INTO "+this.projectName+"_2po_vertex VALUES\n");
				
				lineWriter.write(lct.getNewLine().toSQLString());
				if(((i+1)%50 == 0)||(i == (this.dataContainer.getLines().getLineList().size() + modifiedLines.size() - 1))) lineWriter.write(";");
				else lineWriter.write(",");
				lineWriter.write("\n");
				i++;
			}
			
			lineWriter.write(
					"\nALTER TABLE "+this.projectName+"_2po_4pgr ADD CONSTRAINT pkey_"+this.projectName+"_2po_4pgr PRIMARY KEY(id);\n" + 
					"CREATE INDEX idx_"+this.projectName+"_2po_4pgr_source ON "+this.projectName+"_2po_4pgr(source);\n" + 
					"CREATE INDEX idx_"+this.projectName+"_2po_4pgr_target ON "+this.projectName+"_2po_4pgr(target);\n"
					);
			
			lineWriter.close();
			System.out.println("Successfully wrote to the file.");
			
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
	}
	
	public void executeWritingVertexes() {
		try {
			File myObj = new File("Vertexes.sql");
			if(!myObj.exists()) myObj.createNewFile();
			
			FileWriter vertexWriter = new FileWriter(myObj);
			vertexWriter.write(
					"-- Created by  : osm2po-core\n" + 
					"-- Version     : 5.2.43\n" + 
					"-- Author (c)  : Jakub Vanek - OSM2Po\n" + 
					 
					"\n" + 
					"SET client_encoding = 'UTF8';\n" + 
					"\n" + 
					"DROP TABLE IF EXISTS "+this.projectName+"_2po_vertex;\n" + 
					"-- SELECT DropGeometryTable('"+this.projectName+"_2po_vertex');\n" + 
					"\n" + 
					"CREATE TABLE "+this.projectName+"_2po_vertex(id integer, clazz integer, osm_id bigint, osm_name character varying, ref_count integer, restrictions character varying);\n" + 
					"SELECT AddGeometryColumn('"+this.projectName+"_2po_vertex', 'geom_vertex', 4326, 'POINT', 2);\n"
					);
			int i = 0;
			for (Vertex vertex : this.dataContainer.getVertexes().getVertexList()) {
				if(i%50 == 0) vertexWriter.write("\nINSERT INTO "+this.projectName+"_2po_vertex VALUES\n");
				vertexWriter.write(vertex.toSQLString());
				if(((i+1)%50 == 0)||(i == (this.dataContainer.getVertexes().getVertexList().size()-1))) vertexWriter.write(";");
				else vertexWriter.write(",");
				vertexWriter.write("\n");
				i++;
			}
			
			
			vertexWriter.write(
					"\nALTER TABLE "+this.projectName+"_2po_vertex ADD CONSTRAINT pkey_"+this.projectName+"_2po_vertex PRIMARY KEY(id);\n" + 
					"CREATE INDEX idx_"+this.projectName+"_2po_vertex_osm_id ON "+this.projectName+"_2po_vertex(osm_id);"
					);
			
			vertexWriter.close();
			System.out.println("Successfully wrote to the file.");
			
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

	public String getVertexFileName() {
		return vertexFileName;
	}

	public void setVertexFileName(String vertexFileName) {
		this.vertexFileName = vertexFileName;
	}

	/*public Records getRecords() {
		return records;
	}

	public void setRecords(Records records) {
		this.records = records;
	}
	*/
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










}
