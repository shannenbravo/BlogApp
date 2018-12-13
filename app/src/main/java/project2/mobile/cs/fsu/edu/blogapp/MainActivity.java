package project2.mobile.cs.fsu.edu.blogapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;
import android.view.View;
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
    private HomeFragment homeFragment;


    CollectionReference blogs = db.collection("blogs");
    private RecyclerView recyclerView;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            return;
        }
        user = extras.getParcelable(MainActivity.PASS_USER);
    }

    @Override
    protected void onStart() {
        super.onStart();
        asyncLoadBlogs();
    }

    void createDummyBlogs(){
        blogs.document().set(new Blog("Bob's Tech Blog", "Bob"));
        blogs.document().set(new Blog("Jim's Tech Blog", "Jim"));
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
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

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

//sean/austin this should work but i dont have the firebase stuff so i commented it out
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//        FirebaseRecyclerAdapter<BlogPost, BlogPostViewHolder> recycleAdapter = new FirebaseRecyclerAdapter<BlogPost, BlogPostViewHolder>(
//                Blogzone.class,
//                R.layout.blog_list_item,
//                BlogPostViewHolder.class,
//                mDatabase
//        )
//        {
//            @Override
//            protected void populateViewHolder(BlogPostViewHolder viewHolder, BlogPost model, int position) {
//                final String post_key = getRef(position).getKey().toString();
//                viewHolder.setImage_thumb(model.getImage_thumb());
//                viewHolder.setTitle(model.getTitle());
//                viewHolder.setAuthor(model.getAuthor());
//                viewHolder.setPost(model.getPost());
//                viewHolder.setTopic(model.getTopic());
//            }
//        };
//        recyclerView.setAdapter(recycleAdapter);
//    }

    public static class BlogPostViewHolder extends RecyclerView.ViewHolder{
        View view;
        public BlogPostViewHolder(View viewHolder) {
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
                Intent newPostIntent = new Intent(MainActivity.this, NewPostActivity.class);
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
