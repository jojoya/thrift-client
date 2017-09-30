package test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class JsonTest {
			
	public static void main(String [] args){
		
		JSONObject fieldsObject = new JSONObject();		
		fieldsObject.put("80137276", "爱好游泳");  //new Integer(5024944)
		fieldsObject.put("80137277", "2017-7-24 11:20"); 
		fieldsObject.put("80137278", "4-500"); 
		fieldsObject.put("80137279", "109196"); 
		fieldsObject.put("80137280", "109205"); 
		fieldsObject.put("80137281", "109190,109191,109192,109193,109194"); 	    

	    JSONArray tagidsArray = new JSONArray();  
	    tagidsArray.add(0, new Integer(111));
	    tagidsArray.add(0, new Integer(222));
	    tagidsArray.add(0, new Integer(333));
	    
	    JSONObject dataObject = new JSONObject();  
	    dataObject.put("name", ""); 
	    dataObject.put("call", ""); 
	    dataObject.put("sex", ""); 
	    dataObject.put("birthday", ""); 
	    dataObject.put("birthday_lunar", ""); 
	    dataObject.put("crmfrom", ""); 
	    dataObject.put("groupid", ""); 
	    dataObject.put("title", ""); 
	    dataObject.put("qq", ""); 
	    dataObject.put("qqid", ""); 
	    dataObject.put("mobile", ""); 
	    dataObject.put("mobile_ccode", ""); 
	    dataObject.put("phone", ""); 
	    dataObject.put("phone_ecode", ""); 
	    dataObject.put("email", ""); 
	    dataObject.put("fax", ""); 
	    dataObject.put("fax_ccode", ""); 
	    dataObject.put("fax_ecode", ""); 
	    dataObject.put("province", ""); 
	    dataObject.put("city", ""); 
	    dataObject.put("vocation", ""); 
	    dataObject.put("company", ""); 
	    dataObject.put("website", ""); 
	    dataObject.put("address", ""); 
	    dataObject.put("memo", ""); 
	    dataObject.put("tagids", tagidsArray); 
	    dataObject.put("fields", fieldsObject); 
	    	    
	    JSONObject jsonObj = new JSONObject();
	    jsonObj.put("data",dataObject);
	    jsonObj.put("from",new Integer(1));
	    System.out.println("dataInfo>>>>"+jsonObj.toString());
	    System.out.println("jsonObj:"+String.valueOf(jsonObj));
	}
}
