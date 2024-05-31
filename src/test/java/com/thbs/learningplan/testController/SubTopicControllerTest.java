package com.thbs.learningplan.testController;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thbs.learningplan.controller.SubTopicController;
import com.thbs.learningplan.model.SubTopic;
import com.thbs.learningplan.model.Topic;
import com.thbs.learningplan.service.SubTopicService;
import com.thbs.learningplan.service.TopicService;

@WebMvcTest(SubTopicController.class)
@ExtendWith(MockitoExtension.class)
class SubTopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicService topicService;

    @Mock
    private SubTopicService subTopicService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveSubTopic_SuccessfulSaving() throws Exception {
        SubTopic subTopic = new SubTopic();
        subTopic.setSubTopicName("Introduction to Java");
        when(subTopicService.saveSubTopic(any(SubTopic.class))).thenReturn(subTopic);

        mockMvc.perform(post("/sub-topic")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subTopic)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subTopicName").value("Introduction to Java"));
    }

    @Test
    void testGetSubTopicsByTopic_SuccessfulRetrieval() throws Exception {
        Topic topic = new Topic(1L, "Java Basics", 1L,null);
        List<SubTopic> subTopics = Arrays.asList(
                new SubTopic(1L, "Introduction to Java", topic),
                new SubTopic(2L, "Advanced Java", topic));

        when(topicService.getTopicById(1L)).thenReturn(topic);
        when(subTopicService.getSubTopicsByTopic(topic)).thenReturn(subTopics);

        mockMvc.perform(get("/sub-topic/topic/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(subTopics.size()));
    }
}
