package project2.mobile.cs.fsu.edu.blogapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class NewPostActivity extends AppCompatActivity {

    private EditText blogPost, blogTitle;
    private Spinner topics;
    private Button postBlogButton;
    private ProgressDialog progressBar;

    static final String NEW_POST = "new_post";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference blogs = db.collection("blogs");

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            return;
        }
        user = extras.getParcelable(MainActivity.PASS_USER);
        if(user == null){
            Log.i(NEW_POST, "Failure to open post creation, user did not exist.");
            return;
        }

        getSupportActionBar().setTitle("Add New Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        blogTitle = findViewById(R.id.postTitle_editText);
        blogPost = findViewById(R.id.blogPost_editText);
        topics = findViewById(R.id.topics_spinner);
        postBlogButton = findViewById(R.id.postBlog_button);

        progressBar = new ProgressDialog(this);

        postBlogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setMessage("Posting BlogPost...");
                progressBar.show();

                final String blogText = blogPost.getText().toString();
                final String title = blogTitle.getText().toString();
                final String topic = topics.getSelectedItem().toString();

                String[] array = getResources().getStringArray(R.array.blogPosts_topics);
                String defaultOption = "";
                if(array.length > 0){
                    defaultOption = array[0];
                }

                if(!TextUtils.isEmpty(blogText) && !TextUtils.isEmpty(title)
                        && defaultOption != null && !topic.equals(defaultOption)) {
                    Blog blog = new Blog();
                    blog.setBlogAuthor(user.getUsername());
                    blog.setBlogName(title);
                    blog.setPost(blogText);
                    blog.setTopic(topic);
                    blog.setTimestamp(new Date());
                    blogs.document().set(blog);
                }else{
                    Toast.makeText(NewPostActivity.this,
                            "All fields must be filled in!", Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                    return;
                }
                Intent intent = new Intent(NewPostActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.PASS_USER, user);
                startActivity(intent);
                finish();
            }
        });

    }
}
