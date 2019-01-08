package common.utils;

import java.io.*;
import java.util.*;

public class propertyReader {
	
	
		  String str, key;
		  private String filepath;

		  public void config(String filepath){
			  this.filepath=filepath;
		  }

		  public String readProperty(String propkey){
			  String propval="";
			  try{
				  int check = 0;
				  while(check == 0){
					  check = 1;
					  String curDir = System.getProperty("user.dir");
		        	  File cfgfile = new File(curDir+"\\config.Properties");
					  if(cfgfile.exists()){
							  Properties props = new Properties();
							  FileInputStream propin = new FileInputStream(cfgfile);
							  props.load(propin);
							  propval=props.getProperty(propkey);
						  }
						  else{
							  check = 0;
					  }
					}
				  }
				  catch(IOException e){
					  e.printStackTrace();
				  }
			return propval;
		  }
	}


