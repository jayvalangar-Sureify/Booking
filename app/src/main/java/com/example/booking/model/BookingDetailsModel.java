package com.example.booking.model;

import java.util.Map;

public class BookingDetailsModel {
    private String date;
    private String time;
    private int price;
    private Map<String, Map<String, String>> details;

    public BookingDetailsModel(String date, String time, int price, Map<String, Map<String, String>> details) {
        this.date = date;
        this.time = time;
        this.price = price;
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getPrice() {
        return price;
    }

    public Map<String, Map<String, String>> getDetails() {
        return details;
    }
}
