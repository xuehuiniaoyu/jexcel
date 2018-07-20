import org.xhny.jexcel.pj.Excel2JavaBean;
import org.xhny.jexcel.pj.JavaBean2Excel;
import org.xhny.jexcel.pj.Mapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/7/20 0020.
 */
public class TestMain {
    public static void main(String[] args) throws FileNotFoundException {
        write();
        read();
    }




    /* 写到Excel文件中 */ static void write() {
        JavaBean2Excel javaBean2Excel = new JavaBean2Excel();
        javaBean2Excel.setCursor(1); // 0为追加 >0为覆盖
        javaBean2Excel.setPage(0);  // Excel的第一页

        // 映射关系
        javaBean2Excel
            .mapping("name", "姓名")
            .mapping("age", "年龄")
            .mapping("job", "工作")
            .mapping("sex", "性别")
        ;

        // 选择哪几个字段被写入
        javaBean2Excel.setNames("name", "job", "age", "sex");

        User z3 = new User();
        z3.setName("张三");
        z3.setAge(30);
        z3.setSex("男");
        z3.setJob("工程师");

        User l4 = new User();
        l4.setName("李四");
        l4.setAge(28);
        l4.setSex("男");
        l4.setJob("医生");

        javaBean2Excel.write(Arrays.asList(z3, l4), new File("d:/writeExcel.xls"));
    }




    /* 从Excel读取到内存中 */ static void read() throws FileNotFoundException {
        Excel2JavaBean excel2JavaBean = new Excel2JavaBean();
        excel2JavaBean.setCursor(0); // 从0开始读取
        excel2JavaBean.setCount(20); // 去读最多20条
        excel2JavaBean.setPage(0); // Excel的第一页
        excel2JavaBean
            .mapping(new Mapping("姓名", "name"))
            .mapping(new Mapping("年龄", "age"))
            .mapping(new Mapping("工作", "job"))
            .mapping(new Mapping("性别", "sex"))
        ;
        List<User> list = excel2JavaBean.read(new FileInputStream("d:/writeExcel.xls"), User.class);
        System.out.println(list);
    }
}
