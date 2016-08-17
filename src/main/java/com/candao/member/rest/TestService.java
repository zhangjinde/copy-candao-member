package com.candao.member.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/testService")
public class TestService {
	
	/**
	 * 测试接口
	 * @return
	 */
	@Path("/test")
    @POST
    @Produces({MediaType.TEXT_PLAIN})
	public Response test(String jsonString) throws UnsupportedEncodingException {
			
		return Response.ok(URLDecoder.decode("成功","utf-8")).build();
		
	}
	
}
