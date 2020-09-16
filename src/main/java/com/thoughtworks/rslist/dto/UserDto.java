package com.thoughtworks.rslist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String gender;

    private Integer age;
    @NotEmpty
    private String email;
    @NotEmpty
    private String phone;
}
