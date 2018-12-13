package project2.mobile.cs.fsu.edu.blogapp;

import java.util.Date;

public class BlogPost {

    public String title, author, post, topic, image_thumb;
    public Date timestamp;

    public BlogPost(String title, String post, String author, String topic, String image_thumb, Date timestamp) {
        this.title = title;
        this.post = post;
        this.author = author;
        this.topic = topic;
        this.image_thumb = image_thumb;
        this.timestamp = timestamp;


    }

    public BlogPost() {
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}