package com.candao.member.service;

import org.springframework.web.multipart.MultipartFile;

import com.candao.member.utils.ResultMessage;

/**    
 *     
 * 项目名称：member    
 * 类名称：ExcelExcutorService    
 * 类描述：excel服务    
 * 创建人：dengchao    
 * 创建时间：2016年6月24日 上午9:23:46    
 * 修改人：   
 * 修改时间：
 * 修改备注：    
 * @version     
 *     
 */
public interface ExcelExcutorService {

    /**     * resolve(excel表解析) 
     * @param   file 文件
     * @param   resolveBean 需要解析到的实体bean
     * @param   beginRow  解析起始行号（为excel的标题行号）
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1       
    */
    public ResultMessage resolve(MultipartFile file, Class resolveBean,Integer beginRow);
    
}
