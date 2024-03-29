package com.konstant.tool.lite.data.bean.stock;

import androidx.annotation.Keep;

import java.util.Objects;

@Keep
public class StockData {

    private String name;            // 股票名字
    private String number;          // 股票编码：sz002007
    private double price;           // 当前价格
    private double count;           // 持股数量
    private boolean increase = true;// 是否为涨

    public StockData(String name, String number, double price, double count, boolean increase) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.count = count;
        this.increase = increase;
    }

    public StockData(String number, double count) {
        this.number = number;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isIncrease() {
        return increase;
    }

    public void setIncrease(boolean increase) {
        this.increase = increase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockData stockData = (StockData) o;
        return Objects.equals(number, stockData.number);
    }
}
