package com.realraghavgupta.foodies;
//splash screen

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class sphomeactivity extends AppCompatActivity {
    public static int timeout = 4000; //time after which the second screen will appear
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sphomeactivity);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent homeIntent = new Intent(sphomeactivity.this, spthird_page.class);//passed the intent to ext page
                startActivity(homeIntent);
                finish();

            }
        }, timeout);
    }
}
//references:
//[1] "How to Create Welcome Screen (Splash Screen) in Android Studio", YouTube, 2018. [Online].
// Available: https://www.youtube.com/watch?v=jXtof6OUtcE. [Accessed: 09- Apr- 2018].