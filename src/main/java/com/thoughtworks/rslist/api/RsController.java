package com.thoughtworks.rslist.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.entity.RsEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VotyEntity;
import com.thoughtworks.rslist.repository.RsRepository;
import com.thoughtworks.rslist.repository.UserRepository;
// import com.thoughtworks.rslist.repository.VoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RsController {
    @Autowired
    private UserRepository userRepository;
    // @Autowired
    // private VoteRepository voteRepository;
    @Autowired
    private RsRepository rsRepository;
    public RsController(UserRepository userRepository, RsRepository rsRepository) {
        this.userRepository = userRepository;
        this.rsRepository = rsRepository;
    }

    public static List<RsEvent> rsList = initRsList();
    public List<UserDto> userList = UserController.userList;

    public static List<RsEvent> initRsList() {
        List<RsEvent> tempRsList = new ArrayList<>();
        UserDto userDto = new UserDto("youtube", 20, "male", "abcdefg@gmail.com", "17628282910",10);
        tempRsList.add(new RsEvent("第一条事件", "无分类", 1));
        tempRsList.add(new RsEvent("第二条事件", "无分类", 2));
        tempRsList.add(new RsEvent("第三条事件", "无分类", 3));
        return tempRsList;
    }

    // @GetMapping("/rs/list")
    // public String getAllRs() {
    //     return rsList.toString();
    // }


    @GetMapping("/rs/{index}")
    public ResponseEntity<RsEvent> getRsEvent(@PathVariable int index) {
        return ResponseEntity.ok(rsList.get(index - 1));
    }

    @GetMapping("/rs/list")
    public ResponseEntity<List<RsEvent>> getRsEventByRange(@RequestParam(required = false) Integer start,
                                                           @RequestParam(required = false) Integer end) {
        if (start == null || end == null) {
            return ResponseEntity.ok(rsList);
        }
        return ResponseEntity.ok(rsList.subList(start - 1, end));
    }

    @PostMapping("/rs/event")
    public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) throws JsonProcessingException {

        RsEntity rsEntity = RsEntity.builder()
                .eventName(rsEvent.getEventName())
                .keyword(rsEvent.getKeyword())
                .userId(rsEvent.getUserID())
                .build();
        Optional<UserEntity> user = userRepository.findById(rsEntity.getUserId());
        if (!user.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        rsRepository.save(rsEntity);
        return ResponseEntity.created(null).build();
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

    @PatchMapping("/rs/update/{rsEventId}")
    public ResponseEntity updateRsEventInSql(@RequestBody @Valid RsEvent rsEvent, @PathVariable Integer rsEventId) throws Exception {
        List<RsEntity> rsEvents = rsRepository.findAll();
        if (rsEvents.get(rsEventId-1).getUserId()!= rsEvent.getUserID()){
            return ResponseEntity.badRequest().build();
        }

        RsEntity rsEntity = rsEvents.get(rsEventId-1);
        if (rsEvent.getEventName() != null){
            rsEntity.setEventName((rsEvent.getEventName()));
        }
        if (rsEvent.getKeyword()!= null){
            rsEntity.setKeyword(rsEvent.getKeyword());
        }
        rsRepository.save(rsEntity);
        return ResponseEntity.ok(rsEvent);
    }

    @PostMapping("/rs/vote/{rsEventId}")
    public ResponseEntity voteForRsEvent(@RequestBody @Valid VotyEntity voteEntity, @PathVariable Integer rsEventId) throws Exception {
        int userId = voteEntity.getUserId();
        int voteNum = voteEntity.getVoteNum();
        List<UserEntity> users = userRepository.findAll();
        UserEntity userEntity = users.get(userId-1);

        List<RsEntity> rsEntities = rsRepository.findAll();
        RsEntity rsEntity = rsEntities.get(rsEventId-1);

        if (userEntity.getVoteNum() < voteNum){
            return ResponseEntity.badRequest().build();
        }else {
            userEntity.setVoteNum(userEntity.getVoteNum() - voteNum);
            rsEntity.setVoteNum(rsEntity.getVoteNum() + voteNum);
        }
        userRepository.save(userEntity);
        rsRepository.save(rsEntity);
        // voteRepository.save(voteEntity);
        return ResponseEntity.ok(null);
    }
}
