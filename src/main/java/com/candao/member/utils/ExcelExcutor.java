package com.candao.member.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

/**    
 *     
 * 项目名称：member    
 * 类名称：ExcelExcutor    
 * 类描述：excel执行器    
 * 创建人：dengchao    
 * 创建时间：2016年6月23日 上午11:03:29    
 * 修改人：    
 * 修改时间：    
 * 修改备注：    
 * @version     
 *     
 */
public class ExcelExcutor {

    private static Logger log = LoggerFactory.getLogger(ExcelExcutor.class);

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 工作表名
     */
    private String sheetName;

    /**
     * 标题行号
     */
    private int headerNum;

    /**
     * sheet列表
     */
    private List<Sheet> sheetList;

    /**
     * 是否执行flag
     */
    private Boolean excuteFlag = true;
    
    /**
     * 构造函数
     */
    public ExcelExcutor(){}
    

    /**
     * 初始化excel表
     * @param fileName
     * @throws IOException InvalidFormatException
     */
    public void initExcel(String fileName) throws InvalidFormatException, IOException {
        this.initExcel(new File(fileName));
    }

    /**
     * 初始化excel表
     * @param file
     * @throws IOException InvalidFormatException
     */
    public void initExcel(File file) throws InvalidFormatException, IOException {
        this.initExcel(file.getName(), new FileInputStream(file));
    }

    /**
     * 初始化excel表
     * @param multipartFile
     * @throws IOException InvalidFormatException
     */
    public void initExcel(MultipartFile multipartFile)
            throws InvalidFormatException, IOException {
        this.initExcel(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
    }
    
    /**
     * 初始化excel表
     * @param fileName
     * @param is
     * @throws InvalidFormatException 
     * @throws IOException 
     */
    public void initExcel(String fileName, InputStream is) throws InvalidFormatException, IOException{
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("导入文档为空!");
        } else if (fileName.toLowerCase().endsWith("xls")) {
            this.wb = new HSSFWorkbook(is);
        } else if (fileName.toLowerCase().endsWith("xlsx")) {
            this.wb = new XSSFWorkbook(is);
        } else {
            throw new RuntimeException("文档格式不正确!");
        }
        //获取sheet列表
        List<Sheet> sheetList = new ArrayList<Sheet>();
        for (int i = 0; i < this.wb.getNumberOfSheets(); i++) {
            sheetList.add(this.wb.getSheetAt(i));
        }
        this.setSheetList(sheetList);
        log.info("Initialize success.");
    }
    
    /**
     * 导入excel表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws InvalidFormatException 
     * @throws IOException 
     */
    public void importExcel(int headerNum) throws InvalidFormatException, IOException {
        this.importExcel(headerNum, 0);
    }
    
    /**
     * 导入excel表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws InvalidFormatException 
     * @throws IOException 
     */
    public void importExcel(int headerNum, int sheetIndex)
            throws InvalidFormatException, IOException {
        if (this.wb.getNumberOfSheets() < sheetIndex) {
            throw new RuntimeException("文档中没有工作表!");
        }
        this.sheet = this.wb.getSheetAt(sheetIndex);
        this.setSheetName(this.sheet.getSheetName());
        this.headerNum = headerNum;
        log.info("ImportExcel success.");
    }
    
    /**
     * 导入excel表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetName 工作表名
     * @throws InvalidFormatException 
     * @throws IOException 
     */
    public void importExcel(int headerNum, String sheetName)
            throws InvalidFormatException, IOException {
        if (this.wb.getSheet(sheetName)==null) {
            throw new RuntimeException("文档中没有工作表!");
        }
        this.sheet = this.wb.getSheet(sheetName);
        this.setSheetName(this.sheet.getSheetName());
        this.headerNum = headerNum;
        log.info("ImportExcel success.");
    }

    /**
     * 获取行对象
     * @param rownum
     * @return
     */
    public Row getRow(int rownum) {
        return this.sheet.getRow(rownum);
    }

    /**
     * 获取数据行号
     * @return
     */
    public int getDataRowNum() {
        return headerNum + 1;
    }

    /**
     * 获取最后一个数据行号
     * @return
     */
    public int getLastDataRowNum() {
        return this.sheet.getLastRowNum() + headerNum;
    }

    /**
     * 获取最后一个列号
     * @return
     */
    public int getLastCellNum() {
        return this.getRow(headerNum).getLastCellNum();
    }

    /**
     * 获取单元格值
     * @param row 获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public Object getCellValue(Row row, int column) {
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (cell != null) {
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    val = cell.getNumericCellValue();
                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                    val = cell.getCellFormula();
                } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
                    val = cell.getErrorCellValue();
                }
            }
        } catch (Exception e) {
            return val;
        }
        return val;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<Sheet> getSheetList() {
        return sheetList;
    }

    public void setSheetList(List<Sheet> sheetList) {
        this.sheetList = sheetList;
    }
    
    public <E> Callable<ResultMessage> exe(final Class<E> cls,final Integer beginDataRowNum, final Integer LastDataRowNum,int... groups){
        return  new Callable<ResultMessage>() {
            public ResultMessage call() throws Exception {
                return getDataList(cls,beginDataRowNum,LastDataRowNum);
            }
        };      
    }
    
    /**
     * 获取导入数据列表
     * @param cls 导入对象类型
     * @param groups 导入分组
     */
    public <E> ResultMessage getDataList(Class<E> cls,Integer beginDataRowNum,Integer lastDataRowNum,int... groups) throws InstantiationException, IllegalAccessException {
        //成功记录数
        Integer successNum = 0;
        //失败记录数
        Integer failureNum = 0;
        List<Object[]> annotationList = Lists.newArrayList();
        // Get annotation field 
        Field[] fs = cls.getDeclaredFields();
        for (Field f : fs) {
            ExcelField ef = f.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == 2)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    for (int g : groups) {
                        if (inGroup) {
                            break;
                        }
                        for (int efg : ef.groups()) {
                            if (g == efg) {
                                inGroup = true;
                                annotationList.add(new Object[] { ef, f });
                                break;
                            }
                        }
                    }
                } else {
                    annotationList.add(new Object[] { ef, f });
                }
            }
        }
        // Get annotation method
        Method[] ms = cls.getDeclaredMethods();
        for (Method m : ms) {
            ExcelField ef = m.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == 2)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    for (int g : groups) {
                        if (inGroup) {
                            break;
                        }
                        for (int efg : ef.groups()) {
                            if (g == efg) {
                                inGroup = true;
                                annotationList.add(new Object[] { ef, m });
                                break;
                            }
                        }
                    }
                } else {
                    annotationList.add(new Object[] { ef, m });
                }
            }
        }
        // Field sorting
        Collections.sort(annotationList, new Comparator<Object[]>() {
            public int compare(Object[] o1, Object[] o2) {
                return new Integer(((ExcelField) o1[0]).sort()).compareTo(new Integer(((ExcelField) o2[0]).sort()));
            };
        });
        //log.debug("Import column count:"+annotationList.size());
        // Get excel data
        List<E> successDataList = Lists.newArrayList();
        List<E> errorDataList = Lists.newArrayList();
        for (int i = beginDataRowNum/*this.getDataRowNum()*/; i < lastDataRowNum/*this.getLastDataRowNum()*/; i++) {
            E e = (E) cls.newInstance();
            int column = 0;
            Row row = this.getRow(i);
            StringBuilder sb = new StringBuilder();
            for (Object[] os : annotationList) {
                Object val = this.getCellValue(row, column++);
                if (val != null) {
                    ExcelField ef = (ExcelField) os[0];
                    // If is dict type, get dict value
                    /*if (StringUtils.isNotBlank(ef.dictType())){
                        val = DictUtils.getDictValue(val.toString(), ef.dictType(), "");
                        //log.debug("Dictionary type value: ["+i+","+colunm+"] " + val);
                    }*/
                    // Get param type and type cast
                    Class<?> valType = Class.class;
                    if (os[1] instanceof Field) {
                        valType = ((Field) os[1]).getType();
                    } else if (os[1] instanceof Method) {
                        Method method = ((Method) os[1]);
                        if ("get".equals(method.getName().substring(0, 3))) {
                            valType = method.getReturnType();
                        } else if ("set".equals(method.getName().substring(0, 3))) {
                            valType = ((Method) os[1]).getParameterTypes()[0];
                        }
                    }
                    //log.debug("Import value type: ["+i+","+column+"] " + valType);
                    try {
                        if (valType == String.class) {
                            String s = String.valueOf(val.toString());
                            if (StringUtils.endsWith(s, ".0")) {
                                val = StringUtils.substringBefore(s, ".0");
                            } else {
                                val = String.valueOf(val.toString());
                            }
                        } else if (valType == Integer.class) {
                            val = Double.valueOf(val.toString()).intValue();
                        } else if (valType == Long.class) {
                            val = Double.valueOf(val.toString()).longValue();
                        } else if (valType == Double.class) {
                            val = Double.valueOf(val.toString());
                        } else if (valType == Float.class) {
                            val = Float.valueOf(val.toString());
                        } else if (valType == Date.class) {
                            val = DateUtil.getJavaDate((Double) val);
                        } else {
                            if (ef.fieldType() != Class.class) {
                                val = ef.fieldType().getMethod("getValue", String.class).invoke(null, val.toString());
                            } else {
                                val = Class
                                        .forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(),
                                                "fieldtype." + valType.getSimpleName() + "Type"))
                                        .getMethod("getValue", String.class).invoke(null, val.toString());
                            }
                        }
                    } catch (Exception ex) {
                        log.info("Get cell value [" + i + "," + column + "] error: " + ex.toString());
                        val = null;
                    }
                    // set entity value
                    if (os[1] instanceof Field) {
                        Reflections.invokeSetter(e, ((Field) os[1]).getName(), val);
                    } else if (os[1] instanceof Method) {
                        String mthodName = ((Method) os[1]).getName();
                        if ("get".equals(mthodName.substring(0, 3))) {
                            mthodName = "set" + StringUtils.substringAfter(mthodName, "get");
                        }
                        Reflections.invokeMethod(e, mthodName, new Class[] { valType }, new Object[] { val });
                    }
                }
                sb.append(val + ", ");
            }
            HibernateValidateUtils hvu=new HibernateValidateUtils();
            String result="";
            try {
                result = hvu.validateModel(e);
            } catch (Exception e1) {
                log.error("excel表数据验证失败",e1);
                return new ResultMessage("500", "excel表数据验证失败");
            }//验证指定对象
            if(result.length()==0){
                successDataList.add(e);
                ++successNum;
            }else{
                e=(E) hvu.getvObj();
                //添加验证未通过结果列表
                errorDataList.add(e);
                ++failureNum;
            }
            log.debug("Read success: [" + i + "] " + sb.toString());
        }
        return new ResultMessage("200", successNum.toString(), failureNum.toString(), "成功解析数据表", successDataList, errorDataList);
    }

    /**
     * 导入测试
     */
    public static void main(String[] args) throws Throwable {

        ExcelExcutor ei = new ExcelExcutor();
        //初始化excel表
        ei.initExcel("target/export.xlsx");
        //导出sheet索引0
        ei.importExcel(1, 0);
        
        for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
            Row row = ei.getRow(i);
            for (int j = 0; j < ei.getLastCellNum(); j++) {
                Object val = ei.getCellValue(row, j);
                System.out.print(val + ", ");
            }
            System.out.print("\n");
        }

    }
}
