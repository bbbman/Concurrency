package mythread.test;

import java.util.HashMap;
import java.util.Map;

public class MonitorVehicleTracker {
	
	private final Map<String,MutablePoint> locations;
	
	public MonitorVehicleTracker(Map<String,MutablePoint> locations){
		this.locations = new HashMap<String,MutablePoint>(locations);
	}

	public synchronized Map<String, MutablePoint> getLocations() {
		return locations;
	}
	
	public synchronized MutablePoint getMutablePoint(String id){
		MutablePoint mp = locations.get(id);
		return mp==null?null:new MutablePoint(mp);
	}
	
	public void setMutablePoint(String id,int x,int y){
		MutablePoint mp = locations.get(id);
		if(mp == null)
			throw new IllegalArgumentException("No such ID:"+id);
		mp.x = x;
		mp.y = y;
	}
	private static Map<String,MutablePoint> deepCopy(Map<String,MutablePoint> m){
		Map<String,MutablePoint> result = new HashMap<String,MutablePoint>();
		for(String id:m.keySet())
			result.put(id, new MutablePoint(m.get(id)));
		return result;		
	}
	
	public static void main(String[] args){
		Map<String,MutablePoint> mps = new HashMap<String,MutablePoint>();			
		mps.put("1", new MutablePoint(0,1));
		mps.put("2", new MutablePoint(0,2));
		mps.put("3", new MutablePoint(0,3));
		MonitorVehicleTracker mv = new MonitorVehicleTracker(mps);
		mps.put(mv.new Pp().key , new MutablePoint(0,0));
		mps.get("0").y=9;		
		int y = mv.locations.get("0").y;
	}
	class Pp{
		String key ="0";
	}
	
}
