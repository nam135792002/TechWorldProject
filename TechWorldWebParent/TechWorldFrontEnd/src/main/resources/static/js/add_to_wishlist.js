$(document).ready(function () {
   $("#buttonAdd2WishList").on("click", function (evt) {
       addToWishList(productId03);
   })

    $(document).on("click", ".buttonAdd2WishListTwo", function (evt) {
        evt.preventDefault();
        productId04 = $(this).attr("wid");
        addToWishList(productId04);
    });

    $(document).on("click", ".buttonAdd2WishListThree", function (evt) {
        evt.preventDefault();
        productId05 = $(this).attr("th:wid");
        addToWishList(productId05);
    });
});

function addToWishList(id) {
    url = modelURL + "wishlist/add/" + id;
    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (xhr){
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }

    }).done(function(response){
        if(response === 'Bạn cần phải đăng nhập.' || response === 'Bạn đã thêm sản phẩm này vào trước đó!'){
            warning_noti(response);
        }else{
            var parts = response.split("|");
            var message = parts[0];
            var total = parts[1];
            anim5_noti(message);
            $("#wishlist-count-by-customer").text(total);
            $("#wishlist-count-by-customer-01").text(total);
        }
    }).fail(function () {
        error_noti("Error while adding product to wishlist.");
    });
}