package com.jdk.common;

/**
 * Created by szc on 17/3/7.
 */
public enum DateEnum {

    MON(1),

    TUES(2),

    WED(3),

    THUR(4),

    FRI(5),

    SAT(6),

    SUN(7);

    private int day;

    //必须是私有的
    private DateEnum(int day){
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
