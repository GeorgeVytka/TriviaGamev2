package com.example.triviagame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class SignUpPage extends AppCompatActivity {
    // FirebaseAuth firebaseAuth;

    //define a constant for your tag in MainActivity
    private static final String TAG = "LoginActivity";

    private static final int REQUEST_SIGNUP = 0;

    private Button buttonRegister, btn_BackHome, btn_UserInfo;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private TextView alreadyLoggedIn_tv;
    private FirebaseAuth firebaseAuth;
    private ConstraintLayout layout;


    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //validate whats entered on the fields
        if(!validate(email,password)){
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        progressDialog.dismiss();
        /*
        Firebase
        * Create a new createAccount method which takes in an email address and password,
        * validates them and then creates a new user with the createUserWithEmailAndPassword method.
        *
        * */
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpPage.this,"Registered Successfully", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                }else{
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(SignUpPage.this,"Could not register", Toast.LENGTH_SHORT).show();

                    //use this to go to different activity
                    //updateUI(null);
                }
            }


        });

    }

    private void btnBackToHomeClicked(){

        btn_BackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPage.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
//update the ui or send user to different activity

    public void updateUI(FirebaseUser user){

        // startActivity(new Intent(LoginActivity.this, CreateAccount.class));

    }


    /*
     * validate :boolean
     *
     * This function checks if the email and password fields are empty or
     * the length is wrong. Also outputs a error message
     *
     * returns if valid is right
     * */

    public boolean validate(String email, String password){
        boolean valid = true;

        //get contains of the fields


        //check if email field is empty or email is wrong format
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("enter a valid email address");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            editTextPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }
        return valid;
    }

    private void initialize() {

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister = findViewById(R.id.btnSignUp);

        editTextEmail = findViewById(R.id.etEmailID);

        editTextPassword = findViewById(R.id.etPassword);

        alreadyLoggedIn_tv = findViewById(R.id.tvAlreadyLoggedIn);

        btn_BackHome = findViewById(R.id.btnBackToHome);
        btn_UserInfo = findViewById(R.id.btnUserInfo);
        layout = findViewById(R.id.constraintLayout);


    }

    public void setFullScreen() {

        //hides the title bar
        getSupportActionBar().hide();

        //this code makes the status bar transparent
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        setFullScreen();
        initialize();

        btnBackToHomeClicked();

        HeaderClass headerClassInstance = new HeaderClass();
        headerClassInstance.setBackground(layout, getApplicationContext());

        btn_UserInfo.setVisibility(View.INVISIBLE);


        //button press registers user
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == buttonRegister){
                    registerUser();
                }
            }
        });

        alreadyLoggedIn_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpPage.this, LogInPage.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        setFullScreen();

        initialize();
        //firebaseAuth = FirebaseAuth.getInstance();

        signUp_btn.setOnClickListener(signUp_btn_listner);
        alreadyLoggedIn_tv.setOnClickListener(alrdLgnIn_listner);
        btn_BackHome.setOnClickListener(btn_BackHomeListner);
        btn_UserInfo.setOnClickListener(btn_UserInfoListner);

        btn_UserInfo.setVisibility(View.INVISIBLE);
    }

    View.OnClickListener btn_UserInfoListner= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //user info will be shown
            Toast.makeText(SignUpPage.this, "User Info", Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener btn_BackHomeListner= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SignUpPage.this, HomePage.class);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener signUp_btn_listner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(SignUpPage.this, "Before Break", Toast.LENGTH_SHORT).show();
            if(validateInput()){
                Toast.makeText(SignUpPage.this,"Proccesing...",Toast.LENGTH_SHORT).show();
                String email=userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                addAccount(email, password);
            }
        }
    };


    View.OnClickListener alrdLgnIn_listner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SignUpPage.this, LogInPage.class);
            startActivity(intent);
            finish();
        }
    };

    public void setFullScreen() {

        //hides the title bar
        getSupportActionBar().hide();

        //this code makes the status bar transparent
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }


    private void addAccount(String email, String pswd) {
        Toast.makeText(SignUpPage.this, pswd, Toast.LENGTH_SHORT).show();
        //progressDialog.setMessage("You can subscribe to my channel until you are verified!");
        //progressDialog.show();

        /*firebaseAuth.createUserWithEmailAndPassword(email, pswd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //updateUI(user);
                            finish();
                            Intent intent = new Intent(NewAccountPage.this, HomePage.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(NewAccountPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }*/

/*
    private Boolean validateInput(){
        Boolean result = false;

        if(userPassword.getText().toString().isEmpty()||
                userEmail.getText().toString().isEmpty()){
            Toast.makeText(SignUpPage.this,"Invalid User Input",Toast.LENGTH_SHORT).show();
        }else
            result = true;

        return result;
    }

    private void initialize() {

        //userName = (EditText)findViewById(R.id.etUserName);
        userPassword = (EditText)findViewById(R.id.etPassword);
        userEmail = (EditText)findViewById(R.id.etEmailID);
        signUp_btn = (Button)findViewById(R.id.btnSignUp);
        alreadyLoggedIn_tv = (TextView)findViewById(R.id.tvAlreadyLoggedIn);
        btn_BackHome = (Button)findViewById(R.id.btnBackToHome);
        btn_UserInfo = (Button)findViewById(R.id.btnUserInfo);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}*/
