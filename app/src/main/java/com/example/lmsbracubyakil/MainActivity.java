package com.example.lmsbracubyakil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lmsbracubyakil.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText  InputId, InputPassword;
    private ProgressDialog loadingBar;
    private Button loginButton;
    private TextView adminTxt, teacherTxt, studentTxt;

    private String parentDbname = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        InputId = (EditText) findViewById(R.id.user_id);
        InputPassword = (EditText) findViewById(R.id.user_pass);
        loadingBar = new ProgressDialog(this);
        loginButton = (Button) findViewById(R.id.login_btn);
        adminTxt = (TextView) findViewById(R.id.admin_txt);
        teacherTxt = (TextView) findViewById(R.id.teacher_txt);
        studentTxt = (TextView) findViewById(R.id.student_txt);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });


        teacherTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login Sir");
                teacherTxt.setVisibility(View.INVISIBLE);
                studentTxt.setVisibility(View.VISIBLE);

                parentDbname = "Teachers";
            }
        });
        studentTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login");
                teacherTxt.setVisibility(View.VISIBLE);
                studentTxt.setVisibility(View.INVISIBLE);
                parentDbname = "Users";
            }
        });


        adminTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void LoginUser() {

        String id = InputId.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(id)){
            Toast.makeText(this, "Pls insert id", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Pls insert password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToACcount(id, password);
        }

    }

    private void AllowAccessToACcount(final String id, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbname).child(id).exists()){
                    Users userData = dataSnapshot.child(parentDbname).child(id).getValue(Users.class);

                    if (userData.getId().equals(id)){
                        if (userData.getPassword().equals(password)){

                            if (parentDbname.equals("Teachers")){
                                Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(MainActivity.this, TeacherActivity.class);
                                startActivity(intent);
                            }
                            else if (parentDbname.equals("Users")){

                                Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(MainActivity.this, "Invalid ID or Password", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                        else {
                            Toast.makeText(MainActivity.this, "Invalid ID or Password", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Account with this " + id +" id do not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
