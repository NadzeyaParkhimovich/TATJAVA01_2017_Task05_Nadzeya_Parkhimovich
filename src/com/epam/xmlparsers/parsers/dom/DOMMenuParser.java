package com.epam.xmlparsers.parsers.dom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.SAXException;

import com.epam.xmlparsers.bean.Food;
import com.epam.xmlparsers.parsers.ParserException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList; 



public class DOMMenuParser {    
	
	private static final Logger LOG = LogManager.getRootLogger();
	
	public static HashMap<String, ArrayList<Food>> parse(String file_path) throws ParserException {
		     
		DOMParser parser = new DOMParser();
		try {
			parser.parse(file_path);
		} catch (SAXException | IOException e) {
			LOG.error(e);
			throw new ParserException(e);
		}
		
		Document document = parser.getDocument();
		Element root = document.getDocumentElement();
		 
		ArrayList<Food> foodList;
		HashMap<String, ArrayList<Food>> menu = new HashMap<String, ArrayList<Food>>();
		String type = null;
		Food food = null;
		
		NodeList typeNodes = root.getElementsByTagName("type");
		
		for (int i = 0; i < typeNodes.getLength(); i++) {
			
			foodList = new ArrayList<Food>();
			Element typeElement = (Element) typeNodes.item(i);
			type = typeElement.getAttribute("id");
			
			NodeList foodNodes = typeElement.getElementsByTagName("food");
			for (int j = 0; j < foodNodes.getLength(); j++) {
				
				Element foodElement = (Element) foodNodes.item(j);
				
				food = foodCreator(foodElement);
				
				foodList.add(food);
			}
			
			menu.put(type, foodList);
		}
		
		return menu;
	 
	 }   
	
	private static Food foodCreator(Element foodElement) {
		Food food = new Food();
		food.setId(foodElement.getAttribute("id"));
		food.setName(getSingleChild(foodElement, "name").getTextContent().trim());
		food.setDescription(getSingleChild(foodElement, "description").getTextContent().trim());
		food.setWeight(getSingleChild(foodElement, "weight").getTextContent().trim());
		if (getSingleChild(foodElement, "price").getTextContent().length() > 0){
			food.setPrice(Integer.parseInt(getSingleChild(foodElement, "price").getTextContent()));
		}
		return food;
	}
	
	private static Element getSingleChild(Element element, String childName){
		NodeList nlist = element.getElementsByTagName(childName);
		Element child = (Element) nlist.item(0);
		return child;
	}
}