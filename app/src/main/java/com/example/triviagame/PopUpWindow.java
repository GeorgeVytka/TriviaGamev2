package com.example.triviagame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PopUpWindow extends Activity {

    //error
    public static final String TAG = "AddUserData";

    //var
    private PopUpWindow popUpWindow;
    private LayoutInflater layoutInflater;
    private TextView userNameText;
    private TextView userHighscoreText;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private boolean userFirestoreCheck;
    private DocumentSnapshot mLastQueriedDocument;

    /*
    * The fireStroe query takes some time to complete so i'm thinking of adding a
    * progress bar to show the user that the pop up is loading
    *
    * */
    private ProgressBar mProgress;

    private userClass mUserClass = new userClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);

        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userNameText = findViewById(R.id.tvUserName);
        userHighscoreText = findViewById(R.id.tvHighestScore);
        //displaying this activity will take 60% of the height and 80% of the width
        //int width = (int)0.8*(displayMetrics.widthPixels);
        //int height = (int)0.6*(displayMetrics.heightPixels);

        //getWindow().setLayout(width,height);
        int wid = displayMetrics.widthPixels;
        int hig = displayMetrics.heightPixels;

        getWindow().isFloating();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getWindow().setLayout((int)(wid*.8),(int)(hig*.5));


        //check if user exits
        if(mAuth.getUid() != null) {


            updateUI();


        } else{
            Log.d(TAG,"error");
        }

    }

    public void superupdateUI(userClass user){
        int temp = user.getHighScore();

        userNameText.setText(user.getEmail());
        userHighscoreText.setText(String.valueOf(user.getHighScore()));
    }

    //show user highscore and email
    public void updateUI(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference userRef = db.collection("TriviaUser");


        Query userQuery = userRef.whereEqualTo("uid",mUser.getUid());
        Log.d("Test1", "Document ID: ");
        userQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document: task.getResult()){

                        Log.d("Test", "Document ID: " + document.getId());
                        userClass user = document.toObject(userClass.class);
                        superupdateUI(user);
                       // Log.d(TAG,"display 3");
                   }
                }else{

                }
            }
        });
    }


/*
* .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document: task.getResult()){

                        Log.d("Test", "Document ID: " + document.getId());
                        userClass user = document.toObject(userClass.class);
                        superupdateUI(user);
                       // Log.d(TAG,"display 3");
                   }
                }else{

                }
            }
        });
*
* */
    //query and finds the document according to values
    public void checkIfUserExistsInFireStore(){
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference yourCollRef = rootRef.collection("yourCollection");
        Query query = yourCollRef.whereEqualTo("yourPropery", "yourValue");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

    }


    /*
    * query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    Log.d(TAG, "get user " + mAuth.getUid() + " "+ mUser.getUid());

                    //updateUI();
                    updateUI();


                } else {
                    Log.d(TAG, "Error getting documents: " + mAuth.getUid() + " "+ mUser.getUid());
                    addUserToFireStore();
                    //Log.d(TAG, "Error getting documents: ", task.getException());

                }


            }


        });
    *
    * */

    public void addUserToFireStore(){
        Log.d(TAG, "added user1");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference newUserRef = db.collection("TriviaUser").document();
        userClass user = new userClass();


        user.setEmail(mUser.getEmail());
        user.setUserID(mUser.getUid());
        user.setCoin(0);
        user.setHighScore(0);

        newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Log.d(TAG, "added user");
                    //updateUI();

                }else{

                    Log.d(TAG,"error adding user");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
