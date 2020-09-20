package com.thoughtworks.rslist.entity;
import	java.sql.Timestamp;

import com.thoughtworks.rslist.dto.RsEvent;
import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;

@Entity
@Table(name = "vote")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VotyEntity {
//（根据userId查询他投过票的所有热搜事件，票数和投票时间，根据rsEventId查询所有给他投过票的用户，票数和投票时间）
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer Id;
    private Integer rsEventId;
    private int voteNum;
    private Integer userId;
    private String voteTime;

    public VotyEntity(Integer rsEventId, int voteNum, int userId, String voteTime) {
        this.rsEventId = rsEventId;
        this.voteNum = voteNum;
        this.userId = userId;
        this.voteTime = voteTime;
    }
    // @ManyToOne
    // @JoinColumn(name = "rsEvent_id")
    // private RsEntity rsEntity;
    //
    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // private UserEntity userEntity;
}
