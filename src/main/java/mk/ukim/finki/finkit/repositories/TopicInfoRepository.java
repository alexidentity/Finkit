package mk.ukim.finki.finkit.repositories;

import java.util.List;

import mk.ukim.finki.finkit.model.data.TopicInfo;

public interface TopicInfoRepository extends BaseRepository<TopicInfo> {
	
	List<TopicInfo> findBySubtopic(String subtopic);
	
	TopicInfo findOneBySubtopic(String subtopic);
	
	List<TopicInfo> findByModerator(String moderator);
}
