package project2.mobile.cs.fsu.edu.blogapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public static final String MAIN = "main_activity_logs";
    public static final String PASS_USER = "pass_user";

    private FloatingActionButton addPostButton;
    private BottomNavigationView mainbottomNav;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    BlogAdapter blogAdapter;


    CollectionReference blogs = db.collection("blogs");
    private RecyclerView recyclerView;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRecyclerAdapter();

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            return;
        }
        user = extras.getParcelable(MainActivity.PASS_USER);
    }

    /*
        asyncLoadBlogs loads blogs asynchronously, then allows you to manipulate data from changed
        documents. You can use code generally outside of the switch statement, or do specific actions
        for when a document is added, modified, or removed.
    */

    void asyncLoadBlogs(){
        blogs.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.d(MAIN, "Problem loading blogs: " + e);
                    return;
                }
                if(queryDocumentSnapshots != null) {
                    for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                        DocumentSnapshot documentSnapshot = dc.getDocument();
                        Blog blog = documentSnapshot.toObject(Blog.class);
                        blog.setBlogId(documentSnapshot.getId());
                        int oldIndex = dc.getNewIndex(), newIndex = dc.getOldIndex();
                        switch(dc.getType()){
                            case ADDED:
                                Log.d(MAIN, "Added blog " + blog.blogName + " by " + blog.getBlogAuthor());
                                break;
                            case MODIFIED:
                                Log.d(MAIN, "Modified blog " + blog.blogName);
                                break;
                            case REMOVED:
                                Log.d(MAIN, "Removed blog " + blog.blogName);
                                break;
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkForUser();
        asyncLoadBlogs();
        blogAdapter.startListening();
    }
    void checkForUser(){
        if(user == null){
            Log.i(MAIN, "Send back to login");
            sendToLogin();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        blogAdapter.stopListening();
    }
    void setRecyclerAdapter(){
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        Query query = blogs.orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Blog> options = new FirestoreRecyclerOptions.Builder<Blog>()
                .setQuery(query, Blog.class)
                .build();

        blogAdapter = new BlogAdapter(options);
        recyclerView.setAdapter(blogAdapter);
    }

    public class BlogAdapter extends FirestoreRecyclerAdapter<Blog, BlogViewHolder>{
        public BlogAdapter(@NonNull FirestoreRecyclerOptions<Blog> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull BlogViewHolder holder, int position, @NonNull Blog model) {
            holder.setTitle(model.getBlogName());
            holder.setPost(model.getPost());
            holder.setTimestamp(model.getDateAsString());
            holder.setImage_thumb(model.getImageThumb());
            holder.setUsername(model.getBlogAuthor());
            holder.setTopic(model.getTopic());
        }

        @NonNull
        @Override
        public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_list_item,
                    parent, false);
            return new BlogViewHolder(v);
        }
    }
    public static class BlogViewHolder extends RecyclerView.ViewHolder{
        View view;
        public BlogViewHolder(View viewHolder) {
            super(viewHolder);
            view = viewHolder;
        }

        public void setUsername(String username){
            TextView postUser = view.findViewById(R.id.blogger_username);
            postUser.setText(username);
        }

        public void setTitle(String title){
            TextView post_title = view.findViewById(R.id.blog_title);
            post_title.setText(title);
        }

        public void setPost(String post){
            TextView blogPost = view.findViewById(R.id.blog_post);
            blogPost.setText(post);
        }

        public void setTopic(String topic){
            TextView postTopic = view.findViewById(R.id.blog_topic);
            postTopic.setText(topic);
        }

        public void setImage_thumb(String image_thumb) {
            ImageView userImage = view.findViewById(R.id.blog_user_image);
            userImage.setVisibility(View.VISIBLE);
        }

        public void setTimestamp(String date){
            TextView postDate = view.findViewById(R.id.blog_date);
            postDate.setText(date);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addPostOption:
                if(user == null){
                    Log.i(MAIN, "Could not send to newPostIntent, user was null");
                    sendToLogin();
                    return false;
                }
                Intent newPostIntent = new Intent(MainActivity.this, NewPostActivity.class);
                newPostIntent.putExtra(MainActivity.PASS_USER, user);
                Log.i(MAIN, "Sending to newPostIntent...");
                startActivity(newPostIntent);
                return true;

            case R.id.logoutOption:
                logOut();
                return true;

            default:
                return false;

        }
    }

    private void logOut() {

        sendToLogin();
    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }




}
