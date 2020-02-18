package com.example.epidemicsituation.entity;

/**
 * @description: 用户登录回调实体类
 * @author: ODM
 * @date: 2020/2/18
 */
public class LoginUserCallback {


    /**
     * message : success
     * code : 1
     * data : {"id":704,"username":"13168515169","password":"","openid":null,"sick":false}
     */

    private String message;
    private String code;
    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 704
         * username : 13168515169
         * password :
         * openid : null
         * sick : false
         */

        private int id;
        private String username;
        private String password;
        private Object openid;
        private boolean sick;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public Object getOpenid() {
            return openid;
        }

        public void setOpenid(Object openid) {
            this.openid = openid;
        }

        public boolean isSick() {
            return sick;
        }

        public void setSick(boolean sick) {
            this.sick = sick;
        }
    }
}
