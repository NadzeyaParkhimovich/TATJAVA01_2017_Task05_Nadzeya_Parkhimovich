package com.epam.xmlparsers.parsers.sax;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.epam.xmlparsers.bean.Food;
import com.epam.xmlparsers.bean.MenuTagName;


public class SaxMenuHandler extends DefaultHandler {  
	
	private static final Logger LOG = LogManager.getRootLogger();
	
	private ArrayList<Food> foodList;
	private HashMap<String, ArrayList<Food>> menu = new HashMap<String, ArrayList<Food>>();
	private Food food;  
	private StringBuilder text;
	private String type;

	public HashMap<String, ArrayList<Food>> getMenu() { 
		return menu;  
	} 

	public void startDocument() throws SAXException {
		LOG.trace("Parsing started.");
	}
	
	public void endDocument() throws SAXException {
		LOG.trace("Parsing ended.");
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		text = new StringBuilder();
		
		if (qName.equals("type")) {
			foodList = new ArrayList<Food>(); 
			type = attributes.getValue("id");
		} else {
			if (qName.equals("food")) {
				food = new Food();
				food.setId(attributes.getValue("id"));
			}
		}
	} 

	public void characters(char[] buffer, int start, int length) {
		text.append(buffer, start, length);
	} 

	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		MenuTagName tagName = MenuTagName.valueOf(qName.toUpperCase().replace("-", "_"));
		
		switch(tagName){
		case NAME:
			food.setName(text.toString());
			break;
		case WEIGHT:
			food.setWeight(text.toString());
			break;
		case DESCRIPTION:
			food.setDescription(text.toString());
			break;
		case PRICE:
			if(text.length()>0){
				food.setPrice(Integer.parseInt(text.toString()));
			}
			break;
		case FOOD:
			foodList.add(food);
			food = null;
			break;
		case TYPE:
			menu.put(type, foodList);
			break;
		}  
	} 

   public void warning(SAXParseException e) {
	   LOG.warn(e);
   } 

   public void error(SAXParseException e) {
	   LOG.error(e);
   } 

   public void fatalError(SAXParseException e) throws SAXException {
	   LOG.fatal(e);
	   throw (e);
   }
}
