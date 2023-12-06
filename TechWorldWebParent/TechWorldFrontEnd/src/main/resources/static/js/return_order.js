var returnModal;
var modalTitle;
var fieldNote;
var orderId;
var divReason;
var divMessage;
var firstButton;
var secondButton;

$(document).ready(function () {
    returnModal = $("#returnOrderModal");
    modalTitle = $("#returnOrderModalTitle");
    fieldNote = $("#returnNote");
    divReason = $("#divReason");
    divMessage = $("#divMessage");
    firstButton = $("#firstButton");
    secondButton = $("#secondButton");
});

$(document).on("click", ".linkReturnOrder", function (e) {
    e.preventDefault();
    showReturnModalDialog($(this));
});

function showReturnModalDialog(link){
    divMessage.hide();
    divReason.show();
    firstButton.show();
    secondButton.text("Đóng");
    fieldNote.val("");

    orderId = link.attr("orderId");
    returnModal.modal("show");
    modalTitle.text("Trả Đơn Hàng #"+orderId);
}

function showMessageModal(message){
    divReason.hide();
    firstButton.hide();
    secondButton.text("Đóng");
    divMessage.text(message);

    divMessage.show();
}

function submitReturnForm(){
    reason = $("input[name='returnReason']:checked").val();
    note = fieldNote.val();

    sendReturnOrderRequest(reason,note);
    return false;
}

function sendReturnOrderRequest(reason,note){
    requestURL = contextPath + "orders/return";
    requestBody = {orderId: orderId, reason: reason, note: note};

    $.ajax({
        type: "POST",
        url: requestURL,
        beforeSend: function (xhr){
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        },
        data: JSON.stringify(requestBody),
        contentType: 'application/json'
    }).done(function(returnResponse){
        console.log(returnResponse);
        showMessageModal("Yêu cầu hoàn trả đã dược gửi đi, vui lòng chờ phản hồi.");
        updateStatusTextAndHideReturnButton(orderId);
    }).fail(function (err) {
        console.log(err);
        showMessageModal(err.responseText);
    });
}

function updateStatusTextAndHideReturnButton(orderId){
    $(".textOrderStatus"+orderId).each(function (index) {
        $(this).text("RETURN_REQUESTED");
    });

    $(".linkReturn"+orderId).each(function (index) {
        $(this).text("Done");
    });
}