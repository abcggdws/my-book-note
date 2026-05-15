package com.booknote.util;

import java.util.Timer;
import java.util.TimerTask;

public class ReminderThread {
    public static void startReminder() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\n⏰ 温馨提醒：记得坚持读书哦！");
            }
        }, 60*1000, 3600*1000); // 每1小时提醒（可调整为每天下午6点）
    }
}