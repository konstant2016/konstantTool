package com.konstant.toollite.eventbusparam;

/**
 * Created by konstant on 2018/4/6.
 */

public class TitleChanged {
    private String title;

    public TitleChanged(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
