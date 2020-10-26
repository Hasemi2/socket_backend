package com.chat.realtime.web;

import com.chat.realtime.web.dto.UserSaveRequestDto;
import com.chat.realtime.web.dto.UserSaveResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    private final String SUBSCRIBE_USER_LIST_ENDPOINT = "/topic/user";
    private final String SEND_USER_LIST_ENDPOINT = "/user/userList/get";


    @Test
    public void loginTest() throws Exception {
        String id = "hasemi";
        String password = "1234";

        UserSaveRequestDto requestDto = UserSaveRequestDto.builder().userId(id).password(password).build();

        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void userListTest() throws Exception {
        BlockingQueue<UserSaveResponseDto> blockingQueue = new ArrayBlockingQueue(1);
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSession stompSession = stompClient.connect("http://localhost:" + port + "/test?connect_token=SUPER_TOKEN", new StompSessionHandlerAdapter() {}).get(1, TimeUnit.SECONDS);

        stompSession.subscribe(SUBSCRIBE_USER_LIST_ENDPOINT, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return UserSaveResponseDto.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                System.out.println("handleFrame ============ " + o.toString());
                blockingQueue.add((UserSaveResponseDto) o);
            }
        });

        stompSession.send(SEND_USER_LIST_ENDPOINT, null);
        Assertions.assertNotNull(blockingQueue.poll(1, TimeUnit.SECONDS));
    }

    private List<Transport> createTransportClient() {
        return Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()));
    }
}
