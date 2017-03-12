package mk.ukim.finki.finkit.repositories;

import java.util.List;

import mk.ukim.finki.finkit.model.data.UserSubtopicSubscriptions;

public interface UserSubtopicSubscriptionsRepository extends BaseRepository<UserSubtopicSubscriptions> {
  
  long countBySubtopicId(Long subtopicId);
  
  List<UserSubtopicSubscriptions> findByUserId(Long userId);
  
  UserSubtopicSubscriptions findByUserIdAndSubtopicId(Long userId, Long subtopicId);

}
