package com.ekremt.utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class DatabaseConfig {
	private static final String PERSISTENCE_XML = "/META-INF/persistence.xml";
	private static String databaseName;
	private static String username;
	private static String password;
	private static String url;
	
	static {
		loadConfig();
	}
	
	private static void loadConfig() {
		try {
			InputStream input = DatabaseConfig.class.getResourceAsStream(PERSISTENCE_XML);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(input);
			doc.getDocumentElement().normalize();
			
			NodeList properties = doc.getElementsByTagName("property");
			for (int i = 0; i < properties.getLength(); i++) {
				Element property = (Element) properties.item(i);
				String name = property.getAttribute("name");
				String value = property.getAttribute("value");
				
				switch (name) {
					case "jakarta.persistence.jdbc.url" -> {
						url = value;
						databaseName = value.substring(value.lastIndexOf('/') + 1);
					}
					case "jakarta.persistence.jdbc.user" -> username = value;
					case "jakarta.persistence.jdbc.password" -> password = value;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("persistence.xml dosyası okunamadı: " + e.getMessage(), e);
		}
	}
	
	public static String getDatabaseName() {
		return databaseName;
	}
	
	public static String getUsername() {
		return username;
	}
	
	public static String getPassword() {
		return password;
	}
	
	public static String getUrl() {
		return url;
	}
	
	public static String getBaseUrl() {
		return url.substring(0, url.lastIndexOf('/'));
	}
}