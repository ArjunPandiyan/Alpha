package com.intuit.factory;

import com.intuit.actions.Actions;
import com.intuit.actions.*;

public class ActionFactory {
	public static Actions getActions(String action){
		
		if(action.equals("AddURL")){
			return new AddURL();
		}
		else if(action.equals("DeleteURL")){
			return new DeleteURL();
		}
		else if(action.equals("EditURL")){
			return new EditURL();
		}
		else{
			return new SearchURL();
		}
	}
}
