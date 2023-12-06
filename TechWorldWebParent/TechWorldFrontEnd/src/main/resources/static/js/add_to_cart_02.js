$(document).ready(function () {
   $(".buttonAdd2CartTwo").on("click",function (evt) {
      productId = $(this).attr("pid");
      addToCart(productId);
   }) ;

   $(document).on("click", ".buttonAdd2CartThree", function (evt) {
      evt.preventDefault();
      productId02 = $(this).attr("th:pid");
      addToCart(productId02);
   });
});

function addToCart(productId){
   url = modelURL + "cart/add/" + productId + "/" + 1;
   $.ajax({
      type: "POST",
      url: url,
      beforeSend: function (xhr){
         xhr.setRequestHeader(csrfHeaderName,csrfValue);
      }

   }).done(function(response){
      if(response === 'Bạn cần phải đăng nhập để thêm sản phẩm vào giỏ hàng.' || response.includes('Lưu ý')){
         warning_noti(response);
      } else{
         var parts = response.split("|");
         var message = parts[0];
         var total = parts[1];
         anim5_noti(message);
         $("#cart-count-by-customer").text(total);
         $("#cart-count-by-customer-01").text(total);
      }
   }).fail(function () {
      error_noti("Error while adding product to shopping cart.");
   });
}