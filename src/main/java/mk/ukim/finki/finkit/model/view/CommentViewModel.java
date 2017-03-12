package mk.ukim.finki.finkit.model.view;

import mk.ukim.finki.finkit.model.change.CommentModel;

public class CommentViewModel implements Comparable<CommentViewModel> {
	
	private CommentModel comment;
	
  private long votes;
  
  private boolean isCommentUpvotedByUser;
  
  private boolean isCommentDownvotedByUser;

	public CommentModel getComment() {
		return comment;
	}

	public void setComment(CommentModel comment) {
		this.comment = comment;
	}

	public long getVotes() {
		return votes;
	}

	public void setVotes(long votes) {
		this.votes = votes;
	}

	public boolean getIsCommentUpvotedByUser() {
		return isCommentUpvotedByUser;
	}

	public void setIsCommentUpvotedByUser(boolean isCommentUpvotedByUser) {
		this.isCommentUpvotedByUser = isCommentUpvotedByUser;
	}

	public boolean getIsCommentDownvotedByUser() {
		return isCommentDownvotedByUser;
	}

	public void setIsCommentDownvotedByUser(boolean isCommentDownvotedByUser) {
		this.isCommentDownvotedByUser = isCommentDownvotedByUser;
	}

	@Override
	public int compareTo(CommentViewModel o) {
		return Long.valueOf(o.votes).compareTo(
				Long.valueOf(this.votes));
	}

}
