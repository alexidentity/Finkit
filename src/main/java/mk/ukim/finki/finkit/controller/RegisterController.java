package mk.ukim.finki.finkit.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import mk.ukim.finki.finkit.model.data.User;
import mk.ukim.finki.finkit.repositories.UserRepository;
import mk.ukim.finki.finkit.service.UserSubtopicSubscriptionsService;

@Controller
public class RegisterController {
	
	private static final Long DEFAULT_AVATAR_ID = 1L;
	private static final String DEFAULT_ROLE = "USER";
	
	private static final List<Long> DEFAULT_TOPIC_SUBSCRIPTIONS = Arrays.asList(2L, 3L, 4L, 5L);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserSubtopicSubscriptionsService userSubtopicSubscriptionsService;

	@RequestMapping("/register")
	public ModelAndView registerPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("register");
		modelAndView.addObject("isUsernameTaken", false);
		modelAndView.addObject("errorMessage", "");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/create", method=RequestMethod.POST)
	public ModelAndView createUser(User user) {
		ModelAndView modelAndView = new ModelAndView();
		user.setAvatarId(DEFAULT_AVATAR_ID);
		user.setRole(DEFAULT_ROLE);
		
		User userFound = userRepository.findUserByLogin(user.getLogin());
		if (userFound == null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			user.setRegisteredDate(new Date());
			user.setAvatarId(DEFAULT_AVATAR_ID);
			userRepository.save(user);
			modelAndView.setViewName("redirect:/");
			
			Authentication auth = new UsernamePasswordAuthenticationToken(
					new org.springframework.security.core.userdetails.User(
							user.getLogin(), user.getPassword(), user.getAuthorities()), 
					null, 
					user.getAuthorities()
		  );
			
			SecurityContextHolder.getContext().setAuthentication(auth);
			
			for (Long topicId : DEFAULT_TOPIC_SUBSCRIPTIONS) {
				userSubtopicSubscriptionsService.manageTopicSubscription(topicId);
			}
		} else {
			modelAndView.setViewName("register");
			modelAndView.addObject("isUsernameTaken", true);
			String errorMessage = String.format("The username %s is taken!", userFound.getLogin());
			modelAndView.addObject("errorMessage", errorMessage);
		}
		
		return modelAndView;
	}
	
}