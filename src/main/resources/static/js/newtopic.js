$(document).ready(function(e) {
	
	$(".ta").hide();
	
	if ($(".btnSubmit").hasClass("isEdit")) {
		$(".topicName").prop("readonly", true);
	}
	
	if ($(".nbcDiv").hasClass("hasCustomStyle")) {
		$(".nbcDiv").show();
	} else {
		$(".nbcDiv").hide();
	}
	
	$(".list-group a").on("click", function(event) {
		event.preventDefault();
		
    if ($(this).is(".active")) {
    	$(this).removeClass("active");
    	$(".nbcDiv").removeClass("hasCustomStyle");
    	$(".nbcDiv").hide();
    } else {
    	$(this).addClass("active");
    	$(".nbcDiv").addClass("hasCustomStyle");
    	$(".nbcDiv").show();
    }
	});
	
	$(".btnPreview").click(function(event) {
		event.preventDefault();
		
		// Navbar background colour
		var navbarBackgroundColour = $("#navbarBackgroundColour").val();
		$(".navbar-inverse").css("background-color", navbarBackgroundColour);
		
		// Navbar links
		var navbarLinksColour = $("#navbarLinksColour").val();
		$(".navbar-inverse").css("color", navbarLinksColour);
		$(".navbar-nav > li > a").css("color", navbarLinksColour);
		$(".navbar-inverse .navbar-text").css("color", navbarLinksColour);
		$(".username a").css("color", navbarLinksColour);
		
		// Navbar current topic
		var navbarCurrentTopicColour = $("#navbarCurrentTopicColour").val();
		$(".navbar-inverse .navbar-nav > .active > a").css("color", navbarCurrentTopicColour);
		
		var navbarCurrentTopicBackgroundColour = $("#navbarCurrentTopicBackgroundColour").val();
		$(".navbar-inverse .navbar-nav > .active > a").css("background-color", navbarCurrentTopicBackgroundColour);
		
		$("#topicNavbar li a").mouseover(function() {
	    $(this).css("background-color", navbarCurrentTopicBackgroundColour);
		}).mouseout(function() {
	    $(this).css("background-color", "transparent");
		});
		
		var backgroundImageUrl = $(".backgroundImageUrl").val();
		if (backgroundImageUrl) {
			$("body").css('background', 'url("' + backgroundImageUrl + '") no-repeat fixed');
			$("body").css('background-size', 'cover');
		}
	});
	
	$(".btnReset").click(function(event) {
		event.preventDefault();
		
		$(".navbar-inverse").css("background-color", "#222");
		
		$(".navbar-inverse").css("color", "#9d9d9d");
		$(".navbar-nav > li > a").css("color", "#9d9d9d");
		$(".navbar-inverse .navbar-text").css("color", "#9d9d9d");
		$(".username a").css("color", "#ffffff");
		
		$(".navbar-inverse .navbar-nav > .active > a").css("color", "#fff");
		$(".navbar-inverse .navbar-nav > .active > a").css("background-color", "#080808");
		
		$("#topicNavbar li a").mouseover(function() {
	    $(this).css("background-color", "#636363");
		}).mouseout(function() {
	    $(this).css("background-color", "transparent");
		});
		
		$("#btnFavourite").css("color", "#ff0000");
		$("#btnFinkit").css("color", "#ff9800");
		$("#btnFinkit:hover").css("color", "#ffc107");
	});
	
	$(".topicAlert .alert a").click(function(event) {
		event.preventDefault();
		$(".ta").hide();
	});
	
	$(".btnSubmit").click(function(event) {
		event.preventDefault();
		
		var hasErrors = false;
		var errorMessages = "";
		
		var name = $("#topicName").val();
		var description = $(".topicDescription").val();
		var rules = $(".topicRules").val();
		
		if (!$.trim(name).length 
				|| !$.trim(description).length
				|| !$.trim(rules).length) {
			
			errorMessages = "<li>Name, description and rules are mandatory!</li>";
			$(".errorList").html(errorMessages);
			$(".ta").show();
			
			return;
		}
		
		var url;
		if ($(".btnSubmit").hasClass("isEdit")) {
			url = "edit/save/"
		} else {
			url = "newtopic/save/"
		}
		
		if($(".nbcDiv").hasClass("hasCustomStyle")) {
			url = url + "true";
		} else {
			url = url + "false";
		}
		
		$.ajax({
			type: "POST",
			url: url,
			data: $("#submitTopicForm").serialize(),
			success : function (response) {
				ajaxResponse(response);
			},
			error : function (response) {
				ajaxResponse(response);
			}
		});
		
	});
});

function ajaxResponse(response) {
	if (response.showError) {
		$(".errorList").html(response.errors);
		$(".ta").show();
	} else {
		var topicName = $("#topicName").val();
		var contextPath = window.location.pathname.split("/");
		var redirectUrl = "/" + contextPath[1] + "/t/" + topicName;
		
		window.location.replace(redirectUrl);
	}
}