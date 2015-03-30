package com.rockson.servletplus;

import java.util.Map;

public abstract class Validator {
	protected boolean goOn;
	protected Map<String, String> errors;
	protected String name;
	
	public void addError(String defaultTip, String[] tip) {
		this.goOn = false;
		if(null!=tip && tip.length >0 ){
			errors.put(name, tip[0]);
		}else{
			errors.put(name, defaultTip);
		}
	}

}
