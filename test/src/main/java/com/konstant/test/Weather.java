package com.konstant.test;

import java.util.List;

public class Weather {


    /**
     * historyWeather : {"history":{}}
     * area : [["北京","10101"],["北京","101010100"],["通州","101010600"]]
     * life : {"date":"2019-03-29","info":{"kongtiao":["较少开启","您将感到很舒适，一般不需要开启空调。"],"daisan":["带伞","有降水，请带上雨伞，如果你喜欢雨中漫步，享受大自然给予的温馨和快乐，在短时间外出可收起雨伞。"],"ziwaixian":["最弱","属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"],"yundong":["较不宜","有降水，且风力较强，推荐您在室内进行低强度运动；若坚持户外运动，请注意保暖并携带雨具。"],"ganmao":["易发","昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。"],"xiche":["不宜","不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"],"diaoyu":["适宜","白天风和日丽，适宜垂钓，渺渺蓝天，悠悠白云将陪伴你度过愉快的垂钓时光。"],"guomin":["易发","天气条件易诱发过敏，有降水，易过敏人群应减少外出，如需外出最好穿长衣长裤，预防感冒可能引发的过敏。"],"xianxing":["5和0","五环路（不含）以内道路。本市号牌尾号限行;外地号牌工作日(07:00-09:00、17:00-20:00)全部限行，其他限行时间内尾号限行;法定上班的周六周日不限行。"],"wuran":["良","气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"],"chuanyi":["较冷","建议着大衣、呢外套加毛衣、卫衣等服装。体弱者宜着厚外套、厚毛衣。因昼夜温差较大，注意增减衣服。"]}}
     * realtime : {"mslp":"","wind":{"windspeed":"5.7","direct":"西风","power":"4级"},"time":"16:02:00","pressure":"1005","weather":{"humidity":"23","img":"0","info":"晴","temperature":"14"},"feelslike_c":"14","dataUptime":"1553846520","date":"2019-03-29"}
     * alert : [{"content":"通州区气象台29日10时40分发布大风蓝色预警,受不断补充冷空气影响，预计29日傍晚至31日白天，通州区多4、5级偏北风，阵风可达7级左右，请注意防范。","pubTime":"2019-03-29 10:40","originUrl":"http://mobile.weathercn.com/alert.do?id=11011241600000_20190329104000","alarmTp2":"蓝色","alarmTp1":"大风","type":1,"alarmPic2":"01","alarmPic1":"05"},{"content":"市气象台2019年3月29日10时30分发布大风蓝色预警信号：受不断补充冷空气影响，预计29日傍晚至31日白天，本市多4、5级偏北风，阵风可达7级左右，请注意防范。","pubTime":"2019-03-29 10:34","originUrl":"http://mobile.weathercn.com/alert.do?id=11000041600000_20190329103429","alarmTp2":"蓝色","alarmTp1":"大风","type":1,"alarmPic2":"01","alarmPic1":"05"},{"content":"市森防办与市气象台联合发布森林火险橙色预警：3月29日至4月7日，本市风力较大，气温偏高，较易发生森林火灾。请广大市民不要携带火种进入林区，严禁野外一切违规违法用火。一旦发现森林火情，请及时拨打报警电话12119。","pubTime":"2019-03-28 17:35","originUrl":"http://mobile.weathercn.com/alert.do?id=11000041600000_20190328173504","alarmTp2":"橙色","alarmTp1":"森林火险","type":1,"alarmPic2":"03","alarmPic1":"00"}]
     * trafficalert : []
     * weather : [{"aqi":"62","date":"2019-03-28","info":{"night":["1","多云","0","南风","微风","18:32"],"day":["1","多云","12","南风","微风","06:07"]}},{"aqi":"64","date":"2019-03-29","info":{"night":["0","晴","3","西北风","4-5级","18:33"],"day":["7","小雨","13","西北风","3-5级","06:06"]}},{"aqi":"38","date":"2019-03-30","info":{"night":["0","晴","0","西北风","微风","18:34"],"day":["0","晴","13","西北风","4-5级","06:04"]}},{"aqi":"31","date":"2019-03-31","info":{"night":["0","晴","2","西北风","微风","18:35"],"day":["0","晴","16","西北风","3-5级","06:02"]}},{"aqi":"46","date":"2019-04-01","info":{"night":["0","晴","2","东北风","微风","18:36"],"day":["0","晴","18","东南风","微风","06:01"]}},{"aqi":"76","date":"2019-04-02","info":{"night":["1","多云","4","南风","微风","18:37"],"day":["0","晴","18","南风","微风","05:59"]}},{"aqi":"87","date":"2019-04-03","info":{"night":["1","多云","7","西南风","微风","18:38"],"day":["1","多云","20","南风","微风","05:57"]}},{"aqi":"100","date":"2019-04-04","info":{"night":["0","晴","9","西南风","3-5级","18:39"],"day":["0","晴","24","南风","3-5级","05:56"]}},{"aqi":"","date":"2019-04-05","info":{"night":["1","多云","5","西北风","3-5级","18:40"],"day":["1","多云","20","西北风","5-6级","05:54"]}},{"aqi":"","date":"2019-04-06","info":{"night":["0","晴","3","西北风","微风","18:41"],"day":["0","晴","13","西北风","4-5级","05:53"]}},{"aqi":"","date":"2019-04-07","info":{"night":["0","晴","1","西风","微风","18:42"],"day":["0","晴","16","西北风","3-5级","05:51"]}},{"aqi":"","date":"2019-04-08","info":{"night":["0","晴","4","南风","微风","18:43"],"day":["0","晴","18","西北风","3-5级","05:50"]}},{"aqi":"","date":"2019-04-09","info":{"night":["0","晴","9","西北风","微风","18:44"],"day":["1","多云","25","西南风","3-5级","05:48"]}},{"aqi":"","date":"2019-04-10","info":{"night":["0","晴","7","东风","3-5级","18:45"],"day":["7","小雨","27","东南风","3-5级","05:46"]}},{"aqi":"","date":"2019-04-11","info":{"night":["0","晴","10","南风","微风","18:46"],"day":["0","晴","29","西南风","3-5级","05:45"]}},{"aqi":"","date":"2019-04-12","info":{"night":["1","多云","8","北风","微风","18:47"],"day":["1","多云","25","东风","3-5级","05:43"]}}]
     * pm25 : {"so2":6,"o3":73,"co":"0.4","level":2,"color":"#ffff00","no2":23,"aqi":64,"quality":"良","pm10":77,"pm25":37,"advice":"今天的空气质量是可以接受的，除少数异常敏感体质的人群外，大家可在户外正常活动。","chief":"PM10","upDateTime":1553842800000}
     * hourly_forecast : [{"img":"00","wind_speed":"4","hour":"16","wind_direct":"西北风","temperature":"13","info":"晴"},{"img":"00","wind_speed":"5","hour":"17","wind_direct":"西北风","temperature":"12","info":"晴"},{"img":"00","wind_speed":"8","hour":"18","wind_direct":"东北风","temperature":"11","info":"晴"},{"img":"00","wind_speed":"9","hour":"19","wind_direct":"西北风","temperature":"11","info":"晴"},{"img":"00","wind_speed":"9","hour":"20","wind_direct":"西北风","temperature":"9","info":"晴"},{"img":"00","wind_speed":"8","hour":"21","wind_direct":"西北风","temperature":"9","info":"晴"},{"img":"00","wind_speed":"8","hour":"22","wind_direct":"西北风","temperature":"8","info":"晴"},{"img":"00","wind_speed":"8","hour":"23","wind_direct":"西北风","temperature":"7","info":"晴"},{"img":"00","wind_speed":"7","hour":"0","wind_direct":"西北风","temperature":"6","info":"晴"},{"img":"00","wind_speed":"7","hour":"1","wind_direct":"西北风","temperature":"6","info":"晴"},{"img":"00","wind_speed":"7","hour":"2","wind_direct":"西北风","temperature":"5","info":"晴"},{"img":"00","wind_speed":"7","hour":"3","wind_direct":"西北风","temperature":"5","info":"晴"},{"img":"00","wind_speed":"7","hour":"4","wind_direct":"西北风","temperature":"4","info":"晴"},{"img":"00","wind_speed":"7","hour":"5","wind_direct":"西北风","temperature":"3","info":"晴"},{"img":"00","wind_speed":"7","hour":"6","wind_direct":"西北风","temperature":"4","info":"晴"},{"img":"00","wind_speed":"8","hour":"7","wind_direct":"西北风","temperature":"5","info":"晴"},{"img":"00","wind_speed":"9","hour":"8","wind_direct":"西北风","temperature":"6","info":"晴"},{"img":"00","wind_speed":"9","hour":"9","wind_direct":"西北风","temperature":"8","info":"晴"},{"img":"00","wind_speed":"9","hour":"10","wind_direct":"西北风","temperature":"9","info":"晴"},{"img":"01","wind_speed":"9","hour":"11","wind_direct":"西北风","temperature":"10","info":"多云"},{"img":"01","wind_speed":"9","hour":"12","wind_direct":"西北风","temperature":"11","info":"多云"},{"img":"01","wind_speed":"9","hour":"13","wind_direct":"西北风","temperature":"12","info":"多云"},{"img":"02","wind_speed":"9","hour":"14","wind_direct":"西北风","temperature":"12","info":"阴"},{"img":"01","wind_speed":"8","hour":"15","wind_direct":"西北风","temperature":"13","info":"多云"}]
     */

    private HistoryWeatherBean historyWeather;
    private LifeBean life;
    private RealtimeBean realtime;
    private Pm25Bean pm25;
    private List<List<String>> area;
    private List<AlertBean> alert;
    private List<?> trafficalert;
    private List<WeatherBeanX> weather;
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

    public List<WeatherBeanX> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherBeanX> weather) {
        this.weather = weather;
    }

    public List<HourlyForecastBean> getHourly_forecast() {
        return hourly_forecast;
    }

    public void setHourly_forecast(List<HourlyForecastBean> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    public static class HistoryWeatherBean {
        /**
         * history : {}
         */

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
        /**
         * date : 2019-03-29
         * info : {"kongtiao":["较少开启","您将感到很舒适，一般不需要开启空调。"],"daisan":["带伞","有降水，请带上雨伞，如果你喜欢雨中漫步，享受大自然给予的温馨和快乐，在短时间外出可收起雨伞。"],"ziwaixian":["最弱","属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"],"yundong":["较不宜","有降水，且风力较强，推荐您在室内进行低强度运动；若坚持户外运动，请注意保暖并携带雨具。"],"ganmao":["易发","昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。"],"xiche":["不宜","不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"],"diaoyu":["适宜","白天风和日丽，适宜垂钓，渺渺蓝天，悠悠白云将陪伴你度过愉快的垂钓时光。"],"guomin":["易发","天气条件易诱发过敏，有降水，易过敏人群应减少外出，如需外出最好穿长衣长裤，预防感冒可能引发的过敏。"],"xianxing":["5和0","五环路（不含）以内道路。本市号牌尾号限行;外地号牌工作日(07:00-09:00、17:00-20:00)全部限行，其他限行时间内尾号限行;法定上班的周六周日不限行。"],"wuran":["良","气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"],"chuanyi":["较冷","建议着大衣、呢外套加毛衣、卫衣等服装。体弱者宜着厚外套、厚毛衣。因昼夜温差较大，注意增减衣服。"]}
         */

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
        /**
         * mslp :
         * wind : {"windspeed":"5.7","direct":"西风","power":"4级"}
         * time : 16:02:00
         * pressure : 1005
         * weather : {"humidity":"23","img":"0","info":"晴","temperature":"14"}
         * feelslike_c : 14
         * dataUptime : 1553846520
         * date : 2019-03-29
         */

        private String mslp;
        private WindBean wind;
        private String time;
        private String pressure;
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
            /**
             * windspeed : 5.7
             * direct : 西风
             * power : 4级
             */

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
            /**
             * humidity : 23
             * img : 0
             * info : 晴
             * temperature : 14
             */

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
        /**
         * so2 : 6
         * o3 : 73
         * co : 0.4
         * level : 2
         * color : #ffff00
         * no2 : 23
         * aqi : 64
         * quality : 良
         * pm10 : 77
         * pm25 : 37
         * advice : 今天的空气质量是可以接受的，除少数异常敏感体质的人群外，大家可在户外正常活动。
         * chief : PM10
         * upDateTime : 1553842800000
         */

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
        /**
         * content : 通州区气象台29日10时40分发布大风蓝色预警,受不断补充冷空气影响，预计29日傍晚至31日白天，通州区多4、5级偏北风，阵风可达7级左右，请注意防范。
         * pubTime : 2019-03-29 10:40
         * originUrl : http://mobile.weathercn.com/alert.do?id=11011241600000_20190329104000
         * alarmTp2 : 蓝色
         * alarmTp1 : 大风
         * type : 1
         * alarmPic2 : 01
         * alarmPic1 : 05
         */

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

    public static class WeatherBeanX {
        /**
         * aqi : 62
         * date : 2019-03-28
         * info : {"night":["1","多云","0","南风","微风","18:32"],"day":["1","多云","12","南风","微风","06:07"]}
         */

        private String aqi;
        private String date;
        private InfoBeanX info;

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

        public InfoBeanX getInfo() {
            return info;
        }

        public void setInfo(InfoBeanX info) {
            this.info = info;
        }

        public static class InfoBeanX {
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
        /**
         * img : 00
         * wind_speed : 4
         * hour : 16
         * wind_direct : 西北风
         * temperature : 13
         * info : 晴
         */

        private String img;
        private String wind_speed;
        private String hour;
        private String wind_direct;
        private String temperature;
        private String info;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
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
