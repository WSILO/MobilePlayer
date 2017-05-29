package com.atguigu.maxwu.mobileplayer.utils;

import android.content.Context;
import android.net.TrafficStats;

import java.util.Formatter;
import java.util.Locale;

public class Utils {

    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    private long lastTimeStamp = 0;
    private long lastTotalRxBytes = 0;

    public Utils() {
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

    }

    public String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;

        int minutes = (totalSeconds / 60) % 60;

        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
                    .toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }
    public boolean isNetUri(String data) {
        boolean isNetUri = false;
        if (data != null) {
            if (data.toLowerCase().startsWith("http") || data.toLowerCase().startsWith("rtsp") || data.toLowerCase().startsWith("mms")) {
                isNetUri = true;
            }
        }
        return isNetUri;
    }

    public String getNetSpeed(Context context) {
        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB;
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        String msg = String.valueOf(speed) + " kb/s";
        return msg;
    }

}
