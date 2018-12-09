package project2.mobile.cs.fsu.edu.blogapp;

import com.google.firebase.firestore.Exclude;

class User{

    User(){
        // Firestore wants an empty constructor
    }

    User(String username, String email){
        this.setUsername(username);
        this.setEmail(email);
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