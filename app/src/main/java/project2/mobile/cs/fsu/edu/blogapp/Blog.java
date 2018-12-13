package project2.mobile.cs.fsu.edu.blogapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class Blog implements Parcelable {

    static final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    Blog(){
        // Firestore requires an empty constructor
    }

    Blog(String blogName, String blogAuthor, String imageThumb, Date timestamp, String post, String topic){
        this.setBlogId(blogId);
        this.setBlogName(blogName);
        this.setBlogAuthor(blogAuthor);
        this.setImageThumb(imageThumb);
        this.setBlogAuthor(blogAuthor);
        this.setTimestamp(timestamp);
        this.setPost(post);
        this.setTopic(topic);
    }

    /* NOTE: Order of objects added here must be same as in writeToParcel */
    Blog(Parcel in){
        this.setBlogId(in.readString());
        this.setBlogName(in.readString());
        this.setBlogAuthor(in.readString());
        this.setImageThumb(in.readString());
        this.setBlogAuthor(in.readString());
        this.setTimestamp(new Date(in.readLong()));
        this.setPost(in.readString());
        this.setTopic(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.getBlogId());
        dest.writeString(this.getBlogName());
        dest.writeString(this.getBlogAuthor());
        dest.writeString(this.getImageThumb());
        dest.writeString(this.getBlogAuthor());
        dest.writeLong(this.getTimestamp().getTime());
        dest.writeString(this.getPost());
        dest.writeString(this.getTopic());
    }

    public static final Creator<Blog> CREATOR = new Creator<Blog>(){
        public Blog createFromParcel(Parcel in) {
            return new Blog(in);
        }
        public Blog[] newArray(int size){
            return new Blog[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Exclude
    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getBlogAuthor() {
        return blogAuthor;
    }

    public void setBlogAuthor(String blogAuthor) {
        this.blogAuthor = blogAuthor;
    }

    public String getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(String imageThumb) {
        this.imageThumb = imageThumb;
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

    public Date getTimestamp() {
        return timestamp;
    }
    public String getDateAsString(){
        return format.format(this.getTimestamp());
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestampWithString(String timestamp) {
        this.timestamp = getDateFromString(timestamp);
    }

    String blogId, blogName, blogAuthor, imageThumb, post, topic;
    Date timestamp;

    public static Date getDateFromString(String dateString){
        try{
            return format.parse(dateString);
        }catch(ParseException e){
            return null;
        }
    }

}

