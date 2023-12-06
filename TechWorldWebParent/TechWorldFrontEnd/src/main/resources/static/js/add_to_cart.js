$(document).ready(function () {
   $("#buttonAdd2Cart").on("click",function (evt) {
      addToCart03();
   }) ;
});

function addToCart03(){
   quantity = $("#quantity"+productId03).val();
   url = modelURL + "cart/add/" + productId03 + "/" + quantity;
   $.ajax({
      type: "POST",
      url: url,
      beforeSend: function (xhr){
         xhr.setRequestHeader(csrfHeaderName, csrfValue);
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