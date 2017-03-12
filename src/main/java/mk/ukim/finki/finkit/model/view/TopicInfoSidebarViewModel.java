package mk.ukim.finki.finkit.model.view;

import org.apache.commons.lang3.StringUtils;

import mk.ukim.finki.finkit.service.ImageService;
import mk.ukim.finki.finkit.util.TopicInfoUtils;

public class TopicInfoSidebarViewModel {
	
	private Long id;

  private String subtopic;
  private Long favouritedBy;
  private String description;
  
  private String rules;
  
  private Long avatarId;
  private String moderator;
  
  private boolean isLoggedInUserSubscribed;
  
  public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
  public String getSubtopic() {
    return subtopic;
  }
  
  public void setSubtopic(String subtopic) {
    this.subtopic = subtopic;
  }
  
  public Long getFavouritedBy() {
    return favouritedBy;
  }
  
  public void setFavouritedBy(Long favouritedBy) {
    this.favouritedBy = favouritedBy;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public String[] getRules() {
    return StringUtils.split(rules, '|');
  }
  
  public void setRules(String rules) {
    this.rules = rules;
  }
  
  public Long getAvatarId() {
    return avatarId;
  }
  
  public String getAvatarUrl() {
    return ImageService.resolveUrlFromId(getAvatarId());
  }

  public void setAvatarId(Long avatarId) {
    this.avatarId = avatarId;
  }

  public String getModerator() {
    return moderator;
  }
  
  public void setModerator(String moderator) {
    this.moderator = moderator;
  }
  
  public String getFavouritedByFormatted() {
    return TopicInfoUtils.formatNumber(getFavouritedBy());
  }

	public boolean getIsLoggedInUserSubscribed() {
		return isLoggedInUserSubscribed;
	}

	public void setIsLoggedInUserSubscribed(boolean isLoggedInUserSubscribed) {
		this.isLoggedInUserSubscribed = isLoggedInUserSubscribed;
	}
	
}