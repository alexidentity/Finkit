package mk.ukim.finki.finkit.model.json;

import mk.ukim.finki.finkit.enumeration.VoteType;

public class VotingAsyncCallResponse {

	private boolean hasError;
	
	private String message;
	
	private long postNumber;
	
	private VoteType voteType;

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getPostNumber() {
		return postNumber;
	}

	public void setPostNumber(long postNumber) {
		this.postNumber = postNumber;
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}
	
}
