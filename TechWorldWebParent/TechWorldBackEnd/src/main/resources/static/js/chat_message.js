let stompClient;
const email_user_name = $("#email-user").text();
let toUser = 0;
let emailTo = '';
let isChatGroup = true;

window.onload = function() {
    alert('hi');
    let socket = new SockJS(moduleURL + 'ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/messages/group', function(response) {
            let data = JSON.parse(response.body);
            let messageGroupTemplateHTML = "";
            if (data['email'] === email_user_name && isChatGroup) {
                messageGroupTemplateHTML +=
                    '<div class="chat-content-rightside">' +
                    '    <div class="d-flex ms-auto">' +
                    '        <div class="flex-grow-1 me-2">' +
                    '            <p class="mb-0 chat-time text-end">you, ' + data['createdTime'] + '</p>' +
                    '            <p class="chat-right-msg">' + data['message'] + '</p>' +
                    '        </div>' +
                    '    </div>' +
                    '</div>';

                $("#group-message-chat").text(data['message'].slice(0, 15) + "...");
                $("#group-message-chat").removeClass("text-warning");
                $("#group-message-chat").addClass("text-success");

            } else if(isChatGroup && data['email'] !== null) {
                messageGroupTemplateHTML +=
                    '<div class="chat-content-leftside">' +
                    '    <div class="d-flex">' +
                    '        <img src="' + data['image'] + '" width="48" height="48" class="rounded-circle" alt="" />' +
                    '        <div class="flex-grow-1 ms-2">' +
                    '            <p class="mb-0 chat-time">' + data['fullName'] + ', ' + data['createdTime'] + '</p>' +
                    '            <p class="chat-left-msg">' + data['message'] + '</p>' +
                    '        </div>' +
                    '    </div>' +
                    '</div>';
                $("#group-message-chat").text(data['message'].slice(0, 15) + "...");
                $("#group-message-chat").removeClass("text-success");
                $("#group-message-chat").addClass("text-warning");
            }

            $("#group-chat-time").text(data['timeFormat']);
            $('#formMessageBody').append(messageGroupTemplateHTML);
            let chatMessages = document.getElementById('formMessageBody');
            chatMessages.scrollTop = chatMessages.scrollHeight;
        });

        stompClient.subscribe('/topic/messages/chat', function(response) {
            let data = JSON.parse(response.body);
            let messageGroupTemplateHTML = "";
            let messageGroupTemplateHTML01 = "";
            if (data['emailFrom'] === email_user_name) {
                messageGroupTemplateHTML +=
                    '<div class="chat-content-rightside">' +
                    '    <div class="d-flex ms-auto">' +
                    '        <div class="flex-grow-1 me-2">' +
                    '            <p class="mb-0 chat-time text-end">you, ' + data['date'] + '</p>' +
                    '            <p class="chat-right-msg">' + data['message'] + '</p>' +
                    '        </div>' +
                    '    </div>' +
                    '</div>';

                /*<![CDATA[*/
                var messageFormat = data.message.slice(0,15) + "...";
                messageGroupTemplateHTML01 = `<a href="javascript:;" class="list-group-item user-item" uId="${data.userIdTo}" uName="${data.fullName}" uEmail="${data.emailTo}" data-avatar="${data.avatar}"  onclick="formMessageLauch(this,'user')">
                                    <div class="d-flex">
                                        <div class="chat-user-online">
                                            <img src="${data.avartar}" width="42" height="42" class="rounded-circle" alt="" />
                                        </div>
                                        <div class="flex-grow-1 ms-2">
                                            <h6 class="mb-0 chat-title">${data.fullName}</h6>
                                            <p class="mb-0 chat-msg text-success">${messageFormat}</p>
                                        </div>
                                        <div class="chat-time">${data.date1}</div>
                                    </div>
                                </a>`;
                /*]]>*/
                $('#listContainer').find('.user-item[uId="' + ${data.userIdTo} + '"]').remove();

                $('#listContainer').prepend(messageGroupTemplateHTML01);

            } else if(data['emailTo'] === email_user_name && emailTo === data['emailFrom']){
                messageGroupTemplateHTML +=
                    '<div class="chat-content-leftside">' +
                    '    <div class="d-flex">' +
                    '        <img src="' + data['avatarTo'] + '" width="48" height="48" class="rounded-circle" alt="" />' +
                    '        <div class="flex-grow-1 ms-2">' +
                    '            <p class="mb-0 chat-time">' + data['fullNameTo'] + ', ' + data['date'] + '</p>' +
                    '            <p class="chat-left-msg">' + data['message'] + '</p>' +
                    '        </div>' +
                    '    </div>' +
                    '</div>';

                /*<![CDATA[*/
                var messageFormat = data.message.slice(0,15) + "...";
                messageGroupTemplateHTML01 = `<a href="javascript:;" class="list-group-item user-item" uId="${data.userIdFrom}" uName="${data.fullNameTo}" uEmail="${data.emailFrom}" data-avatar="${data.avatarTo}"  onclick="formMessageLauch(this,'user')">
                                    <div class="d-flex">
                                        <div class="chat-user-online">
                                            <img src="${data.avatarTo}" width="42" height="42" class="rounded-circle" alt="" />
                                        </div>
                                        <div class="flex-grow-1 ms-2">
                                            <h6 class="mb-0 chat-title">${data.fullNameTo}</h6>
                                            <p class="mb-0 chat-msg text-warning">${messageFormat}</p>
                                        </div>
                                        <div class="chat-time">${data.date1}</div>
                                    </div>
                                </a>`;
                /*]]>*/
                $('#listContainer').find('.user-item[uId="' + ${data.userIdFrom} + '"]').remove();

                $('#listContainer').prepend(messageGroupTemplateHTML01);
            }

            $('#formMessageBody').append(messageGroupTemplateHTML);
            let chatMessages = document.getElementById('formMessageBody');
            chatMessages.scrollTop = chatMessages.scrollHeight;
        });

    }, function(error) {
        console.error('Error connecting to WebSocket: ' + error);
    });

};

function sendMessage(type, to) {
    let message = $('#message-to-send').val();

    if (message != null && message !== '') {
        let date = new Date(); // Current date and time

        // Format the date to yyyy-MM-dd hh:mm:ss
        let formattedDate = date.toISOString().slice(0, 19).replace('T', ' ');

        if(type === 'group'){
            stompClient.send("/app/chat/group", {}, JSON.stringify({
                email: email_user_name,
                message: message,
                createdTime: formattedDate
            }));
        }else{
            stompClient.send("/app/chat/" + to, {}, JSON.stringify({
                email: email_user_name,
                message: message,
                createdTime: formattedDate
            }));
        }

        $('#message-to-send').val('');
    } else {
        // Handle empty message
    }
}


function formMessageLauch(chatItem, type) {
    alert('hi');
    let allChatItems = document.querySelectorAll('.list-group-item');

    allChatItems.forEach(item => {
        item.classList.remove('active');
    });

    // Add the "active" class to the clicked chat item
    chatItem.classList.add('active');

    if(type === 'group'){
        isChatGroup = true;
        $('#logo-chat').attr("src", pathImage);
        $('#username-chat').text('Group_General');
        $("#formMessageBody").empty();
        $("#message-chat").empty();
        $.get(moduleURL + "listmessage/group", function (response) {
            let messagesGroup = response;
            let messageGroupTemplateHTML = "";
            for (let i = 0; i < messagesGroup.length; i++) {
                if (messagesGroup[i]['email'] === email_user_name) {
                    messageGroupTemplateHTML +=
                        '<div class="chat-content-rightside">' +
                        '    <div class="d-flex ms-auto">' +
                        '        <div class="flex-grow-1 me-2">' +
                        '            <p class="mb-0 chat-time text-end">you, ' + messagesGroup[i]['createdTime'] + '</p>' +
                        '            <p class="chat-right-msg">' + messagesGroup[i]['message'] + '</p>' +
                        '        </div>' +
                        '    </div>' +
                        '</div>';

                } else {
                    messageGroupTemplateHTML +=
                        '<div class="chat-content-leftside">' +
                        '    <div class="d-flex">' +
                        '        <img src="' + messagesGroup[i]['image'] + '" width="48" height="48" class="rounded-circle" alt="" />' +
                        '        <div class="flex-grow-1 ms-2">' +
                        '            <p class="mb-0 chat-time">' + messagesGroup[i]['fullName'] + ', ' + messagesGroup[i]['createdTime'] + '</p>' +
                        '            <p class="chat-left-msg">' + messagesGroup[i]['message'] + '</p>' +
                        '        </div>' +
                        '    </div>' +
                        '</div>';
                }
            }

            $('#formMessageBody').append(messageGroupTemplateHTML);
            let chatMessages = document.getElementById('formMessageBody');
            chatMessages.scrollTop = chatMessages.scrollHeight;
        });
    }else if(type === 'user'){
        isChatGroup = false;
        let idToUser = $(chatItem).attr("uId");
        let fullName = $(chatItem).attr("uName");
        let avatar = $(chatItem).data("avatar");
        toUser = idToUser;
        emailTo = $(chatItem).attr("uEmail");

        $("#formMessageBody").empty();
        $("#message-chat").empty();

        $('#logo-chat').attr("src", avatar);
        $('#username-chat').text(fullName);

        $.get(moduleURL + "listmessage/"+email_user_name+"/"+idToUser, function (response) {
            let messages = response;
            console.log(messages)
            let messageUserTemplateHTML = "";
            for (let i = 0; i < messages.length; i++) {
                if (messages[i]['emailFrom'] === email_user_name) {
                    messageUserTemplateHTML +=
                        '<div class="chat-content-rightside">' +
                        '    <div class="d-flex ms-auto">' +
                        '        <div class="flex-grow-1 me-2">' +
                        '            <p class="mb-0 chat-time text-end">you, ' + messages[i]['date'] + '</p>' +
                        '            <p class="chat-right-msg">' + messages[i]['message'] + '</p>' +
                        '        </div>' +
                        '    </div>' +
                        '</div>';

                } else {
                    messageUserTemplateHTML +=
                        '<div class="chat-content-leftside">' +
                        '    <div class="d-flex">' +
                        '        <img src="' + messages[i]['avatarTo'] + '" width="48" height="48" class="rounded-circle" alt="" />' +
                        '        <div class="flex-grow-1 ms-2">' +
                        '            <p class="mb-0 chat-time">' + messages[i]['fullNameTo'] + ', ' + messages[i]['date'] + '</p>' +
                        '            <p class="chat-left-msg">' + messages[i]['message'] + '</p>' +
                        '        </div>' +
                        '    </div>' +
                        '</div>';
                }

            }
            $('#formMessageBody').append(messageUserTemplateHTML);
            let chatMessages = document.getElementById('formMessageBody');
            chatMessages.scrollTop = chatMessages.scrollHeight;
        });
    }

    let chatMessageHTML = `<div class="flex-grow-1 pe-2">
                        <div class="input-group">\t<span class="input-group-text"><i class='bx bx-smile'></i></span>
                            <input type="text" id="message-to-send" name="message-to-send" class="form-control" placeholder="Type your message">
                        </div>
                    </div>
                    <div class="chat-footer-menu">
                        <a href="javascript:;"><i id="sendBtn" onclick="sendMessage('${type}','${toUser}')" class='bx bx-send'></i></a>
                    </div>`;
    $("#message-chat").append(chatMessageHTML);
}