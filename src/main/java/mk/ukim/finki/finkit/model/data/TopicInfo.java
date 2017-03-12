package mk.ukim.finki.finkit.model.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name="topic_info")
public class TopicInfo {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "subtopic")
	private String subtopic;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "rules")
	private String rules;
	
	@Column(name = "moderator")
	private String moderator;
	
  @Column(name = "navbar_background_colour")
  private String navbarBackgroundColour;
  
  @Column(name = "navbar_links_colour")
  private String navbarLinksColour;
  
  @Column(name = "navbar_current_topic_colour")
  private String navbarCurrentTopicColour;
  
  @Column(name = "navbar_current_topic_background_colour")
  private String navbarCurrentTopicBackgroundColour;
  
  @Column(name = "background_image_url")
  private String backgroundImageUrl;
	
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getRules() {
		return rules;
	}
	
	public void setRules(String rules) {
		this.rules = rules;
	}
	
	public String getModerator() {
		return moderator;
	}
	
	public void setModerator(String moderator) {
		this.moderator = moderator;
	}
	
	public String getNavbarBackgroundColour() {
    return navbarBackgroundColour;
  }

  public void setNavbarBackgroundColour(String navbarBackgroundColour) {
    this.navbarBackgroundColour = navbarBackgroundColour;
  }

  public String getNavbarLinksColour() {
    return navbarLinksColour;
  }

  public void setNavbarLinksColour(String navbarLinksColour) {
    this.navbarLinksColour = navbarLinksColour;
  }

  public String getNavbarCurrentTopicColour() {
    return navbarCurrentTopicColour;
  }

  public void setNavbarCurrentTopicColour(String navbarCurrentTopicColour) {
    this.navbarCurrentTopicColour = navbarCurrentTopicColour;
  }

  public String getNavbarCurrentTopicBackgroundColour() {
    return navbarCurrentTopicBackgroundColour;
  }

  public void setNavbarCurrentTopicBackgroundColour(String navbarCurrentTopicBackgroundColour) {
    this.navbarCurrentTopicBackgroundColour = navbarCurrentTopicBackgroundColour;
  }

  public String getBackgroundImageUrl() {
    return backgroundImageUrl;
  }

  public void setBackgroundImageUrl(String backgroundImageUrl) {
    this.backgroundImageUrl = backgroundImageUrl;
  }
  
  public String[] splitRules() {
    return StringUtils.split(rules, '|');
  }
}

