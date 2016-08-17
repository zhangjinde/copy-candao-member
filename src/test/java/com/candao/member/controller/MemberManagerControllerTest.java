/**    
 * 文件名：MemberManagerControllerTest.java    
 *    
 * 版本信息：    
 * 日期：2016年7月5日    
 * Copyright candaochina Corporation 2016     
 * 版权所有    
 *    
 */
package com.candao.member.controller;

import org.junit.Test;

/**    
 *     
 * 项目名称：candao-member    
 * 类名称：MemberManagerControllerTest    
 * 类描述：    
 * 创建人：dengchao    
 * 创建时间：2016年7月5日 下午3:12:00    
 * 修改人：    
 * 修改时间： 
 * 修改备注：    
 * @version     
 *     
 */
public class MemberManagerControllerTest {
    
    public static final String SERVER_URL = "http://localhost:8080/member";

    /**     * getCardNoByMobileTest(卡列表测试) 
     * @param   name
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1       
    */
    @Test
    public void getCardNoByMobileTest(){
        String url="/rest/memberCardService/getCardNoByMobile";
        String params = "{\"cardno\": \"15208166402\" ,\"branch_id\":\"404040\",\"password\":\"123456\"}";
        String returnInfo="";
        try {
            returnInfo = HttpUtils.httpPostWithJSON(SERVER_URL+url,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(returnInfo);
    }
    
    /**     * saveTest(注册测试)  
     * @param   name
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1       
    */
    @Test
    public void saveTest(){
        String url="/memberManager/save";
        String params = "{\"mobile\": \"15828515031\" ,\"branch_id\":\"83745\" "
                + ",\"cardno\":\"\"  ,\"password\":\"123456\"  ,\"name\":\"test\" "
                + " ,\"gender\":\"1\"  ,\"birthday\":\"2012-07-12\"  "
                + ",\"branch_addr\":\"ifs\"  ,\"channel\":\"0\" }";
        String returnInfo="";
        try {
            returnInfo = HttpUtils.httpPostWithJSON(SERVER_URL+url,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(returnInfo);
    }
    
    @Test
    public void bindingCardTest(){
        String url="/memberManager/delete";
        String params = "{\"cardno\": \"234\" ,\"branch_id\":\"83745\",\"FMemo\":\"611325\"}";
        String returnInfo="";
        try {
            returnInfo = HttpUtils.httpPostWithJSON(SERVER_URL+url,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(returnInfo);
    }
    
    @Test
    public void deleteByIdTest(){
    	String url="/memberManager/delete";
    	String params = "{\"cardno\": \"000000000000000000100033000001\" ,\"branch_id\":\"561706\"}";
        String returnInfo="";
        try {
            returnInfo = HttpUtils.httpPostWithJSON(SERVER_URL+url,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(returnInfo);
    }
    
    @Test
    public void validMobileTest(){
    	String url="/memberManager/validateTbMemberManager";
    	String params = "{\"branch_id\": \"525964\" ,\"mobile\":\"15982251604\"}";
        String returnInfo="";
        try {
            returnInfo = HttpUtils.httpPostWithJSON(SERVER_URL+url,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(returnInfo);
    }
}
