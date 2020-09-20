package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.entity.RsEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.apache.catalina.User;
import org.hibernate.sql.Delete;
import org.json.JSONArray;
import org.json.JSONString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.constraints.Size;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsRepository rsRepository;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        objectMapper = new ObjectMapper();
    }

    void initUser() throws Exception {
        UserDto userDto = new UserDto("Alibaba", 20, "male", "alibaba@twuc.com", "17628282910");
        String request = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isOk());
    }

    private List<RsEntity> initRsEvent() {
        List<UserEntity> userRepositoryAll = userRepository.findAll();
        UserEntity userEntity = userRepositoryAll.get(0);
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
    void should_register_user() throws Exception {
        UserDto userDto = new UserDto("Alibaba", 20, "male", "alibaba@twuc.com", "17628282910");
        String request = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isOk());

        List<UserEntity> users = userRepository.findAll();

        assertEquals(1, users.size());
        assertEquals("Alibaba", users.get(0).getUsername());
    }

    @Test
    void get_user_by_id() throws Exception {
        initUser();

        assertEquals(1, userRepository.findAll().size());

        mockMvc.perform(get("/user/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("Alibaba")))
                .andExpect(jsonPath("$.age", is(20)));
    }

    @Test
    void delete_user_by_id() throws Exception {
        initUser();
        initRsEvent();

        List<UserEntity> users = userRepository.findAll();

        try {
            assertEquals(1, userRepository.findAll().size());

            mockMvc.perform(delete("/user/del/{id}", users.get(0).getId())).andExpect(status().isOk());

            assertEquals(0, userRepository.findAll().size());
            assertEquals(0, rsRepository.findAll().size());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // @Test
    // void can_not_register_if_name_is_empty() throws Exception {
    //     UserDto userDto = new UserDto(null, 20, "male", "alibaba@twuc.com", "17628282910");
    //
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     String userDtoJson = objectMapper.writeValueAsString(userDto);
    //
    //     mockMvc.perform(post("/user/register")
    //             .content(userDtoJson)
    //             .contentType(MediaType.APPLICATION_JSON)
    //     ).andExpect(status().isBadRequest());
    // }
    //
    // @Test
    // void can_not_register_if_name_longer_tham_8() throws Exception {
    //     UserDto userDto = new UserDto("youtubeBLogger", 20, "male", "alibaba@twuc.com", "17628282910");
    //
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     String userDtoJson = objectMapper.writeValueAsString(userDto);
    //
    //     mockMvc.perform(post("/user/register")
    //             .content(userDtoJson)
    //             .contentType(MediaType.APPLICATION_JSON)
    //     ).andExpect(status().isBadRequest());
    // }
    //
    // @Test
    // void can_not_register_if_gender_empty() throws Exception {
    //     UserDto userDto = new UserDto("youtubeBLogger", 20, "", "alibaba@twuc.com", "17628282910");
    //
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     String userDtoJson = objectMapper.writeValueAsString(userDto);
    //
    //     mockMvc.perform(post("/user/register")
    //             .content(userDtoJson)
    //             .contentType(MediaType.APPLICATION_JSON)
    //     ).andExpect(status().isBadRequest());
    // }
    //
    // @Test
    // void can_not_register_if_age_empty() throws Exception {
    //     UserDto userDto = new UserDto("youtube", null, "male", "alibaba@twuc.com", "17628282910");
    //
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     String userDtoJson = objectMapper.writeValueAsString(userDto);
    //
    //     mockMvc.perform(post("/user/register")
    //             .content(userDtoJson)
    //             .contentType(MediaType.APPLICATION_JSON)
    //     ).andExpect(status().isBadRequest());
    // }
    //
    // @Test
    // void can_not_register_if_age_less_than_18() throws Exception {
    //     UserDto userDto = new UserDto("youtube", 17, "male", "alibaba@twuc.com", "17628282910");
    //
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     String userDtoJson = objectMapper.writeValueAsString(userDto);
    //
    //     mockMvc.perform(post("/user/register")
    //             .content(userDtoJson)
    //             .contentType(MediaType.APPLICATION_JSON)
    //     ).andExpect(status().isBadRequest());
    // }
    //
    // @Test
    // void can_not_register_if_age_more_than_100() throws Exception {
    //     UserDto userDto = new UserDto("youtube", 120, "male", "alibaba@twuc.com", "17628282910");
    //
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     String userDtoJson = objectMapper.writeValueAsString(userDto);
    //
    //     mockMvc.perform(post("/user/register")
    //             .content(userDtoJson)
    //             .contentType(MediaType.APPLICATION_JSON)
    //     ).andExpect(status().isBadRequest());
    // }
    //
    // @Test
    // void can_not_register_if_mail_empty() throws Exception {
    //     UserDto userDto = new UserDto("youtube", 20, "male", "", "17628282910");
    //
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     String userDtoJson = objectMapper.writeValueAsString(userDto);
    //
    //     mockMvc.perform(post("/user/register")
    //             .content(userDtoJson)
    //             .contentType(MediaType.APPLICATION_JSON)
    //     ).andExpect(status().isBadRequest());
    // }
    //
    // @Test
    // void can_not_register_if_mail_not_satisfy() throws Exception {
    //     UserDto userDto = new UserDto("youtube", 20, "male", "abcdefg@", "17628282910");
    //
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     String userDtoJson = objectMapper.writeValueAsString(userDto);
    //
    //     mockMvc.perform(post("/user/register")
    //             .content(userDtoJson)
    //             .contentType(MediaType.APPLICATION_JSON)
    //     ).andExpect(status().isBadRequest());
    // }
    //
    // @Test
    // void can_not_register_if_phone_empty() throws Exception {
    //     UserDto userDto = new UserDto("youtube", 20, "male", "xth@qq.com", "");
    //
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     String userDtoJson = objectMapper.writeValueAsString(userDto);
    //
    //     mockMvc.perform(post("/user/register")
    //             .content(userDtoJson)
    //             .contentType(MediaType.APPLICATION_JSON)
    //     ).andExpect(status().isBadRequest());
    // }
    //
    // @Test
    // void can_not_register_if_phone_not_satisfy() throws Exception {
    //     UserDto userDto = new UserDto("youtube", 20, "male", "abcdefg@gmail.com", "176282829");
    //
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     String userDtoJson = objectMapper.writeValueAsString(userDto);
    //
    //     mockMvc.perform(post("/user/register")
    //             .content(userDtoJson)
    //             .contentType(MediaType.APPLICATION_JSON)
    //     ).andExpect(status().isBadRequest());
    // }
    //
    // @Test
    // void get_all_users() throws Exception {
    //     // ObjectMapper objectMapper = new ObjectMapper();
    //     // String ansStr = "{\"name\":\"youtube\",\"gender\":\"male\",\"age\":20,\"email\":\"abcdefg@gmail.com\",\"phone\":\"17628282910\"}";
    //     // String newStr = objectMapper.writeValueAsString(ansStr);
    //     // String jStr = objectMapper.writeValueAsString(ans);
    //     // JSONObject jsonObject = new JSONObject();
    //     // jsonObject.put("name", "youtube");
    //     // jsonObject.put("gender", "male");
    //     // jsonObject.put("age", 20);
    //     // jsonObject.put("email", "abcdefg@gmail.com");
    //     // jsonObject.put("phone", "17628282910");
    //     //
    //     // JSONArray jsonArray = new JSONArray();
    //     // jsonArray.put("name:youtube");
    //     // jsonArray.put("gender:male");
    //     // jsonArray.put("age:20");
    //     // jsonArray.put("email:abcdefg@gmail.com");
    //     // jsonArray.put("phone:17628282910");
    //
    //     mockMvc.perform(get("/get/users"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$[0]",is(hasKey("user_name"))));
    // }
}