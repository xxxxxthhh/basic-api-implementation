package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.UserDto;
import org.apache.catalina.User;
import org.hamcrest.Matchers;
import org.json.JSONObject;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.ResultMatcher.*;

import java.util.ArrayList;
import java.util.List;

import static com.sun.tools.doclint.Entity.not;
import static org.assertj.core.api.AssertionsForClassTypes.not;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.JsonPathResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RsControllerTest {

  @Autowired
  MockMvc mockMvc;

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
  }

  @Test
  void should_return_all_rs_event() throws Exception {
    mockMvc.perform(get("/rs/sublist"))
        .andExpect(status().isOk())
        // .andExpect(jsonPath("$[0]",not(hasKey("userInfo"))))

        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
        .andExpect(jsonPath("$[0].keyword", is("无分类")))
        .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
        .andExpect(jsonPath("$[1].keyword", is("无分类")))
        .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
        .andExpect(jsonPath("$[2].keyword", is("无分类")));
    // .andExpect(jsonPath("$[0]", not(hasKey("userInfo"))));
  }

  @Test
  void should_get_one_rs_event() throws Exception {
    mockMvc.perform(get("/rs/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.eventName", is("第一条事件")))
        .andExpect(jsonPath("$.keyword", is("无分类")));

    mockMvc.perform(get("/rs/2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.eventName", is("第二条事件")))
        .andExpect(jsonPath("$.keyword", is("无分类")));

    mockMvc.perform(get("/rs/3"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.eventName", is("第三条事件")))
        .andExpect(jsonPath("$.keyword", is("无分类")));
  }

  @Test
  void should_get_rs_event_by_range() throws Exception {
    mockMvc.perform(get("/rs/sublist?start=1&end=3"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
        .andExpect(jsonPath("$[0].keyword", is("无分类")))
        .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
        .andExpect(jsonPath("$[1].keyword", is("无分类")))
        .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
        .andExpect(jsonPath("$[2].keyword", is("无分类")));
  }

  @Test
  void add_one_rs_event() throws Exception {
    mockMvc.perform(get("/rs/sublist"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)));

    UserDto userDto = new UserDto("google", 20, "male", "abcdefg@gmail.com", "17628282910");
    RsEvent rsEvent = new RsEvent("第四条事件", "经济", userDto);
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(rsEvent);
    String user = "{name=google, gender=male, age=20, email=abcdefg@gmail.com, phone=17628282910}";

    mockMvc.perform(post("/rs/event").content(json)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(header().string("index", "3"));

    mockMvc.perform(get("/rs/sublist"))
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
    mockMvc.perform(get("/rs/sublist"))
        .andExpect(jsonPath("$[0]", Matchers.not(hasProperty("user"))));
  }

  @Test
  public void given_out_of_bound_start_and_end_then_handle_exception() throws Exception {
    int start = -1;
    int end = 9;
    String url = "/rs/sublist?start=" + start + "&end=" + end;
    mockMvc.perform(get(url))
        .andExpect(jsonPath("$.error", is("invalid request param")))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void given_out_of_band_index_handle_exception() throws Exception {
    int index = 100;
    String url = "/rs/" + index;
    mockMvc.perform(get(url)).andExpect(jsonPath("$.error", is("invalid index")))
        .andExpect(status().isBadRequest());
  }
}