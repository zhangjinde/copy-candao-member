package com.candao.member.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.candao.member.enums.SheetNameEnum;
import com.candao.member.service.ExcelExcutorService;
import com.candao.member.utils.ExcelExcutor;
import com.candao.member.utils.ResultMessage;

/**    
 *     
 * 项目名称：member    
 * 类名称：ExcelExcutorServiceImpl    
 * 类描述：excel服务    
 * 创建人：dengchao    
 * 创建时间：2016年6月24日 上午9:24:06    
 * 修改人：   
 * 修改时间：
 * 修改备注：    
 * @version     
 *     
 */
@Service
public class ExcelExcutorServiceImpl implements ExcelExcutorService {
    
    private static Logger log = LoggerFactory.getLogger(ExcelExcutorServiceImpl.class);
    
    @Value("#{appconfig.excel_import_seq}")
    private Integer excelImportSeq;

    @Value("#{appconfig.thread_num}")
    private Integer threadNum;
    
    /* (non-Javadoc)    
     * @see com.candao.member.service.ExcelExcutorService#resolve(org.springframework.web.multipart.MultipartFile, java.lang.Class, java.lang.Integer)    
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResultMessage resolve(MultipartFile file, Class resolveBean, Integer beginRow) {
        log.info("ExcelExcutorServiceImpl.beginRow={}.", beginRow);
        Integer successNum=0;
        Integer errorNum=0;
        try {
            //创建ExcelExcutor
            ExcelExcutor ei = new ExcelExcutor();
            //初始化excel表
            ei.initExcel(file);
            //导入会员信息表
            ei.importExcel(beginRow, 0);
            ThreadPoolExecutor pool = new ThreadPoolExecutor(threadNum, threadNum, 1, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(300));
            Integer lastRow = ei.getLastDataRowNum();
            Integer rowNum = lastRow - beginRow + 1;
            Integer span = (rowNum-beginRow) / excelImportSeq;
            //单线程执行
            if (span == 0||excelImportSeq==1|| (rowNum-beginRow)==1) {
                //校验工作簿名
                if (!ei.getSheetName().trim().equals(SheetNameEnum.MemberInfo.getValue().trim())) {
                    return new ResultMessage("400", "excel表中sheet名不符合规则要求");
                }
                //获取对象列表
                return ei.getDataList(resolveBean, beginRow, lastRow-1);
            } else {//多线程执行
                //校验工作簿名
                if (!ei.getSheetName().trim().equals(SheetNameEnum.MemberInfo.getValue().trim())) {
                    return new ResultMessage("400", "excel表中sheet名不符合规则要求");
                }
                Integer beginSeq = beginRow;
                Integer endSeq = beginRow + excelImportSeq;
                List<Object> successResultList = new ArrayList<Object>();
                List<Object> errorResultList = new ArrayList<Object>();
                for (int i = 0; i < span; i++) {
                    //导入会员信息表
                    ei.importExcel(beginSeq, 0);
                    //获取对象列表
                    ResultMessage result=(ResultMessage) pool.submit(ei.exe(resolveBean, beginSeq, endSeq)).get();
                    beginSeq = beginSeq + excelImportSeq;
                    //最后一段客户
                    if (i == (span - 2)) {
                        endSeq = lastRow-1;
                    } else {
                        endSeq = endSeq + excelImportSeq;
                    }
                    List<Object> successList=(List<Object>) result.getSuccessResult();
                    List<Object> errorList=(List<Object>) result.getErrorResult();
                    Integer sn=Integer.valueOf(result.getSuccessNum());
                    Integer en=Integer.valueOf(result.getErrorNum());
                    successNum=successNum+sn;
                    errorNum=errorNum+en;
                    successResultList.addAll(successList);
                    errorResultList.addAll(errorList);
                }
                pool.shutdown();
                return new ResultMessage("200",successNum.toString(),errorNum.toString(), "成功解析数据表", successResultList,errorResultList);
            }
        } catch (Exception e) {
            log.error("excel resolve exception", e);
            return new ResultMessage("500", "服务解析异常");
        }
    }


}
