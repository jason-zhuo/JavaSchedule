package com;
import java.util.Map;


public class AccessingMap {
	final static Map <String, Integer> map=null;
	public static void useMap(final Map<String, Integer> scores)
	{
		scores.put("zzl", 100);
		scores.put("lvmao", 90);
		
		for (final String key: scores.keySet())
		{
			System.out.println(key + "score: "+scores.get(key));
			scores.put("niu", 13);
			
		}
	}
	
}
