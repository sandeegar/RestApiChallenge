/*author sandeepgarg
 *
 */
package com.rest.api;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;

import jxl.JXLException;
import jxl.Sheet;
import jxl.Workbook;
import common.utils.PropertyReader;


public class BaseClass {
	 
    private PropertyReader pr = new PropertyReader();

    protected String baseUri = pr.readProperty("uri");
    protected String port = pr.readProperty("port");
    protected String path = pr.readProperty("ordersPath");
    private static Sheet sheet = null;
    
    
    
    public BaseClass() throws JXLException, IOException {
           String curDir = System.getProperty("user.dir");
           FileInputStream fi = new FileInputStream(curDir+"\\src\\test\\resources\\test_data.xls");
           Workbook w = Workbook.getWorkbook(fi);
           sheet = w.getSheet(0);
       	   PropertyConfigurator.configure(curDir+"\\src\\log4j.properties");
    }
    
    public static String getValue(int col, int row) throws Exception{
           return sheet.getCell(col, row).getContents();
    }
    
   
} 


