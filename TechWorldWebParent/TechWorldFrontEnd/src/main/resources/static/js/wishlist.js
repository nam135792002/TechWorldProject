$(document).ready(function () {
    $(".linkRemoveWishList").on("click",function (evt) {
        evt.preventDefault();
        removeProductWishList($(this));
    });
});

function removeProductWishList(link){
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
        if ($("#wishlistTable tbody tr").length === 0) {
            $("#wishlistTable").hide(); // Assuming your table has an ID 'wishlistTable'
            $("#sectionEmptyCart").removeClass("d-none");
        }
        var parts = response.split("|");
        var message = parts[0];
        var total = parts[1];
        anim5_noti(message);
        $("#wishlist-count-by-customer").text(total);
        $("#wishlist-count-by-customer-01").text(total);
    }).fail(function () {
        error_noti("Error while removing product.");
    });
}

function removeProductHTML(rowNumber){
    $("#row"+rowNumber).remove();
}