package org.xhny.jexcel.pj;


import org.xhny.jexcel.utils.ReadExcel;
import org.xhny.jexcel.utils.Reflect;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel文件内容转换为Java对象列表
 * 第一行内容必须对应java bean的字段名，顺序可以随便放置
 */
public class Excel2JavaBean extends Table {
    /**
     * 游标位置 0为向后追加 >0为从cursor位置覆盖
     **/
    private int cursor;

    /**
     * 希望从Excel中读取多少条数据？ 0为全部 >0为指定条数
     **/
    private int count;

    /**
     * 页签
     **/
    private int page;

    /**
     * 默认构造器
     */
    public Excel2JavaBean() {
    }

    /**
     * 构造器
     *
     * @param page
     */
    public Excel2JavaBean(int page) {
        this.page = page;
    }

    /**
     * 设置游标
     *
     * @param cursor
     * @return
     */
    public Excel2JavaBean setCursor(int cursor) {
        this.cursor = cursor;
        return this;
    }

    /**
     * 设置追加行数或覆盖行数
     *
     * @param count
     * @return
     */
    public Excel2JavaBean setCount(int count) {
        this.count = count;
        return this;
    }

    /**
     * 指定页签
     * @param page
     * @return
     */
    public Excel2JavaBean setPage(int page) {
        this.page = page;
        return this;
    }

    /**
     * 读取Excel内容并转换为对象集合
     *
     * @param <T>
     * @param stream Excel文件的输入流
     * @param tClass 转换对象的类型
     * @return
     */
    public <T> List<T> read(InputStream stream, Class<T> tClass) {
        Reflect reflect = new Reflect();
        ReadExcel readExcel = new ReadExcel();
        List excelList = readExcel.readExcel(stream, page, cursor, count);
        if (excelList != null) {
            List<T> all = new ArrayList<>();
            // 获取位置
            List<String> names = (List<String>) excelList.get(0);
            for (int i = 1; i < excelList.size(); i++) {
                T bean = reflect.clear().on(tClass).constructor().newInstance();
                List values = (List) excelList.get(i);
                for (int j = 0; j < values.size(); j++) {
                    String name = tryToGetMappingValue(names.get(j));
                    Object value = values.get(j);
                    reflect.clear().on(bean);
                    reflect.set(name, value);
                }
                all.add(bean);
            }
            return all;
        }
        return null;
    }
}
