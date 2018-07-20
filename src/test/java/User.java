/**
 * Created by Administrator on 2018/7/20 0020.
 */
public class User {
    private String name;
    private int age;
    private String sex;
    private String job;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "{name:"+name+", age:"+age+", sex:"+sex+", job:"+job+"}";
    }
}
