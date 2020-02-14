package com.example.epidemicsituation.entity;

/**
 * @description: Post请求 注册实体类
 * @author: ODM
 * @date: 2020/2/14
 */
public class RegisterUserPost {

    /**
     * user : {"id":"asd","password":"asd"}
     */

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * id : asd
         * password : asd
         */
        private String id;
        private String password;


        public UserBean(String id , String password){
            this.id = id;
            this.password = password;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
