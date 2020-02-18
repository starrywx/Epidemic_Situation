package com.example.epidemicsituation.entity;

/**
 * @description: Post请求 登录实体类
 * @author: ODM
 * @date: 2020/2/14
 */
public class LoginUserPost {


    /**
     * username : asd
     * password : asd
     */

    private String username;
    private String password;

    /**
     * 构造方法
     * @param useName
     * @param password
     */
    public LoginUserPost(String useName ,String password) {
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
