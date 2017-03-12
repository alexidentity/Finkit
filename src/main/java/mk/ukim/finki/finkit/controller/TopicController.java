package mk.ukim.finki.finkit.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import mk.ukim.finki.finkit.mapper.TopicMapper;
import mk.ukim.finki.finkit.model.change.TopicChangeModel;
import mk.ukim.finki.finkit.model.data.TopicInfo;
import mk.ukim.finki.finkit.model.data.User;
import mk.ukim.finki.finkit.model.json.NewTopicAsyncCallResponse;
import mk.ukim.finki.finkit.model.view.HomePageViewModel;
import mk.ukim.finki.finkit.model.view.TopicInfoSidebarViewModel;
import mk.ukim.finki.finkit.repositories.TopicInfoRepository;
import mk.ukim.finki.finkit.service.TopicService;
import mk.ukim.finki.finkit.service.UserService;
import mk.ukim.finki.finkit.service.UserSubtopicSubscriptionsService;

@Controller
public class TopicController {
	
	public static final String DEFAULT_STYLE = "DEFAULT";
  
	@Autowired
  private UserService userService;
	
  @Autowired
  private TopicService topicService;
	
	@Autowired
	private TopicInfoRepository topicInfoRepository;
	
	@Autowired
  private TopicMapper topicMapper;
	
	@Autowired
	private UserSubtopicSubscriptionsService userSubtopicSubscriptionsService;

	@RequestMapping("/newtopic")
	public ModelAndView newTopicPage() {
		ModelAndView modelAndView = new ModelAndView("topic");
		
		HomePageViewModel homePageViewModel = topicService.createAnEmptyHomePageViewModel();
		modelAndView.addObject("viewModel", homePageViewModel);
		
		TopicInfo topicInfo = topicService.getTopicInfo("");
		modelAndView.addObject("topicInfo", topicInfo);
		
		modelAndView.addObject("hasCustomStyle", false);
		modelAndView.addObject("isEdit", false);
		
		return modelAndView;
	}
	
	@RequestMapping("/t/{topicName}/edit")
	public ModelAndView modifyTopicPage(@PathVariable String topicName) {
		ModelAndView modelAndView = new ModelAndView("topic");

		TopicInfo topicInfo = topicService.getTopicInfo(topicName);
		modelAndView.addObject("topicInfo", topicInfo);
		
		String loggedInUsername = userService.getUsernameFromLoggedInUser();
		String topicModerator = topicInfo.getModerator();
		if (!topicModerator.equals(loggedInUsername)) {
			modelAndView.setViewName("error");
		}
		
		HomePageViewModel homePageViewModel = topicService.createAnEmptyHomePageViewModel();
		modelAndView.addObject("viewModel", homePageViewModel);
		
		String navbarBackgroundColour = topicInfo.getNavbarBackgroundColour();
		if (navbarBackgroundColour.equals("DEFAULT")) {
			modelAndView.addObject("hasCustomStyle", false);
		} else {
			modelAndView.addObject("hasCustomStyle", true);
		}
		
		modelAndView.addObject("isEdit", true);
		
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value = "/t/{topicName}/edit/save/{hasCustomStyle}", method = RequestMethod.POST)
	public NewTopicAsyncCallResponse saveModifiedTopic(
			TopicChangeModel topicModel, 
			@PathVariable String topicName,
			@PathVariable boolean hasCustomStyle) {
		
		NewTopicAsyncCallResponse response = getNewTopicResponse(topicModel, hasCustomStyle, true);
		
		return response;
	}
	
	@RequestMapping("/t/{topic}/delete")
	public String deleteTopic() {
		return "topic";
	}
	
	@ResponseBody
	@RequestMapping(value = "/newtopic/save/{hasCustomStyle}", method = RequestMethod.POST)
	public NewTopicAsyncCallResponse saveTopic(TopicChangeModel topicModel, @PathVariable boolean hasCustomStyle) {
		
		NewTopicAsyncCallResponse response = getNewTopicResponse(topicModel, hasCustomStyle, false);
		
		return response;
	}

	private NewTopicAsyncCallResponse getNewTopicResponse(TopicChangeModel topicModel, boolean hasCustomStyle, boolean isEdit) {
		boolean hasErrors = false;
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<ul>");
		
		String name = topicModel.getName();
		
		List<TopicInfo> subtopics = topicInfoRepository.findBySubtopic(name);
		
		if (!subtopics.isEmpty() && !isEdit) {
			stringBuilder.append("<li>A topic with the name '" + name + "' already exists!</li>");
			hasErrors = true;
		}
		
		String backgroundImageUrl = topicModel.getBackgroundImageUrl();
    boolean isBackgroundImageUrlAnImage = checkIfBackgroundImageUrlIsAnImage(backgroundImageUrl);
		
		if (!isBackgroundImageUrlAnImage && hasCustomStyle) {
			stringBuilder.append("<li>The background url is not a picture!</li>");
			hasErrors = true;
		}
		
		if (!hasErrors) {
			if (!hasCustomStyle) {
				topicModel.setNavbarBackgroundColour(DEFAULT_STYLE);
				topicModel.setNavbarLinksColour(DEFAULT_STYLE);
				topicModel.setNavbarCurrentTopicBackgroundColour(DEFAULT_STYLE);
				topicModel.setNavbarCurrentTopicColour(DEFAULT_STYLE);
				topicModel.setBackgroundImageUrl(DEFAULT_STYLE);
			}
			
			if (isEdit) {
				topicService.modifyExistingTopic(topicModel);
			} else {
				topicService.createNewTopic(topicModel);
			}
		}
		
		stringBuilder.append("</ul>");
		
		User loggedInUser = userService.getLoggedInUser();
		
		TopicInfo topicInfo = topicInfoRepository.findOneBySubtopic("all");
		TopicInfoSidebarViewModel topicInfoSidebarViewModel = topicMapper.mapTopicInfoToTopicInfoSidebarViewModel(topicInfo, loggedInUser);
		
		HomePageViewModel homePageViewModel = new HomePageViewModel();
		homePageViewModel.setTopicInfoSidebar(topicInfoSidebarViewModel);
		
		List<String> subtopicSubscriptionList = userSubtopicSubscriptionsService.getUserSubscriptions(loggedInUser.getId());
		homePageViewModel.setSubtopicSubscriptionList(subtopicSubscriptionList);
		
		NewTopicAsyncCallResponse response = new NewTopicAsyncCallResponse();
		response.setHomePageViewModel(homePageViewModel);
		response.setShowError(hasErrors);
		response.setErrors(stringBuilder.toString());
		
		return response;
	}
	
	@RequestMapping("/dynamic/css/{topicName}.css")
	public ResponseEntity<byte[]> getCustomTopicStyle(
			@PathVariable String topicName, HttpServletResponse response) {
		
		response.setContentType("text/css");
		
		String styleSheetForTopic = topicService.getStyleSheetForTopic(topicName);
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(styleSheetForTopic.getBytes(), HttpStatus.OK);
		return responseEntity;
	}
	
	public boolean checkIfBackgroundImageUrlIsAnImage(String backgroundImageUrl) {
		
		UrlValidator urlValidator = new UrlValidator();
		boolean isUrlValid = urlValidator.isValid(backgroundImageUrl);
		
		if (isUrlValid) {
			try {
		    HttpURLConnection urlConnection = (HttpURLConnection) new URL(backgroundImageUrl).openConnection();
		    urlConnection.setInstanceFollowRedirects(true);
		    HttpURLConnection.setFollowRedirects(true);

		    int status = urlConnection.getResponseCode();
		    if (status >= 300 && status <= 307) {
	        backgroundImageUrl = urlConnection.getHeaderField("Location");
	        urlConnection = (HttpURLConnection) new URL(backgroundImageUrl).openConnection();
		    }
		    
		    String contentType = urlConnection.getHeaderField("Content-Type");
		    if (contentType.startsWith("image/")) {
	        return true;
		    }
		    
			} catch (IOException e) {
		    e.printStackTrace();
			}
		}
		
		return false;
	}

}