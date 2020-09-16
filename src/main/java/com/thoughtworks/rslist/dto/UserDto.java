package com.thoughtworks.rslist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty
    @Size(max = 8)
    private String name;
    @NotEmpty
    private String gender;

    private Integer age;
    @NotEmpty
    private String email;
    @NotEmpty
    private String phone;
}
