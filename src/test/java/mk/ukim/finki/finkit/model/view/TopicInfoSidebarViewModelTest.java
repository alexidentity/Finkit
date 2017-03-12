package mk.ukim.finki.finkit.model.view;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TopicInfoSidebarViewModelTest {
  
  @Test
  public void shouldSplitRules() {
    
    // Given
    String rules = "abc|cde|efg";
    
    TopicInfoSidebarViewModel viewModel = new TopicInfoSidebarViewModel();
    viewModel.setRules(rules);
    
    // When
    String[] splitRules = viewModel.getRules();
    assertThat(splitRules.length, Matchers.equalTo(3));
    assertThat(splitRules, Matchers.hasItemInArray("abc"));
    assertThat(splitRules, Matchers.hasItemInArray("cde"));
    assertThat(splitRules, Matchers.hasItemInArray("efg"));
  }
  
  @Test
  public void shouldNotSplitSingleRule() {
    
    // Given
    String rules = "abc";
    
    TopicInfoSidebarViewModel viewModel = new TopicInfoSidebarViewModel();
    viewModel.setRules(rules);
    
    // When
    String[] splitRules = viewModel.getRules();
    assertThat(splitRules.length, Matchers.equalTo(1));
    assertThat(splitRules, Matchers.hasItemInArray("abc"));
  }
  
  @Test
  public void encryption() {
  	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  	String encode = passwordEncoder.encode("test");
  	
  	System.out.println(encode);
  }
  
}
