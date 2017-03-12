package mk.ukim.finki.finkit.repositories;

import java.util.List;

import mk.ukim.finki.finkit.enumeration.VoteType;
import mk.ukim.finki.finkit.model.data.UserPostVote;

public interface UserPostVotesRepository extends BaseRepository<UserPostVote> {
  
  UserPostVote findByUserIdAndPostId(Long userId, Long postId);
  
  List<UserPostVote> findByUserId(Long userId);
  
  long countByPostIdAndVoteType(Long postId, VoteType voteType);
  
  long countByPostIdAndUserIdAndVoteType(Long postId, Long userId, VoteType voteType);

}
