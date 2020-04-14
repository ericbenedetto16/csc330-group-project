package finalproject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Processor {
	private MapObject[] objects;
	private CustomPolygon[] polygons;
	private Marker[] markers;
	private CustomPoint[] points;
	
	public void process() {
		String content = "";
		try
	    {
	        content = new String ( Files.readAllBytes( Paths.get("./finalproject/assets/hello.geojson") ) );
	    } 
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
		
	    System.out.printf("%s",content);
	}
	
	public MapObject[] getObjects() {
		return this.objects;
	}
	
	public CustomPolygon[] getPolygons() {
		return this.polygons;
	}
	
	public Marker[] getMarkers() {
		return this.markers;
	}
	
	public CustomPoint[] getPoints() {
		return this.points;
	}
}
