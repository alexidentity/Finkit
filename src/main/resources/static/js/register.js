$(document).ready(function(){
	$("#warningMessage").hide();
	
	var isUsernameTaken = $("#alertMessage").data("username-taken");
	var errorMessage = $("#alertMessage").data("error-message");
	
	if (isUsernameTaken) {
		changeAlert("Error:", errorMessage, "alert-warning");
	}
	
	$("#btnLogin").click(function(event) {
    checkIfInputFieldsAreEmpty(event);
  });
	
	$(".btnDismissWarning").click(function(event) {
		$("#warningMessage").hide(500);
  });
});

function changeAlert(title, message, alertType) {
	removeAlertClass();
	
	$("#alertTitle").html(title);
	$("#alertMessage").html(message);
	$("#alertType").addClass(alertType);

	$("#warningMessage").show();
}

function removeAlertClass() {
	$("#alertType").removeClass("alert-warning");
	$("#alertType").removeClass("alert-success");
}
    
function checkIfInputFieldsAreEmpty(event) {
	var username = $("#username").val();
	var password = $("#password").val();
	var confirmedPassword = $("#confirmPassword").val();
	var areFieldsEmpty = true;
	
	if(!$.trim(username).length 
		|| !$.trim(password).length
		|| !$.trim(confirmedPassword).length) {

		event.preventDefault();

		var title = "Warning:";
  	var message = "All fields are mandatory!";
  	var alertType = "alert-warning";
  	
  	changeAlert(title, message, alertType);
	} else {
		areFieldsEmpty = false;
	}

	if (!areFieldsEmpty) {
		if($.trim(password) != confirmedPassword) {
			event.preventDefault();
			
			var title = "Warning:";
	  	var message = "Password does not match!";
	  	var alertType = "alert-warning";
	  	
	  	changeAlert(title, message, alertType);
		}
	}
	
	return;
}