package com.intuit.actions;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.intuit.Beans.URLCache;
import com.intuit.Beans.URLDbBean;
import com.intuit.Beans.URLResponse;
import com.intuit.Utils.Utils;

public class EditURL implements Actions {

	// Function to perform action
	public String performAction(HttpServletRequest request) {
		String key = request.getParameter("key");
		if(!URLCache.checkIfKeyPresent(key)){
			return new JSONObject(new URLResponse("error","Error in Updation.Key not found ")).toString();
		}
		else{
			String url = request.getParameter("value");
			String urlStatus = String.valueOf(Utils.getURLReponse(url));
			// check if the URL response is a valid response
			if(Utils.checkIfValidReponseCode(Integer.parseInt(urlStatus))){
				URLDbBean beanObj = URLCache.getInstance().getURL(key);
				beanObj.setLastModifiedUserEmail(request.getParameter("emailIdUser"));
				beanObj.setUrlValue(url);
				beanObj.setOrgId(request.getParameter("org"));
				beanObj.setUrlInfo(request.getParameter("urlInfo"));
				URLCache.getInstance().putURL(key,beanObj);
				return new JSONObject(new URLResponse("success","Successful Updation")).toString();
			}
			else{
				return new JSONObject(new URLResponse("error","Error in Updation. Not a valid URL.The response code returned is "+urlStatus)).toString();
			}
		}
	}

}
