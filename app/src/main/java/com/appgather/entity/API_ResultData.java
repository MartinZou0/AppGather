package com.appgather.entity;

/**
 *
 * 服务器返回来的结果集
 */

public class API_ResultData {
        private String status;
        private String info;
        public void setStatus(String status) {
            this.status = status;
        }
        public String getStatus() {
            return status;
        }

        public void setInfo(String info) {
            this.info = info;
        }
        public String getInfo() {
            return info;
        }
}
