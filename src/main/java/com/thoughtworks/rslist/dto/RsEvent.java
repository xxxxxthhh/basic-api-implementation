package com.thoughtworks.rslist.dto;

public class RsEvent {
    private String eventName;
    private String keyword;
    private UserDto user;

    public RsEvent() {

    }

    public RsEvent(String eventName, String keyword, UserDto user) {
        this.eventName = eventName;
        this.keyword = keyword;
        this.user = user;
    }

    public String getEventName() {
        return eventName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public UserDto getUserInfo() {
        return user;
    }

    public void setUserInfo(UserDto userInfo) {
        this.user = userInfo;
    }
}
