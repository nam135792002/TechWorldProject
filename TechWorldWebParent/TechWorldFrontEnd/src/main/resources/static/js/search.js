
function getSuggestions(keyword) {
    const url = modelURL + "api/search/" + keyword;
    var products = document.querySelector('.psearch-results')
    products.innerHTML = ''
    $.get(url, function(responseJson) {
        responseJson.forEach(function(item) {
            var productHTML = '<div class="axil-product-list">';
            productHTML += '<div class="thumbnail">';
            productHTML += '<a href="' + modelURL + 'p/' + item.alias + '">';
            productHTML += '<img src="' + item.mainImage + '" style="width: 70px" alt="Yantiti Leather Bags">';
            productHTML += '</a>';
            productHTML += '</div>';
            productHTML += '<div class="product-content">';
            productHTML += '<div class="product-rating" style="display: inline-flex">';
            productHTML += '<div class="star-rating">';
            productHTML += `<input type="text" class="product-detail-rating-star" dir="Ltr" data-th-size="md" value="${item.averageReview}">`;
            productHTML += '</div>';
            productHTML += '<div class="review-link" style="margin-top: 15px; margin-left: 7px; font-size: 15px">';
            productHTML += `<a href="#">(<span>${item.countReview}</span> đánh giá)</a>`;
            productHTML += '</div>';
            productHTML += '</div>';
            productHTML += '<h6 class="product-title"><a href="' + modelURL + 'p/' + item.alias + '">' + item.name + '</a></h6>';
            productHTML += '<div class="product-price-variant">';
            productHTML += '<span class="price current-price">' + item.newPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }) + '</span>';
            productHTML += '<span class="price old-price">' + item.oldPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }) + '</span>';
            productHTML += '</div>';
            productHTML += '<div class="product-cart">';
            productHTML += '</div>';
            productHTML += '</div>';
            productHTML += '</div>';

            var newProduct = document.createElement('div');
            newProduct.innerHTML = productHTML;

            products.appendChild(newProduct);

            $(".product-detail-rating-star").rating({
                displayOnly: true,
                hoverOnClear: false,
                showCaption:  false,
                theme: 'krajee-svg'
            });
        });
    });

}