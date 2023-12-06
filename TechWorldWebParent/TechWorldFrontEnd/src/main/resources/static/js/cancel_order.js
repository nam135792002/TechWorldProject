var cancelModal;
var modalTitle;
var fieldNote01;
var orderId01;
var divReason01;
var divMessage01;
var firstButton01;
var secondButton01;

$(document).ready(function () {
    cancelModal = $("#cancelOrderModal");
    modalTitle = $("#cancelOrderModalTitle");
    fieldNote01 = $("#cancelNote01");
    divReason01 = $("#divCancelReason01");
    divMessage01 = $("#divCancelMessage01");
    firstButton01 = $("#firstCanCelButton01");
    secondButton01 = $("#secondCancelButton02");
});

$(document).on("click", ".linkCancelModal", function (e) {
    e.preventDefault();
    showCancelModalDialog($(this));
});

function showCancelModalDialog(link){
    divMessage01.hide();
    divReason01.show();
    firstButton01.show();
    secondButton01.text("Đóng");
    fieldNote01.val("");

    orderId01 = link.attr("orderId");
    cancelModal.modal("show");
    modalTitle.text("Hủy Đơn Hàng #"+orderId);
}

function showCancelMessageModal(message){
    divReason01.hide();
    firstButton01.hide();
    secondButton01.text("Đóng");
    divMessage01.text(message);

    divMessage01.show();
}

function submitCancelForm(){
    note = fieldNote01.val();

    sendCancelOrderRequest(note);
    return false;
}

function sendCancelOrderRequest(note){
    requestURL = contextPath + "orders/cancel";
    requestBody = {orderId: orderId01, note: note};

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
        showCancelMessageModal("Yêu cầu hủy đơn hàng đã được xử lý thành công.");
        updateStatusTextAndHideReturnButtonCancel(orderId01);
    }).fail(function (err) {
        console.log(err);
        showCancelMessageModal(err.responseText);
    });
}

function updateStatusTextAndHideReturnButtonCancel(orderId){
    $(".textOrderStatus"+orderId).each(function (index) {
        $(this).text("CANCELED");
    });

    $(".linkCancel"+orderId).each(function (index) {
        $(this).text("Done");
    });


}