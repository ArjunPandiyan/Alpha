package com.intuit.Beans;

import java.util.HashMap;

import javax.servlet.http.HttpServlet;

/* This is a singleton class which means that there will be only 
 * one instance running in it.
 * This is basically used as a cache and would contain a list URLDb type objects
 * @author gnayak1 
 * Date of Creation  : 24th April 2014
 * Date of last Modification  :24 April 2014
 * 
 * 
 */
public class URLCache extends HttpServlet{
	private static URLCache instance = null;
	public static HashMap<String,URLDbBean> mapURLKeyAndValue = new HashMap<String,URLDbBean>();
	
	//Constructor
	private URLCache(){
		
	}
	
	public static URLCache getInstance() {
		if (instance == null) {
			instance = new URLCache();
		}
		return instance;
	}
	
	/*The purpose of this function is to put key and corresponding
	   URLDb into the map
	   @param key The key for the URL
	   @param URLDb The correspoding URLDb collection
	*/
	public void putURL(String key,URLDbBean document){
		synchronized(this){
			mapURLKeyAndValue.put(key.trim().toUpperCase(), document);
		}
	} 
	
	/* The purpose of this function is to return the corresponding 
	 * URLDb object for a specific key
	 * @param The key for which the value is to be obtained
	 * @return The URLDb object corresponding to the key
	 */
	public URLDbBean getURL(String key){
		if(mapURLKeyAndValue.containsKey(key.toUpperCase())){
			return mapURLKeyAndValue.get(key);
		}
		else
			return null;
	}
	
	/* The purpose of this function is to remove the URL from the 
	 * cache
	 * @param key The key of the URL which you wish to remove
	 * @return The status of the removal successfull/unsucessful
	 */
	public String removeKey(String key){
		if(key==null || key.trim().length()==0){
			return "Key is null or length is 0";
		}
		else{
			key=key.toUpperCase();
			//Synchronize this block
			synchronized(this){
				if(mapURLKeyAndValue.containsKey(key)){
					mapURLKeyAndValue.remove(key);
					return "Successfull removal of key from cache";
				}
				else{
					return "Key not present in cache";
				}
			}
		}
		
	}
	
	/* The purpose of this function is to check if the key is already present
	 * @param key The key which you wish to lookup
	 * @return boolean true if present
	 *                 else false
	 * 
	 */
	public static boolean checkIfKeyPresent(String key){
		key=key.toUpperCase();
		// if null is returned that means the key is not present
		if(URLCache.getInstance().getURL(key)!=null){
			return false;
		}
		else
		{
			return true;
		}
	}
}
