$( document ).ready( function () {
	var userId = $("#id").val();
	if(userId === null || userId === ''){
		$("#jQueryValidationForm").validate({
			rules: {
				firstName: "required",
				lastName: "required",
				phoneNumber: {
					required: true,
					maxlength: 12
				},
				password: {
					required: true,
					minlength: 5
				},
				confirm_password: {
					required: true,
					minlength: 5,
					equalTo: "#input38"
				},
				email: {
					required: true,
					email: true
				},
				role: "required"
			},
			messages: {
				firstName: "Please enter your first name.",
				lastName: "Please enter your last name.",
				phoneNumber: {
					required: "Please provide a phone number.",
					maxlength: "Phone number must not exceed 11 characters."
				},
				password: {
					required: "Please provide a password.",
					minlength: "Your password must be at least 5 characters long."
				},
				confirm_password: {
					required: "Please provide a password.",
					minlength: "Your password must be at least 5 characters long.",
					equalTo: "Please enter the same password as above."
				},
				email: "Please enter a valid email address.",
				role: "Please select a role."
			}
		});
	}
	if(userId != null && userId !== ''){
		$("#jQueryValidationForm").validate({
			rules: {
				firstName: "required",
				lastName: "required",
				phoneNumber: "required",
				password: {
					minlength: 5
				},
				confirm_password: {
					minlength: 5,
					equalTo: "#input46"
				},
				email: {
					required: true,
					email: true
				},
				role: "required"
			},
			messages: {
				firstName: "Please enter your first name.",
				lastName: "Please enter your last name.",
				phoneNumber: "Please enter your phone number.",
				password: {
					minlength: "Your password must be at least 5 characters long."
				},
				confirm_password: {
					minlength: "Your password must be at least 5 characters long.",
					equalTo: "Please enter the same password as above."
				},
				email: "Please enter a valid email address.",
				role: "Please select a role."
			}
		});
	}
});