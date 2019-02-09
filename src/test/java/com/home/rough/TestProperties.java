package com.home.rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub		
		
		String pathOfCurrentDir = System.getProperty("user.dir");
		
		Properties config = new Properties();
		Properties OR = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
		config.load(fis);
		fis = new FileInputStream(pathOfCurrentDir + "\\src\\test\\resources\\properties\\OR.properties");
		config.load(fis);
		System.out.println(config.getProperty("browser"));
	}
 
}
