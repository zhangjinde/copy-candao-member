package com.candao.member.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {
	
	public static String callByPost(String url,String context) throws URISyntaxException, ClientProtocolException, IOException {
		String result = null;
		BasicHttpEntity urlEntity =  new BasicHttpEntity(); 
        urlEntity.setContent(new ByteArrayInputStream(context.getBytes()));
        HttpUriRequest req = RequestBuilder.post()
                .setUri(new URI(url))
                .setHeader("Content-Type", "text/plain").setHeader("connection", "Keep-Alive")  
                .setEntity(urlEntity).build();        
        CloseableHttpClient httpclient = HttpClients.custom().build();
/*       
        HttpPost hp = new HttpPost();
        hp.setURI(new URI(url));
        hp.setHeader("Content-Type", "text/plain");
        hp.setHeader("connection", "Keep-Alive");
        hp.setEntity(urlEntity);
        CloseableHttpResponse response = httpclient.execute(hp);
*/       
        CloseableHttpResponse response = httpclient.execute(req);
        try {
            HttpEntity entity = response.getEntity();
			System.out.println("状态: " + response.getStatusLine());
            result = EntityUtils.toString(entity, "utf-8");
            System.out.println("返回参数："+result);
            EntityUtils.consume(entity);
            EntityUtils.consume(urlEntity);
        } finally {
            response.close();
            httpclient.close();
        }
        return result;
	}
}
