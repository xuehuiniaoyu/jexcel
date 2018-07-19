package org.xhny.jexcel.pj;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * java bean 集合存储到Excel文件中
 */
public class JavaBean2Excel {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    /**
     * 字段映射关系表
     */
    private final HashMap<String, Integer> mapping = new HashMap<>();

    /** Excel表中的字段名称 **/
    private String[] names;
    /** 游标位置 **/
    private int cursor;
    /** 页签 **/
    private int page;

    public JavaBean2Excel(int page) {
        this.page = page;
    }

    /**
     * 构造器
     * @param names
     */
    public JavaBean2Excel(String ... names) {
        this.names = names;
    }

    /**
     * 构造器
     * @param page
     * @param names 那些字段会被写入由此指定
     */
    public JavaBean2Excel(int page, String ... names) {
        this.page = page;
        this.names = names;
    }

    /**
     * 从哪一行开始写入数据
     * 0 追加 >0 按指定行写入
     * @param cursor
     * @return
     */
    public JavaBean2Excel setCursor(int cursor) {
        this.cursor = cursor;
        return this;
    }

    /**
     * 设置Excel第几页
     * @param page
     * @return
     */
    public JavaBean2Excel setPage(int page) {
        this.page = page;
        return this;
    }

    /**
     * 写入到Excel文件
     * @param data java bean集合
     * @param xls Excel文件
     */
    public void write(List<?> data, File xls) {
        OutputStream out = null;
        Workbook workBook = null;
        try {
            workBook = getWorkbok(xls);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(page);
            Row firstRow = sheet.getRow(0);
            if(firstRow == null) {
                firstRow = sheet.createRow(0);
                Object javaBeanHeadInfo = data.get(0);
                if(names.length == 0) {
                    Field[] fields = javaBeanHeadInfo.getClass().getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        Cell cell = firstRow.createCell(i);
                        Field field = fields[i];
                        cell.setCellValue(field.getName());
                        mapping.put(field.getName(), i);
                    }
                } else {
                    for (int i = 0; i < names.length; i++) {
                        Cell cell = firstRow.createCell(i);
                        Field field = javaBeanHeadInfo.getClass().getDeclaredField(names[i]);
                        cell.setCellValue(field.getName());
                        mapping.put(field.getName(), i);
                    }
                }
            }
            else {
                int i = 0;
                while (true) {
                    Cell cell = firstRow.getCell(i);
                    if (cell == null) {
                        break;
                    }
                    if(names.length > 0) {
                        for (int j = 0; j < names.length; j++) {
                            if (cell.getStringCellValue().equals(names[j])) {
                                mapping.put(cell.getStringCellValue(), i);
                            }
                        }
                    }
                    else {
                        mapping.put(cell.getStringCellValue(), i);
                    }
                    i++;
                }
            }
            int begin = cursor == 0 ? sheet.getLastRowNum() + 1 : cursor;    // 第一行从0开始算
            int end = begin + data.size();
            int j = 0;
            for (int i = begin; i <end; i++) {
                Row row = sheet.getRow(i);
                if(row == null) {
                    row = sheet.createRow(i);
                }
                Object javaBean = data.get(j++);
                for(String key : mapping.keySet()) {
                    try {
                        Field field = javaBean.getClass().getDeclaredField(key);
                        int index = mapping.get(key);
                        Cell cell = row.createCell(index);
                        field.setAccessible(true);
                        cell.setCellValue(""+field.get(javaBean));
                    } catch (NoSuchFieldException e) {
                        //e.printStackTrace();
                    }
                }
            }
            out = new FileOutputStream(xls);
            workBook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(workBook != null) {
                try {
                    workBook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断Excel的版本,获取Workbook
     *
     * @param file
     * @return
     * @throws IOException
     */
    private Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) {     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }
}
