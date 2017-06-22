package com.example.vlad.gfe;

import android.content.Context;
import android.content.Intent;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private String LOG_TAG = "MainActivityLog";

    private Random randNum;

    private TextView textRandNum;
    private ImageView image;
    private ImageButton button;
    private ArrayList<Integer> imageArry = new ArrayList();
    private Context context;
    private BackgroundSound backgrSoundTask;

    private int positImage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // полноєкранный режим


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        randNum = new Random();
        textRandNum = (TextView) findViewById(R.id.textRandNum);
        image = (ImageView) findViewById(R.id.image);
        button = (ImageButton) findViewById(R.id.randNumbutton);
        image.setVisibility(View.GONE);

    }

    @Override
    protected void onResume(){

        // Получение ссылки на контекст данного приложения.
        context = getApplicationContext();
        createArryImage();
        super.onResume();
    }

    @Override
    protected void onStop() {
        backgrSoundTask.onCancelled();
        super.onStop();
    }

    public void onClickRandNumButton (View view){

        new myTimer((randNum.nextInt(50000)+10000), 1000 ).start();
        view.setVisibility(View.GONE);
        image.setVisibility(View.VISIBLE);

        if (positImage > imageArry.size()){
            positImage = 0;
            Collections.shuffle(imageArry);
        }

        image.setImageResource(imageArry.get(positImage));

        backgrSoundTask = new BackgroundSound(); // запуск фоновых звуков
        backgrSoundTask.execute();

        positImage++;


    }

    private void createArryImage(){
        imageArry.add(context.getResources().getIdentifier("airport","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("amusementpark","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("bathroom","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("car","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("football","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("forest","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("kindergarten","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("kitchen","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("picnic","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("playground","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("pond","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("scool","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("score","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("sea","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("theater","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("vilage","drawable","com.example.vlad.gfe"));
        imageArry.add(context.getResources().getIdentifier("zoo","drawable","com.example.vlad.gfe"));
        Collections.shuffle(imageArry);

    }

    private void stopBackgrPlayer(){
        Log.d(LOG_TAG,"Попытка остановить плеер");

        if (backgrSoundTask == null) return;
        backgrSoundTask.onCancelled();

        Log.d(LOG_TAG,"Удались остановить плеер - " + backgrSoundTask.isCancelled());
    }



    public class myTimer extends CountDownTimer {

        int i = 0;
        MediaPlayer mp;

        public myTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textRandNum.setText(Integer.toString(image.getId()));
        }

        @Override
        public void onFinish() {
            textRandNum.setText("Взрыв");
            stopBackgrPlayer();



            new Thread(){
                public void run(){
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.zvuk_vzryva);
                    mp.start();
                }
            }.start();

            image.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
        }
    }



    public class BackgroundSound extends AsyncTask<Void, Void, Void> {

        MediaPlayer player;

        @Override
        protected Void doInBackground(Void... params) {
            player = MediaPlayer.create(MainActivity.this, R.raw.tictac);
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);
            player.start();
            Log.d(LOG_TAG, "Запуск проигрывателя " + player.isPlaying());

            // выключение проигрывания
        return null;
        }


        @Override
        protected void onCancelled() {

            Log.d(LOG_TAG, "запуск метода остановки плеера " + player.isPlaying());
            try {
                player.pause();
                player.release();
                Log.d(LOG_TAG, "Плеер остановлен ");
                Log.d(LOG_TAG, "Поток остановлен " + backgrSoundTask.isCancelled());
                player = null;
            } catch (Exception e) {
                e.printStackTrace();
            }

            super.onCancelled();

        }
    }







}
