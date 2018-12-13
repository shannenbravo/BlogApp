package project2.mobile.cs.fsu.edu.blogapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    public static final String REGISTER = "register_log";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference users = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        verifiedRegistration(new User("saenae", "seanebanks1@gmail.com"), "123456");
    }

    /*
        verifiedRegistration checks Firestore for an existing user before starting the registration
        process. Please use it instead of the below 'registration', which is a helper.

        Parameters: - User object (add any extra fields necessary there; email is used as a key)
                    - String password
    */
    void verifiedRegistration(final User user, final String password){
        users.document(user.getEmail()).get().addOnCompleteListener(this,
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot userInfo = task.getResult();
                            if(userInfo != null && userInfo.exists()){
                                Toast.makeText(
                                        Register.this,
                                        "A user with this email already exist.",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                registration(user, password);
                            }
                        }else{
                            Log.d(REGISTER, "Registration failure.", task.getException());
                        }
                    }
                });
    }
    void registration(final User user, final String password){
        Log.d(REGISTER, "Called registration");
        final String email = user.getEmail();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            currentUser = mAuth.getCurrentUser();
                            users.document(email).set(user);
                            Intent intent = new Intent(Register.this, LoginActivity.class);
                            intent.putExtra(EMAIL, user.getEmail());
                            intent.putExtra(PASSWORD, password);
                            startActivity(intent);
                            finish();
                        }else{
                            if(task.getException() != null) {
                                Toast.makeText(Register.this,
                                        "Registration failed: " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();

                                Log.d(REGISTER, "Registration failure.", task.getException());
                            }
                        }
                    }
                });
    }

}
