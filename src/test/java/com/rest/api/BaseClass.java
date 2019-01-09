/*author sandeepgarg
 *
 */
package com.rest.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeSuite;
import jxl.Sheet;
import jxl.Workbook;
import common.utils.PropertyReader;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;


public class BaseClass {
	 
    private PropertyReader pr = new PropertyReader();

    protected String baseUri = pr.readProperty("uri");
    protected String port = pr.readProperty("port");
    protected String path = pr.readProperty("ordersPath");
    private static Sheet sheet = null;
    
    static Logger log = Logger.getRootLogger();
    protected ArrayList<Double> lattitude = new ArrayList<Double>();
    protected ArrayList<Double> longitude = new ArrayList<Double>();
    protected RequestSpecification httpRequest = null;
    
	@BeforeSuite
	public void initialize() throws Exception {
		try {
				log.setLevel(Level.DEBUG);	
				for (int row=1;row<=3; row++)
				{
					lattitude.add(Double.valueOf(getValue(1, row)));
					longitude.add(Double.valueOf(getValue(2, row)));
				}
				
				RestAssured.baseURI = baseUri+":"+port;		
				httpRequest = RestAssured.given();
		        httpRequest.header("Content-Type", "application/json");
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
		
	}
    
    public BaseClass() throws Exception {
    	try {
	           String curDir = System.getProperty("user.dir");
	           FileInputStream fi = new FileInputStream(curDir+"\\src\\test\\resources\\test_data.xls");
	           Workbook w = Workbook.getWorkbook(fi);
	           sheet = w.getSheet(0);
	       	   PropertyConfigurator.configure(curDir+"\\src\\log4j.properties");
    	}
    	catch(IOException e) {
    		  log.error("There is an IO Execption", e);
    	}
    }
    
    public static String getValue(int col, int row) throws Exception{
    	String sheetValue = null;
    	try {
            sheetValue = sheet.getCell(col, row).getContents();
       	}
    	
    	catch (Exception e) {
			log.error("There is an Execption", e);
		}
		return sheetValue;
    	
    }
    
    
   
} 


