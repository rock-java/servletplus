package com.rockson.servletplus;

import java.util.Map;
import java.util.regex.Pattern;

import com.rockson.validator.PhoneLocale;
import com.rockson.validator.Validator;

public class ParamValidator extends FieldValidator {
	private String value;

	
	
//	public ParamValidator(String name, String value, boolean exists , final Map<String, String> errors ) {
//		this.name = name;
//		this.value = value;
//		this.exists = exists;
//	}

	public ParamValidator( String name, String value, boolean exists,Map<String, String> errors) {
		super(errors, name, exists);
		this.value = value;
	}

	public ParamValidator optional() {
		if (!this.exists) {
			this.goOn = false;
		}
		return this;
	}

	public ParamValidator notEmpty(String... tip) {
		if (this.goOn && null == this.value && "".equals(this.value)) {
			this.addError(String.format("%s can not be empty.", name), tip);
		}
		return this;
	}

	public ParamValidator notEmpty() {
		if (this.goOn) {
			if (null != this.value) {
				this.goOn = false;
			}
		}
		return this;
	}

	public ParamValidator exist(String... tip) {
		if (this.goOn && !this.exists) {
			this.addError(String.format("%s should exists", name), tip);
		}
		return this;
	}

	public ParamValidator match(String reg, int flags, String... tip) {
		if (this.goOn && !Pattern.compile(reg, flags).matcher(value).matches()) {
			this.addError(String.format("%s is bad format", name), tip);
		}
		return this;
	}

	public ParamValidator match(String reg, String... tip) {
		if (this.goOn && !Pattern.compile(reg).matcher(value).matches()) {
			this.addError(String.format("%s is bad format", name), tip);
		}
		return this;
	}

	public ParamValidator notMatch(String reg, int flags, String... tip) {
		if (this.goOn && Pattern.compile(reg, flags).matcher(value).matches()) {
			this.addError(String.format("%s is bad format", name), tip);
		}
		return this;
	}

	public ParamValidator notMatch(String reg, String... tip) {
		if (this.goOn && Pattern.compile(reg).matcher(value).matches()) {
			this.addError(String.format("%s is bad format", name), tip);
		}
		return this;
	}

	public ParamValidator len(int min, int max, String... tip) {
		if (this.goOn && (value==null||(value.length() < min || value.length() > max))) {
			this.addError(String.format("%s's length should equal or great than %d and should equal or less than %d",
					name, min, max), tip);
		}
		return this;
	}

	public ParamValidator isInt(String... tip) {
		if (this.goOn && !Validator.isInt(value)) {
			this.addError(String.format("%s should be a integer", name), tip);
		}
		return this;
	}

	public ParamValidator isFloat(String... tip) {
		if (this.goOn && !Validator.isFloat(value)) {
			this.addError(String.format("%s should be a integer", name), tip);
		}
		return this;
	}

	public ParamValidator isEmail(String... tip) {
		if (this.goOn && !Validator.isEmail(value)) {
			this.addError(String.format("%s should be an email", name), tip);
		}
		return this;
	}

	public ParamValidator isUrl(String... tip) {
		if (this.goOn && !Validator.isURL(value)) {
			this.addError(String.format("%s should be an url", name), tip);
		}
		return this;
	}

	public ParamValidator isMobilePhone(PhoneLocale locale, String... tip) {
		if (this.goOn && !Validator.isMobilePhone(value, locale)) {
			this.addError(String.format("%s should be a moblie phone number", name), tip);
		}
		return this;
	}

	public ParamValidator isIp(String... tip) {
		if (this.goOn && !Validator.isIP(value)) {
			this.addError(String.format("%s should be a moblie phone number", name), tip);
		}
		return this;
	}
	public ParamValidator trim() {
		if (this.goOn && null!=value) {
			value = value.trim();
		}
		return this;
	}

	public String md5(String... tip) {
		notEmpty(tip);
		if (!hasError()) {
			return Validator.md5(value);
		} else {
			return null;
		}
	}
	public String sha1(String... tip) {
		notEmpty(tip);
		if (!hasError()) {
			return Validator.sha1(value);
		} else {
			return null;
		}
	}

	public Integer toInt(String... tip) {
		isInt(tip);
		if (isTransformableValue()&&!hasError()) {
			return Validator.toInt(value);
		} else {
			return null;
		}
	}
	public boolean isTransformableValue(){
		if(!exists || null == this.value || "" == this.value.trim()) {
			return false;
		}
		return true;
	}
	public Integer toInt(int defaultValue,String... tip) {
		if(hasError()){
			return null;
		}
		if(!isTransformableValue()) {
			return defaultValue;
		}
		isInt(tip);
		return Validator.toInt(value);
	}
	public Float toFloat(String... tip) {
		isFloat(tip);
		if (isTransformableValue()&&!hasError()) {
			return Validator.toFloat(value);
		} else {
			return null;
		}
	}
	public Float toFloat(float defaultValue,String... tip) {
		if(hasError()){
			return null;
		}
		if(!isTransformableValue()) {
			return defaultValue;
		}
		isFloat(tip);
		return Validator.toFloat(value);
	}

	public String toString(String defaultValue, String... tip) {
		if(this.hasError()) {
			return null;
		}
		if (!this.exists || null == this.value) {
			return defaultValue;
		} else {
			return value;
		}
	}
	public String toString() {
		if(this.hasError()) {
			return null;
		}
		return value;
	}
}
