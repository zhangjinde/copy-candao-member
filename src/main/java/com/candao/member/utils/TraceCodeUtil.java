package com.candao.member.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TraceCodeUtil {
	
	public static String generateTraceCode(){
		String traceCode = null;
	
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTimeStr = formatter.format(currentTime);
		traceCode = currentTimeStr + Integer.toString((int)((Math.random()*9+1)*1000));
			
		return traceCode ;
	}

}
