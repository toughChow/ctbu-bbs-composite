// 连接endpoint名称为/endpointP2P 的协议点
var sock = new SockJS('/endpointP2P');
var stomp = Stomp.over(sock);
// 连接
stomp.connect({}, function (frame) {
    console.log('Connected:' + frame);
    stomp.subscribe('/user/queue/notifications', function (message) {
        if (message.body == 'yes') {
            layer.msg("发送成功",{icon: 1});
            location.href = "/admin/message/outbox";
        } else if (message.body == 'no') {
            layer.msg("发送失败",{icon: 2});
        } else if (isNumber(message.body)) {
            $('a[data-action="operational"][data-id="'+message.body+'"]').addClass('red-point');
        } else {
            $('.popover-show').popover('show');
            setTimeout(function () {
                $('.popover-show').popover('destroy');
            }, 10000);
        }

    });
});
function isNumber(value) {         //验证是否为数字
    var patrn = /^(-)?\d+(\.\d+)?$/;
    if (patrn.exec(value) == null || value == "") {
        return false;
    } else {
        return true;
    }
}

$('#p2p-form').submit(function (e) {
    e.preventDefault();
    var content = $('#p2p-content').val();
    var receiverName = $('#p2p-username').val();
    var selectGroupBox = $('#selectGroupBox').val();
    // 发送
    stomp.send("/chat", {}, JSON.stringify({'content': content, 'receiver': receiverName, 'groupId': selectGroupBox}));
});
function submitMsg(){
    var content = $('#p2p-content').val();
    var receiverName = $('#p2p-username').val();
    var selectGroupBox = $('#selectGroupBox').val();
    // 发送
    stomp.send("/chat", {}, JSON.stringify({'content': content, 'receiver': receiverName, 'groupId': selectGroupBox}));
}



// ----------- 分隔符 ------------

var stompClient = null;

function connect() {
    var socket = new SockJS('/endpointBoard');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/notifications', function (message) {
            $('.popover-show').popover('show');
            setTimeout(function () {
                $('.popover-show').popover('destroy');
            }, 10000);
        });
    });
}

function sendName() {
    stompClient.send("/board", {}, JSON.stringify({'content': $("#p2m-content").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    connect();
    $("#p2m-form").on('submit', function (e) {
        e.preventDefault();
        sendName();
    });
});