package com.example.lmsbracubyakil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button viewNotice, viewFile, viewAssignment, submitAssignment, stdLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewNotice = (Button) findViewById(R.id.view_notice);
        viewFile = (Button) findViewById(R.id.view_file);
        viewAssignment = (Button) findViewById(R.id.assignments);
        submitAssignment = (Button) findViewById(R.id.submit_assignments);
        stdLogout = (Button) findViewById(R.id.student_logout);

        viewNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewNoticeActivity.class);
                startActivity(intent);
            }
        });

        viewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewFilesActivity.class);
                startActivity(intent);
            }
        });

        viewAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewAssignmentActivity.class);
                startActivity(intent);
            }
        });

        submitAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SubmitActivity.class);
                startActivity(intent);
            }
        });

        stdLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
