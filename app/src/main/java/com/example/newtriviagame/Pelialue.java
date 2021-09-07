package com.example.newtriviagame;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.newtriviagame.databinding.ActivityPelialueBinding;
import com.example.newtriviagame.model.Question;
import com.example.newtriviagame.model.QuestionCounter;
import com.example.newtriviagame.model.Score;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Random;


public class Pelialue extends AppCompatActivity {

    private ActivityPelialueBinding binding;
    private Score score;
    private QuestionCounter laskuri;

 
    CountDownTimer countDownTimer;

    ArrayList<Question>kysymysPankki = new ArrayList<Question>();

    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    int scoreCounter = 0;
    int numOfQuestion = 1;
    int randomIndex = (int)(Math.random() * kysymysPankki.size());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelialue);

        RequestQueue queue = Volley.newRequestQueue(this);

        binding = DataBindingUtil.setContentView(Pelialue.this,R.layout.activity_pelialue);
        score = new Score();
        laskuri = new QuestionCounter();
        countDownTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.aikaValue.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                binding.textView8.setText("Peli on pelattu !");
                binding.buttonFalse.setEnabled(false);
                binding.buttonTrue.setEnabled(false);
                binding.kortti.setBackgroundColor(Color.WHITE);

            }
        }.start();


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Question question = new Question(response.getJSONArray(i).get(0).toString(),response.getJSONArray(i).getBoolean(1));
                        Log.i("kys", "onResponse: " + response.getJSONArray(i));

                        kysymysPankki.add(question);

                        binding.textView8.setText(kysymysPankki.get(randomIndex).getKysymys());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("kys", "onErrorResponse: ERROR!!!");

            }
        });


        binding.buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateQuestionRandom();

            }
        });


        binding.buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                updateQuestionRandom();

            }


        });


        queue.add(jsonArrayRequest);
    }


    private void updateQuestionRandom(){

       randomIndex = (int)(Math.random()* kysymysPankki.size());
       String question = (kysymysPankki.get(randomIndex).getKysymys());

        binding.textView8.setText(question);
        numberOfQuestion();


    }

    private void numberOfQuestion(){

        numOfQuestion += 1;
        laskuri.setLaskuri(numOfQuestion);
        binding.textView9.setText(String.valueOf(laskuri.getLaskuri()));

    }


    private void checkAnswer(boolean userPut){


        boolean vastaus = kysymysPankki.get(randomIndex).isCorrect();

        if(vastaus == userPut){
            binding.kortti.setBackgroundColor(Color.GREEN);
            addScore();
            Snackbar.make(binding.kortti,"OIKEIN !!!",Snackbar.LENGTH_LONG).show();


        }else{

            deductScore();
            Snackbar.make(binding.kortti,"VÄÄRIN !!!",Snackbar.LENGTH_LONG).show();
            binding.kortti.setBackgroundColor(Color.RED);

        }

    }



    public void addScore(){

        scoreCounter += 10;
        score.setScore(scoreCounter);
        binding.pisteValue.setText(String.valueOf(score.getScore()));

    }

    public void deductScore(){
        scoreCounter -= 5;
        score.setScore(scoreCounter);
        binding.pisteValue.setText(String.valueOf(score.getScore()));
    }



}