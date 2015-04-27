package com.rockson.servletplus;

public class ServletUtils {
	
	public static String trimPath(String path) {
		if(path.length()>1 && path.length()-1 == path.lastIndexOf('/')) {
			return  path.substring(0 , path.length()-1);
		}
		return path;
	}

}
