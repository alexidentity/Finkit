package mk.ukim.finki.finkit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mk.ukim.finki.finkit.model.data.User;
import mk.ukim.finki.finkit.model.json.FollowingAsyncCallResponse;
import mk.ukim.finki.finkit.model.view.ProfileViewModel;
import mk.ukim.finki.finkit.service.ProfileService;
import mk.ukim.finki.finkit.service.UserService;

@Controller
public class ProfileController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileService profileService;
	
	@RequestMapping("/profile/{username}")
	public ModelAndView profilePage(@PathVariable String username) {
		ModelAndView modelAndView = new ModelAndView("profile");
		
		User user = userService.getUserFromUsername(username);
		
		if (user == null) {
			modelAndView.setViewName("error");
			return modelAndView;
		}

		ProfileViewModel profileViewModel = profileService.getProfileViewModel(user);
		
		modelAndView.setViewName("profile");
		modelAndView.addObject("viewModel", profileViewModel);
		
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping("/profile/{username}/follow")
	public FollowingAsyncCallResponse followUser(@PathVariable String username) {
		Long userId = userService.getUserIdFromLoggedInUser();
		
		User follower = userService.getUserFromUsername(username);
		Long followerId = follower.getId();
		
		boolean isUserFollowing = profileService.followOrUnfollowUser(userId, followerId);
		
		FollowingAsyncCallResponse response = new FollowingAsyncCallResponse();
		response.setUserFollowing(isUserFollowing);
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/profile/{username}/changeAvatar", method = RequestMethod.POST)
	public boolean changeAvatar(MultipartFile avatar) {
		
		boolean isAvatarChanged = userService.changeAvatar(avatar);
		return isAvatarChanged;
	}
	
}