package com.example.vlad.gfe;

import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import junit.framework.Test;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Random randNum;

    private TextView textRandNum;
    private ImageView image;
    private ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randNum = new Random();
        textRandNum = (TextView) findViewById(R.id.textRandNum);
        image = (ImageView) findViewById(R.id.image);
        button = (ImageButton) findViewById(R.id.randNumbutton);
        image.setVisibility(View.GONE);



    }

    public void onClickRandNumButton (View view){

        new myTimer((randNum.nextInt(50000)+10000), 1000 ).start();
        view.setVisibility(View.GONE);
        image.setVisibility(View.VISIBLE);
        image.setImageResource(R.drawable.forest);


    }

    public class myTimer extends CountDownTimer {

        int i = 0;
        MediaPlayer mp;

        public myTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textRandNum.setText(Integer.toString(i++));
        }

        @Override
        public void onFinish() {
            textRandNum.setText("Взрыв");
            image.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);

            new Thread(){
                public void run(){
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.zvuk_vzryva);
                    mp.start();
                }
            }.start();
        }
    }

}
