package mk.ukim.finki.finkit.model.json;

import mk.ukim.finki.finkit.model.view.HomePageViewModel;

public class NewTopicAsyncCallResponse {

	private HomePageViewModel homePageViewModel;
	
	private boolean showError;
	
	private String errors;

	public HomePageViewModel getHomePageViewModel() {
		return homePageViewModel;
	}

	public void setHomePageViewModel(HomePageViewModel homePageViewModel) {
		this.homePageViewModel = homePageViewModel;
	}

	public boolean getShowError() {
		return showError;
	}

	public void setShowError(boolean showError) {
		this.showError = showError;
	}

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}
	
}
