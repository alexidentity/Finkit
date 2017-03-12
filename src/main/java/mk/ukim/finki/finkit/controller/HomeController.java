package mk.ukim.finki.finkit.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import mk.ukim.finki.finkit.enumeration.VoteType;
import mk.ukim.finki.finkit.model.json.VotingAsyncCallResponse;
import mk.ukim.finki.finkit.model.view.HomePageViewModel;
import mk.ukim.finki.finkit.service.TopicService;
import mk.ukim.finki.finkit.service.UserSubtopicSubscriptionsService;
import mk.ukim.finki.finkit.service.VotingService;

@Controller
public class HomeController {
	
	private static String ALL_SUBTOPIC = "all";

  @Autowired
  private TopicService topicService;
  
  @Autowired
  private VotingService votingService;
  
  @Autowired
  private UserSubtopicSubscriptionsService userSubtopicSubscriptionsService;
  
	@RequestMapping("/")
	public ModelAndView homePage() {
		
		ModelAndView modelAndView = new ModelAndView("index");
		
		HomePageViewModel homePageViewModel = topicService.getHomepageViewModel(ALL_SUBTOPIC, StringUtils.EMPTY);
		modelAndView.addObject("viewModel", homePageViewModel);	
		
		return modelAndView;
	}

	@RequestMapping(value = "/t/{subtopic}", params = "!query")
	public ModelAndView subtopicPage(@PathVariable String subtopic) {
		ModelAndView modelAndView = new ModelAndView();
		
		HomePageViewModel homePageViewModel = topicService.getHomepageViewModel(subtopic, StringUtils.EMPTY);
		
		if (homePageViewModel != null) {
			modelAndView.setViewName("index");
			modelAndView.addObject("viewModel", homePageViewModel);	
		} else {
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/t/{subtopic}", params = "query")
	public ModelAndView subtopicSearchPage(
			@PathVariable String subtopic,
			@RequestParam(value = "query") String searchQuery) {
		ModelAndView modelAndView = new ModelAndView();
		
		HomePageViewModel homePageViewModel = topicService.getHomepageViewModel(subtopic, searchQuery);
		
		if (homePageViewModel != null) {
			modelAndView.setViewName("index");
			modelAndView.addObject("viewModel", homePageViewModel);	
		} else {
			modelAndView.setViewName("error");
		}
		
		return modelAndView;
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/t/{subtopic}/subscribe", method = RequestMethod.POST)
	public void subscribe(@RequestParam("currentTopicId") Long currentTopicId) {
		userSubtopicSubscriptionsService.manageTopicSubscription(currentTopicId);
	}
	
	@ResponseBody
	@RequestMapping(
			value = {"/upvote/{postId}/{postNumber}", "/t/upvote/{postId}/{postNumber}"}, 
			method = RequestMethod.POST)
	public VotingAsyncCallResponse upvotePost(@PathVariable Long postId, @PathVariable Long postNumber) {
		VotingAsyncCallResponse response = votingService.voteOnPost(postId, postNumber, VoteType.UP);
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping(
			value = {"/downvote/{postId}/{postNumber}", "/t/downvote/{postId}/{postNumber}"}, 
			method = RequestMethod.POST)
	public VotingAsyncCallResponse downvotePost(@PathVariable Long postId, @PathVariable Long postNumber) {
		VotingAsyncCallResponse response = votingService.voteOnPost(postId, postNumber, VoteType.DOWN);
		
		return response;
	}
	
}