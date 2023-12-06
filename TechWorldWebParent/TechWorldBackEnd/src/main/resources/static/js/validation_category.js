$( document ).ready( function () {
	var categoryId = $("#id").val();
	if(categoryId === null || categoryId === ''){
		$("#jQueryValidationForm").validate({
			rules: {
				name: "required",
				fileImage: "required"
			},
			messages: {
				name: "Please enter category name.",
				fileImage: "Please choose category's image ."
			}
		});
	}
	if(categoryId !== null && categoryId !== ''){
		$("#jQueryValidationForm").validate({
			rules: {
				name: "required",
			},
			messages: {
				name: "Please enter category name.",
			}
		});
	}
});