package project2.mobile.cs.fsu.edu.blogapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    public static final String MAIN = "login_logs";

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference users = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkForLoginFromRegistration();
        setUpLoginListener();
        setUpRegisterListener();
    }
    void checkForLoginFromRegistration(){
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String email = extras.getString(Register.EMAIL);
            String password = extras.getString(Register.PASSWORD);
            if(email != null && password != null) {
                if (!email.equals("") && !password.equals("")) {
                    login(email, password);
                }
            }
        }
    }
    void setUpRegisterListener(){
        this.findViewById(R.id.reggi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });
    }
    void setUpLoginListener() {
        this.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView emailTV = LoginActivity.this.findViewById(R.id.editEmail);
                TextView passwordTV = LoginActivity.this.findViewById(R.id.editPassword);

                String email = emailTV.getText().toString();
                String password = passwordTV.getText().toString();

                login(email, password);
            }
        });
    }
    void login(final String email, final String password){
        if(email.equals("") || password.equals("")){
            Toast.makeText(LoginActivity.this,
                    "Login failed: Must input both email and password",
                    Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    currentUser = mAuth.getCurrentUser();
                    openMainActivity(email);
                }else{
                    if(task.getException() != null) {
                        Toast.makeText(LoginActivity.this,
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
    void openMainActivity(String email){
        users.document(email).get().addOnCompleteListener(this,
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot userInfo = task.getResult();
                            if(userInfo != null && userInfo.exists()){
                                User user = userInfo.toObject(User.class);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra(MainActivity.PASS_USER, user);
                                startActivity(intent);
                                finish();
                            }else{
                                Log.d(MAIN, "User retrieval failure: User did not exist");
                            }
                        }else{
                            Log.d(MAIN, "User retrieval failure.", task.getException());
                        }
                    }
                });
    }
    void openRegister(){
        Intent intent = new Intent(LoginActivity.this, Register.class);
        startActivity(intent);
        finish();
    }
}
