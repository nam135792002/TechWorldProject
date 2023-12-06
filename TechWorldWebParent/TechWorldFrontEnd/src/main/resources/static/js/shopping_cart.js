$(document).ready(function () {
    $(".linkMinus").on("click",function (evt) {
        evt.preventDefault();
        decreaseQuantity($(this));
    });

    $(".linkPlus").on("click",function (evt) {
        evt.preventDefault();
        increaseQuantity($(this));
    });

    $(".linkRemove").on("click",function (evt) {
        evt.preventDefault();
        removeProduct($(this));
    });
});

function decreaseQuantity(link){
    productId = link.attr("pid")
    quantityInput = $("#quantity"+productId);
    newQuantity = parseInt(quantityInput.val()) - 1;
    if(newQuantity > 0){
        quantityInput.val(newQuantity);
        updateQuantity(productId, newQuantity);
    }else{
        warning_noti('Số lượng tối thiểu mua sản phẩm là 1');
    }
}

function  increaseQuantity(link){
    productId = link.attr("pid")
    quantityInput = $("#quantity"+productId);
    newQuantity = parseInt(quantityInput.val()) + 1;
    if(newQuantity <= 3){
        quantityInput.val(newQuantity);
        updateQuantity(productId, newQuantity);
    }else{
        warning_noti('Số lương tối đa mua sản phẩm là 3');
    }
}

function updateQuantity(productId, quantity){
    url = modelURL + "cart/update/" + productId + "/" + quantity;
    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (xhr){
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }

    }).done(function(updatedSubtotal){
        updateSubtotal(updatedSubtotal, productId)
        updatedTotal();
    }).fail(function () {
        error_noti("Error while updating product to quantity.");
    });
}

function updateSubtotal(updatedSubtotal, productId){
    formatedSubtotal = parseInt(updatedSubtotal);
    formatedSubtotal = formatedSubtotal.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
    $("#subtotal"+productId).text(formatedSubtotal);
}

function updatedTotal(){
    total = 0
    productCount = 0;

    $(".subtotal").each(function (index,element){
       productCount++;
        total += parseInt(element.innerHTML.replaceAll(".","")) ;
    });
    if(productCount < 1){
        showEmptyShoppingCart();
    }else{
        formatedTotal = total.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
        $("#total").text(formatedTotal);
    }
}

function showEmptyShoppingCart(){
    $("#sectionTotal").hide();
    $("#sectionEmptyCart").removeClass("d-none");
}

function removeProduct(link){
    url = link.attr("href");
    $.ajax({
        type: "DELETE",
        url: url,
        beforeSend: function (xhr){
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }

    }).done(function(response){
        rowNumber = link.attr("rowNumber");
        removeProductHTML(rowNumber);
        updatedTotal();
        anim5_noti(response);
    }).fail(function () {
        error_noti("Error while removing product.");
    });
}

function removeProductHTML(rowNumber){
    $("#row"+rowNumber).remove();
}