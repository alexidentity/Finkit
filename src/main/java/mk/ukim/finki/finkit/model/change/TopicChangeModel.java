package mk.ukim.finki.finkit.model.change;

import org.apache.commons.lang3.StringUtils;

public class TopicChangeModel {
  
  private String name;
  private String description;
  private String rules;
  private String navbarBackgroundColour;
  private String navbarLinksColour;
  private String navbarCurrentTopicColour;
  private String navbarCurrentTopicBackgroundColour;
  private String backgroundImageUrl;
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
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

  public boolean hasCustomTopicStyle() {
    boolean hasCustomTopicStyle = !StringUtils.isAnyBlank(navbarBackgroundColour, navbarLinksColour, navbarCurrentTopicColour, navbarCurrentTopicBackgroundColour);
    return hasCustomTopicStyle;
  }
  
}
