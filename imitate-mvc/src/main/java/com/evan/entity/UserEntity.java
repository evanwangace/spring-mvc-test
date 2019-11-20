package com.evan.entity;

/**
 * @ClassName UserEntity
 * @Description
 * @Author EvanWang
 * @Version 1.0.0
 * @Date 2019/11/14 23:39
 */
public class UserEntity {

    private String name;

    private String age;

    private String sex;


    @Override
    public String toString() {
        return "UserEntity{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
