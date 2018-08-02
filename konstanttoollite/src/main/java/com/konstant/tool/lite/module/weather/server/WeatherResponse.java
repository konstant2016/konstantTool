package com.konstant.tool.lite.module.weather;

import java.util.List;

/**
 * 描述:360天气的返回体
 * 创建人:菜籽
 * 创建时间:2018/4/6 下午3:12
 * 备注:
 */

public class WeatherResponse {


    /**
     * history : {}
     */

    private HistoryWeatherBean historyWeather;
    /**
     * date : 2018-04-06
     * info : {"kongtiao":["较少开启","您将感到很舒适，一般不需要开启空调。"],"daisan":["不带伞","天气较好，不会降水，因此您可放心出门，无须带雨伞。"],"ziwaixian":["弱","紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"],"yundong":["较适宜","天气较好，但考虑风力较强且气温较低，推荐您进行室内运动，若在户外运动注意防风并适当增减衣物。"],"ganmao":["易发","天冷风大，易发生感冒，请注意适当增加衣服，加强自我防护避免感冒。"],"xiche":["较不宜","较不宜洗车，未来一天无雨，风力较大，如果执意擦洗汽车，要做好蒙上污垢的心理准备。"],"diaoyu":["较适宜","较适合垂钓，但天气稍凉，会对垂钓产生一定的影响。"],"guomin":["极易发","天气条件极易诱发过敏，风力较大，易过敏人群尽量减少外出，外出穿长衣长裤并佩戴好眼镜和口罩，外出归来时及时清洁手和口鼻，注意防风。"],"xianxing":["不限行","五环路（含）以内道路。本市号牌尾号限行，外地号牌工作日07:00-09:00、17:00-20:00全部限行，其他限行时间内尾号限行。法定上班的周六周日不限行。"],"wuran":["良","气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"],"chuanyi":["较冷","建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"]}
     */

    private LifeBean life;
    /**
     * mslp :
     * wind : {"windspeed":"11.7","direct":"西北风","power":"6级"}
     * time : 14:50:00
     * pressure : 1020
     * weather : {"humidity":"23","img":"2","info":"阴","temperature":"8"}
     * feelslike_c : 8
     * dataUptime : 1522997400
     * date : 2018-04-06
     */

    private RealtimeBean realtime;
    /**
     * so2 : 2
     * o3 : 85
     * co : 0.2
     * level : 1
     * color : #00e400
     * no2 : 9
     * aqi : 28
     * quality : 优
     * pm10 : 27
     * pm25 : 9
     * advice : 今天的空气质量令人满意，各类人群可正常活动。
     * chief : PM10
     * upDateTime : 1522994400000
     */

    private Pm25Bean pm25;
    /**
     * historyWeather : {"history":{}}
     * area : [["北京","10101"],["北京","1010101"],["通州","101010600"]]
     * life : {"date":"2018-04-06","info":{"kongtiao":["较少开启","您将感到很舒适，一般不需要开启空调。"],"daisan":["不带伞","天气较好，不会降水，因此您可放心出门，无须带雨伞。"],"ziwaixian":["弱","紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"],"yundong":["较适宜","天气较好，但考虑风力较强且气温较低，推荐您进行室内运动，若在户外运动注意防风并适当增减衣物。"],"ganmao":["易发","天冷风大，易发生感冒，请注意适当增加衣服，加强自我防护避免感冒。"],"xiche":["较不宜","较不宜洗车，未来一天无雨，风力较大，如果执意擦洗汽车，要做好蒙上污垢的心理准备。"],"diaoyu":["较适宜","较适合垂钓，但天气稍凉，会对垂钓产生一定的影响。"],"guomin":["极易发","天气条件极易诱发过敏，风力较大，易过敏人群尽量减少外出，外出穿长衣长裤并佩戴好眼镜和口罩，外出归来时及时清洁手和口鼻，注意防风。"],"xianxing":["不限行","五环路（含）以内道路。本市号牌尾号限行，外地号牌工作日07:00-09:00、17:00-20:00全部限行，其他限行时间内尾号限行。法定上班的周六周日不限行。"],"wuran":["良","气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"],"chuanyi":["较冷","建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"]}}
     * realtime : {"mslp":"","wind":{"windspeed":"11.7","direct":"西北风","power":"6级"},"time":"14:50:00","pressure":"1020","weather":{"humidity":"23","img":"2","info":"阴","temperature":"8"},"feelslike_c":"8","dataUptime":"1522997400","date":"2018-04-06"}
     * alert : [{"content":"通州区气象台05日17时00分发布大风蓝色预警,受冷空气影响，预计5日夜间至6日，通州区北风风力逐渐加大至4、5级，阵风可达7级左右，请注意防范。","pubTime":"2018-04-05 17:00","originUrl":"http://mobile.weathercn.com/alert.do?id=11011241600000_20180405170000","alarmTp2":"蓝色","alarmTp1":"大风","type":1,"alarmPic2":"01","alarmPic1":"05"},{"content":"北京市气象台05日17时00分发布大风蓝色预警,受冷空气影响，预计5日夜间至6日，本市大部分地区北风风力逐渐加大至4、5级，阵风可达7级左右，请注意防范。","pubTime":"2018-04-05 17:00","originUrl":"http://mobile.weathercn.com/alert.do?id=11000041600000_20180405170000","alarmTp2":"蓝色","alarmTp1":"大风","type":1,"alarmPic2":"01","alarmPic1":"05"}]
     * trafficalert : []
     * weather : [{"aqi":"106","date":"2018-04-05","info":{"night":["1","多云","2","西北风","3-5级","18:41"],"day":["2","阴","8","西南风","微风","05:54"]}},{"aqi":"49","date":"2018-04-06","info":{"night":["0","晴","4","西北风","3-5级","18:42"],"day":["1","多云","10","北风","4-5级","05:52"]}},{"aqi":"43","date":"2018-04-07","info":{"night":["1","多云","3","南风","微风","18:43"],"day":["1","多云","12","西北风","3-5级","05:51"]}},{"aqi":"55","date":"2018-04-08","info":{"night":["0","晴","5","东风","微风","18:44"],"day":["0","晴","17","南风","微风","05:49"]}},{"aqi":"116","date":"2018-04-09","info":{"night":["1","多云","7","西北风","3-5级","18:45"],"day":["0","晴","20","东风","微风","05:48"]}},{"aqi":"123","date":"2018-04-10","info":{"night":["0","晴","8","北风","微风","18:46"],"day":["1","多云","23","西北风","3-5级","05:46"]}},{"aqi":"73","date":"2018-04-11","info":{"night":["1","多云","12","东风","微风","18:47"],"day":["0","晴","23","西南风","微风","05:44"]}},{"aqi":"75","date":"2018-04-12","info":{"night":["1","多云","8","东南风","微风","18:48"],"day":["1","多云","19","西南风","微风","05:43"]}},{"aqi":"","date":"2018-04-13","info":{"night":["1","多云","6","西北风","微风","18:49"],"day":["7","小雨","18","西南风","3-5级","05:41"]}},{"aqi":"","date":"2018-04-14","info":{"night":["1","多云","8","西北风","微风","18:50"],"day":["1","多云","24","西北风","微风","05:40"]}},{"aqi":"","date":"2018-04-15","info":{"night":["0","晴","10","西南风","微风","18:51"],"day":["1","多云","23","东南风","3-5级","05:38"]}},{"aqi":"","date":"2018-04-16","info":{"night":["2","阴","9","东北风","微风","18:52"],"day":["1","多云","21","东北风","微风","05:37"]}},{"aqi":"","date":"2018-04-17","info":{"night":["7","小雨","7","北风","微风","18:53"],"day":["1","多云","19","东风","微风","05:35"]}},{"aqi":"","date":"2018-04-18","info":{"night":["7","小雨","9","北风","微风","18:54"],"day":["1","多云","20","东南风","微风","05:34"]}},{"aqi":"","date":"2018-04-19","info":{"night":["2","阴","7","东南风","微风","18:55"],"day":["7","小雨","19","东风","微风","05:32"]}},{"aqi":"","date":"2018-04-20","info":{"night":["1","多云","8","南风","微风","18:56"],"day":["1","多云","19","东南风","微风","05:31"]}}]
     * pm25 : {"so2":2,"o3":85,"co":"0.2","level":1,"color":"#00e400","no2":9,"aqi":28,"quality":"优","pm10":27,"pm25":9,"advice":"今天的空气质量令人满意，各类人群可正常活动。","chief":"PM10","upDateTime":1522994400000}
     * hourly_forecast : [{"img":"01","wind_speed":"8","hour":"15","wind_direct":"北风","temperature":"11","info":"多云"},{"img":"01","wind_speed":"7","hour":"16","wind_direct":"北风","temperature":"11","info":"多云"},{"img":"01","wind_speed":"7","hour":"17","wind_direct":"北风","temperature":"10","info":"多云"},{"img":"01","wind_speed":"6","hour":"18","wind_direct":"西北风","temperature":"10","info":"多云"},{"img":"01","wind_speed":"4","hour":"19","wind_direct":"西北风","temperature":"9","info":"多云"},{"img":"00","wind_speed":"4","hour":"20","wind_direct":"西北风","temperature":"8","info":"晴"},{"img":"00","wind_speed":"4","hour":"21","wind_direct":"西北风","temperature":"7","info":"晴"},{"img":"00","wind_speed":"5","hour":"22","wind_direct":"西北风","temperature":"7","info":"晴"},{"img":"00","wind_speed":"5","hour":"23","wind_direct":"西北风","temperature":"6","info":"晴"},{"img":"00","wind_speed":"5","hour":"0","wind_direct":"西北风","temperature":"6","info":"晴"},{"img":"00","wind_speed":"6","hour":"1","wind_direct":"西北风","temperature":"6","info":"晴"},{"img":"00","wind_speed":"6","hour":"2","wind_direct":"西北风","temperature":"6","info":"晴"},{"img":"00","wind_speed":"6","hour":"3","wind_direct":"西北风","temperature":"5","info":"晴"},{"img":"00","wind_speed":"6","hour":"4","wind_direct":"西北风","temperature":"4","info":"晴"},{"img":"00","wind_speed":"6","hour":"5","wind_direct":"西北风","temperature":"5","info":"晴"},{"img":"00","wind_speed":"6","hour":"6","wind_direct":"西北风","temperature":"4","info":"晴"},{"img":"00","wind_speed":"7","hour":"7","wind_direct":"西北风","temperature":"5","info":"晴"},{"img":"00","wind_speed":"7","hour":"8","wind_direct":"西北风","temperature":"6","info":"晴"},{"img":"00","wind_speed":"7","hour":"9","wind_direct":"西北风","temperature":"7","info":"晴"},{"img":"00","wind_speed":"7","hour":"10","wind_direct":"西北风","temperature":"9","info":"晴"},{"img":"00","wind_speed":"6","hour":"11","wind_direct":"西北风","temperature":"9","info":"晴"},{"img":"00","wind_speed":"6","hour":"12","wind_direct":"西北风","temperature":"10","info":"晴"},{"img":"01","wind_speed":"6","hour":"13","wind_direct":"西北风","temperature":"11","info":"多云"},{"img":"01","wind_speed":"5","hour":"14","wind_direct":"西北风","temperature":"12","info":"多云"}]
     */

    private List<List<String>> area;
    /**
     * content : 通州区气象台05日17时00分发布大风蓝色预警,受冷空气影响，预计5日夜间至6日，通州区北风风力逐渐加大至4、5级，阵风可达7级左右，请注意防范。
     * pubTime : 2018-04-05 17:00
     * originUrl : http://mobile.weathercn.com/alert.do?id=11011241600000_20180405170000
     * alarmTp2 : 蓝色
     * alarmTp1 : 大风
     * type : 1
     * alarmPic2 : 01
     * alarmPic1 : 05
     */

    private List<AlertBean> alert;
    private List<?> trafficalert;
    /**
     * aqi : 106
     * date : 2018-04-05
     * info : {"night":["1","多云","2","西北风","3-5级","18:41"],"day":["2","阴","8","西南风","微风","05:54"]}
     */

    private List<WeatherBean> weather;
    /**
     * img : 01
     * wind_speed : 8
     * hour : 15
     * wind_direct : 北风
     * temperature : 11
     * info : 多云
     */

    private List<HourlyForecastBean> hourly_forecast;

    public HistoryWeatherBean getHistoryWeather() {
        return historyWeather;
    }

    public void setHistoryWeather(HistoryWeatherBean historyWeather) {
        this.historyWeather = historyWeather;
    }

    public LifeBean getLife() {
        return life;
    }

    public void setLife(LifeBean life) {
        this.life = life;
    }

    public RealtimeBean getRealtime() {
        return realtime;
    }

    public void setRealtime(RealtimeBean realtime) {
        this.realtime = realtime;
    }

    public Pm25Bean getPm25() {
        return pm25;
    }

    public void setPm25(Pm25Bean pm25) {
        this.pm25 = pm25;
    }

    public List<List<String>> getArea() {
        return area;
    }

    public void setArea(List<List<String>> area) {
        this.area = area;
    }

    public List<AlertBean> getAlert() {
        return alert;
    }

    public void setAlert(List<AlertBean> alert) {
        this.alert = alert;
    }

    public List<?> getTrafficalert() {
        return trafficalert;
    }

    public void setTrafficalert(List<?> trafficalert) {
        this.trafficalert = trafficalert;
    }

    public List<WeatherBean> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherBean> weather) {
        this.weather = weather;
    }

    public List<HourlyForecastBean> getHourly_forecast() {
        return hourly_forecast;
    }

    public void setHourly_forecast(List<HourlyForecastBean> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    public static class HistoryWeatherBean {
        private HistoryBean history;

        public HistoryBean getHistory() {
            return history;
        }

        public void setHistory(HistoryBean history) {
            this.history = history;
        }

        public static class HistoryBean {
        }
    }

    public static class LifeBean {
        private String date;
        private InfoBean info;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            private List<String> kongtiao;
            private List<String> daisan;
            private List<String> ziwaixian;
            private List<String> yundong;
            private List<String> ganmao;
            private List<String> xiche;
            private List<String> diaoyu;
            private List<String> guomin;
            private List<String> xianxing;
            private List<String> wuran;
            private List<String> chuanyi;

            public List<String> getKongtiao() {
                return kongtiao;
            }

            public void setKongtiao(List<String> kongtiao) {
                this.kongtiao = kongtiao;
            }

            public List<String> getDaisan() {
                return daisan;
            }

            public void setDaisan(List<String> daisan) {
                this.daisan = daisan;
            }

            public List<String> getZiwaixian() {
                return ziwaixian;
            }

            public void setZiwaixian(List<String> ziwaixian) {
                this.ziwaixian = ziwaixian;
            }

            public List<String> getYundong() {
                return yundong;
            }

            public void setYundong(List<String> yundong) {
                this.yundong = yundong;
            }

            public List<String> getGanmao() {
                return ganmao;
            }

            public void setGanmao(List<String> ganmao) {
                this.ganmao = ganmao;
            }

            public List<String> getXiche() {
                return xiche;
            }

            public void setXiche(List<String> xiche) {
                this.xiche = xiche;
            }

            public List<String> getDiaoyu() {
                return diaoyu;
            }

            public void setDiaoyu(List<String> diaoyu) {
                this.diaoyu = diaoyu;
            }

            public List<String> getGuomin() {
                return guomin;
            }

            public void setGuomin(List<String> guomin) {
                this.guomin = guomin;
            }

            public List<String> getXianxing() {
                return xianxing;
            }

            public void setXianxing(List<String> xianxing) {
                this.xianxing = xianxing;
            }

            public List<String> getWuran() {
                return wuran;
            }

            public void setWuran(List<String> wuran) {
                this.wuran = wuran;
            }

            public List<String> getChuanyi() {
                return chuanyi;
            }

            public void setChuanyi(List<String> chuanyi) {
                this.chuanyi = chuanyi;
            }
        }
    }

    public static class RealtimeBean {
        private String mslp;
        /**
         * windspeed : 11.7
         * direct : 西北风
         * power : 6级
         */

        private WindBean wind;
        private String time;
        private String pressure;
        /**
         * humidity : 23
         * img : 2
         * info : 阴
         * temperature : 8
         */

        private WeatherBean weather;
        private String feelslike_c;
        private String dataUptime;
        private String date;

        public String getMslp() {
            return mslp;
        }

        public void setMslp(String mslp) {
            this.mslp = mslp;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public WeatherBean getWeather() {
            return weather;
        }

        public void setWeather(WeatherBean weather) {
            this.weather = weather;
        }

        public String getFeelslike_c() {
            return feelslike_c;
        }

        public void setFeelslike_c(String feelslike_c) {
            this.feelslike_c = feelslike_c;
        }

        public String getDataUptime() {
            return dataUptime;
        }

        public void setDataUptime(String dataUptime) {
            this.dataUptime = dataUptime;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public static class WindBean {
            private String windspeed;
            private String direct;
            private String power;

            public String getWindspeed() {
                return windspeed;
            }

            public void setWindspeed(String windspeed) {
                this.windspeed = windspeed;
            }

            public String getDirect() {
                return direct;
            }

            public void setDirect(String direct) {
                this.direct = direct;
            }

            public String getPower() {
                return power;
            }

            public void setPower(String power) {
                this.power = power;
            }
        }

        public static class WeatherBean {
            private String humidity;
            private String img;
            private String info;
            private String temperature;

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }
        }
    }

    public static class Pm25Bean {
        private int so2;
        private int o3;
        private String co;
        private int level;
        private String color;
        private int no2;
        private int aqi;
        private String quality;
        private int pm10;
        private int pm25;
        private String advice;
        private String chief;
        private long upDateTime;

        public int getSo2() {
            return so2;
        }

        public void setSo2(int so2) {
            this.so2 = so2;
        }

        public int getO3() {
            return o3;
        }

        public void setO3(int o3) {
            this.o3 = o3;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getNo2() {
            return no2;
        }

        public void setNo2(int no2) {
            this.no2 = no2;
        }

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public int getPm10() {
            return pm10;
        }

        public void setPm10(int pm10) {
            this.pm10 = pm10;
        }

        public int getPm25() {
            return pm25;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public String getAdvice() {
            return advice;
        }

        public void setAdvice(String advice) {
            this.advice = advice;
        }

        public String getChief() {
            return chief;
        }

        public void setChief(String chief) {
            this.chief = chief;
        }

        public long getUpDateTime() {
            return upDateTime;
        }

        public void setUpDateTime(long upDateTime) {
            this.upDateTime = upDateTime;
        }
    }

    public static class AlertBean {
        private String content;
        private String pubTime;
        private String originUrl;
        private String alarmTp2;
        private String alarmTp1;
        private int type;
        private String alarmPic2;
        private String alarmPic1;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public String getOriginUrl() {
            return originUrl;
        }

        public void setOriginUrl(String originUrl) {
            this.originUrl = originUrl;
        }

        public String getAlarmTp2() {
            return alarmTp2;
        }

        public void setAlarmTp2(String alarmTp2) {
            this.alarmTp2 = alarmTp2;
        }

        public String getAlarmTp1() {
            return alarmTp1;
        }

        public void setAlarmTp1(String alarmTp1) {
            this.alarmTp1 = alarmTp1;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAlarmPic2() {
            return alarmPic2;
        }

        public void setAlarmPic2(String alarmPic2) {
            this.alarmPic2 = alarmPic2;
        }

        public String getAlarmPic1() {
            return alarmPic1;
        }

        public void setAlarmPic1(String alarmPic1) {
            this.alarmPic1 = alarmPic1;
        }
    }

    public static class WeatherBean {
        private String aqi;
        private String date;
        private InfoBean info;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            private List<String> night;
            private List<String> day;

            public List<String> getNight() {
                return night;
            }

            public void setNight(List<String> night) {
                this.night = night;
            }

            public List<String> getDay() {
                return day;
            }

            public void setDay(List<String> day) {
                this.day = day;
            }
        }
    }

    public static class HourlyForecastBean {
        private int img;
        private String wind_speed;
        private String hour;
        private String wind_direct;
        private String temperature;
        private String info;

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public String getWind_speed() {
            return wind_speed;
        }

        public void setWind_speed(String wind_speed) {
            this.wind_speed = wind_speed;
        }

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public String getWind_direct() {
            return wind_direct;
        }

        public void setWind_direct(String wind_direct) {
            this.wind_direct = wind_direct;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
