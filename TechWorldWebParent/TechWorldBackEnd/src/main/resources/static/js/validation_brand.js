$( document ).ready( function () {
	var brandId = $("#id").val();
	if(brandId === null || brandId === ''){
		$("#jQueryValidationForm").validate({
			rules: {
				name: "required",
				fileImage: "required",
				categories: "required"
			},
			messages: {
				name: "Please enter brand name.",
				fileImage: "Please choose brand's image .",
				categories: "Please choose one or many categories for this brand"
			}
		});
	}
	if(brandId !== null && brandId !== ''){
		$("#jQueryValidationForm").validate({
			rules: {
				name: "required",
				categories: "required"
			},
			messages: {
				name: "Please enter category name.",
				categories: "Please choose one or many categories for this brand"
			}
		});
	}
});