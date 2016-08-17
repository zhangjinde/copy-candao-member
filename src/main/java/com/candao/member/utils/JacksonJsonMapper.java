package com.candao.member.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JacksonJsonMapper {
	static volatile ObjectMapper objectMapper = null;

	private JacksonJsonMapper() {
	}

	public static ObjectMapper getInstance() {
		if (objectMapper == null) {
			synchronized (ObjectMapper.class) {
				if (objectMapper == null) {
					objectMapper = new JacksonObjectMapper();
				} 
			}
		}
		return objectMapper;
	}

	public static String objectToJson(Object beanobject) {
		ObjectMapper mapper = JacksonJsonMapper.getInstance();
		String resutl = null;
		try {
			resutl = mapper.writeValueAsString(beanobject);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		return resutl;
	}

	public static <T> T jsonToObject(String jsonString, Class<T> claszz) {
		T t = null;
		try {
			t = JacksonJsonMapper.getInstance().readValue(jsonString, claszz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * json字符串转换为泛型列表
	 * @param jsonString,json字符串
	 * @param claszz,列表中的泛型类
	 * @return
	 */
	public static <E, T> ArrayList<E> jsonToList(String jsonString, Class<T> claszz) {
		ObjectMapper mapper = JacksonJsonMapper.getInstance();
		ArrayList<E> t = null;
		try {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, claszz);   
			t = mapper.readValue(jsonString, javaType); 
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	 public static Map<String, String> getData(String str){
	     String sb = str.substring(1, str.length()-1);
	     String[] name =  sb.split("\\\",\\\"");
	     String[] nn =null;
	     Map<String, String> map = new HashMap<String,String>();
	     for(int i= 0;i<name.length; i++){
	         nn = name[i].split("\\\":\\\"");
	         map.put(nn[0], nn[1]);
	     }
	     return map;
	 }
	 
	 
	 public static Map<String, Object> parseJSON2Map(String jsonStr){  
	        Map<String, Object> map = new HashMap<String, Object>();  
	        //最外层解析  
	        JSONObject json = JSONObject.fromObject(jsonStr);  
	        for(Object k : json.keySet()){  
	            Object v = json.get(k);   
	            //如果内层还是数组的话，继续解析  
	            if(v instanceof JSONArray){  
	                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
	                Iterator<JSONObject> it = ((JSONArray)v).iterator();  
	                while(it.hasNext()){  
	                    JSONObject json2 = it.next();  
	                    list.add(parseJSON2Map(json2.toString()));  
	                }  
	                map.put(k.toString(), list);  
	            } else {  
	                map.put(k.toString(), v);  
	            }  
	        }  
	        return map;  
	    }  
	 
}
