package com.konstant.tool.lite.data.bean.stock;

import androidx.annotation.Keep;

import java.util.Objects;

@Keep
public class StockData {

    private String name;
    private String number;
    private double price;
    private int count;

    public StockData(String name, String number, double price, int count) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.count = count;
    }

    public StockData(String name, String number) {
        this.name = name;
        this.number = number;
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

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockData stockData = (StockData) o;
        return Objects.equals(number, stockData.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
