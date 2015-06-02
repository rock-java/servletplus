package com.rockson.servletplus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ServletUtils {
	
	public static String trimPath(String path) {
		if(path.length()>1 && path.length()-1 == path.lastIndexOf('/')) {
			return  path.substring(0 , path.length()-1);
		}
		return path;
	}
	
	public static void pipe(InputStream in , OutputStream out ) throws IOException {
		pipe(in, out,  4096);
	}
	public static void pipe(InputStream in , OutputStream out , int bufferSize) throws IOException {
		byte[] buffer = new byte[bufferSize];
		int n ;
		while(-1< (n = in.read(buffer))) {
			out.write(buffer, 0, n);
		}
	}

}
