/**    
 * 文件名：ConvertUtils.java    
 *    
 * 版本信息：    
 * 日期：2016年6月29日    
 * Copyright candaochina Corporation 2016     
 * 版权所有    
 *    
 */
package com.candao.member.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**    
 *     
 * 项目名称：candao-member    
 * 类名称：ConvertUtils    
 * 类描述：    
 * 创建人：dengchao    
 * 创建时间：2016年6月29日 下午5:11:50    
 * 修改人：    
 * 修改时间： 
 * 修改备注：    
 * @version     
 *     
 */
public class ConvertUtils {

    /**     * convertMap(这里用一句话描述这个方法的作用) 
     * @param   name
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1       
    */
    public static Object convertMap(Class type, Map map) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); //获取类属性 
        Object obj = type.newInstance(); //创建 JavaBean 对象 
        //给 JavaBean 对象的属性赋值 
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (map.containsKey(propertyName)) {
                //下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。 
                Object value = map.get(propertyName);
                Object[] args = new Object[1];
                args[0] = value;
                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }
}
