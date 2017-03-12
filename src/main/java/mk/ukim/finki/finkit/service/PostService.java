package mk.ukim.finki.finkit.service;

import mk.ukim.finki.finkit.enumeration.VoteType;
import mk.ukim.finki.finkit.mapper.TopicMapper;
import mk.ukim.finki.finkit.model.change.CommentModel;
import mk.ukim.finki.finkit.model.change.NewPostModel;
import mk.ukim.finki.finkit.model.data.Comment;
import mk.ukim.finki.finkit.model.data.Post;
import mk.ukim.finki.finkit.model.data.TopicInfo;
import mk.ukim.finki.finkit.model.data.User;
import mk.ukim.finki.finkit.model.view.CommentViewModel;
import mk.ukim.finki.finkit.model.view.PostViewModel;
import mk.ukim.finki.finkit.model.view.TopicInfoSidebarViewModel;
import mk.ukim.finki.finkit.repositories.CommentRepository;
import mk.ukim.finki.finkit.repositories.PostRepository;
import mk.ukim.finki.finkit.repositories.TopicInfoRepository;
import mk.ukim.finki.finkit.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private TopicInfoRepository topicInfoRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;
  
  @Autowired
  private TopicMapper topicMapper;
  
  @Autowired
  private VotingService votingService;
  
  @Autowired
	private UserSubtopicSubscriptionsService userSubtopicSubscriptionsService;

  public PostViewModel getPostViewModel(Long postId) {
    PostViewModel postViewModel = new PostViewModel();

    Post post = postRepository.findOne(postId);
    
    if (post == null) {
    	return null;
    }
    
    postViewModel.setPost(post);
    
    Long userId = userService.getUserIdFromLoggedInUser();
    
    boolean isPostUpvotedByUser = votingService.isPostUpvotedByUser(postId, userId);
    boolean isPostDownvotedByUser = votingService.isPostDownvotedByUser(postId, userId);
    
    postViewModel.setPostUpvotedByUser(isPostUpvotedByUser);
    postViewModel.setPostDownvotedByUser(isPostDownvotedByUser);

    String subtopic = post.getSubtopic();
    List<TopicInfo> topicInfoList = topicInfoRepository.findBySubtopic(subtopic);
    TopicInfo topicInfo = topicInfoList.get(0);
    
    String navbarBackgroundColour = topicInfo.getNavbarBackgroundColour();
  	if (navbarBackgroundColour.equals("DEFAULT")) {
  		postViewModel.setIsTopicStyleDefault(true);
  	} else {
    	postViewModel.setIsTopicStyleDefault(false);
  	}
    
    List<CommentViewModel> commentViewModelList = new ArrayList<>();

    List<Comment> comments = commentRepository.findByPostIdAndParentCommentIdIsNull(postId);
    for (Comment comment : comments) {
    	long commentId = comment.getId();
    	
    	CommentViewModel commentViewModel = new CommentViewModel();
    	
  		if (userId != null) {
  			boolean isCommentUpvotedByUser = votingService.isCommentUpvotedByUser(postId, userId, commentId);
  			commentViewModel.setIsCommentUpvotedByUser(isCommentUpvotedByUser);
  	    
  			boolean isCommentDownvotedByUser = votingService.isCommentDownvotedByUser(postId, userId, commentId);
  			commentViewModel.setIsCommentDownvotedByUser(isCommentDownvotedByUser);
  		} else {
  			commentViewModel.setIsCommentUpvotedByUser(false);
  			commentViewModel.setIsCommentDownvotedByUser(false);
  		}
    	
  		long upvotes = votingService.getUpvotesForComment(postId, commentId);
    	long downvotes = votingService.getDownvotesForComment(postId, commentId);
  		
    	CommentModel commentModel = mapCommentToCommentModel(comment, postId, userId);
  		commentViewModel.setComment(commentModel);
  		commentViewModel.setVotes(upvotes - downvotes);
  		
  		commentViewModelList.add(commentViewModel);
    }
    
    Collections.sort(commentViewModelList);
    postViewModel.setRootComments(commentViewModelList);

    String moderator = topicInfo.getModerator();
    User moderatorUser = userRepository.findUserByLogin(moderator);
    
    long upvotes = votingService.getUpvotesForPost(postId);
    postViewModel.setUpvotes(upvotes);
    
    long downvotes = votingService.getDownvotesForPost(postId);
    postViewModel.setDownvotes(downvotes);
    
    TopicInfoSidebarViewModel topicInfoSidebar = topicMapper.mapTopicInfoToTopicInfoSidebarViewModel(topicInfo, moderatorUser);
    postViewModel.setTopicInfoSidebar(topicInfoSidebar);
    
    List<String> subtopicSubscriptionList = userSubtopicSubscriptionsService.getUserSubscriptions(userId);
    postViewModel.setSubtopicSubscriptionList(subtopicSubscriptionList);
    
    return postViewModel;
  }
  
  private CommentModel mapCommentToCommentModel(Comment comment, Long postId, Long userId) {
  	CommentModel commentModel = new CommentModel();
  	commentModel.setId(comment.getId());
  	commentModel.setContent(comment.getContent());
  	commentModel.setCreated(comment.getCreated());
  	commentModel.setUser(comment.getUser());
  	commentModel.setPostId(comment.getId());
  	commentModel.setParentCommentId(comment.getParentCommentId());
  	
  	List<CommentViewModel> nestedCommentsViewModel = new ArrayList<CommentViewModel>();
  	
  	List<Comment> nestedComments = comment.getNestedComments();
  	if (!nestedComments.isEmpty()) {
  		for (Comment nestedComment : nestedComments) {
  			long commentId = nestedComment.getId();
      	
      	CommentViewModel commentViewModel = new CommentViewModel();
      	
    		if (userId != null) {
    			boolean isCommentUpvotedByUser = votingService.isCommentUpvotedByUser(postId, userId, commentId);
    			commentViewModel.setIsCommentUpvotedByUser(isCommentUpvotedByUser);
    	    
    			boolean isCommentDownvotedByUser = votingService.isCommentDownvotedByUser(postId, userId, commentId);
    			commentViewModel.setIsCommentDownvotedByUser(isCommentDownvotedByUser);
    		} else {
    			commentViewModel.setIsCommentUpvotedByUser(false);
    			commentViewModel.setIsCommentDownvotedByUser(false);
    		}
      	
    		long upvotes = votingService.getUpvotesForComment(postId, commentId);
      	long downvotes = votingService.getDownvotesForComment(postId, commentId);
    		
      	CommentModel nestedCommentModel = new CommentModel();
      	nestedCommentModel.setId(nestedComment.getId());
      	nestedCommentModel.setContent(nestedComment.getContent());
      	nestedCommentModel.setCreated(nestedComment.getCreated());
      	nestedCommentModel.setUser(nestedComment.getUser());
      	nestedCommentModel.setPostId(nestedComment.getId());
      	nestedCommentModel.setParentCommentId(nestedComment.getParentCommentId());
      	
    		commentViewModel.setComment(nestedCommentModel);
    		commentViewModel.setVotes(upvotes - downvotes);
    		
    		nestedCommentsViewModel.add(commentViewModel);
			}
  	}
  	
  	Collections.sort(nestedCommentsViewModel);
  	commentModel.setNestedComments(nestedCommentsViewModel);
  	
		return commentModel;
	}

	public Post createNewPostAndPersistInDatabase(String subtopicName, NewPostModel newPostModel) {
    
    Post post = new Post();
    
    post.setTitle(newPostModel.getPostTitle());
    post.setPostLink(newPostModel.getPostLink());
    post.setDescription(newPostModel.getPostText());
   
    String postedBy = userService.getUsernameFromLoggedInUser();
    post.setPostedBy(postedBy);
    
    Date postInfo = new Date();
		post.setPostInfo(postInfo);
    post.setSubtopic(subtopicName);
    
    postRepository.save(post);
    
    Long userId = userService.getUserIdFromLoggedInUser();
    votingService.upvotePost(userId, post.getId());
    
    return post;
  }
	
	public Comment saveCommentOnPost(User user, String content, Long postId, Long rootCommentId) {
		Comment commentEntity = new Comment();
		commentEntity.setContent(content);
		commentEntity.setCreated(new Date());
		commentEntity.setUser(user);
		commentEntity.setPostId(postId);
		commentEntity.setParentCommentId(rootCommentId);
		
		Comment savedComment = commentRepository.saveAndFlush(commentEntity);
		votingService.voteOnComment(postId, savedComment.getId(), VoteType.UP);
		
		return savedComment;
	}
}
