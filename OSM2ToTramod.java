package OSM2Tramod;

import java.io.IOException;


public class OSM2ToTramod {
	
	
	public static void main(String[] args) throws IOException {
		//System.out.println("Hello World!");
		
		//TODO ověřit počty parametrů a jejich hodnoty
		
		//int userGridRequest = 1000;
		String tileCount = args[1];
		int userGridRequest = Integer.valueOf(tileCount);
		//String projectName = "Pilsen";
		
		String projectName = args[0];
		String VertexesFileName = args[2];
		//String VertexesFileName = "src/OSM2Tramod/2po_sources_"+projectName+"/"+projectName+"_2po_vertex.sql";		
		
		String edgesFileName = args[3];
		//String edgesFileName = "src/OSM2Tramod/2po_sources_"+projectName+"/"+projectName+"_2po_4pgr.sql";
		
		String buildingsFileName = args[4];
		//String buildingsFileName = "src/OSM2Tramod/2po_sources_"+projectName+"/"+projectName+"-buildings.osm";
		
		String outputFolder = args[5];
		
		System.out.println("OSM2Tramod");

		long startTime = System.nanoTime();
				
		DataContainer dataContainer = new DataContainer();
				
		PoReader po = new PoReader(edgesFileName, VertexesFileName, dataContainer);
		//PoReader po = new PoReader("parametr1", "parametr2");
		po.executeReadingLines();
		po.executeReadingVertexes();
		
		
		
		 
		OSMBuildingReader obr = new OSMBuildingReader(buildingsFileName, dataContainer);
		obr.load();

		Modifier modifier = new Modifier(dataContainer);
		modifier.execute();
	
		
	
		System.out.println("Calculating areas..");

		dataContainer.calculateBuildingAreas();

		System.out.println("Areas have been calculated.");

		System.out.println("Calculating centroids..");

		dataContainer.calculateBuildingCentroids();

		System.out.println("Centroids have been calculated");
		
		System.out.println("User Input: " + userGridRequest);
		
		dataContainer.processGrid(userGridRequest);
		
		System.out.println("Real number of tiles: " + dataContainer.getGrid().getTiles().size());
		
		
		dataContainer.assignTrafficGenerators();
		
		System.out.println("Counting tiles without vertex");
		
		System.out.println("Tiles without vertexes: "+ dataContainer.countTilesWithoutVertex());
		
		System.out.println("Assigning traffic Generators without vertex...");
		
		dataContainer.assignVertexlessGenerators();
		dataContainer.assignZoneIDs();
		
		
		int uncountedBuildings = 0;
		
		//dataContainer.printTiles();
		
		
		
		for (OSMBuilding b : dataContainer.getBuildings()) {
			if(b.flag == false) uncountedBuildings++;//System.out.println(b.toString());
		}

		//obr.executeReadingRecords(dataContainer);
		System.out.println("Number of unassigned buildings: " + uncountedBuildings);
		System.out.println("Buildings: " + dataContainer.getBuildings().size());
		System.out.println("Nodes: " + dataContainer.getNodes().size());

		System.out.println("done...");

		TramodWriter trW = new TramodWriter(outputFolder+"/"+projectName + "_Tramod_Edges.sql", outputFolder+"/"+projectName + "_Tramod_Turn_Restrictions.sql", outputFolder+"/"+projectName + "_Tramod_Vertexes.sql", outputFolder+"/"+projectName + "_Tramod_Zones.sql", dataContainer, projectName);
		trW.executeWritingLines();
		trW.executeWritingTurnRestrictions();
		trW.executeWritingNodes();
		trW.executeWritingZones();
		
		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;
	
		System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);

		
	}

}
