package mk.ukim.finki.finkit.repositories;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import mk.ukim.finki.finkit.enumeration.VoteType;
import mk.ukim.finki.finkit.model.data.UserPostVote;

@Repository
public class PostDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Long> searchPostsBySubtopic(List<String> subtopicFilter) {
		Date date = new Date();
		Date yesterday = DateUtils.addDays(date, -1);
		return performPostSearch(subtopicFilter, Optional.of(yesterday), Optional.absent());
	}
	
	public List<Long> searchPostsByTitle(String subtopic, String titleSearchFilter) {
		return performPostSearch(
				Lists.newArrayList(subtopic), 
				Optional.absent(), 
				Optional.fromNullable(titleSearchFilter));
	}
	
	private List<Long> performPostSearch(
			List<String> subtopicFilter, 
			Optional<Date> dateFilter,
			Optional<String> titleFilter) {
		
		String query = "select upv "
				+ "from UserPostVote upv, Post p "
				+ "where upv.postId = p.id "
				+ "and p.subtopic in :subtopics ";
		
		if (dateFilter.isPresent()) {
			query = query + "and p.postInfo > :dateFilter ";
		}
		
		if (titleFilter.isPresent()) {
			query = query + "and lower(p.title) like :titleFilter ";
		}
		
		TypedQuery<UserPostVote> typedQuery = entityManager.createQuery(query, UserPostVote.class);
		
		typedQuery.setParameter("subtopics", subtopicFilter);
		
		if (dateFilter.isPresent()) {
			Date filteringDate = dateFilter.get();
			typedQuery.setParameter("dateFilter", filteringDate);
		}
		
		if (titleFilter.isPresent()) {
			String titleFilteringString = titleFilter.get();
			typedQuery.setParameter("titleFilter", "%" + titleFilteringString.toLowerCase() + "%");
		}
		
		List<UserPostVote> userPostVotes = typedQuery.getResultList();
	
		Map<Long, Long> postIdToVotesMap = Maps.newHashMap();
		
		for (UserPostVote userPostVote : userPostVotes) {
			Long postId = userPostVote.getPostId();
			if (postIdToVotesMap.containsKey(postId)) {
				Long existingValue = postIdToVotesMap.get(postId);
				Long newValue = VoteType.UP.equals(userPostVote.getVoteType()) ? existingValue + 1 : existingValue - 1;  
				postIdToVotesMap.put(postId, newValue);
			} else {
				Long value = VoteType.UP.equals(userPostVote.getVoteType()) ? 1L : -1L;  
				postIdToVotesMap.put(postId, value);
			}
		}
		
		List<VotedPost> votedPosts = Lists.newArrayList();
		for (Entry<Long, Long> postIdVotesEntry : postIdToVotesMap.entrySet()) {
			votedPosts.add(new VotedPost(postIdVotesEntry.getKey(), postIdVotesEntry.getValue()));
		}
		
		Collections.sort(votedPosts);
		
		List<Long> relevantPostIds = Lists.newArrayList();
		for (VotedPost votedPost : votedPosts) {
			relevantPostIds.add(votedPost.getPostId());
		}
		
		return relevantPostIds;
	}
	
}

class VotedPost implements Comparable<VotedPost> {
	
	private Long postId;
	private Long votes;
	
	VotedPost(Long postId, Long votes) {
		this.postId = postId;
		this.votes = votes;
	}
	
	Long getPostId() {
		return postId;
	}

	long getVotes() {
		return votes;
	}

	@Override
	public int compareTo(VotedPost o) {
		return o.votes.compareTo(this.votes);
	}
	
}