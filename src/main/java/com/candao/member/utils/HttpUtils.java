package com.candao.member.utils;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory; 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException; 
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map; 

/** 
* http工具类
* 用来模拟一些http的请求或者处理http相关的对象
* @author lishoukun
* @date 2015/05/08
*/ 
public final class HttpUtils { 
	private static Log log = LogFactory.getLog(HttpUtils.class); 
	
    /**
     * 定义编码格式 UTF-8
     */
    public static final String URL_PARAM_DECODECHARSET_UTF8 = "UTF-8";

    /**
     * 定义编码格式 GBK
     */
    private static final String URL_PARAM_CONNECT_FLAG = "&";

    private static final String EMPTY = "";

    private static MultiThreadedHttpConnectionManager connectionManager = null;

    private static int connectionTimeOut = 60 * 1000;//250000;

    private static int socketTimeOut = 60 * 1000;//250000;

    private static int maxConnectionPerHost = 10;

    private static int maxTotalConnections = 10;

    private static HttpClient client;

    private static final String APPLICATION_JSON = "application/json";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	
	/** 
	 * 执行一个HTTP GET请求，返回请求响应的HTML 
	 * 
	 * @param url	请求的URL地址 
	 * @param queryString 	请求的查询参数,可以为null 
	 * @return 返回请求响应的HTML 
	 */ 
	public static String doGet(String url, String queryString) { 
		//定义URL和接收返回的字段
	 	String uriAPI = url+"?"+queryString;
		String result= "";
		
		//创建HttpGet或HttpPost对象，将要请求的URL通过构造方法传入HttpGet或HttpPost对象。
		//HttpGet httpRequst = new HttpGet(URI uri);
		//HttpGet httpRequst = new HttpGet(String uri);
		HttpGet httpRequst = new HttpGet(uriAPI);
	
		try {
			//使用DefaultHttpClient类的execute方法发送HTTP GET请求，并返回HttpResponse对象。
			//new DefaultHttpClient().execute(HttpUriRequst requst);
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);//其中HttpGet是HttpUriRequst的子类
		    if(httpResponse.getStatusLine().getStatusCode() == 200)
		    {
		    	HttpEntity httpEntity = httpResponse.getEntity();
		    	result = EntityUtils.toString(httpEntity);//取出应答字符串
		    	// 一般来说都要删除多余的字符 
		    	result.replaceAll("\r", "");//去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格  
		    }else{
		    	httpRequst.abort();
		    }
			}
		    catch (ClientProtocolException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		return result;
	} 
	
	/** 
	 * 执行一个HTTP POST请求，返回请求响应的HTML 
	 * 
	 * @param url	请求的URL地址 
	 * @param params	请求的查询参数,可以为null 
	 * @param charset 字符集 
	 * @return 返回请求响应的HTML 
	 */ 
	public static String doPost(String url, Map<String, String> params1) {
		//定义URL和接收返回的字段
		String uriAPI = url;//Post方式没有参数在这里
    	String result = "";
    	//创建HttpPost对象
    	HttpPost httpRequst = new HttpPost(uriAPI);
    	//拼凑参数
    	List <NameValuePair> params = new ArrayList<NameValuePair>();
    	if(params1!=null){
    		Iterator<String> it = params1.keySet().iterator();
        	while(it.hasNext()){
        		String key = it.next();
        		String value = String.valueOf(params1.get(key));
        		params.add(new BasicNameValuePair(key,value));
        	}
    	}
    	
    	try {
			httpRequst.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
		    if(httpResponse.getStatusLine().getStatusCode() == 200)
		    {
		    	HttpEntity httpEntity = httpResponse.getEntity();
		    	result = EntityUtils.toString(httpEntity);//取出应答字符串
		    }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		}
    	return result;
	} 
	
	
	public static String httpPostBookorderArray(String url, JSONObject json) {
		try {
			org.apache.http.client.HttpClient client = new org.apache.http.impl.client.DefaultHttpClient();
			client =  setClientTimeOutOneMin(client, 60000);
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/json; charset=UTF-8");
			post.setEntity(new StringEntity(json.toString(), "UTF-8"));
			HttpResponse response = client.execute(post);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				InputStream is = response.getEntity().getContent();
				String result = InputStreamTOString(is);
				return result;
			} else {
				return "error：" + code;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	  public static String InputStreamTOString(InputStream in) throws Exception{  
		  
		  int BUFFER_SIZE = 88888888;
	      
	      ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	      byte[] data = new byte[BUFFER_SIZE];  
	      int count = -1;  
	      while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
	          outStream.write(data, 0, count);  
	        
	      data = null;  
	      return new String(outStream.toByteArray(),"UTF-8");//  
	  }  

	
	public static org.apache.http.client.HttpClient setClientTimeOutOneMin(org.apache.http.client.HttpClient client, int time) {
		client.getParams().setParameter("1000", time); // 请求超时
		client.getParams().setParameter("1000", time); // 读取超时
		return client;
	}
	
	
	public static void main(String[] args) { 
		String user = "shcdwlw";
		//原密码为B9Wp802j，md5 32位小写后为76a36271631306061c31b36d375a330c
		String pwd = "76a36271631306061c31b36d375a330c";
		Map<String,String> param = new HashMap<String,String>();
		param.put("jsonString", "{\"branch_id\":820313,\"securityCode\":11,\"cardno\":147,\"password\":123456}");
	    String y = doPost("http://localhost:8080/member/memberManager/findByParams", param); 
	    System.out.println("result:\n"+y); 
	}

	public static String httpPostWithJSON(String url, String json) throws Exception{
		// TODO Auto-generated method stub
		 //将JSON进行UTF-8编码,以便传输中文
        //        String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
        StringEntity se = new StringEntity(json);
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
        httpPost.setEntity(se);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        org.json.JSONObject resultJsonObject = null;
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            //得到httpResponse的实体数据
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8 * 1024);
                    StringBuilder entityStringBuilder = new StringBuilder();
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        entityStringBuilder.append(line + "/n");
                    }
                    // 利用从HttpEntity中得到的String生成JsonObject
                    resultJsonObject = new org.json.JSONObject(entityStringBuilder.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resultJsonObject.toString();
    }
}