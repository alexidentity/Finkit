package mk.ukim.finki.finkit.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.ukim.finki.finkit.enumeration.VoteType;
import mk.ukim.finki.finkit.model.data.UserCommentVote;
import mk.ukim.finki.finkit.model.data.UserPostVote;
import mk.ukim.finki.finkit.model.json.VotingAsyncCallResponse;
import mk.ukim.finki.finkit.repositories.UserCommentVotesRepository;
import mk.ukim.finki.finkit.repositories.UserPostVotesRepository;

@Service
public class VotingService {
	
	@Autowired
	private UserService userService;
  
  @Autowired
  private UserPostVotesRepository userPostVotesRepository;
  
  @Autowired
  private UserCommentVotesRepository userCommentVotesRepository;
  
  public VoteType getVoteTypeForUserOnPost(Long userId, Long postId) {
    
    UserPostVote userPostVote = userPostVotesRepository.findByUserIdAndPostId(userId, postId);
    
    if (userPostVote != null) {
      return userPostVote.getVoteType();
    }
    
    return null;
  }
  
  public long getUpvotesForPost(Long postId) {
    long upvotes = userPostVotesRepository.countByPostIdAndVoteType(postId, VoteType.UP);
    return upvotes;
  }

  public long getDownvotesForPost(Long postId) {
    long downvotes = userPostVotesRepository.countByPostIdAndVoteType(postId, VoteType.DOWN);
    return downvotes;
  }
  
  public boolean isPostUpvotedByUser(Long postId, Long userId) {
    long upvoteNumber = userPostVotesRepository.countByPostIdAndUserIdAndVoteType(postId, userId, VoteType.UP);
    
    if (upvoteNumber != 1) {
    	return false;
    }
    
    return true;
  }

  public boolean isPostDownvotedByUser(Long postId, Long userId) {
    long downvoteNumber = userPostVotesRepository.countByPostIdAndUserIdAndVoteType(postId, userId, VoteType.DOWN);
    
    if (downvoteNumber != 1) {
    	return false;
    }
    
    return true;
  }
  
  public void upvotePost(Long userId, Long postId) {
    vote(userId, postId, VoteType.UP);
  }
  
  private void downvotePost(Long userId, Long postId) {
    vote(userId, postId, VoteType.DOWN);
  }

  private void vote(Long userId, Long postId, VoteType voteType) {
    
    UserPostVote userPostVote = userPostVotesRepository.findByUserIdAndPostId(userId, postId);
    
    if (userPostVote != null) {
      if (voteType.equals(userPostVote.getVoteType())) {
        userPostVotesRepository.delete(userPostVote);
      } else {
        userPostVote.setVoteTimestamp(new Date());
        userPostVote.setVoteType(voteType);
        userPostVotesRepository.save(userPostVote);
      }
    } else {
      UserPostVote userPostUpvote = new UserPostVote();
      userPostUpvote.setUserId(userId);
      userPostUpvote.setPostId(postId);
      userPostUpvote.setVoteTimestamp(new Date());
      userPostUpvote.setVoteType(voteType);
      userPostVotesRepository.save(userPostUpvote);
    }
  }
  
  public VotingAsyncCallResponse voteOnPost(Long postId, Long postNumber, VoteType voteType) {
		VotingAsyncCallResponse response = new VotingAsyncCallResponse();
		response.setPostNumber(postNumber);
		response.setVoteType(voteType);
		
		Long userId = userService.getUserIdFromLoggedInUser();
		if (userId == null) {
			response.setHasError(true);
			response.setMessage("User is not logged in!");
			
			return response;
		}
		
		if (voteType.equals(VoteType.UP)) {
			upvotePost(userId, postId);
			response.setMessage("Upvote successfully registered!");
		} else {
			downvotePost(userId, postId);
			response.setMessage("Downvote successfully registered!");
		}
		
		response.setHasError(false);
		
		return response;
	}
  
  public long getUpvotesForComment(Long postId, Long commentId) {
    long upvotes = userCommentVotesRepository.countByPostIdAndCommentIdAndVoteType(postId, commentId, VoteType.UP);
    return upvotes;
  }

  public long getDownvotesForComment(Long postId, Long commentId) {
    long downvotes = userCommentVotesRepository.countByPostIdAndCommentIdAndVoteType(postId, commentId, VoteType.DOWN);
    return downvotes;
  }
  
  public boolean isCommentUpvotedByUser(Long postId, Long userId, Long commentId) {
    long upvoteNumber = userCommentVotesRepository.countByPostIdAndUserIdAndCommentIdAndVoteType(postId, userId, commentId, VoteType.UP);
    
    if (upvoteNumber != 1) {
    	return false;
    }
    
    return true;
  }

  public boolean isCommentDownvotedByUser(Long postId, Long userId, Long commentId) {
    long downvoteNumber = userCommentVotesRepository.countByPostIdAndUserIdAndCommentIdAndVoteType(postId, userId, commentId, VoteType.DOWN);
    
    if (downvoteNumber != 1) {
    	return false;
    }
    
    return true;
  }
  
  private void upvoteComment(Long userId, Long postId, Long commentId) {
  	voteComment(userId, postId, commentId, VoteType.UP);
  }
  
  private void downvoteComment(Long userId, Long postId, Long commentId) {
  	voteComment(userId, postId, commentId, VoteType.DOWN);
  }

  private void voteComment(Long userId, Long postId, Long commentId, VoteType voteType) {
    
    UserCommentVote userCommentVote = userCommentVotesRepository.findByUserIdAndPostIdAndCommentId(userId, postId, commentId);
    
    if (userCommentVote != null) {
      if (voteType.equals(userCommentVote.getVoteType())) {
      	userCommentVotesRepository.delete(userCommentVote);
      } else {
      	userCommentVote.setVoteTimestamp(new Date());
      	userCommentVote.setVoteType(voteType);
      	userCommentVotesRepository.save(userCommentVote);
      }
    } else {
    	UserCommentVote userCommentUpvote = new UserCommentVote();
    	userCommentUpvote.setUserId(userId);
    	userCommentUpvote.setPostId(postId);
    	userCommentUpvote.setCommentId(commentId);
    	userCommentUpvote.setVoteTimestamp(new Date());
    	userCommentUpvote.setVoteType(voteType);
    	userCommentVotesRepository.save(userCommentUpvote);
    }
  }
  
  public VotingAsyncCallResponse voteOnComment(Long postId, Long commentId, VoteType voteType) {
		VotingAsyncCallResponse response = new VotingAsyncCallResponse();
		response.setVoteType(voteType);
		
		Long userId = userService.getUserIdFromLoggedInUser();
		if (userId == null) {
			response.setHasError(true);
			response.setMessage("User is not logged in!");
			
			return response;
		}
		
		if (voteType.equals(VoteType.UP)) {
			upvoteComment(userId, postId, commentId);
			response.setMessage("Upvote successfully registered!");
		} else {
			downvoteComment(userId, postId, commentId);
			response.setMessage("Downvote successfully registered!");
		}
		
		response.setHasError(false);
		
		return response;
	}
  
}
