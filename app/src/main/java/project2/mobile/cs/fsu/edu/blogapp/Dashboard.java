package project2.mobile.cs.fsu.edu.blogapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class Dashboard extends AppCompatActivity {

    LinearLayout write;
    LinearLayout readStuff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        write = (LinearLayout)findViewById(R.id.writePost);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Dashboard.this, NewPostActivity.class);
                startActivity(intent);
            }
        });

        readStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( Dashboard.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}
