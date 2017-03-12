$(document).ready(function() {
	$(".changeAvatar").hide();
	$("#manageTopics").hide();
	$("#warningMessage").hide();
	
	initFollowButton();
	initUserActivity();
	
	$(".closePopup").click(function(event) {
		event.preventDefault();
		$(".changeAvatar").hide(500);
	});
	
	$("#btnManageTopics").click(function(event) {
		event.preventDefault();
		$("#manageTopics").show("slide", { direction: "left" }, 500);
	});
	
	$("#btnCloseManageTopics").click(function(event) {
		event.preventDefault();
		$("#manageTopics").hide("slide", { direction: "left" }, 500);
	});
	
	$("#btnSave").prop("disabled", true);
	
	var currentAvatar = $(".avatarCircle").attr("src");
	$("#image").attr("src", currentAvatar);
	$("#image").width(208);
  $("#image").height(200);
	
	$(".btn-pref .btn").click(function () {
		$(".btn-pref .btn").removeClass("btn-primary").addClass("btn-default");
		$(this).removeClass("btn-default").addClass("btn-primary");
	});
	
	$(".useravatar").click(function(event) {
		event.preventDefault();
		$(".changeAvatar").show(500);
	});
	
	$(".btnDismissWarning").click(function(event) {
    $("#warningMessage").hide(500);
  });
	
	$("#changeAvatarForm").submit(function(event) {
		event.preventDefault();
		
		var uploadedAvatar = $("#image").attr("src");
		$(".avatarCircle").attr("src", uploadedAvatar);
		$(".card-bkimg").attr("src", uploadedAvatar);
		
		var username = $(".profileUsername").data("username");
		var url = username + "/changeAvatar";
		
		$.ajax({
		  type: "POST",
		  url: url,
      data: new FormData(this),
      processData: false,
      contentType: false,
		  success: function (response) {
		  	
		  	if (response) {
		  		var title = "Success:";
			  	var message = "The avatar was successfully uploaded!";
			  	var alertType = "alert-success";
			  	
			  	changeAlert(title, message, alertType);
			  	$(".changeAvatar").hide(500);
		  	} else {
		  		var title = "Warning:";
		    	var message = "Please upload your avatar again!<br />"
		  		+ "Allowed formats are: .png, .jpg and .gif";
		    	var alertType = "alert-warning";
		    	
		    	changeAlert(title, message, alertType);
		  	}
		  }
		});
	});
	
	$("#btnFollow").click(function(event) {
		event.preventDefault();

		var username = $("#btnFollow").data("username");
		var url = username + "/follow";
		
		$.ajax({
		  type: "POST",
		  url: url,
		  success: function (response) {
		  	clearBtnFollowClasses();
		  	
		  	if (response.userFollowing) {
		  		var title = "Success:";
		    	var message = "You are now following this user!";
		    	var alertType = "alert-success";
		    	
		    	$("#btnFollow").addClass("btn-danger");
		    	$("#btnFollow").text("Unfollow");
		    	
		    	changeAlert(title, message, alertType);
		  	} else {
		  		var title = "Success:";
		    	var message = "You unfollowed this user!";
		    	var alertType = "alert-success";
		    	
		    	$("#btnFollow").addClass("btn-info");
		    	$("#btnFollow").text("Follow");
		    	
		    	changeAlert(title, message, alertType);
		  	}
		  	
		  }
		});
	});
	
	$("#upload").change(function() {
    readURL(this);
    $("#image").width(208);
    $("#image").height(200);
  });
	
});

function clearBtnFollowClasses() {
	$("#btnFollow").removeClass("btn-info");
	$("#btnFollow").removeClass("btn-danger");
}

function readURL(input) {
  if (input.files && input.files[0]) {
    var reader = new FileReader();  
    
    reader.onload = function(e) {
      $("#image").attr("src", e.target.result);
      $("#uploadedImg").show(500);
    }
        
    reader.readAsDataURL(input.files[0]);
    
    var mimeType = input.files[0].type;
    var imageSize = input.files[0].size;
    checkImageValidations(mimeType, imageSize);
  }
}

function checkImageValidations(mimeType, imageSize) {
	var showWarningMessage = false;
	
	if (mimeType == "image/jpg"
		|| mimeType == "image/jpeg"
  	|| mimeType == "image/png"
  	|| mimeType == "image/gif") {
  	
  	$("#btnSave").prop("disabled", false);
	} else {
  	showWarningMessage = true;
  	$("#btnSave").prop("disabled", true);
  }
	
	if (imageSize >= 1024*1024) {
		showWarningMessage = true;
		$("#btnSave").prop("disabled", true);
	}
	
	if (showWarningMessage) {
		var title = "Warning:";
  	var message = "Please upload your avatar again!<br />"
	  	+ "Image size should not be above 2MB.<br />"
			+ "Allowed formats are: .png, .jpg and .gif";
  	var alertType = "alert-warning";
  	
  	changeAlert(title, message, alertType);
	}
}

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

function initFollowButton() {
	$("#btnFollow").removeClass("btn-info");
	$("#btnFollow").removeClass("btn-danger");
	
	if ($("#btnFollow").hasClass("userIsFollowing")) {
		$("#btnFollow").addClass("btn-danger");
		$("#btnFollow").text("Unfollow");
	} else {
		$("#btnFollow").addClass("btn-info");
		$("#btnFollow").text("Follow");
	}
}

function initUserActivity() {
	$(".activityType").each(function() {
		
		var activityListItem = $(this).parent().parent().parent();
		var recentPostLeftBar = activityListItem.find(".recentPostLeftBar");
		var recentPost = activityListItem.find(".recentPost");
		
		if ($(this).hasClass("UPVOTED")) {
			$(this).addClass("glyphicon-triangle-top");
			$(this).css({"color": "#5A9509"});
			recentPostLeftBar.css({"background-color": "#5A9509"});
			recentPost.css({"background-color": "rgba(90,149,9,0.15)"});
		} else {
			if ($(this).hasClass("DOWNVOTED")) {
				$(this).addClass("glyphicon-triangle-bottom");
				$(this).css({"color": "#EE4444"});
				recentPostLeftBar.css({"background-color": "#EE4444"});
				recentPost.css({"background-color": "rgba(238,68,68,0.15)"});
			} else if ($(this).hasClass("COMMENTED")) {
				$(this).addClass("glyphicon glyphicon-record");
				$(this).css({"color": "#FF9933"});
				recentPostLeftBar.css({"background-color": "#FF9933"});
				recentPost.css({"background-color": "rgba(255, 153, 51, 0.15)"});
			} else {
				$(this).addClass("glyphicon glyphicon-plus");
				$(this).css({"color": "#4F92B9"});
				recentPostLeftBar.css({"background-color": "#4F92B9"});
				recentPost.css({"background-color": "rgba(79,146,185,0.15)"});
			}
		}
 });
}