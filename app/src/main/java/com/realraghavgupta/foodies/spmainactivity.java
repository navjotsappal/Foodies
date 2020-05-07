package com.realraghavgupta.foodies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
//splash screen code

public class spmainactivity extends AppCompatActivity
{
    public static int timeout = 4000;//time for another screen to be appear in milliseconds
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spmainactivity);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent homeIntent = new Intent(spmainactivity.this, sphomeactivity.class);//passed the intent to sphomeactivity
                startActivity(homeIntent);
                finish();

            }
        }, timeout);
    }
}

//refernces:
//[1] "How to Create Welcome Screen (Splash Screen) in Android Studio", YouTube, 2018. [Online].
// Available: https://www.youtube.com/watch?v=jXtof6OUtcE. [Accessed: 09- Apr- 2018].