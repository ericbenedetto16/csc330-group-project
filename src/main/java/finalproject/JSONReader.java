package finalproject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;

public class JSONReader  
{ 	
	private JSONObject jo; 	     
    private JSONArray features;
    private ArrayList<JSONObject> geometries;
    private Map<String, JSONObject> objects;
    private Map<String, JSONArray> coordinatesMap;
    private ArrayList<JSONArray> coordinates;
    private ArrayList<ArrayList<double[]>> shapes;
    private Map<String, ArrayList<double[]>> shapesMap;
    
	public JSONReader(String file) throws FileNotFoundException, IOException, ParseException {
		try {
		 Object obj = new JSONParser().parse(new FileReader(JSONReader.class.getClassLoader().getResource(file).getFile().replace("%20", " ")));
	     
		 jo = (JSONObject) obj; 
		 System.out.println(obj);
		 
	     features = (JSONArray)((JSONArray)(jo.get("features")));
	     geometries = new ArrayList<JSONObject>();
		 objects = new HashMap<String,JSONObject>();
	     coordinates = new ArrayList<JSONArray>();
		 coordinatesMap = new HashMap<String,JSONArray>();
	     shapes = new ArrayList<ArrayList<double[]>>();
	     shapesMap = new HashMap<String,ArrayList<double[]>>();
	     
	     setGeometries(features);
	     setCoordinates(geometries);
	     setShapes(coordinates);
	     
	     System.out.println(shapes.size());
	     System.out.println(shapesMap.size());

	     for(ArrayList<double[]> shape : shapes) {
//	    	 System.out.println("Shape");
	    	 
	    	 for(double[] coord : shape) {
//	    		 System.out.println("[" + coord[0] + ", " + coord[1] + "]");
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
		int counter = 0;
		for(Object f : features) {
			System.out.println(counter);
	    	 geometries.add((JSONObject)((JSONObject)f).get("geometry"));
	    	 if(Objects.isNull((String)((JSONObject)((JSONObject)f).get("properties")).get("name"))) {
	    		 objects.put(" " + counter,(JSONObject)((JSONObject)f).get("geometry")); 
				}else {
					objects.put((String)((JSONObject)((JSONObject)f).get("properties")).get("name"),(JSONObject)((JSONObject)f).get("geometry")); 
				}
	    	 
	    	 counter++; 
		}
		
//		for(Object f : features) {
//			if(Objects.isNull((String)((JSONObject)((JSONObject)f).get("properties")).get("name"))) {
//				objects.put("N/A",(JSONObject)((JSONObject)f).get("geometry")); 
//			}else {
//				objects.put((String)((JSONObject)((JSONObject)f).get("properties")).get("name"),(JSONObject)((JSONObject)f).get("geometry")); 
//			}
//		}
		System.out.println(geometries.size());
		System.out.println(objects.size());
	}
	
	private void setCoordinates(ArrayList<JSONObject> geometries) {
		for(JSONObject g : geometries) {
	    	 coordinates.add((JSONArray)((JSONArray)((JSONArray)g.get("coordinates")).get(0)).get(0));
	     }
		
		Iterator<Entry<String, JSONObject>> itr = objects.entrySet().iterator();
		while(itr.hasNext()) {
			Map.Entry<String, JSONObject> pair = itr.next();
			String name = pair.getKey();
			JSONObject geom = pair.getValue();
			coordinatesMap.put(name,(JSONArray)((JSONArray)((JSONArray)geom.get("coordinates")).get(0)).get(0));
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
		
		Iterator<Entry<String, JSONArray>> itr = coordinatesMap.entrySet().iterator();
		while(itr.hasNext()) {
			Map.Entry<String, JSONArray> pair = itr.next();
			String name = pair.getKey();
			JSONArray geom = pair.getValue();
			
			ArrayList<double[]> shape = new ArrayList<double[]>();
	    	 for(Object a : geom) {
	    		 double x = (double) ((JSONArray)a).get(0), y = (double) ((JSONArray)a).get(1);
	    		 double[] point = new double[] {x,y};
	    		 shape.add((double[])point);
	    	 }
	    	 shapesMap.put(name,shape);
		}
	}
	
//	public ArrayList<ArrayList<double[]>> getShapes() {
//		return shapes;
//	}
	
	public Map<String,ArrayList<double[]>> getShapes() {
		return shapesMap;
	}
}
	
