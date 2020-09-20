package com.thoughtworks.rslist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
public class RsEvent {
    @NotNull
    private String eventName;
    @NotNull
    private String keyword;
    @NotNull
    private Integer userID;


    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    @JsonProperty
    public void setUserID(Integer userId) {
        this.userID = userId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getKeyword() {
        return keyword;
    }
    @JsonIgnore
    public Integer getUserID() {
        return userID;
    }
}
