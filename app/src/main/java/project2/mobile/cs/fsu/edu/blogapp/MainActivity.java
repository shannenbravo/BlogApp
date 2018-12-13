package project2.mobile.cs.fsu.edu.blogapp;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    public static final String MAIN = "main_activity_logs";
    public static final String PASS_USER = "pass_user";

    private FloatingActionButton addPostButton;
    private BottomNavigationView mainbottomNav;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference blogs = db.collection("blogs");

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
