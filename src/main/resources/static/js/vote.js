$(document).ready(function(e) {
	
	setInitialUserVotes();
	
	$(".postVotes").each(function() {
		var currentPostVotes = parseInt($(this).text(), 10);
		
		$("").removeAttr("background-color");
		
		if (currentPostVotes == 0) {
			$(this).parent().css({"background-color": "#FF9933"});
		} else if (currentPostVotes > 0) {
			$(this).parent().css({"background-color": "rgb(90, 149, 9)"});
		} else {
			$(this).parent().css({"background-color": "rgb(238, 68, 68)"});
		}
		
 });

	$(".upvote").click(function(event) {
		event.preventDefault();
		
		var postId = $(this).parent().data("post-id");
		var postNumber = $(this).parent().data("post-number");
		
		vote(postId, postNumber, "upvote");
	});
	
	$(".downvote").click(function(event) {
		event.preventDefault();
		
		var postId = $(this).parent().data("post-id");
		var postNumber = $(this).parent().data("post-number");
		
		vote(postId, postNumber, "downvote");
	});
	
	function vote(postId, postNumber, voteType) {
		var url = voteType + "/" + postId + "/" + postNumber;
		
		$.ajax({
			  type: "POST",
			  url: url,
			  success: function (response) {
			  	if (response.hasError) {
			  		$(".dropdown-toggle").dropdown("toggle");
			  	} else {
			  		resetVoteClasses(postNumber);
			  		
			  		if (response.voteType == "UP") {
			  			toggleUpvote(postNumber);
			  		} else {
			  			toggleDownvote(postNumber);
			  		}
			  		
			  		var postVotesSpan = $("#postVotes_" + postNumber);
			  		var postVotesValue = parseInt(postVotesSpan.text(), 10);
			  		var postVotesBackground = postVotesSpan.parent();
			  		
		  			$(postVotesBackground).removeAttr("background-color");
		  			
		  			if (postVotesValue == 0) {
		  				postVotesBackground.css({"background-color": "#FF9933"});
		  			} else if (postVotesValue > 0) {
		  				postVotesBackground.css({"background-color": "rgb(90, 149, 9)"});
		  			} else {
		  				postVotesBackground.css({"background-color": "rgb(238, 68, 68)"});
		  			}
		  			
		  			if ($("#postVotes_" + postNumber).hasClass("topicVotes")) {
		  				postVotesBackground.css({"background-color": ""});
		  			}
			  			
			  	}
			  	
			  }
		});
	}
	
	function toggleUpvote(postNumber) {
		var isPostUpvoted = $("#upvote_" + postNumber).hasClass("voted");
		var isPostDownvoted = $("#downvote_" + postNumber).hasClass("voted");
		
		var postVotesSpan = $("#postVotes_" + postNumber);
		var postVotesValue = parseInt(postVotesSpan.text(), 10);
		
		if (isPostUpvoted) {
			$("#upvote_" + postNumber).css({
				"background-color": "",
				"color": "#5A9509"
			});
			
			postVotesSpan.html(postVotesValue - 1);
			$("#upvote_" + postNumber).removeClass("voted");
		} else {
			$("#upvote_" + postNumber).css({
				"background-color": "#5A9509",
				"color": "white"
			});
			
			$("#upvote_" + postNumber).addClass("voted");
			
			if (isPostDownvoted) {
				postVotesSpan.html(postVotesValue + 2);
				$("#downvote_" + postNumber).removeClass("voted");
			} else {
				postVotesSpan.html(postVotesValue + 1);
			}
		}
	}
	
	function toggleDownvote(postNumber) {
		var isPostUpvoted = $("#upvote_" + postNumber).hasClass("voted");
		var isPostDownvoted = $("#downvote_" + postNumber).hasClass("voted");
		
		var postVotesSpan = $("#postVotes_" + postNumber);
		var postVotesValue = parseInt(postVotesSpan.text(), 10);
		
		if (isPostDownvoted) {
			$("#downvote_" + postNumber).css({
				"background-color": "",
				"color": "#EE4444"
			});
			
			postVotesSpan.html(postVotesValue + 1);
			$("#downvote_" + postNumber).removeClass("voted");
		} else {
			$("#downvote_" + postNumber).css({
				"background-color": "#EE4444",
				"color": "white"
			});
			
			$("#downvote_" + postNumber).addClass("voted");
			
			if (isPostUpvoted) {
				postVotesSpan.html(postVotesValue - 2);
				$("#upvote_" + postNumber).removeClass("voted");
			} else {
				postVotesSpan.html(postVotesValue - 1);
			}
		}
	}
	
	function resetVoteClasses(postNumber) {
		var isPostUpvoted = $("#upvote_" + postNumber).hasClass("voted");
		var isPostDownvoted = $("#downvote_" + postNumber).hasClass("voted");
		
		var postVotesSpan = $("#postVotes_" + postNumber);
		var postVotesValue = parseInt(postVotesSpan.text(), 10);
		
		$("#upvote_" + postNumber).css({
			"background-color": "",
			"color": "#5A9509"
		});
		
		$("#downvote_" + postNumber).css({
			"background-color": "",
			"color": "#EE4444"
		});
	}
	
	function setInitialUserVotes() {
		$(".upvotedByUser").css({
			"background-color": "#5A9509",
			"color": "white"
		});
		
		$(".downvotedByUser").css({
			"background-color": "#EE4444",
			"color": "white"
		});
	}

});