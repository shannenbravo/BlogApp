package project2.mobile.cs.fsu.edu.blogapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ArticleActivity extends AppCompatActivity {

    public static final String ARTICLE = "article_logs";

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            return;
        }
        user = extras.getParcelable(MainActivity.PASS_USER);
        loadArticles();
    }

    void loadArticles(){

    }

}
