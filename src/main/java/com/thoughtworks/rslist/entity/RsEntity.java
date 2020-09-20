package com.thoughtworks.rslist.entity;

import com.thoughtworks.rslist.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "rs_event")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class RsEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String eventName;
    private String keyword;
    // @NotNull
    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // private Integer userId;
    // private UserEntity user;
    private Integer voteNum = 0;

    // @NotNull
    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // private UserEntity userEntity;
    // @OneToMany(mappedBy = "rsEvent", cascade = CascadeType.REMOVE)
    // private List<VotyEntity> votes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
