package com.rockson.servletplus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamPath {

	private String namedPath;
	private Pattern pathPattern;
	private String[] pathNames;

	public ParamPath(String namedPath) {
		this.namedPath = namedPath;
		pathToRegStr();
	}

	/**
	 * convert path to reg eg1. /user/{id} -> ^/user/(?<id>[^/]+) eg1. /user/{id}/name -> ^/user/(?<id>[^/]+)/name
	 * 
	 * @return
	 */
	private void pathToRegStr() {
		Matcher matcher = Pattern.compile("\\{([^\\{]+)\\}").matcher(namedPath);
		StringBuffer sb = new StringBuffer();
		sb.append('^');
		LinkedList<String> names = new LinkedList<String>();
		while (matcher.find()) {
			names.add(matcher.group(1));
			matcher.appendReplacement(sb, "(?<" + matcher.group(1) + ">[^/]+)");
		}
		matcher.appendTail(sb);
		sb.append('$');
		this.pathNames = names.toArray(new String[0]);
		this.pathPattern = Pattern.compile(sb.toString(), Pattern.CASE_INSENSITIVE);
	}

	public Map<String, String> matchPathReg(String path) {
		Matcher matcher = pathPattern.matcher(path);
		Map<String, String> map = new HashMap<String, String>();
		boolean matches = false;
		while (matcher.find()) {
			for (String name : pathNames) {
				map.put(name, matcher.group(name));
			}
			matches = true;
		}
		if (!matches) {
			return null;
		} else {
			return map;
		}
	}

}
