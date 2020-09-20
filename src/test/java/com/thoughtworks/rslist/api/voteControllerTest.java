package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.Vote;
import com.thoughtworks.rslist.entity.RsEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VotyEntity;
import com.thoughtworks.rslist.repository.RsRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class voteControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    RsRepository rsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoteRepository voteRepository;


    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");

    @BeforeEach
    public void beforeEach() {
        userRepository.deleteAll();
        rsRepository.deleteAll();
        UserEntity userOne = UserEntity.builder().username("clark")
                .age(19).email("lkn@163.com").gender("男").phone("11111111111")
                .voteNum(10).build();
        UserEntity userTwo = UserEntity.builder().username("jim")
                .age(29).email("lkn@163.com").gender("女").phone("22111111111")
                .voteNum(10).build();
        UserEntity userThree = UserEntity.builder().username("amy")
                .age(19).email("lkn@163.com").gender("男").phone("33111111111")
                .voteNum(10).build();
        userRepository.save(userOne);
        userRepository.save(userTwo);
        userRepository.save(userThree);
    }

    private List<RsEntity> initRsEvent() {
        List<UserEntity> userRepositoryAll = userRepository.findAll();
        UserEntity userEntity = userRepositoryAll.get(0);
        userRepository.save(userEntity);
        RsEntity rsEntity1 = RsEntity.builder()
                .eventName("你好啊")
                .keyword("社会")
                .user(userEntity)
                .voteNum(0)
                .build();
        rsRepository.save(rsEntity1);
        RsEntity rsEntity2 = RsEntity.builder()
                .eventName("死扑街")
                .keyword("社会")
                .user(userEntity)
                .voteNum(0)
                .build();
        rsRepository.save(rsEntity2);
        RsEntity rsEntity3 = RsEntity.builder()
                .eventName("社会人")
                .keyword("社会")
                .user(userEntity)
                .voteNum(0)
                .build();
        rsRepository.save(rsEntity3);
        RsEntity rsEntity4 = RsEntity.builder()
                .eventName("酸辣粉")
                .keyword("社会")
                .user(userEntity)
                .voteNum(0)
                .build();
        rsRepository.save(rsEntity4);

        List<RsEntity> rsEntities = rsRepository.findAll();

        return rsEntities;
    }

    @Test
    public void get_vote_between_start_and_end() throws Exception {

        initRsEvent();

        Date now = new Date();

        VotyEntity voteOne = new VotyEntity(1,1,1,ft.format(now));
        sleep(2000);
        VotyEntity voteTwo = new VotyEntity(1,2,2,ft.format(now));
        sleep(1000);
        VotyEntity voteThree = new VotyEntity(1,3,3,ft.format(now));

        ObjectMapper objectMapper = new ObjectMapper();
        String voteJson1 = objectMapper.writeValueAsString(voteOne);
        String voteJson2 = objectMapper.writeValueAsString(voteTwo);
        String voteJson3 = objectMapper.writeValueAsString(voteThree);

        mockMvc.perform(post("/rs/vote/1").contentType(MediaType.APPLICATION_JSON)
                .content(voteJson1)).andExpect(status().isCreated());
        mockMvc.perform(post("/rs/vote/1").contentType(MediaType.APPLICATION_JSON)
                .content(voteJson2)).andExpect(status().isCreated());
        mockMvc.perform(post("/rs/vote/1").contentType(MediaType.APPLICATION_JSON)
                .content(voteJson3)).andExpect(status().isCreated());

        String start_time = "1997-01-01 11:11:11";
        String end_time = "2997-01-01 11:11:11";
        mockMvc.perform(get("/vote/listBetweenTime?startTime="+ start_time +
                "&endTime=" + end_time))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }
}