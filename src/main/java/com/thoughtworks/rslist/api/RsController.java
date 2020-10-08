package com.thoughtworks.rslist.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.Exception.CommentError;
import com.thoughtworks.rslist.Exception.RsIndexException;
import com.thoughtworks.rslist.Exception.StartEndException;
import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.UserDto;
import jdk.net.SocketFlow;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {

    public static List<RsEvent> rsList = initRsList();
    public List<UserDto> userList = UserController.userList;

    public static List<RsEvent> initRsList() {
        List<RsEvent> tempRsList = new ArrayList<>();
        UserDto userDto = new UserDto("youtube", 20, "male", "abcdefg@gmail.com", "17628282910");
        tempRsList.add(new RsEvent("第一条事件", "无分类", userDto));
        tempRsList.add(new RsEvent("第二条事件", "无分类", userDto));
        tempRsList.add(new RsEvent("第三条事件", "无分类", userDto));
        return tempRsList;
    }

    // @GetMapping("/rs/list")
    // public String getAllRs() {
    //     return rsList.toString();
    // }


    @GetMapping("/rs/{index}")
    public ResponseEntity<RsEvent> getRsEvent(@PathVariable int index) throws RsIndexException {
        if (index<1||index>=rsList.size()){
          throw new RsIndexException("invalid index");
        }
        return ResponseEntity.ok(rsList.get(index - 1));
    }

    @GetMapping("/rs/sublist")
    public ResponseEntity<List<RsEvent>> getRsEventByRange(@RequestParam(required = false) Integer start,
                                                           @RequestParam(required = false) Integer end) {

        if (start == null || end == null) {
            return ResponseEntity.ok(rsList);
        }
        if ((start<0||end>rsList.size())||(start>end)){
            throw new StartEndException("invalid request param");
        }

        return ResponseEntity.ok(rsList.subList(start - 1, end));
    }

    @PostMapping("/rs/event")
    public ResponseEntity addRsEvent(@RequestBody String rsEventStr) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RsEvent rsEvent = objectMapper.readValue(rsEventStr, RsEvent.class);
        UserDto userDto = rsEvent.getUser();
        rsList.add(rsEvent);
        // if (userList.stream().noneMatch(currentUser -> currentUser.getName().equals(userDto.getName()))) {
        if (!userList.contains(userDto)) {
            userList.add(userDto);
        }
        return ResponseEntity.created(null).header("index", String.valueOf(rsList.indexOf(rsEvent))).build();
    }

    @PostMapping("/rs/update")
    protected ResponseEntity<List<RsEvent>> updateEvent(@RequestParam String updateIndex, @RequestParam String eventName, @RequestParam String keyword) {
        RsEvent rsEvent = rsList.get(Integer.parseInt(updateIndex) - 1);
        if (eventName != null) rsEvent.setEventName(eventName);
        if (keyword != null) rsEvent.setKeyword(keyword);
        return ResponseEntity.status(201).header("index", String.valueOf(rsList.indexOf(rsEvent))).body(rsList);
    }

    @GetMapping("/rs/delEvent/{index}")
    public ResponseEntity<List<RsEvent>> delEvent(@PathVariable int index) {
        rsList.remove(index - 1);
        return ResponseEntity.ok(rsList);
    }

}
