package com.example.lmsbracubyakil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class TeacherActivity extends AppCompatActivity {

    /*Button selectFile, upload;
    TextView notification;
    Uri pdfUri;
    Button fetch;

    FirebaseStorage storage;  //used for uploading files
    FirebaseDatabase database; //used to store URLS of uploaded files
    private UploadTask uploadTask;*/

    Button uploadSlides, uploadTask, viewAssignmentSubmission, logout, publishNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        uploadSlides = (Button) findViewById(R.id.upload_slide);
        uploadTask = (Button) findViewById(R.id.upload_task);
        viewAssignmentSubmission = (Button) findViewById(R.id.view_submitted_assignment);
        publishNotice = (Button) findViewById(R.id.publish_notice);
        logout = (Button) findViewById(R.id.teacher_logout);

        publishNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherActivity.this, AnnouncementActivity.class);
                startActivity(intent);
            }
        });

        uploadSlides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });

        uploadTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherActivity.this, AssignmentActivity.class);
                startActivity(intent);
            }
        });

        viewAssignmentSubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherActivity.this, ViewSubmittedActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /*storage = FirebaseStorage.getInstance(); //return an object of firebase Storage
        database = FirebaseDatabase.getInstance(); //return an object of firebase Database

        selectFile = (Button) findViewById(R.id.select_file);
        upload = (Button) findViewById(R.id.upload_file);
        notification = (TextView) findViewById(R.id.file_selection_txt);

        //---------------Fetch---------------------
        fetch = (Button) findViewById(R.id.fetch_file);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(TeacherActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        selectPdf();
                }
                else{
                    ActivityCompat.requestPermissions(TeacherActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pdfUri!=null) {
                    uploadFile(pdfUri);
                }
                else{
                    Toast.makeText(TeacherActivity.this, "Select a file", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void uploadFile(Uri pdfUri) {

        Log.e("uri",pdfUri.getEncodedPath());

        final String fileName = pdfUri.getPath();
        StorageReference storageRef= storage.getReference();

        StorageReference riversRef = storageRef.child("uploads/"+pdfUri.getLastPathSegment());
        uploadTask = riversRef.putFile(pdfUri);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(TeacherActivity.this, "file not successfully uploaded: "+exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Toast.makeText(TeacherActivity.this, "file successfully uploaded", Toast.LENGTH_SHORT).show();
            }
        });

//        storageRef.child("Uploads").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                // Got the download URL for 'users/me/profile.png' in uri
//                String url=uri.toString();
//
//                DatabaseReference reference= database.getReference();
//
//                reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                        if (task.isSuccessful()){
//                            Toast.makeText(TeacherActivity.this, "file successfully uploaded", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
//                            Toast.makeText(TeacherActivity.this, "file not successfully uploaded", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//                Toast.makeText(TeacherActivity.this, "file not successfully uploaded 2", Toast.LENGTH_SHORT).show();
//
//            }
//        });*/

        //---------not ok--------------
       /* storageReference.child("Uploads").child(fileName).putFile(pdfUri).
                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //String url= taskSnapshot.getStorage().getDownloadUrl();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

            }
        });*/
       //--------not ok----------
    /*}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==9 && grantResults[0]==getPackageManager().PERMISSION_GRANTED){
            selectPdf();
        }
        else {
            Toast.makeText(this, "Pls provide permission", Toast.LENGTH_SHORT).show();
        }

    }

    private void selectPdf() {
            //to offer user to select a file using file manager

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT); //to fetch files
        startActivityForResult(intent,86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            notification.setText("A file is selected");
        } else {
            Toast.makeText(this, "Pls select a file", Toast.LENGTH_SHORT).show();
        }*/
    }
}

