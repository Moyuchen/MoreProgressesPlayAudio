package com.moyuchen.moreprogressplayaudio.test;

public class MediaEntity {

    public final static int STATUS_PLARY = 1;
    public final static int STATUS_PAUSE = 0;
    public final static int STATUS_END = 2;
    public final static int RECIEVE = 1;
    public final static int SEND = 2;

    String uri;
    String startTime;
    String endTime;
    boolean playStatus;
    int type;
    String content;

    public String getContent() {
        return content;
    }

    public MediaEntity(String uri, String startTime, String endTime, boolean playStatus, int type, String content) {
        this.uri = uri;
        this.startTime = startTime;
        this.endTime = endTime;
        this.playStatus = playStatus;
        this.type = type;
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MediaEntity(String uri, String startTime, String endTime, boolean playStatus) {
        this.uri = uri;
        this.startTime = startTime;
        this.endTime = endTime;
        this.playStatus = playStatus;
    }

    public MediaEntity(String uri, String startTime, String endTime, boolean playStatus, int type) {
        this.uri = uri;
        this.startTime = startTime;
        this.endTime = endTime;
        this.playStatus = playStatus;
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(boolean playStatus) {
        this.playStatus = playStatus;
    }
}
