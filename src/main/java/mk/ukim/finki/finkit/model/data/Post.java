package mk.ukim.finki.finkit.model.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import mk.ukim.finki.finkit.util.DateFormatterUtils;

@Entity
@Table(name="posts")
public class Post {
	
	@Id
	@GeneratedValue
  @Column(name = "id")
	private Long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "post_link")
	private String postLink;
	
	@Column(name = "post_info")
	private Date postInfo;
	
	@Column(name = "posted_by")
	private String postedBy;
	
	@Column(name = "subtopic")
	private String subtopic;
	
	public Long getId() {
    return id;
	}
	
	public void setId(Long id) {
    this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getShortDescription() {
		return StringUtils.abbreviate(description, 150);
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPostLink() {
		return postLink;
	}

	public void setPostLink(String postLink) {
		this.postLink = postLink;
	}

	public Date getPostInfo() {
		return postInfo;
	}
	
	public String getPostInfoFormatted() {
		return DateFormatterUtils.formatDate(getPostInfo());
	}
	
	public void setPostInfo(Date postInfo) {
		this.postInfo = postInfo;
	}
	
	public String getPostedBy() {
		return postedBy;
	}
	
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}
	
	public String getSubtopic() {
		return subtopic;
	}
	
	public void setSubtopic(String subtopic) {
		this.subtopic = subtopic;
	}
	
}
