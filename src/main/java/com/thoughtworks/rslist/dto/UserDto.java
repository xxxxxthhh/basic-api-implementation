package com.thoughtworks.rslist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("user_name")
    private String name;

    @Min(18)
    @Max(100)
    @NotNull
    @JsonProperty("user_age")
    private Integer age;

    @NotEmpty
    @JsonProperty("user_gender")
    private String gender;

    @NotEmpty
    @Email
    @JsonProperty("user_email")
    private String email;
    @NotEmpty
    @Pattern(regexp ="^1\\d{10}")
    @JsonProperty("user_phone")
    private String phone;
}
