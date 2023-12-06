$(document).ready(function () {
    $(".linkMinus").on("click",function (evt) {
        evt.preventDefault();
        productId = $(this).attr("pid")
        quantityInput = $("#quantity"+productId);
        newQuantity = parseInt(quantityInput.val()) - 1;
        if(newQuantity > 0){
            quantityInput.val(newQuantity);
        }else{
            warning_noti('Số lượng mua sản phẩm tối thiểu là 1');
        }
    });

    $(".linkPlus").on("click",function (evt) {
        evt.preventDefault();
        productId = $(this).attr("pid")
        quantityInput = $("#quantity"+productId);
        newQuantity = parseInt(quantityInput.val()) + 1;

        if(newQuantity <= 3){
            quantityInput.val(newQuantity);
        }else{
            warning_noti('Số lượng mua sản phẩm tối đa là 3');
        }
    });
});