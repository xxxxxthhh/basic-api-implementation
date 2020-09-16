package com.thoughtworks.rslist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty
    @Size(max = 8)
    private String name;
    @NotEmpty
    private String gender;
    @Min(18)
    @Max(100)
    @NotNull
    private Integer age;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Pattern(regexp ="^1\\d{10}")
    private String phone;
}
