package mk.ukim.finki.finkit.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mk.ukim.finki.finkit.model.data.StaticResource;
import mk.ukim.finki.finkit.model.data.User;
import mk.ukim.finki.finkit.repositories.StaticResourceRepository;
import mk.ukim.finki.finkit.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StaticResourceRepository staticResourceRepository;
	
	public User getUserFromUsername(String login) {
		User user = userRepository.findUserByLogin(login);
		return user;
	}
	
	public User getUserFromUserId(Long userId) {
		User user = userRepository.findUserById(userId);
		return user;
	}

  public String getUsernameFromLoggedInUser() {

    Authentication authentication = getCurrentAuthenticationObject();
    String username = authentication.getName();
    
    boolean isUserAnonymous = StringUtils.equalsIgnoreCase(username, "anonymousUser");
    if (isUserAnonymous) {
    	return StringUtils.EMPTY;
    }

    return username;
  }
  
  public Long getUserIdFromLoggedInUser() {
  	User user = getLoggedInUser();
  	
  	if (user == null) {
  		return null;
  	}
  	
		return user.getId();
  }
  
  public User getLoggedInUser() {
  	
  	String username = getUsernameFromLoggedInUser();
  	
  	if (StringUtils.isBlank(username)) {
  		return null;
  	}
  	
  	User user = userRepository.findUserByLogin(username);
		return user;
  }

  private Authentication getCurrentAuthenticationObject() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication;
  }

	public boolean changeAvatar(MultipartFile avatar) {
		
		try {
			byte[] avatarBytes = avatar.getBytes();
			
			User loggedInUser = getLoggedInUser();
			
			StaticResource newAvatar = new StaticResource();
			newAvatar.setContent(avatarBytes);
			staticResourceRepository.save(newAvatar);
			loggedInUser.setAvatarId(newAvatar.getId());
			userRepository.saveAndFlush(loggedInUser);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
  
}