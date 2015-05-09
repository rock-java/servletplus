package com.rockson.servletplus;

import java.util.Map;

public abstract class FieldValidator {
	protected boolean goOn = true;
	protected Map<String, String> errors;
	protected String name;
	protected boolean exists;
	
	public FieldValidator(Map<String, String> errors, String name, boolean exists) {
		this.errors = errors;
		this.name = name;
		this.exists = exists;
	}
	protected void addError(String defaultTip, String[] tip) {
		this.goOn = false;
		if(null!=tip && tip.length >0 ){
			errors.put(name, tip[0]);
		}else{
			errors.put(name, defaultTip);
		}
	}
	public boolean hasError() {
		return null!=errors&&!errors.isEmpty(); 
	}
	public Map<String, String> getErrors(){
		return this.errors;
	}

}
