package com.epam.xmlparsers.parsers.stax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.xmlparsers.bean.Food;
import com.epam.xmlparsers.bean.MenuTagName;
import com.epam.xmlparsers.parsers.ParserException;

public class StAXMenuParser { 
	 
	 private static final Logger LOG = LogManager.getRootLogger();
	
	 public static HashMap<String, ArrayList<Food>> parse(String file_path) throws ParserException {   
		 
		 XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		 HashMap<String, ArrayList<Food>> menu = null;
		 try {
			 InputStream input = new FileInputStream(file_path); 
			 XMLStreamReader reader = inputFactory.createXMLStreamReader(input);
			 
			 menu = process(reader);
			   
		 } catch (XMLStreamException | FileNotFoundException e) {    
			 LOG.error(e); 
			 throw new ParserException(e);
		 }
		return menu; 
		 
	 } 
	 
	 private static  HashMap<String, ArrayList<Food>> process(XMLStreamReader reader) throws XMLStreamException {   
		 ArrayList<Food> foodList = null;
		 HashMap<String, ArrayList<Food>> menu = new HashMap<String, ArrayList<Food>>();
		 Food food = null;
		 String type = null;
		 MenuTagName elementName = null;
		 while (reader.hasNext()) {
			 
			 int element = reader.next();
			 switch (element) {
			 case XMLStreamConstants.START_ELEMENT:
				 elementName = MenuTagName.valueOf(reader.getLocalName().toUpperCase());
				 switch (elementName) {
				 case TYPE:
					 foodList = new ArrayList<Food>(); 
					 type = reader.getAttributeValue(null, "id");
				 case FOOD:
					 food = new Food();
					 food.setId(reader.getAttributeValue(null, "id"));
					 break;
				 }
				 break; 
				 
			 case XMLStreamConstants.CHARACTERS:
				 String text = reader.getText().trim();
				 if (text.isEmpty()) {
					 break;     
				 }
				 switch (elementName) {
				 case NAME:
					 food.setName(text);
					 break;
				 case DESCRIPTION:
					 food.setDescription(text);
					 break;
				 case WEIGHT:
					 food.setWeight(text);
					 break;
				 case PRICE:
					 food.setPrice(Integer.parseInt(text));
				 }
				 break; 
	 
			 case XMLStreamConstants.END_ELEMENT:
				 elementName = MenuTagName.valueOf(reader.getLocalName().toUpperCase());
				 switch (elementName) { 
				 case FOOD:
					 foodList.add(food);
					 break;
				 case TYPE:
					 menu.put(type, foodList);
				 } 
	   } 
	 
	  }   
		 return menu;  
	} 
}
