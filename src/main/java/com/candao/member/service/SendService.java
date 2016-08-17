/**
 * 发送账户服务
 */
package com.candao.member.service;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhao
 *
 */
public interface SendService {
  /**
   * 通过邮件发送账户密码
   * @param recipient
   * @param account
 * @throws IOException 
 * @throws MessagingException 
 * @throws AddressException 
   */
  void sendAccountByMail(String recipient, String account) throws IOException, AddressException, MessagingException;
  /**
   * 通过短信发送账户密码
   * @param telephone
   * @param account //change by 2016-07-08 添加返回短信状态
 * @throws IOException 
   */
  String sendAccountBySms(HttpServletRequest request, HttpServletResponse response,
			String telephone, String account) throws IOException;
  /**
   *  通过邮件发送验证码
   * @param paramMap 参数
 * @throws IOException 
 * @throws MessagingException 
 * @throws AddressException 
   */
  void sendValicodeByMail(String email, String valicode) throws IOException, AddressException, MessagingException;
  /**
   *  通过短信发送验证码
   * @param paramMap 参数
 * @throws IOException 
   */
  String sendValicodeBySms(String mobile, String valicode) throws IOException;
  /**
   *  通过邮件发送  找回密码  验证码
   * @param paramMap 参数
 * @throws IOException 
 * @throws MessagingException 
 * @throws AddressException 
   */
  void sendRetrievePwdValicodeByMail(String email, String valicode) throws IOException, AddressException, MessagingException;
  /**
   *  通过短信发送  找回密码  验证码
   * @param paramMap 参数
 * @throws IOException 
   */
  void sendRetrievePwdValicodeBySms(String mobile, String valicode) throws IOException;

}
