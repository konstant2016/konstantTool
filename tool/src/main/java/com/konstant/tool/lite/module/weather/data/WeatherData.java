package com.konstant.tool.lite.module.weather.data;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherData {

    public class Current {
        private String alert;
        private String alertUrl;
        private long updateTime;
        private int temperature;
        private String direction;
        private String power;
        private String weather;

        public Current(String alert, String alertUrl, long updateTime, int temperature, String direction, String power, String weather) {
            this.alert = alert;
            this.alertUrl = alertUrl;
            this.updateTime = updateTime;
            this.temperature = temperature;
            this.direction = direction;
            this.power = power;
            this.weather = weather;
        }

        public String getAlert() {
            return alert;
        }

        public String getAlertUrl() {
            return alertUrl;
        }

        public String getUpdateTime() {
            return "更新时间：" + new SimpleDateFormat("MM-dd HH:mm").format(new Date(updateTime * 1000));
        }

        public int getTemperature() {
            return temperature;
        }

        public String getDirection() {
            return direction;
        }

        public String getPower() {
            return power;
        }

        public String getWeather() {
            return "天气：" + weather;
        }
    }

    public class Title {
        private String title;

        public Title(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    public class Hour {
        private List<HourData> hourDataList;

        public Hour() {
        }

        public Hour(List<HourData> hourDataList) {
            this.hourDataList = hourDataList;
        }

        public List<HourData> getHourDataList() {
            return hourDataList;
        }

        public class HourData {
            private int imgType;
            private int hour;
            private int temperature;

            public HourData(int imgType, int hour, int temperature) {
                this.imgType = imgType;
                this.hour = hour;
                this.temperature = temperature;
            }

            public int getImgType() {
                return imgType;
            }

            public int getHour() {
                return hour;
            }

            public String getTemperature() {
                return temperature + "℃";
            }
        }
    }

    public class Day {
        private String date;
        private String dayWeather;
        private String nightWeather;
        private int dayTemperature;
        private int nightTemperature;
        private String direction;
        private String power;
        private int imgType;

        public Day(String date, String dayWeather, String nightWeather, int dayTemperature, int nightTemperature, String direction, String power, int imgType) {
            this.date = date;
            this.dayWeather = dayWeather;
            this.nightWeather = nightWeather;
            this.dayTemperature = dayTemperature;
            this.nightTemperature = nightTemperature;
            this.direction = direction;
            this.power = power;
            this.imgType = imgType;
        }

        public String getDate() {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(this.date);
                return new SimpleDateFormat("MM-dd").format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
        }

        public String getWeek() {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(this.date);
                return new SimpleDateFormat("E").format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
        }

        public String getWeather() {
            if (TextUtils.equals(dayWeather, nightWeather)) return dayWeather;
            return dayWeather + "转" + nightWeather;
        }

        public String getTemperature() {
            return dayTemperature + "~" + nightTemperature + "℃";
        }

        public String getDirection() {
            return direction;
        }

        public String getPower() {
            return power;
        }

        public int getImgType() {
            return imgType;
        }
    }

    public class Life {
        private List<LifeData> lifeDataList;

        public Life() {
        }

        public Life(List<LifeData> lifeDataList) {
            this.lifeDataList = lifeDataList;
        }

        public List<LifeData> getLifeDataList() {
            return lifeDataList;
        }

        public class LifeData {
            private String title;
            private String describe;

            public LifeData(String title, String describe) {
                this.title = title;
                this.describe = describe;
            }

            public String getTitle() {
                return title;
            }

            public String getDescribe() {
                return describe;
            }
        }
    }
}
