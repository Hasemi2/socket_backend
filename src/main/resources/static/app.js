var stompClient = null;
var token = null;

//Socket 연결 *********************************************************************
function login() {

        var data = {
            userId : "hasemi",
            pw : "1234"
        }

            $.ajax({
                type:'POST',
                url : '/login',
                dataType: 'json',
                contentType : 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function(res){
                console.log("login test => " , res);
                connect(res);
            }).fail(function(e){
                console.log(JSON.stringify(e));
            });
}


function connect(res) {
    //여기서 인증정보 넣고여기서
    var socket = new SockJS('/test?connect_token=' + res.connectToken);
    stompClient = Stomp.over(socket);
    token = res.authToken;
    stompClient.connect({"Authorization" : res.authToken} , onConnected, onError)

}

function onConnected(payload) {
    console.log("onConnected " , payload);

    ChatRoomAll(token); //모든 채팅방 리스트 호출

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
function ChatRoomAll(token){
   console.log("ChatRoomAll ============================== " , token)
   //구독
   stompClient.subscribe('/topic/room', onChatRoomAllReceived);
    stompClient.send("/room/roomList/get" ,
           {"Authorization" : token } ,
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
        login();
      // $( "#create" ).click(function() { create(); });

});

