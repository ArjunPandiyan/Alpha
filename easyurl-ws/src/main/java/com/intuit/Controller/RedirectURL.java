package com.intuit.Controller;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

import com.intuit.Dao.URLDBDao;

@ApplicationPath("/*")
public class RedirectURL extends javax.ws.rs.core.Application{
	private static Set<Class<?>> classes= new HashSet<Class<?>>();
	static{
		classes.add(URLDBDao.class);
	}
	
	@Override
	public Set<Class<?>> getClasses(){
		return classes;
	}
}
