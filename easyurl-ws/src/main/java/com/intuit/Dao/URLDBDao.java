package com.intuit.Dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;

import org.bson.BSONObject;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONArray;

import com.intuit.Beans.URLCache;
import com.intuit.Beans.URLDbBean;
import com.intuit.Utils.Utils;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;

@Path("/v1")
public class URLDBDao extends GlobalDAO{

	
	public URLDBDao(){
		super();
	}
	
	@GET
	@Path("/things")
	public String getThings(){
		return "You found things!!!";
	}
	
	/* This function is to get the URL for the specified key
	 * @param key The key for which the URL has to be got
	 * @return the corresponding URL
	 * 
	 * 
	 */
	
	@GET
	@Path("/getKey/{key}")
	public String getURLForKey(@PathParam("key") String key){
		System.out.println("In Function getURLForKey");
		try{
			if(key!=null && key.length()>0){
				// If search in the cache otherwise make a call to the database
				// Key made uppercase out here
				if(URLCache.getInstance()!=null && URLCache.getInstance().getURL(key.toUpperCase())!=null){
					return URLCache.getInstance().getURL(key.toUpperCase()).getUrlValue();
				}
				else{
					DBCollection collection = database.getCollection("urldb");
					// Key made uppercase out here
					BasicDBObject query = new BasicDBObject("_id",key.toUpperCase());
					DBObject dbObject = collection.findOne(query);
					if(dbObject!=null){
						// update the cache
						// make a URLCache entry
						URLDbBean urlBean = new URLDbBean(dbObject.get("_id").toString(),dbObject.get("url").toString(),dbObject.get("ownerEmail").toString(),dbObject.get("lastModifiedByEmail").toString(),dbObject.get("lastModifiedDate").toString(),dbObject.get("urlStatus").toString(),dbObject.get("orgId").toString(),dbObject.get("urlInfo").toString());
						URLCache.getInstance().putURL(key, urlBean);
						return dbObject.get("url").toString();
					}
					else{
						return "No Entry present in the database for the key :"+key;
					}
				}
			}
			else{
				return "Key is null or has 0 length";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return "Exception occured while retrieving the value";
		}
		finally{
			mongoClient.close();
		}
	}
	
	/* The purpose of this function is to 
	 * insert "URL Key" and "Value" into the database
	 * @param key The key for the URL
	 * @param value The corresponding value for the key
	 * @param ownerEmail The email address of the user who is creating it
	 * @param orgId The organization for which the url key-value pair is being created
	 * @param urlInfo Some key information with respect the the URL
	 * 
	 * @return status message
	 */
	//String key,String urlValue,String ownerEmail,String orgId,String urlInfo
	@POST
	@Path("/insert")
	//@Consumes(MediaType.APPLICATION_JSON)
	public String insertURL(final String input){
		JSONObject urlJSONObj = new JSONObject(input);
		String key =urlJSONObj.getString("urlKey");// urlBeanObj.getUrlKey()
		String urlValue =urlJSONObj.getString("urlValue");// urlBeanObj.getUrlValue();
		String ownerEmail =urlJSONObj.getString("ownerEmail");//urlBeanObj.getOwnerEmail();
		String orgId = urlJSONObj.getString("orgId");//urlBeanObj.getOrgId();
		String urlInfo =urlJSONObj.getString("urlInfo");//urlBeanObj.getUrlInfo();
		try {
			DBCollection collection = database.getCollection("urldb");
			// Key and owner email made upperKeys out here
			key = key.toUpperCase();
			ownerEmail = ownerEmail.toUpperCase();
			BasicDBObject query = new BasicDBObject("_id", key);
			DBObject dbObject = collection.findOne(query);
			if (dbObject != null) {
				return "Error!!!Key with the same+"+key+"name already exists";
			} else { 
				// The document does not exists in the database
				int urlStatus = Utils.getURLReponse(urlValue);
				if(Utils.checkIfValidReponseCode(urlStatus)){
					String lastModifiedTimeStamp = Utils.getSystemDate();
					// Since the owner will be the only one who last modified it
					String lastModifiedByEmail = ownerEmail;
					String urlStatusInString = urlStatus+"";
					BasicDBObject urlDBdoc = new BasicDBObject("_id", key)
							.append("url", urlValue)
							.append("ownerEmail", ownerEmail)
							.append("orgId", orgId).append("urlInfo", urlInfo)
							.append("lastModifiedDate", lastModifiedTimeStamp)
							.append("lastModifiedByEmail", ownerEmail)
							.append("urlStatus", urlStatus).append("urlInfo", urlInfo);
					collection.insert(urlDBdoc);
					URLCache.getInstance().putURL(key.toUpperCase(),new
					URLDbBean(key,urlValue, ownerEmail,
							lastModifiedByEmail, lastModifiedTimeStamp,
							urlStatusInString, orgId, urlInfo));
					return "Success!!! Key:" + key
							+ " has been created. This Key is case insensitive";
				}
				else{
					return ("Error!!!The url : "+urlValue+ " does not have a valid response code");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error!!! There was some problem while inserting a URL";
		} finally {
			mongoClient.close();
		}
	}
	
	/* The purpose of this function is to 
	 * delete a URL Key Value from the database
	 * @param key The key for the URL which you wish to delete
	 * @return status message
	 * 
	 * 
	 */
	@POST
	@Path("/delete")
	@Consumes(MediaType.TEXT_PLAIN)
	public String deleteURL(String key){
		System.out.println("KEY"+key);
		try{
			if(key!=null && key.trim().length()>0){
				DBCollection collection = database.getCollection("urldb");
				// Covert to uppercase
				key=key.toUpperCase();
				BasicDBObject query = new BasicDBObject("_id", key);
				DBObject dbObject = collection.findOne(query);
				if(dbObject==null){// No such key exists
					return "Key with the specified name does not exist";
				}
				else{ //  key is present
					collection.remove(dbObject);
					//update cache to by removing it from the cache
					URLCache.getInstance().removeKey(key);
					return "Key:"+key+" has been removed permanently";
				}
			}
			else{
				return "The key is null or the length of the key is 0";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return "Exception occured while deletion of the URL";
		}
		finally{
			mongoClient.close();
		}
	}
	
	/* The purpose of this function is to 
	 * update a value from a specified key
	 * @param key The key which you wish to update(mandatory)
	 * @param value The updated value for the key(mandatory)
	 * @param orgId The orgId to which the URL belongs (keep the orgId name 'default' if the URL doesnt belong to any org)(mandatory)
	 * @param ownerEmail The owner of the URL(mandatory)
	 * @return status message
	 * 
	 */
	@POST
	@Path("/update")
	//@Consumes(MediaType.TEXT_PLAIN)
	public String updateURL(final String jsonObjectString){
		System.out.println(jsonObjectString);
		JSONObject jsoObject = new JSONObject(jsonObjectString);
		String key = jsoObject.getString("urlKey");
		String value = jsoObject.getString("urlValue");
		String updateEmail =jsoObject.getString("updateEmail");
		String org_id = jsoObject.getString("orgId");
		String urlInfo =jsoObject.getString("urlInfo");
		try{
			if(key!=null && key.trim().length()!=0 && value!=null && value.trim().length()!=0 && org_id!=null && org_id.trim().length()!=0 && updateEmail!=null && updateEmail.trim().length()!=0 && urlInfo!=null && urlInfo.trim().length()!=0){
				DBCollection collection = database.getCollection("urldb");
				key=key.toUpperCase();
				updateEmail=updateEmail.toUpperCase();
				BasicDBObject query = new BasicDBObject("_id", key);
				DBObject dbObject = collection.findOne(query);
				if(dbObject==null){ // No such key exists
					return "Error!!!No such Key:"+key+" present";
				}
				else{
					int reponseCode = Utils.getURLReponse(value);
					if(Utils.checkIfValidReponseCode(reponseCode)){
						//  key is present
						collection.update(dbObject,new BasicDBObject("$set",new BasicDBObject().append("url",value).append("updateEmail", updateEmail).append("urlInfo",urlInfo)));
						// Get the document first from the cache
						URLDbBean urlBean =URLCache.getInstance().getURL(key);
						if(urlBean!=null){
							urlBean.setUrlValue(value);
							urlBean.setLastModifiedUserEmail(updateEmail);							
							urlBean.setLastModifiedDate(Utils.getSystemDate());
							urlBean.setUrlInfo(urlInfo);
							URLCache.getInstance().putURL(key, urlBean);
						}
						return "Success!!!Key:"+key+" has been updated successfully";
					}
					else{
						return "Error!!!The url : "+value+ " does not have a valid response code";
					}
				}
			}
			else{
				if(key==null || key.trim().length()==0)
					return "Error!!!The key is null or the length of the key is 0";
				else if(value==null || value.trim().length()==0)
					return "Error!!!The URL is null or of length 0";
				else if(org_id==null || org_id.trim().length()==0)
					return "Error!!!The org id is null or the length is 0";
				else 
					return "Error!!!The email of the user who is updating is null";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return "Error!!!Exception occured while updating the URL";
		}
		finally{
			mongoClient.close();
		}
	}
	
	/* The purpose of this function is to get all the records from the database and then load it 
	 * to the cache(This function would be executed only once)
	 * 
	 */
	
	@GET
	@Path("/loadCache")
	public static void loadCache(){
		DBCursor cursor = null;
		try{
			MongoClient mongoClnt = new MongoClient("localhost" ,27017);
			DB db=mongoClnt.getDB("easyURLdb");
			DBCollection collection = db.getCollection("urldb");
			cursor = collection.find();
			while(cursor.hasNext()) {
				   DBObject dbObject = cursor.next();
				   //System.out.println(assignValueForNull(dbObject.get("_id")+","+dbObject.get("url").toString()+","+dbObject.get("ownerEmail").toString()+","+dbObject.get("lastModifiedByEmail").toString()+","+dbObject.get("lastModifiedDate").toString()+","+dbObject.get("urlStatus").toString()+","+dbObject.get("orgId").toString()+","+dbObject.get("urlInfo").toString());
				   URLDbBean urlBean = new URLDbBean(Utils.assignValueForNull("NA",dbObject.get("_id")),Utils.assignValueForNull("NA",dbObject.get("url")),Utils.assignValueForNull("NA",dbObject.get("ownerEmail")),Utils.assignValueForNull("NA",dbObject.get("lastModifiedByEmail")),Utils.assignValueForNull("NA",dbObject.get("lastModifiedDate")),Utils.assignValueForNull("NA",dbObject.get("urlStatus")),Utils.assignValueForNull("NA",dbObject.get("orgId")),Utils.assignValueForNull("NA",dbObject.get("urlInfo")));
				   URLCache.getInstance().putURL(dbObject.get("_id").toString(), urlBean);
			}
			JSONArray jsonStringer = new JSONArray(URLCache.getInstance().mapURLKeyAndValue.values());
			System.out.println(jsonStringer.toString());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(cursor!=null){
				cursor.close();
			}
		}
	}
	
	/* The purpose of this function is to get all the records in the cache and convert it into JSON format  
	 * and return it as a JSON string
	 * 
	 */
	
	@GET
	@Path("/getAllURLs")
	public static String getAllURLs(){
			// Convert the data in the cache into a JSONArray
			JSONArray jsonStringer = new JSONArray(URLCache.getInstance().mapURLKeyAndValue.values());
			return jsonStringer.toString();
	}
	
	public static void main(String args[]){
		URLDBDao dao=new URLDBDao();
		loadCache();
		//dao.insertURL(key, urlValue, ownerEmail, orgId, urlInfo)
		//System.out.println(dao.insertURL("CTO-QLTYDASH-3","http://pdevdv1ao14sfsdfsdfz.corp.intuit.net/qrs/dashboard/OrgSummary.jsp?orgId=CTODEV","geetish_nayak@intuit.com","CTODEV","geetish_nayak@intuit.com"));
		//System.out.println(dao.getURLForKey("CtO-QLtYDaSH-2"));
	}
}
