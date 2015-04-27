package com.rockson.servletplus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamPath {

	private String path;
	private Pattern pathPattern;
	private String[] pathNames;

	public ParamPath(String path) {
		this.path = ServletUtils.trimPath(path);
		if (0 == path.indexOf('^')) {
			parseRegPath();
		} else {
			parseParamPath();
		}
	}
	


	private void parseRegPath() {
		Pattern pattern = Pattern.compile("(?<!\\\\)\\((?!\\?)");
		Matcher matcher = pattern.matcher(path);
		int i = 1;
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "(?<PARAM" + i + ">");
			i++;
		}
		matcher.appendTail(sb);
		pathPattern = Pattern.compile(sb.toString());
		Pattern groupNamePattern = Pattern.compile("\\?\\<([^\\>]*)\\>");
		Matcher groupNameMatcher = groupNamePattern.matcher(sb.toString());
		LinkedList<String> names = new LinkedList<String>();
		while (groupNameMatcher.find()) {
			names.add(groupNameMatcher.group(1));
		}
		this.pathNames = names.toArray(new String[0]);
	}

	/**
	 * convert path to reg eg1. /user/{id} -> ^/user/(?<id>[^/]+) eg1. /user/{id}/name -> ^/user/(?<id>[^/]+)/name
	 * 
	 * @return
	 */
	private void parseParamPath() {
		Matcher matcher = Pattern.compile("\\{([^\\{]+)\\}").matcher(path);
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
		Matcher matcher = pathPattern.matcher(ServletUtils.trimPath(path));
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
