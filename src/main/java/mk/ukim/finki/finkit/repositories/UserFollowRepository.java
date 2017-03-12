package mk.ukim.finki.finkit.repositories;

import java.util.List;

import mk.ukim.finki.finkit.model.data.UserFollow;

public interface UserFollowRepository extends BaseRepository<UserFollow> {
	
	List<UserFollow> findByUserId(Long userId);
	
	UserFollow findByUserIdAndFollowerId(Long userId, Long followerId);
	
}
