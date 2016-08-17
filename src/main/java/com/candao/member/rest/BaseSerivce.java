/**    
 * 文件名：BaseSerivce.java    
 *    
 * 版本信息：    
 * 日期：2016年7月13日    
 * Copyright candaochina Corporation 2016     
 * 版权所有    
 *    
 */
package com.candao.member.rest;

import java.util.HashMap;
import java.util.Map;

import com.candao.member.utils.JacksonJsonMapper;

/**    
 *     
 * 项目名称：candao-member    
 * 类名称：BaseSerivce    
 * 类描述：    
 * 创建人：dengchao    
 * 创建时间：2016年7月13日 上午11:02:33    
 * 修改人：    
 * 修改时间： 
 * 修改备注：    
 * @version     
 *     
 */
public class BaseSerivce {

    protected String getSuccess(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("retcode", "0");
        map.put("retInfo", msg);
        return JacksonJsonMapper.objectToJson(map);
    }

    /**
     * 返回失败
     * @param mag
     * @return
     */
    protected String getFailure(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("retcode", "1");
        map.put("retInfo", msg);
        return JacksonJsonMapper.objectToJson(map);
    }

}
