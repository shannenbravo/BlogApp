package project2.mobile.cs.fsu.edu.blogapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;

public class Register extends AppCompatActivity {

    public static final String REGISTER = "register_log";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference users = db.collection("users");

    Button registerButton;
    EditText username;
    EditText email;
    EditText password;
    EditText cPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getViews();
        setUpRegisterButtonListener();
    }

    void getViews(){
        registerButton = findViewById(R.id.registerButt);
        username = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.editPassword);
        cPassword = findViewById(R.id.cPassword);
    }
    void setUpRegisterButtonListener(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password != null && username != null && email != null) {
                    String passwordString = password.getText().toString();
                    String userNameString = username.getText().toString();
                    String emailString = email.getText().toString();

                    String err = null;
                    if (emailString.equals("") || userNameString.equals("") || passwordString.equals("")) {
                        err = "All fields must be filled in!";
                    }
                    if (!cPassword.getText().toString().equals(passwordString)) {
                        err = "Passwords do not match!";
                    }
                    if (err != null) {
                        showAlert(err);
                        return;
                    }
                    User user = new User(userNameString, emailString);
                    verifiedRegistration(user, passwordString);
                }
            }
        });
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
                                showAlert("A user with this email already exist.");
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
                                showAlert("Registration failed: " + task.getException());
                                Log.d(REGISTER, "Registration failure.", task.getException());
                            }
                        }
                    }
                });
    }


    public void showAlert(String errMessage){
        AlertDialog.Builder formNotComplete = new AlertDialog.Builder(Register.this);
        formNotComplete.setMessage(errMessage).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = formNotComplete.create();
        alert.setTitle("Alert!");
        alert.show();
    }
}
