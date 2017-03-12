CREATE TABLE `static_resource` (
	`id` number(10) NOT NULL AUTO_INCREMENT,
	`content` blob NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `users` (  
  `id` number(10) NOT NULL AUTO_INCREMENT,  
  `login` varchar(20) NOT NULL,  
  `password` varchar(80) NOT NULL,  
  `role` varchar(30) NOT NULL,
  `avatar_id` number(10) NOT NULL,
  `registered_date` date NOT NULL,
  
  PRIMARY KEY (`id`),
  CONSTRAINT fk_avatarId2 FOREIGN KEY (avatar_id)
	REFERENCES static_resource(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `user_follows` (
	`id` number(10) NOT NULL AUTO_INCREMENT,
	`user_id` number(10) NOT NULL,
	`follower_id` number(10) NOT NULL,
	
	CONSTRAINT fk_userId2 FOREIGN KEY (user_id)
	REFERENCES users(id),
	CONSTRAINT fk_followerId FOREIGN KEY (follower_id)
	REFERENCES users(id),
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `posts` (
	`id` number(10) NOT NULL AUTO_INCREMENT,
	`title` varchar(100) NOT NULL, 
	`description` varchar(2000) NOT NULL, 
	`post_link` varchar(255) NOT NULL, 
	`post_info` timestamp NOT NULL,
	`posted_by` varchar(20) NOT NULL, 
	`subtopic` varchar(100) NOT NULL, 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `topic_info` (
	`id` number(10) NOT NULL AUTO_INCREMENT,
	`subtopic` varchar(100) NOT NULL,
	`description` varchar(4000) NOT NULL,
	`rules` varchar(4000),
	`moderator` varchar(20) NOT NULL,
	`navbar_background_colour` varchar(10),
	`navbar_links_colour` varchar(10),
	`navbar_current_topic_colour` varchar(10),
	`navbar_current_topic_background_colour` varchar(10),
	`background_image_url` varchar(256)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `comments` (
	`id` number(10) NOT NULL AUTO_INCREMENT,
	`content` varchar(5000) NOT NULL,
	`created` timestamp NOT NULL,
	`user_id` number(10) NOT NULL,
	`post_id` number(10) NOT NULL,
	`parent_comment_id` number(10),
	
	CONSTRAINT fk_userId FOREIGN KEY (user_id)
	REFERENCES users(id),
	CONSTRAINT fk_postId FOREIGN KEY (post_id)
	REFERENCES posts(id),
	CONSTRAINT fk_parentCommentId FOREIGN KEY (parent_comment_id)
	REFERENCES comments(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `user_post_vote` (
	`id` number(10) NOT NULL AUTO_INCREMENT,
	`user_id` number(10) NOT NULL,
	`post_id` number(10) NOT NULL,
	`vote_timestamp` timestamp NOT NULL,
	`vote_type` varchar(5) NOT NULL CHECK `vote_type` IN ('UP', 'DOWN')
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `user_comment_vote` (
	`id` number(10) NOT NULL AUTO_INCREMENT,
	`user_id` number(10) NOT NULL,
	`post_id` number(10) NOT NULL,
	`comment_id` number(10) NOT NULL,
	`vote_timestamp` timestamp NOT NULL,
	`vote_type` varchar(5) NOT NULL CHECK `vote_type` IN ('UP', 'DOWN')
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `user_subtopic_subscriptions` (
	`id` number(10) NOT NULL AUTO_INCREMENT,
	`user_id` number(10) NOT NULL,
	`topic_info_id` number(10) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
