package project2.mobile.cs.fsu.edu.blogapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
