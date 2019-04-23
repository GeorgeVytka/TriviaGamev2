package com.example.triviagame;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Random;

public class QuestionPage extends AppCompatActivity {

    private TextView tv_Question, tv_Timer, tv_Category ;
    private Button btn_Option1,btn_Option2,btn_Option3,btn_Option4, btn_ScorePoints, btn_BackToHome, btn_NextQuestion;
    private String option1, option2, option3, option4, rightAnswer;
    private static int TOTAL_QUESTIONS;
    private static boolean RANDOM_QUESTION;
    private ConstraintLayout layout;
    private boolean timerRunning;
    private CountDownTimer countDownTimer;
    private long timeLeftInMills = 60000;
    //will be used to return id answer was right or wrong
    boolean answerState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page);

        setFullScreen();

        assignValues();
        setTotalQuestions();

        startClock();

        //if checkAnswer returns true, we add point. Deduct otherwise
        if(checkAnswer()){
            addPoint();
            getQuestion();
            //Toast.makeText(getApplicationContext(),"Right Answer ",Toast.LENGTH_LONG).show();
        }
        else{
            deductPoint();
            //Toast.makeText(getApplicationContext(),"Wrong Answer ",Toast.LENGTH_LONG).show();
        }

        btn_BackToHome.setText("Back To\n Home");
        btn_ScorePoints.setText("Score /\n Points");
        //HomePage homePage = new HomePage();

        //homePage.setFullScreen();
        HeaderClass headerClassInstance = new HeaderClass();
        headerClassInstance.setBackground(layout, getApplicationContext());

        nextQuestion();
    }

    private void addPoint(){
        //increase points by 10
    }

    private void deductPoint(){
        //deduct points by 5
    }

    public void setRandomQuestionTrue(){
        RANDOM_QUESTION = true;
    }


    public void startClock(){
        if(timerRunning){
            stopTimer();
        }else{
            startTimer();
        }
        getQuestion();
    }
    public void stopTimer(){

        countDownTimer.cancel();
        timerRunning = false;
    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMills, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMills = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(),QuestionPage.class));
                finish();
            }
        }.start();
    }

    public void updateTimer(){

        int minutes = (int)timeLeftInMills/60000;
        int seconds = (int) timeLeftInMills %60000/1000;

        String timeLeftText;

        timeLeftText = "";
        if(minutes<10)timeLeftText ="0";
        timeLeftText += ""+minutes;
        timeLeftText += ":";

        if(seconds<10)timeLeftText +="0";
        timeLeftText += seconds;

        tv_Timer.setText(timeLeftText);

    }
    private void getQuestion(){

        //resetOptionColor();

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        //databaseAccess.close();
        databaseAccess.open();

        //String querry_result = databaseAccess.getAddress("");
        int randomID = getRandomNum(TOTAL_QUESTIONS);
        String []tempArray = databaseAccess.getAddress(randomID);

        resetQuestion(tempArray);

        databaseAccess.close();
        //sleep();
    }

    public void resetQuestion(String[] array){


        tv_Question.setText(array[0]);
        tv_Category.setText(array[6]);

        rightAnswer = array[5].trim();

        btn_Option1.setText(array[1].trim());
        btn_Option2.setText(array[2].trim());
        btn_Option3.setText(array[3].trim());
        btn_Option4.setText(array[4].trim());

    }

    private boolean checkAnswer(){

        btn_Option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightAnswer.equals(btn_Option1.getText().toString().trim())){
                    btn_Option1.setTextColor(Color.GREEN);
                    answerState = true;

                }else{
                    btn_Option1.setTextColor(Color.RED);
                    showRightAnswer();
                }
            }
        });

        btn_Option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightAnswer.equals(btn_Option2.getText().toString().trim())){
                    btn_Option2.setTextColor(Color.GREEN);
                    answerState = true;

                }else{
                    btn_Option2.setTextColor(Color.RED);
                    showRightAnswer();
                }
            }
        });

        btn_Option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightAnswer.equals(btn_Option3.getText().toString().trim())){
                    btn_Option3.setTextColor(Color.GREEN);
                    answerState = true;
                }else{
                    btn_Option3.setTextColor(Color.RED);
                    showRightAnswer();
                }
            }
        });

        btn_Option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightAnswer.equals(btn_Option4.getText().toString().trim())){
                    btn_Option4.setTextColor(Color.GREEN);
                    answerState = true;

                }else{
                    btn_Option4.setTextColor(Color.RED);
                    showRightAnswer();
                }
            }
        });

        Toast.makeText(getApplicationContext(),"End of CheckAnswer: "+ answerState,Toast.LENGTH_LONG).show();
        return answerState;
    }

    private void showRightAnswer(){

        if (rightAnswer.equals(btn_Option1.getText().toString().trim()))
            btn_Option1.setTextColor(Color.GREEN);
        else if (rightAnswer.equals(btn_Option2.getText().toString().trim()))
            btn_Option2.setTextColor(Color.GREEN);
        else if (rightAnswer.equals(btn_Option3.getText().toString().trim()))
            btn_Option3.setTextColor(Color.GREEN);
        else if (rightAnswer.equals(btn_Option4.getText().toString().trim()))
            btn_Option4.setTextColor(Color.GREEN);


        //sleep();
        answerState = false;
    }

    private void nextQuestion(){

        btn_NextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestion();
                resetOptionColor();
            }
        });
    }

    private void resetOptionColor(){
        btn_Option1.setTextColor(Color.BLACK);
        btn_Option2.setTextColor(Color.BLACK);
        btn_Option3.setTextColor(Color.BLACK);
        btn_Option4.setTextColor(Color.BLACK);
    }

    public  void sleep(){
        SystemClock.sleep(1000);
    }
    public int getRandomNum(int range){
        //this wil return a random int from 1 to range
        return (new Random().nextInt(range+1));
    }

    public void assignValues(){
        tv_Question = findViewById(R.id.tvQuestion);
        tv_Timer = findViewById(R.id.tvTimer);
        tv_Category = findViewById(R.id.tvCategory);
        btn_Option1 = findViewById(R.id.btnOption1);
        btn_Option2 = findViewById(R.id.btnOption2);
        btn_Option3 = findViewById(R.id.btnOption3);
        btn_Option4 = findViewById(R.id.btnOption4);
        btn_NextQuestion = findViewById(R.id.btnNextQuestion);
        //using "Back Home" and "User Info" buttons from top_menu_bar.xml
        btn_ScorePoints = findViewById(R.id.btnUserInfo);
        btn_BackToHome = findViewById(R.id.btnBackToHome);
        layout = findViewById(R.id.constraintLayout);

    }
    public void setFullScreen(){

        //hides the title bar
        getSupportActionBar().hide();

        //this code makes the status bar transparent
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


    }

    private void setTotalQuestions(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        //databaseAccess.close();
        databaseAccess.open();

        //Toast.makeText(getApplicationContext(), Integer.toString(databaseAccess.totalQuestions()),Toast.LENGTH_LONG).show();
        TOTAL_QUESTIONS = databaseAccess.totalQuestions();
        databaseAccess.close();
    }

    @Override
    public void onBackPressed() {

    }
}
