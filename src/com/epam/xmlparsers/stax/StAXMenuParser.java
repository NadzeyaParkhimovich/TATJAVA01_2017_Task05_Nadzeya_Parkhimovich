package com.epam.xmlparsers.stax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.xmlparsers.bean.Food;
import com.epam.xmlparsers.bean.MenuTagName;

public class StAXMenuParser { 
	 
	 public static void main(String[] args) throws FileNotFoundException {   
		 XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		 try {
			 InputStream input = new FileInputStream("D:/Projects/XMLParsers/src/com/epam/xmlparsers/menu.xml"); 
			 XMLStreamReader reader = inputFactory.createXMLStreamReader(input);
			 
			 HashMap<String, ArrayList> menu = process(reader);
			 for (Map.Entry<String, ArrayList> entry : menu.entrySet()) {
				  System.out.println(entry.getKey());
				  ArrayList<Food> meals = entry.getValue();
				  for (Food food : meals) {
					  System.out.println(food.toString());
				  }
			
			 }   
		 } catch (XMLStreamException e) {    
			 e.printStackTrace();   
		 } 
	 } 
	 
	 private static  HashMap<String, ArrayList> process(XMLStreamReader reader) throws XMLStreamException {   
		 ArrayList<Food> foodList = null;
		 HashMap<String, ArrayList> menu = new HashMap<String, ArrayList>();
		 Food food = null;
		 String type = null;
		 MenuTagName elementName = null;
		 while (reader.hasNext()) {
			 // определение типа "прочтённого" элемента (тега)    
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
