# jexcel
java-bean -> excel-table | excel-table -> java-bean

## Gradle

```
1.Add it in your root build.gradle at the end of repositories （/build.gradle）:

allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

2.Add the dependency（添加依赖到app下的build.gradle）

dependencies {
	...
	implementation 'com.github.xuehuiniaoyu:jexcel:v1.1'
}

```

## Excel -> Java

Excel表中的内容：

```
job	      name	     age	sex
teacher   zhangsan   30   男
```

User对象：
public static class User {
    private String name;
    private Integer age;
    private String sex;
    private String job;
    
    ... get set
}

Excel表中的第一行内容和java对象中的属性一一对应

把Excel内容加载到对象列表，代码如下：
InputStream is = getAssets().open("writeExcel.xls");
List<User> userList = new Excel2JavaBean().setPage(1).setCursor(0).setCount(30).read(is, User.class);


## Java -> Excel

JavaBean2Excel j2e = new JavaBean2Excel("job", "name" , "age", "sex").setCursor(3);
User user = new User();
user.setName("333");
user.setAge(29);
user.setSex("男1");
user.setJob("胜多负少");
j2e.write(Arrays.asList(new User[]{user}), new File("D:/writeExcel.xls"));
