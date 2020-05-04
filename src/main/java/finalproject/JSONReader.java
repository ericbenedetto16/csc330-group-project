package finalproject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;

public class JSONReader  
{ 	
	private JSONObject jo; 	     
    private JSONArray features;
    private ArrayList<JSONObject> geometries;
    private ArrayList<JSONArray> coordinates;
    private ArrayList<ArrayList<double[]>> shapes;
    
	public JSONReader(String file) throws FileNotFoundException, IOException, ParseException {
		try {
		 Object obj = new JSONParser().parse(new FileReader(JSONReader.class.getClassLoader().getResource(file).getFile().replace("%20", " ")));
	     
		 jo = (JSONObject) obj; 
		 System.out.println(obj);
		 
	     features = (JSONArray)((JSONArray)(jo.get("features")));
	     geometries = new ArrayList<JSONObject>();
	     coordinates = new ArrayList<JSONArray>();
	     shapes = new ArrayList<ArrayList<double[]>>();
	     
	     setGeometries(features);
	     setCoordinates(geometries);
	     setShapes(coordinates);

	     for(ArrayList<double[]> shape : shapes) {
	    	 System.out.println("Shape");
	    	 
	    	 for(double[] coord : shape) {
	    		 System.out.println("[" + coord[0] + ", " + coord[1] + "]");
	    	 }
	     }
	 }catch(FileNotFoundException e) { 
		 System.out.println("File Could Not Be Found");
	 }catch(IOException e) {
		 System.out.println("File Could Not Be Read");
	 }catch(ParseException e) {
		 System.out.println("JSON Could Not Be Parsed");
	 }
	}
	
	private void setGeometries(JSONArray features) {
		for(Object f : features) {
	    	 geometries.add((JSONObject)((JSONObject)f).get("geometry"));
	     }
	}
	
	private void setCoordinates(ArrayList<JSONObject> geometries) {
		for(JSONObject g : geometries) {
	    	 coordinates.add((JSONArray)((JSONArray)((JSONArray)g.get("coordinates")).get(0)).get(0));
	     }
	} 
	
	private void setShapes(ArrayList<JSONArray> coordinates) {
		for(JSONArray p : coordinates) {
	    	 ArrayList<double[]> shape = new ArrayList<double[]>();
	    	 for(Object a : p) {
	    		 double x = (double) ((JSONArray)a).get(0), y = (double) ((JSONArray)a).get(1);
	    		 double[] point = new double[] {x,y};
	    		 shape.add((double[])point);
	    	 }
	    	 shapes.add(shape);
	     }
	}
	
	public ArrayList<ArrayList<double[]>> getShapes() {
		return shapes;
	}
}
	
