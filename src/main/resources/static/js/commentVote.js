$(document).ready(function(e) {
	
	setInitialUserVotes();
	
	$(".commentUpvote").click(function(event) {
		event.preventDefault();
		
		var postId = $(".topic").data("post-id");
		var rootCommentId = $(this).parent().data("comment-id");		
		var isCommentRoot = $(this).parent().hasClass("root");
		var nestedCommentId = $(this).parent().data("nested-comment-id");
		
		vote(postId, rootCommentId, nestedCommentId, isCommentRoot, "upvote");
	});
	
	$(".commentDownvote").click(function(event) {
		event.preventDefault();
		
		var postId = $(".topic").data("post-id");
		var rootCommentId = $(this).parent().data("comment-id");		
		var isCommentRoot = $(this).parent().hasClass("root");
		var nestedCommentId = $(this).parent().data("nested-comment-id");
		
		vote(postId, rootCommentId, nestedCommentId, isCommentRoot, "downvote");
	});
	
	function vote(postId, rootCommentId, nestedCommentId, isCommentRoot, voteType) {
		var url = voteType + "Comment/" + postId + "/" + rootCommentId + "/" + nestedCommentId;

		$.ajax({
			  type: "POST",
			  url: url,
			  success: function (response) {
			  	var commentIdSuffix = rootCommentId + "_" + nestedCommentId;
			  	
			  	if (response.hasError) {
			  		$(".dropdown-toggle").dropdown("toggle");
			  	} else {
			  		resetVoteClasses(commentIdSuffix);
			  		
			  		if (response.voteType == "UP") {
			  			toggleUpvote(commentIdSuffix);
			  		} else {
			  			toggleDownvote(commentIdSuffix);
			  		}
			  		
			  		colourCommentPointsLabel(commentIdSuffix);
			  	}
			  	
			  }
		});
	}
	
	function colourCommentPointsLabel(commentIdSuffix) {
		var postVotesSpan = $("#commentVotes_" + commentIdSuffix);
		var currentCommentPoints = parseInt(postVotesSpan.text(), 10);
		
		postVotesSpan.removeClass("label-warning label-success label-danger");
		
		if (currentCommentPoints == 0) {
			postVotesSpan.addClass("label-warning");
		} else if (currentCommentPoints > 0) {
			postVotesSpan.addClass("label-success");
		} else {
			postVotesSpan.addClass("label-danger");
		}
	}
	
	function toggleUpvote(commentIdSuffix) {
		var isPostUpvoted = $("#upvoteComment_" + commentIdSuffix).hasClass("voted");
		var isPostDownvoted = $("#downvoteComment_" + commentIdSuffix).hasClass("voted");
		
		var postVotesSpan = $("#commentVotes_" + commentIdSuffix);
		var postVotesValue = parseInt(postVotesSpan.text(), 10);
		
		if (isPostUpvoted) {
			$("#upvoteComment_" + commentIdSuffix).css({
				"background-color": "",
				"color": "#5A9509"
			});
			
			postVotesSpan.html(postVotesValue - 1);
			$("#upvoteComment_" + commentIdSuffix).removeClass("voted");
		} else {
			$("#upvoteComment_" + commentIdSuffix).css({
				"background-color": "#5A9509",
				"color": "white"
			});
			
			$("#upvoteComment_" + commentIdSuffix).addClass("voted");
			
			if (isPostDownvoted) {
				postVotesSpan.html(postVotesValue + 2);
				$("#downvoteComment_" + commentIdSuffix).removeClass("voted");
			} else {
				postVotesSpan.html(postVotesValue + 1);
			}
		}
	}
	
	function toggleDownvote(commentIdSuffix) {
		var isPostUpvoted = $("#upvoteComment_" + commentIdSuffix).hasClass("voted");
		var isPostDownvoted = $("#downvoteComment_" + commentIdSuffix).hasClass("voted");
		
		var postVotesSpan = $("#commentVotes_" + commentIdSuffix);
		var postVotesValue = parseInt(postVotesSpan.text(), 10);
		
		if (isPostDownvoted) {
			$("#downvoteComment_" + commentIdSuffix).css({
				"background-color": "",
				"color": "#EE4444"
			});
			
			postVotesSpan.html(postVotesValue + 1);
			$("#downvoteComment_" + commentIdSuffix).removeClass("voted");
		} else {
			$("#downvoteComment_" + commentIdSuffix).css({
				"background-color": "#EE4444",
				"color": "white"
			});
			
			$("#downvoteComment_" + commentIdSuffix).addClass("voted");
			
			if (isPostUpvoted) {
				postVotesSpan.html(postVotesValue - 2);
				$("#upvoteComment_" + commentIdSuffix).removeClass("voted");
			} else {
				postVotesSpan.html(postVotesValue - 1);
			}
		}
	}
	
	function resetVoteClasses(commentIdSuffix) {
		var isPostUpvoted = $("#upvoteComment_" + commentIdSuffix).hasClass("voted");
		var isPostDownvoted = $("#downvoteComment_" + commentIdSuffix).hasClass("voted");
		
		var postVotesSpan = $("#commentVotes_" + commentIdSuffix);
		var postVotesValue = parseInt(postVotesSpan.text(), 10);
		
		$("#upvoteComment_" + commentIdSuffix).css({
			"background-color": "",
			"color": "#5A9509"
		});
		
		$("#downvoteComment_" + commentIdSuffix).css({
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