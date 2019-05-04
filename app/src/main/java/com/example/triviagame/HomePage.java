package com.example.triviagame;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.prefs.PreferenceChangeEvent;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;


public class HomePage extends AppCompatActivity {

    //use this to stop music
    HomePage mHomeWatcher;
    //variables to assign the button or text that user see on screen
    //NOTE: btn is for Button, tv for TextView
    private Button btn_GameStart, btn_BackHome, btn_UserInfo;
    private TextView tv_LogIn, tv_ChangeBackground, tv_GameName;
    private ConstraintLayout layout;
    //to exit the app this condition will be used
    private Boolean exitCondition = false;
  private  MediaPlayer mp,mpSwoosh,mpBackground;


    private FirebaseAuth auth;

    HeaderClass headerClassInstance = new HeaderClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //setContentView(R.layout.top_menu_bar);

        setFullScreen();

        // this will assign variables to button, text, etc from xml file
        assignVariables();

        initializeSettings();

        checkUser();

        clickListner();
        changeBackground();
        mpBackground.start();
    }

    public void checkUser(){
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();


        if (auth.getCurrentUser() != null){
            tv_LogIn.setVisibility(View.INVISIBLE);
        }
    }


    //set settings based on user preferences
    private void initializeSettings(){

        headerClassInstance.setBackground(layout, getApplicationContext());

        //we do not need to show BackHome button on HomePage
        btn_BackHome.setVisibility(View.INVISIBLE);

        //set text programmatically rather than doing fix text using xml
        tv_GameName.setText(HeaderClass.GAME_NAME);
        btn_GameStart.setText("Start Game");
        tv_LogIn.setText("Log in / Sign Up");

    }

    private void clickListner(){

        btn_UserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                startActivity(new Intent(getApplicationContext(), PopUpWindow.class));
            }
        });

        btn_GameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),CategoriesPage.class));
                mp.start();
              //mp.release();
                finish();
            }
        });

        tv_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                startActivity(new Intent(getApplicationContext(), LogInPage.class));
            }
        });
    }

    private void changeBackground(){
        tv_ChangeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpSwoosh.start();
                int backgroundNum = headerClassInstance.getCurBackground(getApplicationContext());

                int temp = headerClassInstance.chnageBackground(backgroundNum, layout);

                headerClassInstance.saveBackground(temp, getApplicationContext());
            }
        });
    }

    public void setFullScreen(){

        //hides the title bar
        getSupportActionBar().hide();

        //this code makes the status bar transparent
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

    }

    private void assignVariables(){
        mpBackground = MediaPlayer.create(this,R.raw.bensoundjazzyfrenchy);
        mp = MediaPlayer.create(this,R.raw.btclick);
        mpSwoosh = MediaPlayer.create(this,R.raw.swoosh);
        btn_GameStart = findViewById(R.id.btnGameStart);
        tv_LogIn = findViewById(R.id.tvLogIn);
        btn_BackHome = findViewById(R.id.btnBackToHome);
        btn_UserInfo = findViewById(R.id.btnUserInfo);
        tv_ChangeBackground = findViewById(R.id.tvChangeBackground);
        layout = findViewById(R.id.layoutHeader);
        tv_GameName = findViewById(R.id.tvGameName);
    }


/*
    @Override
    protected void onPause() {
        super.onPause();

        PowerManager pm = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (pm != null) {
            isScreenOn = pm.isScreenOn();
        }

        if (!isScreenOn) {
            if (mServ != null) {
                mServ.pauseMusic();
            }
        }

    }*/
    @Override
    public void onBackPressed() {
        if (exitCondition){
            finish();
            super.onBackPressed();
        }else{
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            exitCondition = true;
        }
    }
}
