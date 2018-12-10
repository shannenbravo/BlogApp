package project2.mobile.cs.fsu.edu.blogapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

class Blog implements Parcelable {

    Blog(){
        // Firestore requires an empty constructor
    }

    Blog(String blogName, String blogAuthor){
        this.setBlogId(blogId);
        this.setBlogName(blogName);
        this.setBlogAuthor(blogAuthor);
    }

    /* NOTE: Order of objects added here must be same as in writeToParcel */
    Blog(Parcel in){
        this.setBlogId(in.readString());
        this.setBlogName(in.readString());
        this.setBlogAuthor(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.getBlogId());
        dest.writeString(this.getBlogName());
        dest.writeString(this.getBlogAuthor());
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

    String blogId, blogName, blogAuthor;

}