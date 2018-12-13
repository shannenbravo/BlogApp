package project2.mobile.cs.fsu.edu.blogapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



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
        View mView;
        public BlogPostViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setTitle(String title){
            TextView post_title = mView.findViewById(R.id.blog_title);
            post_title.setText(title);
        }
        public void setAuthor(String author){
            TextView postAuthor = mView.findViewById(R.id.blogger_name);
            postAuthor.setText(author);
        }

        public void setPost(String post){
            TextView blogPost = mView.findViewById(R.id.blog_post);
            blogPost.setText(post);
        }

        public void setTopic(String topic){
            TextView postTopic = mView.findViewById(R.id.blog_topic);
            postTopic.setText(topic);
        }

        public void setImage_thumb(String image_thumb) {
            ImageView userImage = mView.findViewById(R.id.blog_user_image);
            userImage.setVisibility(View.VISIBLE);
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
