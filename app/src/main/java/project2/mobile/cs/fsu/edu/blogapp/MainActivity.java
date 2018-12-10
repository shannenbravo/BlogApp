package project2.mobile.cs.fsu.edu.blogapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN = "main_activity_logs";
    public static final String PASS_USER = "pass_user";

    private FloatingActionButton addPostButton;
    private BottomNavigationView mainbottomNav;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference users = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpLoginListener();
    }

    void setUpLoginListener() {
        this.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView emailTV = MainActivity.this.findViewById(R.id.editEmail);
                TextView passwordTV = MainActivity.this.findViewById(R.id.editPassword);

                String email = emailTV.getText().toString();
                String password = passwordTV.getText().toString();

                login(email, password);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

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

    void login(final String email, final String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    currentUser = mAuth.getCurrentUser();
                    openArticleActivity(email);
                }else{
                    if(task.getException() != null) {
                        Toast.makeText(MainActivity.this,
                                "Login failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();

                        Log.d(MAIN, "Login failure.", task.getException());
                    }
                }
            }
        });
    }


    /*
        Asynchronously gets user info and then takes user to article activity.
    */
    void openArticleActivity(String email){
        users.document(email).get().addOnCompleteListener(this,
            new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot userInfo = task.getResult();
                        if(userInfo != null && userInfo.exists()){
                            User user = userInfo.toObject(User.class);
                            Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
                            intent.putExtra(PASS_USER, user);
                            startActivity(intent);
                        }else{
                            Log.d(MAIN, "User retrieval failure: User did not exist");
                        }
                    }else{
                        Log.d(MAIN, "User retrieval failure.", task.getException());
                    }
                }
            });
    }
}
