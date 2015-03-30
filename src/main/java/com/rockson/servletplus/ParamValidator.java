package com.rockson.servletplus;

import java.util.Map;

public class ParamValidator extends Validator {
	public ParamValidator(String name, String parameter, final Map<String, String> errors) {
		this.errors = errors;
		this.name = name;
	}
	public ParamValidator optional() {
		return this;
	}
	
	public ParamValidator len(int min , int max , String ...tip) {
		this.addError(String.format("%s's length should equal or great than %d and should equal or less than %d" , name , min ,max ),tip);
		return this;
	}

}
