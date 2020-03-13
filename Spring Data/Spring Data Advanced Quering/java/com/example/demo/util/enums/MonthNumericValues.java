package com.example.demo.util.enums;

public enum MonthNumericValues {
    Jan(1),
    Feb(2),
    Mar(3),
    Apr(4),
    May(5),
    Jun(6),
    Jul(7),
    Aug(8),
    Sep(9),
    Oct(10),
    Nov(11),
    Dec(12),;

    private int monthValue;

    public int getMonthValue() {
        return this.monthValue;
    }

    MonthNumericValues(int monthValue) {
        this.monthValue = monthValue;
    }
}