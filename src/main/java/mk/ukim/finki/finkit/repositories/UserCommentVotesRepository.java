package mk.ukim.finki.finkit.repositories;

import mk.ukim.finki.finkit.enumeration.VoteType;
import mk.ukim.finki.finkit.model.data.UserCommentVote;

public interface UserCommentVotesRepository extends BaseRepository<UserCommentVote> {
  
  UserCommentVote findByUserIdAndPostIdAndCommentId(Long userId, Long postId, Long commentId);
  
  long countByPostIdAndCommentIdAndVoteType(Long postId, Long commentId, VoteType voteType);
  
  long countByPostIdAndUserIdAndCommentIdAndVoteType(Long postId, Long userId, Long commentId, VoteType voteType);

}