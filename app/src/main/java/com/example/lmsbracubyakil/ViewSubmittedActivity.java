package com.example.lmsbracubyakil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewSubmittedActivity extends AppCompatActivity {

    ListView mySubmittedAssignment;
    DatabaseReference databaseReference;
    List<SubmitAssignment> viewSubmittedAssignments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_submitted);

        mySubmittedAssignment = (ListView) findViewById(R.id.mySubmittedAssignmentView);
        viewSubmittedAssignments = new ArrayList<>();

        viewAllFiles();

        mySubmittedAssignment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SubmitAssignment submitAssignment = viewSubmittedAssignments.get(position);

                Intent intent = new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(submitAssignment.getUrl()));
                startActivity(intent);

            }
        });


    }

    private void viewAllFiles() {

        databaseReference = FirebaseDatabase.getInstance().getReference("SubmittedAssignment");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                    SubmitAssignment submitAssignment = postSnapshot.getValue(SubmitAssignment.class);
                    viewSubmittedAssignments.add(submitAssignment);

                }

                String[] uploads = new String[viewSubmittedAssignments.size()];

                for (int i=0; i<uploads.length;i++){

                    uploads[i] = viewSubmittedAssignments.get(i).getName();

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, uploads);
                mySubmittedAssignment.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
