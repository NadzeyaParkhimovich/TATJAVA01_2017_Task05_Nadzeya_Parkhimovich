package com.epam.xmlparsers.sax;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.epam.xmlparsers.bean.Food;
import com.epam.xmlparsers.bean.MenuTagName;


public class MenuSaxHandler extends DefaultHandler {  
	
	private ArrayList<Food> foodList;
	private HashMap<String, ArrayList> menu = new HashMap<String, ArrayList>();
	private Food food;  
	private StringBuilder text;
	private String type;

	public HashMap<String, ArrayList> getMenu() { 
		return menu;  
	} 

	public void startDocument() throws SAXException {
		System.out.println("Parsing started.");
	}
	
	public void endDocument() throws SAXException {
		System.out.println("Parsing ended.");
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

   public void warning(SAXParseException exception) {
	   System.err.println("WARNING: line " + exception.getLineNumber() + ": " + exception.getMessage());
   } 

   public void error(SAXParseException exception) {
	   System.err.println("ERROR: line " + exception.getLineNumber() + ": " + exception.getMessage());
   } 

   public void fatalError(SAXParseException exception) throws SAXException {
	   System.err.println("FATAL: line " + exception.getLineNumber() + ": " + exception.getMessage());
	   throw (exception);
   }
}
