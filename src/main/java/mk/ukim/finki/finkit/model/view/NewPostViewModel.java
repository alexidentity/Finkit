package mk.ukim.finki.finkit.model.view;

import java.util.List;

public class NewPostViewModel {
	
	private String subtopicName;
	
	private String[] rules;
	
  private List<String> subtopicSubscriptionList;
  
	public String getSubtopicName() {
		return subtopicName;
	}

	public void setSubtopicName(String subtopicName) {
		this.subtopicName = subtopicName;
	}

	public String[] getRules() {
		return rules;
	}

	public void setRules(String[] rules) {
		this.rules = rules;
	}

	public List<String> getSubtopicSubscriptionList() {
		return subtopicSubscriptionList;
	}

	public void setSubtopicSubscriptionList(List<String> subtopicSubscriptionList) {
		this.subtopicSubscriptionList = subtopicSubscriptionList;
	}
	
}
