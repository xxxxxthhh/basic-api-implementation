package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.UserDto;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.rslist.api.RsController.*;

@RestController
public class UserController {

    public static List<UserDto> userList = initUserList();

    public List<RsEvent> rsEventList = RsController.rsList;

    private static List<UserDto> initUserList() {
        List<UserDto> tempUserList = new ArrayList<>();
        UserDto userDto = new UserDto("youtube", "male", 20, "abcdefg@gmail.com", "17628282910");
        tempUserList.add(userDto);
        return tempUserList;
    }

    @PostMapping("/user/register")
    public ResponseEntity register(@Valid @RequestBody UserDto userDto) {
        userList.add(userDto);
        return ResponseEntity.created(null)
                .header("index", String.valueOf(userList.indexOf(userDto))).build();
    }

}
