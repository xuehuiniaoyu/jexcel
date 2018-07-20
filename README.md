# jexcel
## java-bean -> excel-table | excel-table -> java-bean
jexcel是一个Java库,可用于将Java对象内容写入到Excel表格中。同时它也可以被用来将一个Excel表格内容换成一个等效的Java对象集合。

Jexcel is a Java library that can be used to the Java object content written to Excel spreadsheet.At the same time it also can be used to replace an Excel spreadsheet content with an equivalent collection of Java objects.

## Gradle

```

allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}


dependencies {
	implementation 'com.github.xuehuiniaoyu:jexcel:v1.2'
}

```

## Excel -> Java

Excel表中的内容：

```
job	  name	     age  sex
teacher   zhangsan   30   男
```

User对象：
```
public static class User {
    private String name;
    private Integer age;
    private String sex;
    private String job;
    
    ... get set
}
```

Excel表中的第一行内容和java对象中的属性一一对应

把Excel内容加载到对象列表，代码如下：
```
InputStream is = getAssets().open("writeExcel.xls");
List<User> userList = new Excel2JavaBean().setPage(1).setCursor(0).setCount(30).read(is, User.class);
```


## Java -> Excel

```
JavaBean2Excel j2e = new JavaBean2Excel();
//JavaBean2Excel j2e = new JavaBean2Excel("job", "name" , "age", "sex").setCursor(3);
User user = new User();
user.setName("333");
user.setAge(29);
user.setSex("男1");
user.setJob("胜多负少");
j2e.write(Arrays.asList(new User[]{user}), new File("D:/writeExcel.xls"));
```
