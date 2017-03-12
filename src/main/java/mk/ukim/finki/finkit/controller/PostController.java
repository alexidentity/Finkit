package mk.ukim.finki.finkit.controller;

import mk.ukim.finki.finkit.service.PostService;
import mk.ukim.finki.finkit.service.UserService;
import mk.ukim.finki.finkit.service.VotingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import mk.ukim.finki.finkit.enumeration.VoteType;
import mk.ukim.finki.finkit.model.data.Comment;
import mk.ukim.finki.finkit.model.data.User;
import mk.ukim.finki.finkit.model.json.CommentAsyncCallResponse;
import mk.ukim.finki.finkit.model.json.VotingAsyncCallResponse;
import mk.ukim.finki.finkit.model.view.PostViewModel;

@Controller
@RequestMapping("post")
public class PostController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private VotingService votingService;
	
	@RequestMapping("/{postId}")
	public ModelAndView showPostPage(@PathVariable Long postId) {
		PostViewModel postViewModel = postService.getPostViewModel(postId);

		ModelAndView modelAndView = new ModelAndView();
		
		if (postViewModel != null) {
			modelAndView.setViewName("post");
			modelAndView.addObject("viewModel", postViewModel);
		} else {
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value = "/comment/{postId}", method = RequestMethod.POST)
	public CommentAsyncCallResponse commentOnPost(
			@RequestParam("comment") String comment,
			@RequestParam("postId") Long postId,
			@RequestParam("rootCommentId") Long rootCommentId) {
		User user = userService.getLoggedInUser();
		
		Comment savedComment = postService.saveCommentOnPost(user, comment, postId, rootCommentId);

		CommentAsyncCallResponse response = new CommentAsyncCallResponse();
		response.setUser(user);
		response.setComment(savedComment);
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/upvote/{postId}/{postNumber}")
	public VotingAsyncCallResponse upvotePost(@PathVariable Long postId, @PathVariable Long postNumber) {
		VotingAsyncCallResponse response = votingService.voteOnPost(postId, postNumber, VoteType.UP);
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/downvote/{postId}/{postNumber}")
	public VotingAsyncCallResponse downvotePost(@PathVariable Long postId, @PathVariable Long postNumber) {
		VotingAsyncCallResponse response = votingService.voteOnPost(postId, postNumber, VoteType.DOWN);
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/upvoteComment/{postId}/{rootCommentId}/{nestedCommentId}")
	public VotingAsyncCallResponse upvoteComment(
			@PathVariable Long postId,
			@PathVariable Long rootCommentId,
			@PathVariable Long nestedCommentId) {
		
		VotingAsyncCallResponse response = null;
		
		if (nestedCommentId == -1) {
			response = votingService.voteOnComment(postId, rootCommentId, VoteType.UP);
		} else {
			response = votingService.voteOnComment(postId, nestedCommentId, VoteType.UP);
		}
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/downvoteComment/{postId}/{rootCommentId}/{nestedCommentId}")
	public VotingAsyncCallResponse downvoteComment(
			@PathVariable Long postId,
			@PathVariable Long rootCommentId,
			@PathVariable Long nestedCommentId) {
		
		VotingAsyncCallResponse response = null;
		
		if (nestedCommentId == -1) {
			response = votingService.voteOnComment(postId, rootCommentId, VoteType.DOWN);
		} else {
			response = votingService.voteOnComment(postId, nestedCommentId, VoteType.DOWN);
		}
		
		return response;
	}
}