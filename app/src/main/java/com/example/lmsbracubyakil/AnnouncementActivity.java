package com.example.lmsbracubyakil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class AnnouncementActivity extends AppCompatActivity {

    EditText myNotice;
    Button notice_upload;


    //StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        myNotice = (EditText) findViewById(R.id.notice1);
        notice_upload = (Button) findViewById(R.id.upload_notice);

        databaseReference = FirebaseDatabase.getInstance().getReference("Notice");

        notice_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    private void saveData() {

        String txt = myNotice.getText().toString();

        String key = databaseReference.push().getKey();

        Announcement announcement = new Announcement(txt);

        databaseReference.child(key).setValue(announcement);
        Toast.makeText(this, "Notice Added", Toast.LENGTH_SHORT).show();

        myNotice.setText("");

    }
}
