/*author sandeepgarg
 *
 */
package com.rest.api;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import jxl.Sheet;
import jxl.Workbook;

import common.utils.propertyReader;


public class baseClass {
	
	propertyReader pr = new propertyReader();
	
	String baseUri = pr.readProperty("uri");
	String port = pr.readProperty("port");
	String path = pr.readProperty("ordersPath");
	

	public static Sheet get_input_sheet() throws Exception
	{
		String curDir = System.getProperty("user.dir");
		FileInputStream fi = new FileInputStream(curDir+"\\test_data.xls");
		Workbook w = Workbook.getWorkbook(fi);
		Sheet s = w.getSheet(0);
		return s;
		
	}
	
	public static String getValue(int col, int row) throws Exception{
		return get_input_sheet().getCell(col, row).getContents();
	}
	
	
}

