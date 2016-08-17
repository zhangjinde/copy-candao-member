package com.candao.member.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;


/**    
 *     
 * 项目名称：candao-member    
 * 类名称：HttpUtils    
 * 类描述： http请求工具类   
 * 创建人：dengchao    
 * 创建时间：2016年7月5日 上午10:18:01    
 * 修改人：    
 * 修改时间： 
 * 修改备注：    
 * @version     
 *     
 */
public class HttpUtils {

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

    static {
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
        connectionManager.getParams().setSoTimeout(socketTimeOut);
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
        client = new HttpClient(connectionManager);
    }

    /**
     * POST方式提交数据
     * @param url
     * 			待请求的URL
     * @param params
     * 			要提交的数据
     * @param enc
     * 			编码
     * @return
     * 			响应结果
     * @throws java.io.IOException
     * 			IO异常
     */
    public static String URLPost(String url, Map<String, String> params, String enc) {

        String response = EMPTY;
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            //将表单的值放入postMethod中
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                String value = params.get(key);
                postMethod.addParameter(key, value);
            }
            //执行postMethod
            int statusCode = client.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = reader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                response = stringBuffer.toString();
            } else {
                log.error("响应状态码 = " + postMethod.getStatusCode());
            }
        } catch (HttpException e) {
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("发生网络异常", e);
            e.printStackTrace();
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
                postMethod = null;
            }
        }
        return response;
    }

    /**
     * GET方式提交数据
     * @param url
     * 			待请求的URL
     * @param params
     * 			要提交的数据
     * @param enc
     * 			编码
     * @return
     * 			响应结果
     * @throws IOException
     * 			IO异常
     */
    public static String URLGet(String url, Map<String, String> params, String enc) {

        String response = EMPTY;
        GetMethod getMethod = null;
        StringBuffer strtTotalURL = new StringBuffer(EMPTY);

        log.info("GET请求URL = \n" + strtTotalURL.toString());
        if (strtTotalURL.indexOf("?") == -1) {
            strtTotalURL.append(url).append("?").append(getUrl(params, enc));
        } else {
            strtTotalURL.append(url).append("&").append(getUrl(params, enc));
        }

        try {
            getMethod = new GetMethod(strtTotalURL.toString());
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            //执行getMethod
            int statusCode = client.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                response = getMethod.getResponseBodyAsString();
            } else {
                log.debug("响应状态码 = " + getMethod.getStatusCode());
            }
        } catch (HttpException e) {
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("发生网络异常", e);
            e.printStackTrace();
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
                getMethod = null;
            }
        }
        return response;
    }

    /**
     * 据Map生成URL字符串
     * @param map
     * 			Map
     * @param valueEnc
     * 			URL编码
     * @return
     * 			URL
     */
    private static String getUrl(Map<String, String> map, String valueEnc) {

        if (null == map || map.keySet().size() == 0) {
            return (EMPTY);
        }
        StringBuffer url = new StringBuffer();
        Set<String> keys = map.keySet();
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            String key = it.next();
            if (map.containsKey(key)) {
                String val = map.get(key);
                String str = val != null ? val : EMPTY;
                try {
                    str = URLEncoder.encode(str, valueEnc);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
            }
        }
        String strURL = EMPTY;
        strURL = url.toString();
        if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {
            strURL = strURL.substring(0, strURL.length() - 1);
        }
        return (strURL);
    }

    /**     * httpPostWithJSON(json格式数据请求) 
     * @param   name
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1       
    */
    public static String httpPostWithJSON(String url, String json) throws Exception {
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
        JSONObject resultJsonObject = null;
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
                    resultJsonObject = new JSONObject(entityStringBuilder.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resultJsonObject.toString();
    }
}
