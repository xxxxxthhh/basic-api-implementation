package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.entity.RsEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VotyEntity;
import com.thoughtworks.rslist.repository.RsRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.not;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RsControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RsRepository rsRepository;
    @Autowired
    UserRepository userRepository;
    // @Autowired
    // VoteRepository voteRepository;

    //@Test
    void contextLoads() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(content().string("[第一条事件, 第二条事件, 第三条事件]"));
    }

    @BeforeEach
    void setUp() throws Exception {
        RsController.rsList = RsController.initRsList();
        UserController.userList = UserController.initUserList();

        // initUsers();
    }

    private UserEntity initUser() {
        UserEntity userEntity = UserEntity.builder()
                .username("user1")
                .email("abc@163.cn")
                .age(19)
                .gender("male")
                .phone("17628282910")
                .voteNum(10)
                .build();
        return userEntity;
    }

    private List<RsEntity> initRsEvent() {
        RsEntity rsEntity1 = RsEntity.builder()
                .eventName("你好啊")
                .keyword("社会")
                .userId(1)
                .voteNum(0)
                .build();
        rsRepository.save(rsEntity1);
        RsEntity rsEntity2 = RsEntity.builder()
                .eventName("死扑街")
                .keyword("社会")
                .userId(2)
                .voteNum(0)
                .build();
        rsRepository.save(rsEntity2);
        RsEntity rsEntity3 = RsEntity.builder()
                .eventName("社会人")
                .keyword("社会")
                .userId(3)
                .voteNum(0)
                .build();
        rsRepository.save(rsEntity3);
        RsEntity rsEntity4 = RsEntity.builder()
                .eventName("酸辣粉")
                .keyword("社会")
                .userId(4)
                .voteNum(0)
                .build();
        rsRepository.save(rsEntity4);

        List<RsEntity> rsEntities = rsRepository.findAll();

        return rsEntities;
    }

    @Test
    void should_return_all_rs_event() throws Exception {
        initRsEvent();
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                // .andExpect(jsonPath("$[0]",not(hasKey("userInfo"))))

                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].eventName", is("你好啊")))
                .andExpect(jsonPath("$[0].keyword", is("社会")))
                .andExpect(jsonPath("$[1].eventName", is("死扑街")))
                .andExpect(jsonPath("$[1].keyword", is("社会")))
                .andExpect(jsonPath("$[2].eventName", is("社会人")))
                .andExpect(jsonPath("$[2].keyword", is("社会")));
        // .andExpect(jsonPath("$[0]", not(hasKey("userInfo"))));
    }

    @Test
    void should_get_one_rs_event() throws Exception {
        List<RsEntity> rsEntities = initRsEvent();
        mockMvc.perform(get("/rs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName", is("你好啊")))
                .andExpect(jsonPath("$.keyword", is("社会")));

        mockMvc.perform(get("/rs/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName", is("死扑街")))
                .andExpect(jsonPath("$.keyword", is("社会")));

        mockMvc.perform(get("/rs/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName", is("社会人")))
                .andExpect(jsonPath("$.keyword", is("社会")));
    }

    @Test
    void should_get_rs_event_by_range() throws Exception {
        initRsEvent();
        mockMvc.perform(get("/rs/list?start=1&end=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("你好啊")))
                .andExpect(jsonPath("$[0].keyword", is("社会")))
                .andExpect(jsonPath("$[1].eventName", is("死扑街")))
                .andExpect(jsonPath("$[1].keyword", is("社会")))
                .andExpect(jsonPath("$[2].eventName", is("社会人")))
                .andExpect(jsonPath("$[2].keyword", is("社会")));
    }

    // @Test
    void add_one_rs_event() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        UserDto userDto = new UserDto("google", 20, "male", "abcdefg@gmail.com", "17628282910");
        RsEvent rsEvent = new RsEvent("第四条事件", "经济", 1);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsEvent);
        String user = "{name=google, gender=male, age=20, email=abcdefg@gmail.com, phone=17628282910}";

        mockMvc.perform(post("/rs/event").content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("index", "3"));

        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyword", is("无分类")))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyword", is("无分类")))
                .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
                .andExpect(jsonPath("$[2].keyword", is("无分类")))
                .andExpect(jsonPath("$[3].eventName", is("第四条事件")))
                .andExpect(jsonPath("$[3].keyword", is("经济")));

        // .andExpect(jsonPath("$[3].userInfo", );

    }

    @Test
    void update_one_Rs_event() throws Exception {
        // RsEvent rsEvent = new RsEvent("第五条事件","花边新闻");
        mockMvc.perform(post("/rs/update")
                .param("updateIndex", "1")
                .param("eventName", "第五条事件")
                .param("keyword", "花边新闻"))
                .andExpect(status().isCreated())
                .andExpect(header().string("index", "0"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("第五条事件")))
                .andExpect(jsonPath("$[0].keyword", is("花边新闻")));
    }

    @Test
    void delete_event_by_index() throws Exception {
        mockMvc.perform(get("/rs/delEvent/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[0].keyword", is("无分类")));
    }

    @Test
    void should_return_all_rs_event_without_users() throws Exception {
        initRsEvent();
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0]", Matchers.not(hasProperty("user"))));
    }

    @Test
    void add_rs_event_to_sql() throws Exception {

        UserEntity userEntity = initUser();
        userRepository.save(userEntity);
        String jsonValue = "{\"eventName\":\"热搜事件名\", \"keyword\":\"关键字\",\"userID\":" + userEntity.getId() + "}";

        mockMvc.perform(post("/rs/event")
                .content(jsonValue).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<RsEntity> rsEvents = rsRepository.findAll();

        assertEquals(1, rsEvents.size());
        assertEquals("热搜事件名", rsEvents.get(0).getEventName());
        assertEquals(userEntity.getId(), rsEvents.get(0).getUserId());
    }

    @Test
    void only_registered_user_can_add_rs_event() throws Exception {

        UserEntity userEntity = initUser();
        userRepository.save(userEntity);

        String jsonValue = "{\"eventName\":\"热搜事件名\", \"keyword\":\"关键字\",\"userID\":" + 2 + "}";

        mockMvc.perform(post("/rs/event")
                .content(jsonValue).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_rs_event_in_sql() throws Exception {
        UserEntity userEntity = initUser();
        userRepository.save(userEntity);

        RsEntity rsEntity = RsEntity.builder()
                .eventName("猪肉太贵了")
                .keyword("肉类")
                .userId(userEntity.getId())
                .build();
        rsRepository.save(rsEntity);

        String jsonValue = "{\"eventName\":\"热搜事件名\", \"keyword\":\"关键字\",\"userID\":" + rsEntity.getUserId() + "}";

        mockMvc.perform(patch("/rs/update/1")
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<RsEntity> rsEvents = rsRepository.findAll();
        assertEquals("热搜事件名", rsEvents.get(0).getEventName());
        assertEquals("关键字", rsEvents.get(0).getKeyword());
        assertEquals(1, rsEvents.get(0).getUserId());
    }

    @Test
    void user_not_match_get_400() throws Exception {
        UserEntity userEntity = initUser();
        userRepository.save(userEntity);

        RsEntity rsEntity = RsEntity.builder()
                .eventName("猪肉太贵了")
                .keyword("肉类")
                .userId(userEntity.getId())
                .build();
        rsRepository.save(rsEntity);

        String jsonValue = "{\"eventName\":\"热搜事件名\", \"keyword\":\"关键字\",\"userID\":" + 2 + "}";

        mockMvc.perform(patch("/rs/update/1")
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void vote_for_rs_event_fail_when_status() throws Exception {
        List<RsEntity> rsEntities = initRsEvent();
        UserEntity userEntity = initUser();
        userRepository.save(userEntity);
        ObjectMapper objectMapper = new ObjectMapper();

        VotyEntity votyEntity = new VotyEntity(11, userEntity.getId(), new Timestamp(System.currentTimeMillis()));

        String voteJson = objectMapper.writeValueAsString(votyEntity);

        mockMvc.perform(post("/rs/vote/1")
                .content(voteJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void vote_for_rs_event() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        UserEntity userEntity = initUser();
        userRepository.save(userEntity);

        List<RsEntity> rsEntities = initRsEvent();

        // String voteValue = "{\"voteNum\":5, \"userId\":" + userEntity.getId() + ",\"voteTime\":" + new Timestamp(System.currentTimeMillis()) + "}";

        VotyEntity votyEntity = new VotyEntity(5, userEntity.getId(), new Timestamp(System.currentTimeMillis()));

        String voteJson = objectMapper.writeValueAsString(votyEntity);

        mockMvc.perform(post("/rs/vote/1")
                .content(voteJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(5, userRepository.findAll().get(0).getVoteNum());
        assertEquals(5, rsRepository.findAll().get(0).getVoteNum());
    }
}