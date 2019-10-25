package com.konstant.tool.lite.network.response;

import androidx.annotation.Keep;

import java.util.List;

/**
* 时间：2019/4/25 15:21
* 创建：吕卡
* 描述：菜鸟裹裹物流查询的返回体
*/

public class ExpressResponse {


    /**
     * status : 1
     * msg :
     * data : {"nu":"669865132113","company":"天天快递","code":"tiantian","tel":"400-188-8888","img":"//ims-cdn0.sm.cn/ims?kt=url&at=sc&key=aHR0cHM6Ly9ndG1zMDQuYWxpY2RuLmNvbS90cHMvaTQvVEIxZGFIaUtWWFhYWGE5WFZYWHF4RkpWRlhYLTEwMC0xMDAucG5n&sign=yx:vh7Ne4eV7zHHx6kanNhWPre_KKk=&tv=150_150&x.jpg","url":"http://www.ttkdex.com","status":"已签收","messages":[{"context":"您的快件已签收，签收人是011059.0019本人，签收网点是【SN北京朝阳八里庄站010-57306488】扫描员是张海杰","time":"2019-04-25 11:08:13"},{"context":"【北京市】SN北京朝阳八里庄站派件员 张海杰 18515102788正在为您派件","time":"2019-04-25 08:38:08"},{"context":"快件到达【SN北京朝阳八里庄站010-57306488】，上一站是【北京朝阳集散】扫描员是赵张松","time":"2019-04-25 08:25:31"},{"context":"【北京市】快件已从北京朝阳集散发出，准备发往SN北京朝阳东郊","time":"2019-04-25 03:42:47"},{"context":"【北京市】快件已从SN北京朝阳东郊发出，准备发往北京朝阳集散","time":"2019-04-24 17:57:20"},{"context":"快件到达【SN北京朝阳东郊010-57306488】，上一站是【北京朝阳集散】扫描员是闪永川","time":"2019-04-24 12:57:38"},{"context":"【北京市】快件已从北京朝阳集散发出，准备发往SN北京朝阳东郊","time":"2019-04-24 12:21:42"},{"context":"快件到达【北京朝阳集散】，上一站是【华北廊坊枢纽】扫描员是蒋宝强白班","time":"2019-04-24 11:08:54"},{"context":"【廊坊市】快件已从华北廊坊枢纽发出，准备发往北京朝阳集散","time":"2019-04-24 08:55:02"},{"context":"快件到达【华北廊坊枢纽】，上一站是【华南东莞枢纽】扫描员是外围白班贺文静","time":"2019-04-24 08:52:14"},{"context":"【东莞市】快件已从华南东莞枢纽发出，准备发往华北廊坊枢纽","time":"2019-04-22 05:28:28"},{"context":"【华南东莞枢纽】已进行装袋扫描，扫描员是省外小件李吉凤","time":"2019-04-22 05:20:46"},{"context":"快件到达【华南东莞枢纽】，上一站是【SN深圳市场部】扫描员是刘春桥","time":"2019-04-22 04:45:37"},{"context":"【SN深圳项目十部020-32279767】的扫描员操作部18148982990已收件","time":"2019-04-21 19:04:02"},{"context":"包裹正在等待揽收","time":"2019-04-21 15:36:56"},{"context":"商品已经下单","time":"2019-04-21 13:39:25"}],"hasItem":"true","source_name":"菜鸟裹裹","source_url":"https://m.guoguo-app.com/?source=10019857&from=sm"}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    
    public static class DataBean {
        /**
         * nu : 669865132113
         * company : 天天快递
         * code : tiantian
         * tel : 400-188-8888
         * img : //ims-cdn0.sm.cn/ims?kt=url&at=sc&key=aHR0cHM6Ly9ndG1zMDQuYWxpY2RuLmNvbS90cHMvaTQvVEIxZGFIaUtWWFhYWGE5WFZYWHF4RkpWRlhYLTEwMC0xMDAucG5n&sign=yx:vh7Ne4eV7zHHx6kanNhWPre_KKk=&tv=150_150&x.jpg
         * url : http://www.ttkdex.com
         * status : 已签收
         * messages : [{"context":"您的快件已签收，签收人是011059.0019本人，签收网点是【SN北京朝阳八里庄站010-57306488】扫描员是张海杰","time":"2019-04-25 11:08:13"},{"context":"【北京市】SN北京朝阳八里庄站派件员 张海杰 18515102788正在为您派件","time":"2019-04-25 08:38:08"},{"context":"快件到达【SN北京朝阳八里庄站010-57306488】，上一站是【北京朝阳集散】扫描员是赵张松","time":"2019-04-25 08:25:31"},{"context":"【北京市】快件已从北京朝阳集散发出，准备发往SN北京朝阳东郊","time":"2019-04-25 03:42:47"},{"context":"【北京市】快件已从SN北京朝阳东郊发出，准备发往北京朝阳集散","time":"2019-04-24 17:57:20"},{"context":"快件到达【SN北京朝阳东郊010-57306488】，上一站是【北京朝阳集散】扫描员是闪永川","time":"2019-04-24 12:57:38"},{"context":"【北京市】快件已从北京朝阳集散发出，准备发往SN北京朝阳东郊","time":"2019-04-24 12:21:42"},{"context":"快件到达【北京朝阳集散】，上一站是【华北廊坊枢纽】扫描员是蒋宝强白班","time":"2019-04-24 11:08:54"},{"context":"【廊坊市】快件已从华北廊坊枢纽发出，准备发往北京朝阳集散","time":"2019-04-24 08:55:02"},{"context":"快件到达【华北廊坊枢纽】，上一站是【华南东莞枢纽】扫描员是外围白班贺文静","time":"2019-04-24 08:52:14"},{"context":"【东莞市】快件已从华南东莞枢纽发出，准备发往华北廊坊枢纽","time":"2019-04-22 05:28:28"},{"context":"【华南东莞枢纽】已进行装袋扫描，扫描员是省外小件李吉凤","time":"2019-04-22 05:20:46"},{"context":"快件到达【华南东莞枢纽】，上一站是【SN深圳市场部】扫描员是刘春桥","time":"2019-04-22 04:45:37"},{"context":"【SN深圳项目十部020-32279767】的扫描员操作部18148982990已收件","time":"2019-04-21 19:04:02"},{"context":"包裹正在等待揽收","time":"2019-04-21 15:36:56"},{"context":"商品已经下单","time":"2019-04-21 13:39:25"}]
         * hasItem : true
         * source_name : 菜鸟裹裹
         * source_url : https://m.guoguo-app.com/?source=10019857&from=sm
         */

        private String nu;
        private String company;
        private String code;
        private String tel;
        private String img;
        private String url;
        private String status;
        private String hasItem;
        private String source_name;
        private String source_url;
        private List<MessagesBean> messages;

        public String getNu() {
            return nu;
        }

        public void setNu(String nu) {
            this.nu = nu;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getHasItem() {
            return hasItem;
        }

        public void setHasItem(String hasItem) {
            this.hasItem = hasItem;
        }

        public String getSource_name() {
            return source_name;
        }

        public void setSource_name(String source_name) {
            this.source_name = source_name;
        }

        public String getSource_url() {
            return source_url;
        }

        public void setSource_url(String source_url) {
            this.source_url = source_url;
        }

        public List<MessagesBean> getMessages() {
            return messages;
        }

        public void setMessages(List<MessagesBean> messages) {
            this.messages = messages;
        }

        
        public static class MessagesBean {
            /**
             * context : 您的快件已签收，签收人是011059.0019本人，签收网点是【SN北京朝阳八里庄站010-57306488】扫描员是张海杰
             * time : 2019-04-25 11:08:13
             */

            private String context;
            private String time;

            public String getContext() {
                return context;
            }

            public void setContext(String context) {
                this.context = context;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
