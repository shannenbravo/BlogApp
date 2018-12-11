package project2.mobile.cs.fsu.edu.blogapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewPostActivity extends AppCompatActivity {

    private EditText blogPost, blogTitle;
    private Spinner topics;
    private Button postBlogButton;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        getSupportActionBar().setTitle("Add New Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        blogTitle = findViewById(R.id.postTitle_editText);
        blogPost = findViewById(R.id.blogPost_editText);
        topics = findViewById(R.id.topics_spinner);
        postBlogButton = findViewById(R.id.postBlog_button);

        progressBar = new ProgressDialog(this);

//        topics = findViewById(R.id.topics_spinner);
//        ArrayAdapter<String> peopleArray = new ArrayAdapter<>(NewPostActivity.this, android.R.layout.simple_spinner_dropdown_item, blo);
//        peopleArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        topics.setAdapter(peopleArray);


        postBlogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String blogText = blogPost.getText().toString();
                final String title = blogTitle.getText().toString();


                if(!TextUtils.isEmpty(blogText) && !TextUtils.isEmpty(title)) {
                    progressBar.setMessage("Posting BlogPost...");
                    progressBar.show();
                    Intent newPostIntent = new Intent(NewPostActivity.this, MainActivity.class);
                    startActivity(newPostIntent);
                }
            }
        });


    }
}
