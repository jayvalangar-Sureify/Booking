package com.example.booking.interfaces;

import java.util.LinkedHashMap;

public interface OnHistoryDataChangedListener {
    void onHistoryDataChanged(LinkedHashMap<String, LinkedHashMap<String, String>> historyData);
}