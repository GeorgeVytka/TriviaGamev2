package com.example.triviagame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

//package com.example.triviagame;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EndPage extends AppCompatActivity {

    private ConstraintLayout layout;
    private Button btn_PlayAgain, btn_BackToCategory, btn_BackToHome, btn_UserInfo;
    private TextView tv_Score, tv_CoinRecived;
    private static String totalPoints;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_page);
        setFullScreen();

        // this will assign variables to button, text, etc from xml file
        assignVariables();

        HeaderClass headerClassInstance = new HeaderClass();
        headerClassInstance.setBackground(layout, getApplicationContext());

        backToHome();

        selectCategories();

        btn_UserInfo.setVisibility(View.GONE);

        tv_Score.setText(totalPoints);
        countCoins(Integer.parseInt(totalPoints));

        playAgain();
    }

    public void setTotalPoints(int points){
        totalPoints = Integer.toString(points);
    }

    private void countCoins(int points){

        if(points>160)
            tv_CoinRecived.setText("11");
            //points between 151 to 160
        else if(points>150)
            tv_CoinRecived.setText("10");
            //points between 141 to 150
        else if(points>140)
            tv_CoinRecived.setText("09");
            //points between 131 to 140
        else if(points>130)
            tv_CoinRecived.setText("08");
            //points between 121 to 130
        else if(points>120)
            tv_CoinRecived.setText("07");
            //points between 111 to 120
        else if(points>110)
            tv_CoinRecived.setText("06");
            //points between 101 to 110
        else if(points>100)
            tv_CoinRecived.setText("05");
            //points between 81 to 100
        else if(points>80)
            tv_CoinRecived.setText("04");
            //points between 50 to 80
        else if(points>=50)
            tv_CoinRecived.setText("02");
    }

    private void playAgain(){

        btn_PlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), QuestionPage.class));
                finish();
            }
        });
    }

    private void backToHome(){

        btn_BackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), HomePage.class));
            }
        });
    }

    private void selectCategories(){

        btn_BackToCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),CategoriesPage.class));
                finish();
            }
        });
    }

    private void assignVariables(){

        layout = findViewById(R.id.layout);
        btn_PlayAgain = findViewById(R.id.btnPlayAgain);
        btn_BackToCategory = findViewById(R.id.btnBackToCategory);
        btn_BackToHome = findViewById(R.id.btnBackToHome);
        btn_UserInfo = findViewById(R.id.btnUserInfo);
        tv_Score = findViewById(R.id.tvScore);
        tv_CoinRecived = findViewById(R.id.tvCoinRecived);

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

    @Override
    public void onBackPressed() {
    }
}