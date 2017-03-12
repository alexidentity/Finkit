package mk.ukim.finki.finkit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import mk.ukim.finki.finkit.model.change.NewPostModel;
import mk.ukim.finki.finkit.model.data.Post;
import mk.ukim.finki.finkit.model.data.TopicInfo;
import mk.ukim.finki.finkit.model.view.NewPostViewModel;
import mk.ukim.finki.finkit.repositories.TopicInfoRepository;
import mk.ukim.finki.finkit.service.PostService;
import mk.ukim.finki.finkit.service.UserService;
import mk.ukim.finki.finkit.service.UserSubtopicSubscriptionsService;

@Controller
public class NewPostController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;
	
	@Autowired
	private TopicInfoRepository topicInfoRepository;
	
	@Autowired
	private UserSubtopicSubscriptionsService userSubtopicSubscriptionsService;
	
  @RequestMapping(path = "/t/{subtopicName}/newpost", method = RequestMethod.GET)
	public ModelAndView newPostPage(@PathVariable("subtopicName") String subtopicName) {
  	
  	List<TopicInfo> subtopicList = topicInfoRepository.findBySubtopic(subtopicName);
  	TopicInfo topicInfo = subtopicList.get(0);
  	
  	String[] rules = topicInfo.splitRules();
  	
    ModelAndView modelAndView = new ModelAndView("newpost");
    NewPostViewModel newPostViewModel = new NewPostViewModel();
    newPostViewModel.setSubtopicName(subtopicName);
    newPostViewModel.setRules(rules);
    
    Long userId = userService.getUserIdFromLoggedInUser();
    List<String> subtopicSubscriptionList = userSubtopicSubscriptionsService.getUserSubscriptions(userId);
    newPostViewModel.setSubtopicSubscriptionList(subtopicSubscriptionList);
    
    modelAndView.addObject("viewModel", newPostViewModel);
    
    return modelAndView;
	}
	
	@RequestMapping(path = "/t/{subtopicName}/newpost", method = RequestMethod.POST)
	public String createNewPost(@PathVariable("subtopicName") String subtopicName, NewPostModel newPostModel) {
	  Post post = postService.createNewPostAndPersistInDatabase(subtopicName, newPostModel);
	  return String.format("redirect:/post/%s", post.getId());
	}

}