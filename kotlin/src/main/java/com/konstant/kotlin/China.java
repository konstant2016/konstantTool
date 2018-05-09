package com.konstant.kotlin;

import java.util.List;

/**
 * 描述：
 * 创建人： 菜籽
 * 创建时间： 2018/5/9 下午2:52.
 * 备注信息：
 */
public class China {

    /**
     * id : 01
     * name : 北京
     * city : [{"id":"0101","name":"北京","county":[{"id":"010101","name":"北京","weatherCode":"101010100"},{"id":"010102","name":"海淀","weatherCode":"101010200"},{"id":"010103","name":"朝阳","weatherCode":"101010300"},{"id":"010104","name":"顺义","weatherCode":"101010400"},{"id":"010105","name":"怀柔","weatherCode":"101010500"},{"id":"010106","name":"通州","weatherCode":"101010600"},{"id":"010107","name":"昌平","weatherCode":"101010700"},{"id":"010108","name":"延庆","weatherCode":"101010800"},{"id":"010109","name":"丰台","weatherCode":"101010900"},{"id":"010110","name":"石景山","weatherCode":"101011000"},{"id":"010111","name":"大兴","weatherCode":"101011100"},{"id":"010112","name":"房山","weatherCode":"101011200"},{"id":"010113","name":"密云","weatherCode":"101011300"},{"id":"010114","name":"门头沟","weatherCode":"101011400"},{"id":"010115","name":"平谷","weatherCode":"101011500"}]}]
     */

    private List<Province> province;

    public List<Province> getProvinceList() {
        return province;
    }

    public void setProvinceList(List<Province> province) {
        this.province = province;
    }

    public static class Province {
        private String id;
        private String name;
        /**
         * id : 0101
         * name : 北京
         * county : [{"id":"010101","name":"北京","weatherCode":"101010100"},{"id":"010102","name":"海淀","weatherCode":"101010200"},{"id":"010103","name":"朝阳","weatherCode":"101010300"},{"id":"010104","name":"顺义","weatherCode":"101010400"},{"id":"010105","name":"怀柔","weatherCode":"101010500"},{"id":"010106","name":"通州","weatherCode":"101010600"},{"id":"010107","name":"昌平","weatherCode":"101010700"},{"id":"010108","name":"延庆","weatherCode":"101010800"},{"id":"010109","name":"丰台","weatherCode":"101010900"},{"id":"010110","name":"石景山","weatherCode":"101011000"},{"id":"010111","name":"大兴","weatherCode":"101011100"},{"id":"010112","name":"房山","weatherCode":"101011200"},{"id":"010113","name":"密云","weatherCode":"101011300"},{"id":"010114","name":"门头沟","weatherCode":"101011400"},{"id":"010115","name":"平谷","weatherCode":"101011500"}]
         */

        private List<City> city;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<City> getCityList() {
            return city;
        }

        public void setCityList(List<City> city) {
            this.city = city;
        }

        public static class City {
            private String id;
            private String name;
            /**
             * id : 010101
             * name : 北京
             * weatherCode : 101010100
             */

            private List<County> county;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<County> getCountyList() {
                return county;
            }

            public void setCountyList(List<County> county) {
                this.county = county;
            }

            public static class County {
                private String id;
                private String name;
                private String weatherCode;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getWeatherCode() {
                    return weatherCode;
                }

                public void setWeatherCode(String weatherCode) {
                    this.weatherCode = weatherCode;
                }
            }
        }
    }
}
