$(document).ready(function(e) {
	initPostPage();
});

function initPostPage() {
	setInitialUserVotes();

	$(".commentReply").hide();
	$(".postReply").hide();
	
	addListenersForCommenting();
	addListenersForReplyButton();
	addListenersForVoteButton();
	
	recolourCommentVotes();
}

function commentOnPost(comment, postId, rootCommentId, url) {
	$.ajax({
	  type: "POST",
	  url: url,
	  data: {
	  	comment : comment,
	  	postId : postId,
	  	rootCommentId : rootCommentId},
	  success: function (response) {
	  	var user = response.user;
	  	var comment = response.comment;
	  	var rootCommentId = comment.id;
	  	
			var nestedCommentId = -1;
			var parentCommentId = response.comment.parentCommentId;
			if (parentCommentId != null) {
				nestedCommentId = rootCommentId;
				rootCommentId = parentCommentId;
			}
	  	
	  	var commentRoot = "#postReply .commentVoting .root";
	  	$(commentRoot).attr({"data-comment-id" : rootCommentId, "data-nested-comment-id" : nestedCommentId});
	  	
	  	$(commentRoot + " .commentUpvote a").attr({"id" : "upvoteComment_" + rootCommentId + "_" + nestedCommentId});
	  	$(commentRoot + " .commentUpvote a").addClass("voted");
	  	$(commentRoot + " .commentUpvote a").css({
	  		"background-color": "#5A9509",
				"color": "white"
			});
	  	
	  	$(commentRoot + " .commentDownvote a").attr({"id" : "downvoteComment_" + rootCommentId + "_" + nestedCommentId});
	  	$(commentRoot + " .commentDownvote a").addClass("notVoted");
	  	
	  	var commentUserInfo = "#postReply .commentUserInfo";
	  	$(commentUserInfo + " a").attr("href", "/finkit/profile/" + user.login);
	  	$(commentUserInfo + " a img").attr("src", "/finkit" + user.avatarUrl);
	  	
	  	$(commentUserInfo + " .commentUsername a").text(user.login);
	  	$(commentUserInfo + " .commentUsername a").attr({
	  		"href" : "/finkit/profile/" + user.login,
	  		"data-comment-id" : rootCommentId
	  	});
	  	
	  	var commentTimePostedDiv = commentUserInfo + " .commentUsername .commentTimePostedDiv";
	  	$(commentTimePostedDiv + " .commentTimePosted").text("moments ago");
	  	$(commentTimePostedDiv + " .commentPoints").attr({"id" : "commentVotes_" + rootCommentId + "_" + nestedCommentId});
	  	$(commentTimePostedDiv + " .commentPoints").text(1);
	  	$(commentTimePostedDiv + " .commentPoints").addClass("label-success");
	  	$(commentTimePostedDiv + " .commentPoints").removeClass("label-danger");
	  	
	  	$("#postReply .commentDiv .comment").text(comment.content);
	  	
	  	$("#postReply .commentReply").attr({
	  		"id" : "commentReply" + rootCommentId,
	  		"data-comment-id" : rootCommentId
	  	});
	  	$("#postReply .commentReply textarea").attr({"placeholder" : "Reply to @" + user.login});
	  	
	  	$("#postReply .subComments").attr({"id" : "subComments_" + rootCommentId});
	  	
	  	$("#postReply .subComments ul li").remove();
	  	
	  	var newPostReplyId = "postReply" + rootCommentId + "_" + nestedCommentId;
	  	if (parentCommentId == null) {
	  		$("#postReply").clone().prop("id", newPostReplyId).prependTo(".comments-list");
	  	} else {
	  		var subcommentsList = "#subComments_" + rootCommentId + " .subcomments-list";
	  		var subcommentReplyDiv = "#" + newPostReplyId + " .commentDiv div";
	  		var commentReplyDivOnNested = "#" + newPostReplyId + " .commentReply";
	  		
	  		$("#postReply .commentVoting ul").removeClass("root");
	  		$("#postReply").clone().prop("id", newPostReplyId).prependTo(subcommentsList);
	  		$(subcommentReplyDiv).remove();
	  		$(commentReplyDivOnNested).remove();
	  	}
	  	
	  	resetPostReplyIDs();
	  	$("#commentReply" + rootCommentId).hide();
	  	addListenersForVoteButton();
	  	addListenersForReplyButton();
	  	addListenersForCommenting();
	  	
	  	$("#" + newPostReplyId).show();
	  }
	});
}

function recolourCommentVotes() {
	$(".commentPoints").each(function() {
		var currentCommentPoints = parseInt($(this).text(), 10);
		
		if (currentCommentPoints == 0) {
			$(this).addClass("label-warning");
		} else if (currentCommentPoints > 0) {
			$(this).addClass("label-success");
		} else {
			$(this).addClass("label-danger");
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

function resetPostReplyIDs() {
	$("#postReply .commentVoting ul").addClass("root");
	var commentRoot = "#postReply .commentVoting .root";
	
	$(commentRoot + " .commentUpvote a").attr({"id" : ""});
	$(commentRoot + " .commentDownvote a").attr({"id" : ""});
	
	var commentUserInfo = "#postReply .commentUserInfo";
	var commentTimePostedDiv = commentUserInfo + " .commentUsername .commentTimePostedDiv";
	$(commentTimePostedDiv + " .commentPoints").attr({"id" : ""});
	
	$("#postReply .commentReply").attr({"id" : ""});
	$("#postReply .subComments").attr({"id" : ""});
	$("#postReply .subComments .subcomments-list li").attr({"id" : ""});
	$("#postReply .subComments .subcomments-list li .subComments").attr({"id" : ""});
}

function addListenersForReplyButton() {
	$(".reply").unbind().click(function(event) {
    event.preventDefault();
    
    $(".commentReply").hide();
    
    var postId = $(".topic").data("post-id");

    var usernameLink = $(this).parent().parent().parent().find(".commentUserInfo > .commentUsername > .commentUsernameLink").first();
    var usernameToReply = usernameLink.text();
    
    var commentId = $(usernameLink).data("comment-id");
    
    var commentReplyDiv = "#commentReply" + commentId;

    $(commentReplyDiv).show();
    $(commentReplyDiv + " textarea").focus();

    $(this).animate({ scrollTop: 0 }, "slow");
  });
}

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

function addListenersForVoteButton() {
	$(".commentUpvote").unbind().click(function(event) {
		event.preventDefault();
		
		var postId = $(".topic").data("post-id");
		var rootCommentId = $(this).parent().data("comment-id");		
		var isCommentRoot = $(this).parent().hasClass("root");
		var nestedCommentId = $(this).parent().data("nested-comment-id");
		
		vote(postId, rootCommentId, nestedCommentId, isCommentRoot, "upvote");
	});
	
	$(".commentDownvote").unbind().click(function(event) {
		event.preventDefault();
		
		var postId = $(".topic").data("post-id");
		var rootCommentId = $(this).parent().data("comment-id");		
		var isCommentRoot = $(this).parent().hasClass("root");
		var nestedCommentId = $(this).parent().data("nested-comment-id");
		
		vote(postId, rootCommentId, nestedCommentId, isCommentRoot, "downvote");
	});
}

function addListenersForCommenting() {
	$("#commentOnPost").unbind().submit(function(event) {
		event.preventDefault();
		
		var postId = $(".topic").data("post-id");
    var comment = $("#commentText").val();
    
    var url = "comment/" + postId;
    var rootCommentId = null;

    commentOnPost(comment, postId, rootCommentId, url);
	});
	
	$(".commentOnComment").unbind().submit(function(event) {
		event.preventDefault();
		
		var postId = $(".topic").data("post-id");
		var comment = $(this).children("textarea").val();
		var rootCommentId = $(this).parent().data("comment-id");
    
    var url = "comment/" + postId;

    commentOnPost(comment, postId, rootCommentId, url);
	});
}