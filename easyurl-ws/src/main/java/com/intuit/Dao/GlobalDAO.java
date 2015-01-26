package com.intuit.Dao;

import javax.servlet.http.HttpServlet;

import com.mongodb.MongoClient;
import com.mongodb.DB;

public class GlobalDAO extends HttpServlet {
	MongoClient mongoClient;
	DB database;
	
	public GlobalDAO(){
		try{
			mongoClient =  new MongoClient("localhost" ,27017);
			database = mongoClient.getDB("easyURLdb");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
