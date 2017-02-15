package com.epam.xmlparsers.sax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.epam.xmlparsers.bean.Food;

public class SaxDemo {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException { 
		 
		  XMLReader reader = XMLReaderFactory.createXMLReader();
		  MenuSaxHandler handler = new MenuSaxHandler();
		  reader.setContentHandler(handler);
		  reader.parse(new InputSource("D:/Projects/XMLParsers/src/com/epam/xmlparsers/menu.xml"));
		 		 
		  HashMap<String, ArrayList> menu = handler.getMenu();
		  for (Map.Entry<String, ArrayList> entry : menu.entrySet()) {
			  System.out.println(entry.getKey());
			  ArrayList<Food> meals = entry.getValue();
			  System.out.println("Name | Description | Weight");
			  for (Food food : meals) {
				  System.out.println(food.getName() + " | " + food.getDescription() + " | " + food.getWeight());
			  }
		  }
	} 
}
