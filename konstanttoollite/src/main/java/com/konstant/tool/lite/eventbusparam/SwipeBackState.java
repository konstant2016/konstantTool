package com.konstant.tool.lite.eventbusparam;

/**
 * Created by konstant on 2018/4/4.
 */

public class SwipeBackState {

    private boolean state;

    public SwipeBackState(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
