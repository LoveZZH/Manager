package cn.itcast.utils;

import java.beans.IntrospectionException;
import java.io.IOException;  
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
 
import java.text.SimpleDateFormat;  
import java.util.ArrayList;  
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.itcast.pojo.ExcelBean;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
  
  
public class ExcelUtils {  
      
    private final static String excel2003L =".xls";    //2003- 版本的excel  
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel  
      
    /** 
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象 
     * @param in,fileName 
     * @return 
     * @throws IOException  
     */  
    public  List<List<Object>> getBankListByExcel(InputStream in,String fileName) throws Exception{  
        List<List<Object>> list = null;  
          
        //创建Excel工作薄  
        Workbook work = this.getWorkbook(in,fileName);  
        if(null == work){  
            throw new Exception("创建Excel工作薄为空！");  
        }  
        Sheet sheet = null;  //页数
        Row row = null;  //行数
        Cell cell = null;  //列数
          
        list = new ArrayList<List<Object>>();  
        //遍历Excel中所有的sheet  
        for (int i = 0; i < work.getNumberOfSheets(); i++) {  
            sheet = work.getSheetAt(i);  
            if(sheet==null){continue;}  
              
            //遍历当前sheet中的所有行  
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {  
                row = sheet.getRow(j);  
                if(row==null||row.getFirstCellNum()==j){continue;}  
                  
                //遍历所有的列  
                List<Object> li = new ArrayList<Object>();  
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {  
                    cell = row.getCell(y);  
                    li.add(this.getValue(cell));  
                }  
                list.add(li);  
            }  
        }  
 
        return list;  
        
    }  
      
    /** 
     * 描述：根据文件后缀，自适应上传文件的版本  
     * @param inStr,fileName 
     * @return 
     * @throws Exception 
     */  
    public  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{  
        Workbook wb = null;  
        String fileType = fileName.substring(fileName.lastIndexOf("."));  
        if(excel2003L.equals(fileType)){  
            wb = new HSSFWorkbook(inStr);  //2003-  
        }else if(excel2007U.equals(fileType)){  
            wb = new XSSFWorkbook(inStr);  //2007+  
        }else{  
            throw new Exception("解析的文件格式有误！");  
        }  
        return wb;  
    }  
  
    /** 
     * 描述：对表格中数值进行格式化 
     * @param cell 
     * @return 
     */  
  //解决excel类型问题，获得数值  
    public  String getValue(Cell cell) {  
        String value = "";  
        if(null==cell){  
            return value;  
        }  
        switch (cell.getCellType()) {  
        //数值型  
        case Cell.CELL_TYPE_NUMERIC:  
            if (HSSFDateUtil.isCellDateFormatted(cell)) {  
                //如果是date类型则 ，获取该cell的date值  
                Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());  
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
                value = format.format(date);;  
            }else {// 纯数字  
                BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
                value = big.toString();  
                //解决1234.0  去掉后面的.0  
                if(null!=value&&!"".equals(value.trim())){  
                     String[] item = value.split("[.]");  
                     if(1<item.length&&"0".equals(item[1])){  
                         value=item[0];  
                     }  
                }  
            }  
            break;  
            //字符串类型   
        case Cell.CELL_TYPE_STRING:  
            value = cell.getStringCellValue().toString();  
            break;  
        // 公式类型  
        case Cell.CELL_TYPE_FORMULA:  
            //读公式计算值  
            value = String.valueOf(cell.getNumericCellValue());  
            if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串  
                value = cell.getStringCellValue().toString();  
            }  
            break;  
        // 布尔类型  
        case Cell.CELL_TYPE_BOOLEAN:  
            value = " "+ cell.getBooleanCellValue();  
            break;   
        default:  
            value = cell.getStringCellValue().toString();  
    }  
    if("null".endsWith(value.trim())){  
        value="";  
    }  
  return value;  
    }  
      
    /**
	 * 导出Excel表
	 * @param clazz 数据源model类型
	 * @param objs excel标题以及对应的model字段
	 * @param map 标题行数以及cell字体样式
	 * @param sheetName 工作簿名称
	 * @return
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static XSSFWorkbook createExcelFile(
			Class<?> clazz,
			List<Map<String,Object>> objs,
			Map<Integer,List<ExcelBean>> map,
			String sheetName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		//创建新的工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		//创建工作表
		XSSFSheet sheet = workbook.createSheet(sheetName);
		//设置excel的字体样式以及标题与内容的创建
		createFont(workbook);//字体样式
		createTableHeader(sheet,map);//创建标题
		createTableRows(sheet,map,objs,clazz);//创建内容
		System.out.println(workbook);
		return workbook;
	}
	private static XSSFCellStyle fontStyle;
	private static XSSFCellStyle fontStyle2;
	private static void createFont(XSSFWorkbook workbook) {
		//表头
		fontStyle = workbook.createCellStyle();
		XSSFFont font1 = workbook.createFont();
		font1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font1.setFontName("黑体");
		font1.setFontHeightInPoints((short) 12);//字体大小
		fontStyle.setFont(font1);
		fontStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);//下边框
		fontStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
		fontStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//右边框
		fontStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
		fontStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//居中
		//内容
		fontStyle2 = workbook.createCellStyle();
		XSSFFont font2 = workbook.createFont();
		font2.setFontName("宋体");
		font2.setFontHeightInPoints((short)10);
		fontStyle2.setFont(font2);
		fontStyle2.setBorderBottom(XSSFCellStyle.BORDER_THIN);//下边框
		fontStyle2.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
		fontStyle2.setBorderTop(XSSFCellStyle.BORDER_THIN);//右边框
		fontStyle2.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
		fontStyle2.setAlignment(XSSFCellStyle.ALIGN_CENTER);//居中
	}
	/**
	 * 根据ExcelMapping 生成列头(多行列头)
	 * @param sheet 工作簿
	 * @param map 每行每个单元格对应的列头信息
	 */
	private static void createTableHeader(
			XSSFSheet sheet, 
			Map<Integer, List<ExcelBean>> map) {
		int startIndex = 0;//cell起始位置
		int endIndex = 0;//cell终止位置
		for(Map.Entry<Integer,List<ExcelBean>> entry: map.entrySet()){
			XSSFRow row = sheet.createRow(entry.getKey()); //创建行
			List<ExcelBean> excels = entry.getValue();
			for(int x=0;x<excels.size();x++){
				//合并单元格
				if(excels.get(x).getCols()>1){
					if(x==0){
						endIndex += excels.get(x).getCols()-1;
						//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
						sheet.addMergedRegion(new CellRangeAddress(0, 0, startIndex, endIndex));
						startIndex += excels.get(x).getCols();
					}else{
						endIndex += excels.get(x).getCols();
						sheet.addMergedRegion(new CellRangeAddress(0, 0, startIndex, endIndex));
						startIndex += excels.get(x).getCols();
					}
					XSSFCell cell = row.createCell(startIndex-excels.get(x).getCols());
					//设置内容
					cell.setCellValue(excels.get(x).getHeadTextName());
					if(excels.get(x).getCellStyle() != null){
						//设置格式
						cell.setCellStyle(excels.get(x).getCellStyle());
					}
					cell.setCellStyle(fontStyle);
				}else{
					XSSFCell cell = row.createCell(x);
					//设置内容
					cell.setCellValue(excels.get(x).getHeadTextName());
					if(excels.get(x).getCellStyle() != null){
						//设置格式
						cell.setCellStyle(excels.get(x).getCellStyle());
					}
					cell.setCellStyle(fontStyle);
				}
			}
		}
	}
	/**
	 * 为excel表中循环添加数据
	 * @param sheet
	 * @param map  字段名
	 * @param objs	查询的数据
	 * @param clazz 无用
	 */
	private static void createTableRows(
			XSSFSheet sheet, 
			Map<Integer,List<ExcelBean>> map, 
			List<Map<String,Object>> objs, 
			Class<?> clazz) 
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int rowindex = map.size();
		int maxkey = 0;
		List<ExcelBean> ems = new ArrayList<ExcelBean>();
		for(Map.Entry<Integer,List<ExcelBean>> entry : map.entrySet()){
			if(entry.getKey() > maxkey){
				maxkey = entry.getKey();
			}
		}
		ems = map.get(maxkey);
		List<Integer> widths = new ArrayList<Integer>(ems.size());
		for(Map<String,Object> obj : objs){
			XSSFRow row = sheet.createRow(rowindex);
			for(int i=0;i<ems.size();i++){
				  ExcelBean em = (ExcelBean)ems.get(i);
				  String propertyName = em.getPropertyName();
				  Object value = obj.get(propertyName);
				  XSSFCell cell = row.createCell(i);
				  String cellValue = "";
				  if("valid".equals(propertyName)){
					  cellValue = value.equals(1)?"启用":"禁用";
				  }else if(value==null){
					  cellValue = "";
				  }else if(value instanceof Date){
					  cellValue = new SimpleDateFormat("yyyy-MM-dd").format(value);
				  }else{
					  cellValue = value.toString();
				  }
				  cell.setCellValue(cellValue);
				  cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				  cell.setCellStyle(fontStyle2);
				  sheet.autoSizeColumn(i);
			}
			rowindex++;
		}
		
		//设置列宽
		for(int index=0;index<widths.size();index++){
			Integer width = widths.get(index);
			width = width<2500?2500:width+300;
			width = width>10000?10000+300:width+300;
			sheet.setColumnWidth(index, width);
		}
	}


  
}  
