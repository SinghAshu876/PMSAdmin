package com.pms.util;

import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

//import org.apache.log4j.Logger;

public class PropertyUtil implements ApplicationConstants {


	private String findPropertyLocation(String environment) {
		//System.out.println("findPropertyLocation ENTRY");
		String propertyLocation = RESOURCE_LOC;
		if (environment.equals(DEV)) {
			propertyLocation = propertyLocation.concat(DB_DEV_PROP);
		} else if (environment.equals(DEV_BASTI)) {
			propertyLocation = propertyLocation.concat(DB_DEV_BASTI_PROP);
		} else if (environment.equals(ALKA)) {
			propertyLocation = propertyLocation.concat(DB_ALKA_PROP);
		} else if (environment.equals(BASTI)) {
			propertyLocation = propertyLocation.concat(DB_BASTI_PROP);
		}
		else{
			throw new IllegalArgumentException();
		}
		//System.out.println("findPropertyLocation EXIT : "+ propertyLocation);
		return propertyLocation;

	}
	
	public void loadProperties(String environment) {
		//System.out.println("loadProperties ENTRY");
		if(environment.equals(EMPTY_STRING)){
			return;
		}
		Properties properties = new Properties();
		try (InputStream stream = getInputStream(findPropertyLocation(environment))){			
			properties.load(stream);
		} catch (Exception e) {
			//LOG.error("EXCEPTION IN LOADING PROPERTIES FILE");
		}
		for(Entry<Object, Object> p : properties.entrySet()){
			System.setProperty((String)p.getKey(), (String)p.getValue());
		}
		//System.out.println("loadProperties EXIT"+System.getProperties());
      
	}
	
	private InputStream getInputStream(String location) {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(location);
		//System.out.println(inputStream);
		return inputStream;
	}	
	
	
}
