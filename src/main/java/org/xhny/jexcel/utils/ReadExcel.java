package org.xhny.jexcel.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {
    public List readExcel(File file, int sheet, int start, int count) {
        try {
            InputStream is = new FileInputStream(file.getAbsolutePath());
            return readExcel(is, sheet, start, count);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
    public List readExcel(InputStream is, int sheetIndex, int start, int count) {
        Workbook wb = null;
        try {
            // jxl提供的Workbook类
            wb = Workbook.getWorkbook(is);
            // Excel的页签数量
            List<List> outerList = new ArrayList<List>();
            // 每个页签创建一个Sheet对象
            Sheet sheet = wb.getSheet(sheetIndex);
            // sheet.getRows()返回该页的总行数

            // 第一行
            List innerList0 = new ArrayList();
            for (int j = 0; j < sheet.getColumns(); j++) {
                String cellinfo = sheet.getCell(j, 0).getContents();
                innerList0.add(cellinfo);
            }
            outerList.add(innerList0);
            int begin = start + 1;
            int end = count == 0 ? sheet.getRows() : Math.min(begin + count, sheet.getRows());
            for (int i = begin; i < end; i++) {
                List innerList = new ArrayList();
                for (int j = 0; j < sheet.getColumns(); j++) {
                    String cellinfo = sheet.getCell(j, i).getContents();
                    innerList.add(cellinfo);
                }
                outerList.add(innerList);
            }
            return outerList;
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (wb != null) {
                wb.close();
            }
        }
        return null;
    }
}
