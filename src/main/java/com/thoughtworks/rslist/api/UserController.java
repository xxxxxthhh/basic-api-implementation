package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.UserDto;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private List<UserDto> userList = initUserList();

    private List<UserDto> initUserList() {
        List<UserDto> tempUserList = new ArrayList<>();
        UserDto userDto = new UserDto("youtube", "male", 20, "abcdefg@gmail.com", "17628282910");
        tempUserList.add(userDto);
        return tempUserList;
    }

    @PostMapping("/user/register")
    public void register(@Valid @RequestBody UserDto userDto) {
        userList.add(userDto);
    }

    @PostMapping("/user/list")
    public List<UserDto> getUserList() {
        return userList;
    }
}
