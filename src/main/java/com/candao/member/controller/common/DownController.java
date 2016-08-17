package com.candao.member.controller.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.candao.member.controller.BaseController;

@Controller
@RequestMapping("/down")
public class DownController extends BaseController {

	@RequestMapping("/detailTemplate")
	@ResponseBody
	public String detailTemplate(HttpServletRequest request,HttpServletResponse response){
		 String targetName = "会员导入模版.xls";
		 String fileName = "会员导入模版.xls";
	     String agent = request.getHeader("USER-AGENT").toLowerCase();
	     //根据浏览器类型处理文件名称
	     if(agent.indexOf("msie")>-1){
	         try {
				fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	     }else{
	         try {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	     }		
		response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        try {
            String path = Thread.currentThread().getContextClassLoader()
                    .getResource("").getPath()
                    + "imp";
            InputStream inputStream = new FileInputStream(new File(path
                    + File.separator + targetName));

            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.close();

            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
}
