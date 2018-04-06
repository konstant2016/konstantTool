package com.konstant.toollite.eventbusparam;

/**
 * Created by konstant on 2018/4/7.
 */

public class IndexChanged {
    private int index;

    public IndexChanged(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
