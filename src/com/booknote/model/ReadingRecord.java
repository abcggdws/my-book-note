package com.booknote.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReadingRecord implements Serializable {
    private int pagesRead;
    private long timestamp;

    public ReadingRecord(int pagesRead, long timestamp) {
        this.pagesRead = pagesRead;
        this.timestamp = timestamp;
    }

    public int getPagesRead() { return pagesRead; }
    public long getTimestamp() { return timestamp; }
    public String getFormattedTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(timestamp));
    }
}