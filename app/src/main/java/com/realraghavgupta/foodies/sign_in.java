package com.realraghavgupta.foodies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;        // Reference[1]
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Collins on 20/02/2018.
 */

public class sign_in extends AppCompatActivity

{

    EditText email, password;
    Button sign_in, sign_up;
    Boolean check_email_password_in_database = false;  // checking if email and password exist in database
    Boolean check1 = false;  // preventing onDataChange()to show toast message multiple times
    public static final String sendEmailIntent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);

        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        sign_in = findViewById(R.id.sign);
        sign_up = findViewById(R.id.sign_up);

        SharedPreferences prefs1 = getSharedPreferences("USER_EMAIL_SHARED_PREFERENCE", MODE_PRIVATE);
        prefs1.edit().clear().commit();


        sign_in.setOnClickListener(new View.OnClickListener() {
            private boolean emailBool = false;    // False if email is not valid. Otherwise true
            private boolean passwordBool = false; // False if password is not valid. Otherwise true


            public void onClick(View v) {


                emailBool = checkEmail(email.getText().toString());  // calling function to check email validity
                passwordBool = checkPassword(password.getText().toString());// calling function to check password validity

                if (emailBool == false) {

                    Toast.makeText(sign_in.this, "Invalid Email !", Toast.LENGTH_LONG).show();

                } else if (passwordBool == false) {
                    Toast.makeText(sign_in.this, "Invalid Password !", Toast.LENGTH_LONG).show();

                } else {
                    checkLogin();  // calling function to check login credentials
                }


            }

        });


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sign_in.this, user_registration.class); // redirecting to user_regisration activity
                startActivity(intent);
                finish();

            }
        });


    }


    public boolean checkEmail(String s)  // validating email. return false if email is invalid
    {

        String Regular_expression = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";                   // Reference [3]
        Pattern pattern = Pattern.compile(Regular_expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            return true;

        } else {
            return false;
        }

    }

    public boolean checkPassword(String s) // validating password. return false if password is invalid
    {
        if (s.equals("")) {
            return false;
        } else if (password.length() < 6 || password.length() > 8) {
            return false;
        } else {
            return true;
        }
    }

    public void checkLogin() // checking if credentials are in database or not.
    {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");           // pointing reference to user node   //Reference [4][5]
        ref.addListenerForSingleValueEvent(new ValueEventListener()                              // binding listener to reference
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dspOne : dataSnapshot.getChildren())                        // getting snapshot of all children and checking email and password in each child   //Reference [5]
                {
                    String tempEmail = dspOne.child("email").getValue(String.class);
                    String tempPassword = dspOne.child("password").getValue(String.class);

                    if (email.getText().toString().toLowerCase().equals(tempEmail.toLowerCase()) && password.getText().toString().equals(tempPassword)) {
                        check_email_password_in_database = true; // email and password found in database


                        SharedPreferences.Editor editor = getSharedPreferences("USER_EMAIL_SHARED_PREFERENCE", MODE_PRIVATE).edit();  // Reference[7] // storing email of logged user to check later in other activities if user is logged in or not
                        editor.putString("userEmail", tempEmail);
                        editor.apply();


                        SharedPreferences prefs = getSharedPreferences("USER_EMAIL_SHARED_PREFERENCE", MODE_PRIVATE);
                        String user_email = prefs.getString("userEmail", "No email defined");//"No name defined" is the default value.

                        // neha ..use above two lines to fetch email from shared preference

                        break;
                    } else {
                        check_email_password_in_database = false; // email and password not in database
                    }
                }

                if (check_email_password_in_database == true) {
                    if (check1 == false) {
                        Toast.makeText(getApplicationContext(), " Login Successful", Toast.LENGTH_LONG).show();

                        email.setText("");
                        password.setText("");

                        // navdeep uncomment below lines to Redirect to home page


                        Intent intent = new Intent(sign_in.this, sidemenu.class);
                        startActivity(intent);
                        finish();


                    }
                } else if (check_email_password_in_database == false) {
                    Toast.makeText(getApplicationContext(), " Invalid Email or Password! ", Toast.LENGTH_LONG).show();
                    check1 = true;
                    check_email_password_in_database = true;

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), " Sorry.No Internet Connectivity! ", Toast.LENGTH_LONG).show();

                //Log.w(TAG, "Read Cancelled ", databaseError.toException());
            }
        });
        check1 = false; // preventing onDataChange() to run toast message multiple times
    }


}











/* References:
        [1] firebase.google.com, "Add Firebase to Your Android Project",firebase.google.com, para.1-5, Feb.,14,2018 [Online]. Available: https://firebase.google.com/docs/android/setup?authuser=0 . [Accessed Mar.16, 2018].
        [2] Stema, “Java Regex to Validate Full Name allow only Spaces and Letters”, stackoverflow.com, para. 2, Apr. 4, 2013. [Online]. Available:https://stackoverflow.com/questions/15805555/java-regex-to-validate-full-name-allow-only-spaces-and-letters [Accessed Feb.11, 2018].
        [3] Jason Buberel, “Java regex email” stackoverflow.com, para. 2, Nov. 20, 2011. [Online]. Available:https://stackoverflow.com/questions/8204680/java-regex-email. [Accessed Feb.11, 2018].
        [4] Ravi Tamada, "Inserting data" in "Android working with Firebase Realtime Database",para.3.1, [Online]. Available:  https://www.androidhive.info/2016/10/android-working-with-firebase-realtime-database/ .  [Accessed Mar.16, 2018].
        [5] Benyamine Malki,Frank van Puffelen, "How to get child of child value from firebase in android?",stackoverflow.com ,para.3.1, Aug.,19,2017 [Online]. Available: https://stackoverflow.com/questions/43293935/how-to-get-child-of-child-value-from-firebase-in-android/43295289.   [Accessed Mar.16, 2018].
        [6] Pankaj Rai,"Use map to create well formed structure" in "Firebase the dynamic database!" para. 10, May 9,2017,[Online], Available:https://android.jlelse.eu/firebase-the-dynamic-database-5b7878ebba2d .[Accessed:Mar. 18,2018].
        [7]Pulkit,Jorgesys,"Setting values in Preference:" in "Android Shared preferences example [closed]",para. 1, July 6,2017,[Online], Available:https://stackoverflow.com/questions/23024831/android-shared-preferences-example. [Accessed: Apr.3,2018]
    */

