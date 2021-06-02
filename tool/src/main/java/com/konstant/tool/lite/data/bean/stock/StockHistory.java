package com.konstant.tool.lite.data.bean.stock;

import androidx.annotation.Keep;
import java.util.Objects;

@Keep
public class StockHistory implements Comparable {

    private int year;
    private int month;
    private int day;
    private double total;

    public StockHistory(int year, int month, int day, double total) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.total = total;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof StockHistory)) return 0;
        StockHistory other = (StockHistory) o;
        if (this.getYear() != other.getYear()) return this.getYear() - other.getYear();
        if (this.getMonth() != other.getMonth()) return this.getMonth() - other.getMonth();
        if (this.getDay() != other.getDay()) return this.getDay() - other.getDay();
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockHistory)) return false;
        StockHistory that = (StockHistory) o;
        return getYear() == that.getYear() &&
                getMonth() == that.getMonth() &&
                getDay() == that.getDay();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getYear(), getMonth(), getDay(), getTotal());
    }
}
