package com.example.epidemicsituation.bean;

import java.util.List;

public class HistoryInfo {

    /**
     * message : success
     * code : 1
     * data : [{"id":11,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":12,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":13,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":14,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":15,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":16,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":17,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":18,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":19,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":20,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":21,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":22,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":23,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":24,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":25,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":26,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":27,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":28,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":29,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":30,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":31,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":32,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":33,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":34,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":35,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":36,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":37,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":38,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":39,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":40,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":41,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":42,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":43,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":44,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":45,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":46,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null},{"id":47,"time":"2020-02-09 10:22","lon":null,"lat":null,"user_id":null}]
     */

    private String message;
    private int code;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 11
         * time : 2020-02-09 10:22
         * lon : null
         * lat : null
         * user_id : null
         */

        private int id;
        private String time;
        private double lon;
        private double lat;
        private int user_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
