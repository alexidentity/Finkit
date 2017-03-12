package mk.ukim.finki.finkit.model.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_subtopic_subscriptions")
public class UserSubtopicSubscriptions {
  
  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;
  
  @Column(name = "user_id")
  private Long userId;
  
  @Column(name = "topic_info_id")
  private Long subtopicId;
  
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

  public Long getSubtopicId() {
    return subtopicId;
  }

  public void setSubtopicId(Long subtopicId) {
    this.subtopicId = subtopicId;
  }

}
