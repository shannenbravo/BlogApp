package project2.mobile.cs.fsu.edu.blogapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

class User implements Parcelable {

    User(){
        // Firestore requires an empty constructor
    }

    User(String username, String email){
        this.setUsername(username);
        this.setEmail(email);
    }

    /* NOTE: Order of objects added here must be same as in writeToParcel */
    User(Parcel in){
        this.setUsername(in.readString());
        this.setEmail(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.getUsername());
        dest.writeString(this.getEmail());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        public User createFromParcel(Parcel in) {
            return new User(in);
        }
        public User[] newArray(int size){
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Exclude
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    String username, email, userId;

}