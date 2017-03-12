package mk.ukim.finki.finkit.model.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mk.ukim.finki.finkit.enumeration.VoteType;

@Entity
@Table(name="user_comment_vote")
public class UserCommentVote {
  
  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;
  
  @Column(name = "user_id")
  private Long userId;
  
  @Column(name = "post_id")
  private Long postId;
  
  @Column(name = "comment_id")
  private Long commentId;
  
  @Column(name = "vote_timestamp")
  @Temporal(TemporalType.TIMESTAMP)
  private Date voteTimestamp;
  
  @Column(name = "vote_type")
  @Enumerated(EnumType.STRING)
  private VoteType voteType;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }
  
  public Long getCommentId() {
    return commentId;
  }

  public void setCommentId(Long commentId) {
    this.commentId = commentId;
  }

  public Date getVoteTimestamp() {
    return voteTimestamp;
  }

  public void setVoteTimestamp(Date voteTimestamp) {
    this.voteTimestamp = voteTimestamp;
  }

  public VoteType getVoteType() {
    return voteType;
  }

  public void setVoteType(VoteType voteType) {
    this.voteType = voteType;
  }
  
}
