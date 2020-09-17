package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.org.apache.regexp.internal.RE;
import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.thoughtworks.rslist.api.RsController.*;

@RestController
public class UserController {

    public static List<UserDto> userList = initUserList();

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<RsEvent> rsEventList = RsController.rsList;


    public static List<UserDto> initUserList() {
        List<UserDto> tempUserList = new ArrayList<>();
        UserDto userDto = new UserDto("youtube", 20, "male", "abcdefg@gmail.com", "17628282910");
        // UserDto userDto = new UserDto("youtube", "male", 20, "abcdefg@gmail.com", "17628282910");
        tempUserList.add(userDto);
        return tempUserList;
    }

    @PostMapping("/user")
    public void registerInSql(@RequestBody @Valid UserDto user) {
        UserEntity userEntity = UserEntity.builder()
                .username(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .gender(user.getGender())
                .phone(user.getPhone())
                .voteNum(user.getVoteNum())
                .build();
        userRepository.save(userEntity);
    }

    @GetMapping("/user/get/{id}")
    public ResponseEntity<UserEntity> getUserInSqlById(@PathVariable int id) throws Exception {
        Optional<UserEntity> userDto = userRepository.findById(id);
        if (!userDto.isPresent()) {
            throw new Exception("Invalid Id");
        }
        UserEntity user = userDto.get();
        return ResponseEntity.ok(UserEntity.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .age(user.getAge())
                .gender(user.getGender())
                .phone(user.getPhone())
                .voteNum(user.getVoteNum())
                .build());
    }

    @PostMapping("/user/register")
    public ResponseEntity register(@Valid @RequestBody UserDto userDto) {
        userList.add(userDto);
        return ResponseEntity.created(null)
                .header("index", String.valueOf(userList.indexOf(userDto))).build();
    }

    @GetMapping("/get/users")
    public ResponseEntity<List<UserDto>> getAllUsers() throws JsonProcessingException {

        return ResponseEntity.ok(userList);
    }
}
