package com.chat.realtime.web;

import com.chat.realtime.domain.chat.ChatRepository;
import com.chat.realtime.domain.room.ChatRoom;
import com.chat.realtime.domain.room.ChatRoomRepository;
import com.chat.realtime.domain.user.User;
import com.chat.realtime.domain.user.UserRepository;
import com.chat.realtime.web.dto.ChatSaveResponseDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRepository chatRepository;

    private CompletableFuture<ChatSaveResponseDto> completableFuture;

    private final String SUBSCRIBE_CHAT_SAVE_ENDPOINT = "/topic/room/1";

    private final String SEND_CHAT_SAVE_ENDPOINT = "/chat/1/message/send";

    private UUID testUserId;

//    @Before
//    public void setup() {
//        completableFuture = new CompletableFuture<>();
//
//        User user = User.builder().userId("chattest").password("test1234").userToken("SUPER_TOKEN").build();
//        ChatRoom room = ChatRoom.builder().roomId((long)1).build();
//
//        userRepository.save(user);
//        chatRoomRepository.save(room);
//
//        testUserId = user.getUserPk();
//        System.out.println("setup =============== " + testUserId);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        System.out.println("After =============== " + testUserId);
//        userRepository.deleteAll();
//        chatRoomRepository.deleteAll();
//        chatRepository.deleteAll();
//    }


    @Test
    public void chatSendTest() throws Exception {
//        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//        StompSession stompSession = stompClient.connect("http://localhost:" + port + "/test?connect_token=SUPER_TOKEN", new StompSessionHandlerAdapter() {
//        }).get(1, TimeUnit.SECONDS);
//
//        stompSession.subscribe(SUBSCRIBE_CHAT_SAVE_ENDPOINT, new ChatSaveStompFrameHandler());
//
//        StompHeaders headers = new StompHeaders();
//        headers.set("Authorization" , "SUPER_TOKEN");
//        headers.setDestination(SEND_CHAT_SAVE_ENDPOINT);
//
//        String message = "hello";
//
//        stompSession.send(headers, message);
//
//        ChatSaveResponseDto responseDto = completableFuture.get();
//        System.out.println("responseDto =============== " + responseDto.toString());
//        assertNotNull(responseDto);
    }

    private List<Transport> createTransportClient() {
        return Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()));
    }

    private class ChatSaveStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return ChatSaveResponseDto.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFuture.complete((ChatSaveResponseDto) o);
        }
    }
}
