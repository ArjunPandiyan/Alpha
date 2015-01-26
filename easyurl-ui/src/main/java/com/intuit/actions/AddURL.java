package com.intuit.actions;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.intuit.Beans.URLCache;
import com.intuit.Beans.URLDbBean;
import com.intuit.Beans.URLResponse;
import com.intuit.Utils.Utils;

public class AddURL implements Actions {

	// Constructor
	public AddURL() {

	}

	// Function to perform action
	public String performAction(HttpServletRequest request) {
		String key = request.getParameter("key");
		String url = request.getParameter("value");
		String owner = request.getParameter("emailIdUser");
		String lastModifiedBy = request.getParameter("emailIdUser");
		String lastModifiedDate = Utils.getSystemDate();
		String urlStatus = String.valueOf(Utils.getURLReponse(url));
		String org = request.getParameter("org");
		String urlInfo = request.getParameter("urlInfo");

		if(!URLCache.checkIfKeyPresent(key)){
			if(Utils.checkIfValidReponseCode(Integer.parseInt(urlStatus))){
				URLCache.getInstance().putURL(
						key,
						new URLDbBean(key, url, owner, lastModifiedBy,
								lastModifiedDate, urlStatus, org, urlInfo));
				return new JSONObject(new URLResponse("success","Successful Insertion")).toString();
			}
			else{
				return new JSONObject(new URLResponse("error","Error in Insertion. Not a valid URL.The response code returned is "+urlStatus)).toString();
			}
		}
		else{
			return new JSONObject(new URLResponse("error","Error in Insertion. Duplicate Key")).toString();
		}
	}
}
