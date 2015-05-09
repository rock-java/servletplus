package com.rockson.servletplus;

import java.util.Map;

public class FileValidator extends FieldValidator  {

	public FileValidator(Map<String, String> errors, String name, boolean exists) {
		super(errors, name, exists);
	}

}
