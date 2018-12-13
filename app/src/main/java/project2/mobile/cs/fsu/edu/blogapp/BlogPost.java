package project2.mobile.cs.fsu.edu.blogapp;

import java.util.Date;

public class BlogPost {

    public String username, title, post, topic, image_thumb;
    public Date timestamp;

    public BlogPost(String image_thumb, String username, String title, Date timestamp, String post, String topic) {
        this.image_thumb = image_thumb;
        this.username = username;
        this.title = title;
        this.timestamp = timestamp;
        this.post = post;
        this.topic = topic;
    }

    public BlogPost() {
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }



}