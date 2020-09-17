var stompClient = null;

//Socket 연결 *********************************************************************
function connect() {
    //여기서 인증정보 넣고여기서
    var socket = new SockJS('/test?Authorization=SUPER_TOKEN');
    stompClient = Stomp.over(socket);
    stompClient.connect({} , onConnected, onError)

}

function onConnected(payload) {
    console.log("onConnected " , payload);
    ChatRoomAll(); //모든 채팅방 리스트 호출

}

function onError(error) {
    console.log("onError===================>" , error)
}

//***********************************************************************************

//function disconnect() {
//    if (stompClient !== null) {
//        stompClient.disconnect();
//    }
//    setConnected(false);
//    console.log("Disconnected");
//}

//todo 0825  구현 필요
//user 인증 값 불필요1
function ChatRoomAll(){
   console.log("ChatRoomAll ============================== ")
   //구독
   stompClient.subscribe('/topic/user', onChatRoomAllReceived);
    // Tell your username to the server
    //todo :요기서 data를 넘겨서 컨트롤러에서 받는게 안됩니다. 왜죠?
    //(void) send(destination, headers = {} , body = '')
    stompClient.send("/user/sessionId/get" ,
        {"Authorization" : "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDAwMDIzMTYsImlkIjoiaGFzZW1pIn0.XII2Z6X96oUma1Uc0uyGp68OuZT840U1ny3sT0f_PCE" } ,
       {});

    stompClient.send("/user/userList/get" ,
           {"Authorization" : "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDAwMDIzMTYsImlkIjoiaGFzZW1pIn0.XII2Z6X96oUma1Uc0uyGp68OuZT840U1ny3sT0f_PCE" } ,
       {});
}

function onChatRoomAllReceived(payload){
    console.log("onChatRoomAllReceived=======================");
    var rooms = JSON.parse(payload.body);
    console.log(rooms);
}



$(function () {
        $("form").on('submit' , function(e) {
            e.preventDefault();
        });
        connect();
      // $( "#create" ).click(function() { create(); });

});

