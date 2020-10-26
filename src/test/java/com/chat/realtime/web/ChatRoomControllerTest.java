package com.chat.realtime.web;

import com.chat.realtime.web.dto.ChatRoomListResponseDto;
import com.chat.realtime.web.dto.UserSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ChatRoomControllerTest {

    @LocalServerPort
    private int port;

    private final String SUBSCRIBE_ROOM_URL = "/topic/room";

    private final String SEND_ROOM_LIST_URL = "/room/roomList/get";

    private final String SEND_ROOM_ADD_URL = "/room/add";

    private final String SUBSCRIBE_ENTER_ROOM_URL = "/topic/room/1";

    private final String SEND_ENTER_ROOM_URL = "/room/enter/1";

    private final String SEND_LEAVE_ROOM_URL = "/room/leave";

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void roomListsTest() throws Exception {
//        String id = "hasemi";
//        String password = "1234";
//
//        UserSaveRequestDto requestDto = UserSaveRequestDto.builder().userId(id).password(password).build();
//
//        MvcResult result = mvc.perform(post("/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(requestDto)))
//                .andDo(print())
//                .andReturn();
//
//        String strResult = result.getResponse().getContentAsString();
//        Map<String, String> resultMap = mapper.readValue(strResult, Map.class);
//
//        System.out.println("resultMap ===== " + resultMap.toString());
//
//        String authToken = resultMap.get("authToken");
//        String connectToken = resultMap.get("connectToken");
//
//        BlockingQueue<ChatRoomListResponseDto> blockingQueue = new ArrayBlockingQueue(1);
//        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//
//        StompHeaders nativeHeaders = new StompHeaders();
//        nativeHeaders.set("Authorization", authToken);
//
//        StompSession stompSession = stompClient.connect("http://localhost:" + port + "/test?connect_token=" + connectToken , new StompSessionHandlerAdapter() {
//        }).get(1, TimeUnit.SECONDS);
//
//        if (stompSession.isConnected()) {
//            System.out.println("stompSession.isConnected() ============================= ");
//            stompSession.subscribe(SUBSCRIBE_ROOM_URL, new StompFrameHandler() {
//                @Override
//                public Type getPayloadType(StompHeaders stompHeaders) {
//                    System.out.println("getPayloadType ============================= " + stompHeaders.toString());
//                    return ChatRoomListResponseDto.class;
//                }
//
//                @Override
//                public void handleFrame(StompHeaders stompHeaders, Object o) {
//                    System.out.println("handleFrame" + o.toString());
//                    blockingQueue.add((ChatRoomListResponseDto) o);
//                }
//
//
//            });
//
//            StompHeaders headers = new StompHeaders();
//            headers.set("Authorization", authToken);
//            headers.setDestination(SEND_ROOM_LIST_URL);
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            stompSession.send(headers, null);
//            System.out.println("stompSession.isConnected() ============================= " + stompSession.isConnected());
//
//        } else {
//            System.out.println("stompSession.isClosed() ============================= ");
//        }


        // Assertions.assertNotNull(blockingQueue.poll(1,TimeUnit.SECONDS));

    }

    //    @Test
//    public void addTest() throws Exception {
//        BlockingQueue<ChatRoomSaveResponseDto> blockingQueue = new ArrayBlockingQueue(1);
//        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//        StompSession stompSession = stompClient.connect("http://localhost:" + port + "/test?connect_token=SUPER_TOKEN", new StompSessionHandlerAdapter() {
//        }).get(1, TimeUnit.SECONDS);
//
//        stompSession.subscribe(SUBSCRIBE_ROOM_URL, new StompFrameHandler() {
//            @Override
//            public Type getPayloadType(StompHeaders stompHeaders) {
//                System.out.println("getPayloadType ============================= " + stompHeaders.toString());
//                return ChatRoomSaveResponseDto.class;
//            }
//
//            @Override
//            public void handleFrame(StompHeaders stompHeaders, Object o) {
//                System.out.println("handleFrame" + o.toString());
//                blockingQueue.add((ChatRoomSaveResponseDto) o);
//            }
//
//
//        });
//
//        StompHeaders headers = new StompHeaders();
//        headers.set("Authorization", "SUPER_TOKEN");
//        headers.setDestination(SEND_ROOM_ADD_URL);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        ChatRoomSaveRequestDto requestDto
//                = ChatRoomSaveRequestDto.builder().receiver("hasemi").build();
//        stompSession.send(headers, requestDto);
//
//        // Assertions.assertNotNull(blockingQueue.poll(1,TimeUnit.SECONDS));
//
//    }
    private List<Transport> createTransportClient() {
        return Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()));
    }
}
