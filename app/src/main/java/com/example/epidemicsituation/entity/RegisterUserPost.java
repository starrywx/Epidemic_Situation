package com.example.epidemicsituation.entity;

/**
 * @description: Post请求 注册实体类
 * @author: ODM
 * @date: 2020/2/14
 */
public class RegisterUserPost {

    /**
     * username : asd
     * password : asd
     */

    //限制 11位手机号码
    private String username;
    //限制6-9位数字与字母搭配
    private String password;

    /**
     * 构造方法
     * @param useName
     * @param password
     */
    public RegisterUserPost(String useName ,String password) {
        this.username = useName;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
