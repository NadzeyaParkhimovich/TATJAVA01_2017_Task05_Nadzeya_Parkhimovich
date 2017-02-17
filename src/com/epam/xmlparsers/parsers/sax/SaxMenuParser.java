package com.epam.xmlparsers.parsers.sax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.epam.xmlparsers.bean.Food;
import com.epam.xmlparsers.parsers.ParserException;

public class SaxMenuParser {

	private static final Logger LOG = LogManager.getRootLogger();
	
	public static HashMap<String, ArrayList<Food>> parse(String file_path) throws ParserException { 
		 
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			SaxMenuHandler handler = new SaxMenuHandler();
			reader.setContentHandler(handler);
			reader.parse(new InputSource(file_path));
			return handler.getMenu();
		} catch (IOException | SAXException e) {
			LOG.error(e);
			throw new ParserException(e);
		}
	} 
}
