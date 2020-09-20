package com.thoughtworks.rslist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RsEvent {
    @NotNull
    private String eventName;
    @NotNull
    private String keyword;
    @NotNull
    private Integer userID;

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(Integer voteNum) {
        this.voteNum = voteNum;
    }

    @NotNull
    private Integer entityId;
    @NotNull
    private Integer voteNum;


    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    // @JsonProperty
    public void setUserID(Integer userId) {
        this.userID = userId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getKeyword() {
        return keyword;
    }
    // @JsonIgnore
    public Integer getUserID() {
        return userID;
    }
}
