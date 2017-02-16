package com.epam.xmlparsers.dom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.SAXException;

import com.epam.xmlparsers.bean.Food;
import com.epam.xmlparsers.bean.MenuTagName;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 



public class DOMMenuParser {    
	
	public static void main(String[] args) throws SAXException, IOException{
		//создание DOM-анализатора (Xerces)       
		DOMParser parser = new DOMParser();
		parser.parse("D:/Projects/XMLParsers/src/com/epam/xmlparsers/menu.xml");
		Document document = parser.getDocument();
		Element root = document.getDocumentElement();
		 
		ArrayList<Food> foodList;
		HashMap<String, ArrayList> menu = new HashMap<String, ArrayList>();
		String type = null;
		MenuTagName elementName = null;
		Food food = null;
		
		NodeList typeNodes = root.getElementsByTagName("type");
		
		for (int i = 0; i < typeNodes.getLength(); i++) {
			
			foodList = new ArrayList<Food>();
			Element typeElement = (Element) typeNodes.item(i);
			type = typeElement.getAttribute("id");
			
			NodeList foodNodes = typeElement.getChildNodes();
			for (int j = 1; j < foodNodes.getLength(); j+=2) {
				food = new Food();
				if(foodNodes.item(j).getNodeType() != Node.ELEMENT_NODE){
					System.out.println(foodNodes.item(j).getNodeType());
				}
				Element foodElement = (Element) foodNodes.item(j);
				food.setId(foodElement.getAttribute("id"));
				food.setName(getSingleChild(foodElement, "name").getTextContent().trim());
				food.setDescription(getSingleChild(foodElement, "description").getTextContent().trim());
				food.setWeight(getSingleChild(foodElement, "weight").getTextContent().trim());
				if (getSingleChild(foodElement, "price").getTextContent().length() > 0){
					food.setPrice(Integer.parseInt(getSingleChild(foodElement, "price").getTextContent()));
				}
				foodList.add(food);
			}
			
			menu.put(type, foodList);
		}
		
		for (Map.Entry<String, ArrayList> entry : menu.entrySet()) {
			  System.out.println(entry.getKey());
			  ArrayList<Food> meals = entry.getValue();
			  for (Food meal : meals) {
				  System.out.println(meal.toString());
			  }
		  }
			
	 
	 }    
	
	private static Element getSingleChild(Element element, String childName){
		NodeList nlist = element.getElementsByTagName(childName);
		Element child = (Element) nlist.item(0);
		return child;
	}
}