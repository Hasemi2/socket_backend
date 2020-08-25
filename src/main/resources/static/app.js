var stompClient = null;

//Socket 연결 *********************************************************************
function connect() {
    //여기서 인증정보 넣고여기서
    var socket = new SockJS('/test');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    console.log("onConnected ================================= ");
    ChatRoomAll(); //모든 채팅방 리스트 호출

}

function onError(error) {
    console.log("onError===================>" , error)
}

//***********************************************************************************

//function create() {
// var data ={
//            roomName: $("#roomName").val()
//        };
//
//            $.ajax({
//                type:'POST',
//                url : '/create',
//                dataType: 'json',
//                contentType : 'application/json; charset=utf-8',
//                data: JSON.stringify(data)
//            }).done(function(res){
//                alert("등록 완료");
//                window.location.href  = '/';
//            }).fail(function(e){
//                alert(JSON.stringify(e));
//            });
//}

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
   stompClient.subscribe('/topic/roomLists', onChatRoomAllReceived);
    // Tell your username to the server
//    var data = {
//        type : 'chatRoomAll',
//        sender : 'semi'
//    }

    var data = {
        'name'      : 'chatRoomAll',
        'message'   : 'semi'
    }
    //todo :요기서 data를 넘겨서 컨트롤러에서 받는게 안됩니다. 왜죠?
    //(void) send(destination, headers = {} , body = '')
    stompClient.send("/app/roomLists",
        {},
        JSON.stringify(data)
        );
}

function onChatRoomAllReceived(payload){
    console.log("onChatRoomAllReceived=======================");
    //console.log("payload ===== " , payload);
    var rooms = JSON.parse(payload.body);
    rooms.forEach(function(el){
        $("#chatrooms").append("<tr><td>" + el.roomName + "</td></tr>");
    });
}

//현재 접속자 보기(유저 인증값 불필요)
function ChatUserAll(){
}

//참여한 채팅방 보기(user 인증값 필요)
function ChatRoomMine(){
}


$(function () {
      connect();
});