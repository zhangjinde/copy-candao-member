package com.candao.member.utils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.candao.member.service.impl.ExcelExcutorServiceImpl;

/**    
 *     
 * 项目名称：candao-member    
 * 类名称：HibernateValidateUtils    
 * 类描述：    
 * 创建人：dengchao    
 * 创建时间：2016年6月27日 上午9:15:38    
 * 修改人：   
 * 修改时间：  
 * 修改备注：    
 * @version     
 *     
 */
public class HibernateValidateUtils {
    
    private static Logger log = LoggerFactory.getLogger(ExcelExcutorServiceImpl.class);
    
    public Object vObj;

    /**    
     * vObj    
     *    
     * @return  the vObj    
     * @since   CodingExample Ver(编码范例查看) 1.0    
    */
    
    public Object getvObj() {
        return vObj;
    }

    /**    
     * @param vObj the vObj to set    
     */
    public void setvObj(Object vObj) {
        this.vObj = vObj;
    }



    /**     * validateModel(验证某个指定对象) 
     * @param   name
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1       
    */
    public String validateModel(Object obj) throws Exception {  
        StringBuffer buffer = new StringBuffer(64);//用于存储验证后的错误信息  
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);//验证某个对象,，其实也可以只验证其中的某一个属性的  
        Iterator<ConstraintViolation<Object>> iter = constraintViolations.iterator();
        try {
            while (iter.hasNext()) {
                ConstraintViolation<Object> it=iter.next();
                //Path error=it.getPropertyPath();
                String message = it.getMessage();
                /*Field[] fs=it.getLeafBean().getClass().getDeclaredFields();
                for (Field f : fs) {
                    if(f.getName().equals(error.toString())){
                        f.set(obj, "<span style='color:red'>"+message+"</span>");
                    }
                }*/
                buffer.append(message);
            };
            this.setvObj(obj);
        } catch (Exception e) {
            log.error("validateModel error",e);
        }
        return buffer.toString();
    }

}
