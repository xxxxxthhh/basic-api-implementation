package com.thoughtworks.rslist.entity;

import com.thoughtworks.rslist.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private int userId;
}
