package finalproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private Map<String, ArrayList<double[]>> shapesMap;
    
	public JSONReader(String file) throws FileNotFoundException, IOException, ParseException {
		try {
		 InputStream in = getClass().getResourceAsStream("/" + file); 
		 BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		 Object obj = new JSONParser().parse(reader);
	     
		 jo = (JSONObject) obj; 
		 
	     features = (JSONArray)((JSONArray)(jo.get("features")));
	     geometries = new ArrayList<JSONObject>();
		 objects = new HashMap<String,JSONObject>();
		 coordinatesMap = new HashMap<String,JSONArray>();
	     shapesMap = new HashMap<String,ArrayList<double[]>>();
	     
	     setGeometries();
	     setCoordinates();
	     setShapes();
	    
	 }catch(FileNotFoundException e) { 
		 e.printStackTrace();
		 System.out.println(JSONReader.class.getClassLoader().getResource(file).getFile().replace("%20", " ").replace("file:/", ""));
		 System.out.println("File Could Not Be Found");
	 }catch(IOException e) {
		 System.out.println("File Could Not Be Read");
	 }catch(ParseException e) {
		 System.out.println("JSON Could Not Be Parsed");
	 }
	}
	
	private void setGeometries() {
		int counter = 0;
		for(Object f : features) {
	    	 geometries.add((JSONObject)((JSONObject)f).get("geometry"));
	    	 if(Objects.isNull((String)((JSONObject)((JSONObject)f).get("properties")).get("name"))) {
	    		 objects.put(" " + counter,(JSONObject)((JSONObject)f).get("geometry")); 
				}else {
					objects.put((String)((JSONObject)((JSONObject)f).get("properties")).get("name"),(JSONObject)((JSONObject)f).get("geometry")); 
				}
	    	 counter++; 
		}
	}
	
	private void setCoordinates() {	
		Iterator<Entry<String, JSONObject>> itr = objects.entrySet().iterator();
		while(itr.hasNext()) {
			Map.Entry<String, JSONObject> pair = itr.next();
			String name = pair.getKey();
			JSONObject geom = pair.getValue();
			coordinatesMap.put(name,(JSONArray)((JSONArray)((JSONArray)geom.get("coordinates")).get(0)).get(0));
		}
	} 
	
	private void setShapes() {
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
	
	public Map<String,ArrayList<double[]>> getShapes() {
		return shapesMap;
	}
}
	
