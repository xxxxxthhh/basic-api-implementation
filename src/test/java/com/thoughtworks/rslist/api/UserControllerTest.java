package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.UserDto;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONString;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.constraints.Size;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void should_register_user() throws Exception {
        UserDto userDto = new UserDto("Alibaba", "female", 20, "alibaba@twuc.com", "17628282910");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user/register")
                .content(userDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
                .andExpect(header().string("index", "1"));
    }

    @Test
    void can_not_register_if_name_is_empty() throws Exception {
        UserDto userDto = new UserDto(null, "female", 20, "alibaba@twuc.com", "17628282910");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user/register")
                .content(userDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void can_not_register_if_name_longer_tham_8() throws Exception {
        UserDto userDto = new UserDto("youtubeBLogger", "female", 20, "alibaba@twuc.com", "17628282910");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user/register")
                .content(userDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void can_not_register_if_gender_empty() throws Exception {
        UserDto userDto = new UserDto("youtubeBLogger", "", 20, "alibaba@twuc.com", "17628282910");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user/register")
                .content(userDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void can_not_register_if_age_empty() throws Exception {
        UserDto userDto = new UserDto("youtube", "male", null, "alibaba@twuc.com", "17628282910");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user/register")
                .content(userDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void can_not_register_if_age_less_than_18() throws Exception {
        UserDto userDto = new UserDto("youtube", "male", 17, "alibaba@twuc.com", "17628282910");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user/register")
                .content(userDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void can_not_register_if_age_more_than_100() throws Exception {
        UserDto userDto = new UserDto("youtube", "male", 101, "alibaba@twuc.com", "17628282910");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user/register")
                .content(userDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void can_not_register_if_mail_empty() throws Exception {
        UserDto userDto = new UserDto("youtube", "male", 20, "", "17628282910");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user/register")
                .content(userDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void can_not_register_if_mail_not_satisfy() throws Exception {
        UserDto userDto = new UserDto("youtube", "male", 20, "abcdefg@", "17628282910");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user/register")
                .content(userDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void can_not_register_if_phone_empty() throws Exception {
        UserDto userDto = new UserDto("youtube", "male", 20, "xth@qq.com", "");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user/register")
                .content(userDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void can_not_register_if_phone_not_satisfy() throws Exception {
        UserDto userDto = new UserDto("youtube", "male", 20, "abcdefg@gmail.com", "176282829");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user/register")
                .content(userDtoJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void get_all_users() throws Exception {
        // ObjectMapper objectMapper = new ObjectMapper();
        // String ansStr = "{\"name\":\"youtube\",\"gender\":\"male\",\"age\":20,\"email\":\"abcdefg@gmail.com\",\"phone\":\"17628282910\"}";
        // String newStr = objectMapper.writeValueAsString(ansStr);
        // String jStr = objectMapper.writeValueAsString(ans);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "youtube");
        jsonObject.put("gender", "male");
        jsonObject.put("age", 20);
        jsonObject.put("email", "abcdefg@gmail.com");
        jsonObject.put("phone", "17628282910");

        JSONArray jsonArray = new JSONArray();
        jsonArray.put("name:youtube");
        jsonArray.put("gender:male");
        jsonArray.put("age:20");
        jsonArray.put("email:abcdefg@gmail.com");
        jsonArray.put("phone:17628282910");

        mockMvc.perform(get("/get/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonObject.toJSONString()));
    }
}