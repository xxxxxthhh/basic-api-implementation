package com.thoughtworks.rslist.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties("userInfo")

public class RsEvent {
    @NotEmpty
    private String eventName;
    @NotEmpty
    private String keyword;
    @NotNull
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

    public void setUser(UserDto user) {
        this.user = user;
    }
}
