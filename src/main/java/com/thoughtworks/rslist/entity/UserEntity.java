package com.thoughtworks.rslist.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.rslist.dto.RsEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String username;
    private int age;
    private String gender;
    private String email;
    private String phone;
    private int voteNum;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RsEntity> rsEntities;


    // @OneToMany(mappedBy = "userEntity",cascade = CascadeType.REMOVE)
    // private List<RsEvent> rsEvents;
    // @OneToMany(mappedBy = "userEntity",cascade = CascadeType.REMOVE)
    // private List<VotyEntity> votes;
}
