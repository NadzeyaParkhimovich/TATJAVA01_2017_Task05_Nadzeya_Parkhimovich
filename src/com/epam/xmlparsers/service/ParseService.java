package com.epam.xmlparsers.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.xmlparsers.bean.Food;
import com.epam.xmlparsers.parsers.ParserException;
import com.epam.xmlparsers.parsers.dom.DOMMenuParser;
import com.epam.xmlparsers.parsers.sax.SaxMenuParser;
import com.epam.xmlparsers.parsers.stax.StAXMenuParser;

public class ParseService {
	
	private static final Logger LOG = LogManager.getRootLogger();
	private final static String FILE_PATH = "D:/Projects/XMLParsers/src/com/epam/xmlparsers/menu.xml";	

	public static String domParse() {
		String response;
		try {
			HashMap<String, ArrayList<Food>> menu = DOMMenuParser.parse(FILE_PATH);
			response = responseCreator(menu);
		} catch (ParserException e) {
			LOG.error(e);
			response = "Sorry, we have error in DOM parsing";
		}
		return response;
	}
	
	public static String staxParse() {
		String response;
		try {
			HashMap<String, ArrayList<Food>> menu = StAXMenuParser.parse(FILE_PATH);
			response = responseCreator(menu);
		} catch (ParserException e) {
			LOG.error(e);
			response = "Sorry, we have error in StAX parsing";
		}
		return response;
	}
	
	public static String saxParse() {
		String response;
		try {
			HashMap<String, ArrayList<Food>> menu = SaxMenuParser.parse(FILE_PATH);
			response = responseCreator(menu);
		} catch (ParserException e) {
			LOG.error(e);
			response = "Sorry, we have error in SAX parsing";
		}
		return response;
	}
	
	private static String responseCreator(HashMap<String, ArrayList<Food>> menu) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, ArrayList<Food>> entry : menu.entrySet()) {
			sb.append(entry.getKey() + "\n");
			ArrayList<Food> meals = entry.getValue();
			for (Food food : meals) {
				sb.append(food.toString() + "\n");
			}
		}
		return sb.toString();
	}

}
