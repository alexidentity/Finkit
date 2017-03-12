$(document).ready(function() {
  $(this).tooltip();
  
  initSubscribtionButtonStyle();
  
  $("#btnFavourite").click(function(event) {
		event.preventDefault();
		
		var currentTopicId = $(".favCurrentTopic").data("current-topic-id");
		var currentTopicName = $(".favCurrentTopic").data("current-topic");
		
		var contextPath = window.location.pathname;
		var url = contextPath + "/subscribe";
		$.ajax({
		  type: "POST",
		  url: url,
		  data: {
		  	currentTopicId : currentTopicId
		  },
		  success: function () {
		  	var isCurrentTopicFavourited = $(".favCurrentTopic").hasClass("subscribed");
		  	var currentTopicName = $(".favCurrentTopic").data("current-topic");
				toggleSubscribedButton(isCurrentTopicFavourited);
				toggleSubtopicToTheSidebar(isCurrentTopicFavourited, currentTopicName);
		  }
		});
	});
  
});

function initSubscribtionButtonStyle() {
	var isCurrentTopicFavourited = $(".favCurrentTopic").hasClass("subscribed");
	toggleSubscribedButton(!isCurrentTopicFavourited);
}

function toggleSubscribedButton(isCurrentTopicFavourited) {
	if (isCurrentTopicFavourited) {
		$(".favCurrentTopic i").removeClass("fa-heart");
		$(".favCurrentTopic i").addClass("fa-heart-o");
		$(".favCurrentTopic i").removeClass("subscribed");
		$(".favCurrentTopic").removeClass("subscribed");
	} else {
		$(".favCurrentTopic i").removeClass("fa-heart-o");
		$(".favCurrentTopic i").addClass("fa-heart");
		$(".favCurrentTopic i").addClass("subscribed");
		$(".favCurrentTopic").addClass("subscribed");
	}
}

function toggleSubtopicToTheSidebar(isCurrentTopicFavourited, currentTopicName) {
	var selector = ".topicName:contains('" + currentTopicName + "')";
	if (isCurrentTopicFavourited) {
		$(selector).parent().parent().remove();
	} else {
		var prependSubtopicListItem = "<li><a href='/t/" + currentTopicName + "'><span class='full-nav topicName'>" + currentTopicName + "</span></a></li>";
		$("#topicNavbar").append(prependSubtopicListItem);
	}
}