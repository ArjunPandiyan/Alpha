package com.intuit.Utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

/* This is a utils class which contains all the functions 
 * for utility purposes
 * @author gnayak1
 * Last modifed By : gnayak1
 * Date of Creation : 25th April 2014
 * Date of last Modification : 25th April 2014
 * 
 */
public class Utils {
	
	/* The purpose of this function is to
	 * get the Response Code when a URL is hit
	 */
	private static Set<Integer> responseCode=new HashSet<Integer>(); 
	// Static intialization block
	static{
		//****To add more valid response codes add here ****//
		responseCode.add(200);
		responseCode.add(201);
		responseCode.add(202);
		responseCode.add(203);
		responseCode.add(204);
		responseCode.add(205);
		responseCode.add(206);
		responseCode.add(207);
		responseCode.add(208);
		responseCode.add(226);
		responseCode.add(301);
		responseCode.add(302);
		responseCode.add(303);
		responseCode.add(304);
		responseCode.add(305);
		responseCode.add(306);
		responseCode.add(307);
		responseCode.add(308);
		
	}
	
	
	/* The purpose of this function is to get the response code when 
	 * the url is hit
	 * @param url The url for which the response code has to be bought
	 * @return int the response code received when the url was hit
	 * 
	 */
	public static int getURLReponse(String url){
		HttpURLConnection con = null;
		try{
			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();
			int responseCodeFromURL = con.getResponseCode();
			return responseCodeFromURL;
		}
		catch(MalformedURLException e){
			System.out.println("Malformed URL Exception is thrown in function getURLReponse()");
			e.printStackTrace();
			return -999;
		}
		catch(IOException e){
			System.out.println("IOException is thrown in function getURLReponse()");
			e.printStackTrace();
			return -999;
		}
		catch(Exception e){
			System.out.println("Exception is thrown in function getURLReponse()");
			e.printStackTrace();
			return -999;
		}
		finally{
			con.disconnect();
		}
	}
	
	/* The purpose of this function is to check if the response code is a valid
	 * response code, that means if the set responseCode contains the entry
	 * @param responseCode The response code that you wish to check
	 * @return the boolean response if the set contains the response code or not
	 * 
	 * 
	 */
	public static boolean checkIfValidReponseCode(int respCode){
		return responseCode.contains(respCode);
	}
	
	/* The purpose of this function is to get system date
	 * @return the system date
	 * 
	 */
	public static String getSystemDate(){
		DateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		java.util.Date date = new java.util.Date();
		return dateFormat.format(date);
	}
	

	/* The purpose of this function is to assign 'NA' is the value of the string passed is null
	 * 
	 */
	public static String assignValueForNull(String valueIfNull,Object obj){
		if(obj!=null){
			return obj.toString();
		}
		else{
			return valueIfNull;
		}
	}
}
