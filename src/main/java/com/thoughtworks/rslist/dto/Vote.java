package com.thoughtworks.rslist.dto;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Vote {

    private int voteNum;
    private String voteTime;
    private Integer eventId;
    private Integer userId;

}
