package common.utils;

import java.io.*;
import java.util.*;

public class PropertyReader {
	 
    String str, key;
    private Properties prop = new Properties();

    public PropertyReader() throws IOException {
           String curDir = System.getProperty("user.dir");
           loadProps(curDir + "\\src\\test\\resources\\config.Properties");
    }
    
    public void loadProps(String propertyFile) throws IOException {
           File cfgfile = new File(propertyFile);
           if (cfgfile.exists()) {
                  FileInputStream propin = new FileInputStream(cfgfile);
                  prop.load(propin);
           }
    }
    
    public String readProperty(String propkey) {
           
           return prop.getProperty(propkey);
    }
} 



