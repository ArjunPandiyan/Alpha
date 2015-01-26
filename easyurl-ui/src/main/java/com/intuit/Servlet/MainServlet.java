package com.intuit.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intuit.Beans.URLCache;
import com.intuit.Beans.URLDbBean;
import com.intuit.Utils.Utils;
import com.intuit.actions.*;
import com.intuit.factory.*;

/**
 * Servlet implementation class MainServlet
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//public static  URLCache cache;
	public static Map<String,String> mapActionNamesClasses = new HashMap<String,String>();
	
    /**
     * Default constructor. 
     */
    public MainServlet() {
    	System.out.println("In Contructor");
    }
    
    public void init (ServletConfig config) throws ServletException
    {
       System.out.println("In Init");
       createMap();
       registerClasses();
    }
   
    
    private void createMap()
    {
    	/*URLCache.getInstance().putURL("JIRA-QA".toUpperCase(),new URLDbBean("jira-qa".toUpperCase(),"http://jiraqa.intuit.com","geetish_nayak@intuit.com",
						"geetish_nayak@intuit.com", Utils.getSystemDate(),String.valueOf(Utils.getURLReponse("http://jiraqa.intuit.com")), "CTODEV", "Jira QA Env"));*/
    }
    
    private void registerClasses(){
    	mapActionNamesClasses.put("addURL.do","AddURL");
    	mapActionNamesClasses.put("deleteURL.do","DeleteURL");
    	mapActionNamesClasses.put("editURL.do","EditURL");
    	mapActionNamesClasses.put("searchURL.do","SearchURL");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 String url = request.getRequestURL().toString();
	     String key = url.substring(url.lastIndexOf("/") + 1).trim();
	     if(key.contains("do")||key.contains("DO")){
	    	
	     }
	     else{
	    	 // if key present in cache
	    	 if(URLCache.getInstance().checkIfKeyPresent(key)){
	    		 response.sendRedirect(URLCache.getURL(key).getUrlValue());
	    	 }
	    	 //If key not present in cache 
	    	 else{	
	    		 
	    	 }
	     }  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		System.out.println("URL"+url);
	    String key = url.substring(url.lastIndexOf("/") + 1).trim();
	    System.out.println("KEY"+key);
	    if(key.contains("do")||key.contains("DO")){
	    	 try{
	    		 //Actions actions = (Actions)Class.forName(mapActionNamesClasses.get(key.trim())).getConstructor().newInstance();
	    		 Actions actionObj = ActionFactory.getActions(mapActionNamesClasses.get(key.trim()));
	    		 String responseInJson = actionObj.performAction(request);
	    		 //System.out.println(responseInJson);
	    		 PrintWriter out = response.getWriter();
	    		 response.setContentType("text/json");
	    		 out.println(responseInJson);
	    		 out.close();
	    	 }
	    	 catch(Exception e){
	    		 
	    	 }
	    }
	}

}
