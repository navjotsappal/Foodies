package com.realraghavgupta.foodies;

/**
 * Created by Collins on 20/02/2018.
 */

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.view.View.OnFocusChangeListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;                              // Reference [1]
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class user_registration extends AppCompatActivity

{
    EditText first_name, last_name, email, password, confirm_password;
    Button Register;
    boolean check = false;
    boolean check1 = false;
    public static final String sendEmailIntent = "";   // sending value of email in intent


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration_layout);

        first_name = findViewById(R.id.FirstName);
        last_name = findViewById(R.id.LastName);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        confirm_password = findViewById(R.id.Confirm_Password);
        Register = findViewById(R.id.Register);


        Register.setOnClickListener(new View.OnClickListener() {
            private boolean firstNameBool = false, lastNameBool = false, emailBool = false, passwordBool = false;//initially setting boolean variables as false(invalid)

            public void onClick(View v) {

                firstNameBool = checkFirstName(first_name.getText().toString());  // calling function for validating first name,returning false if invalid
                lastNameBool = checkFirstName(last_name.getText().toString()); // calling function for validating last name ,returning false if invalid
                emailBool = checkEmail(email.getText().toString()); // calling function for validating email ,returning false if invalid
                passwordBool = checkPassword(); // calling function for validating password,returning false if invalid


                if (firstNameBool == false)   // if first name is invalid
                {

                    Toast.makeText(user_registration.this, "Invalid First Name! Use Alphabets Only!", Toast.LENGTH_SHORT).show();


                } else if (lastNameBool == false)  // if last name is invalid
                {

                    Toast.makeText(user_registration.this, "Invalid Last Name! Use Alphabets Only!", Toast.LENGTH_SHORT).show();


                } else if (emailBool == false) // if email is invalid
                {
                    Toast.makeText(user_registration.this, "Invalid Email!", Toast.LENGTH_SHORT).show();


                } else if (passwordBool == false)  // if password is invalid
                {
                    Toast.makeText(user_registration.this, "Invalid Password or Confirm Password !", Toast.LENGTH_SHORT).show();


                } else {

                    addUser();  // after passing all validations add user in database only if user is not in database before


                }


            }

        });


    }

    public boolean checkFirstName(String s)      // function to validate first name
    {
        String Regular_expression = "^[\\p{L} .'-]+$";                            //Reference [2]
        //String Regular_expression = "^[a-zA-Z\\\\s]*$";

        Pattern pattern = Pattern.compile(Regular_expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            return true;
        } else {
            return false;
        }


    }

    public boolean checkEmail(String s)      // function to validate email
    {

        String Regular_expression = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";                   //Reference [3]
        Pattern pattern = Pattern.compile(Regular_expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            return true;

        } else {
            return false;
        }

    }

    public boolean checkPassword()          // function to validate password
    {


        int length1, length2;
        length1 = password.getText().toString().length();
        length2 = confirm_password.getText().toString().length();

        if ((length1 >= 6 && length1 <= 8) && (length2 >= 6 && length2 <= 8))  // check if length of password and confirm password is between 6 to 8 characters
        {

            if (password.getText().toString().equals(confirm_password.getText().toString())) {
                return true;   // return true if both passwords match
            } else {
                return false;//return false if both passwords do not match
            }
        } else {

            return false;// passwords are invalid

        }
    }


    public void addUser() {
        DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference("User");          // pointing reference to user node   //Reference [4][5]
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {                           // binding listener to reference
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())                         // getting snapshot of all children  //Reference [5]
                {
                    String tempEmail = dataSnapshot1.child("email").getValue(String.class);
                    if (email.getText().toString().equals(tempEmail))        // checking if email already exists
                    {
                        check = true; // email already exists

                        break;
                    } else {
                        check = false; // email does not exists
                    }

                }

                if (check == true)  // prevent user from creating an account if email exists
                {
                    if (check1 == false) {

                        Toast.makeText(getApplicationContext(), "Email account already exists !", Toast.LENGTH_LONG).show();
                    }
                } else if (check == false)  // add new user to database
                {
                    DatabaseReference db_ref2 = FirebaseDatabase.getInstance().getReference("User");
                    String userId = db_ref2.push().getKey();                                   // Reference [4]

                    String S1 = first_name.getText().toString().trim();  // getting values of all edittexts
                    String S2 = last_name.getText().toString().trim();
                    String S3 = email.getText().toString().trim();
                    String S4 = password.getText().toString().trim();

                    User user = new User(S1, S2, S3, S4); // creating user model class object
                    db_ref2.child(userId).setValue(user); // adding user to database using UserId
                    Toast.makeText(getApplicationContext(), " User Account created successfully !", Toast.LENGTH_LONG).show();

                    clearAllEditTexts(); // clearing edit texts after adding user to database

                    check = true;      // setting true once user is created
                    check1 = true;     // preventing onDataChange() to show toast message multiple times


                    // uncomment below lines to Redirect to home page  and add email to intent

                    //Intent intent = new Intent(user_registration.this,hommy.class);
                    //intent.putExtra(sendEmailIntent,S3);  // fetch these key value pair from  home page
                    //startActivity(intent);
                    // finish();

                    Intent intent = new Intent(user_registration.this, sign_in.class); // redirecting to sign in page 
                    startActivity(intent);
                    finish();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), " Database Error: No Internet Connectivity  !", Toast.LENGTH_SHORT).show();

            }
        });
        check1 = false; // to allow onDataChange() to show toast message only once


    }


    public void clearAllEditTexts() {
        first_name.setText("");
        last_name.setText("");
        email.setText("");
        password.setText("");
        confirm_password.setText("");
    }


}






/* References:

    [Reference 1] firebase.google.com, "Add Firebase to Your Android Project",firebase.google.com, para.1-5, Feb.,14,2018 [Online]. Available: https://firebase.google.com/docs/android/setup?authuser=0 . [Accessed Mar.16, 2018].
    [Reference 2] Stema, “Java Regex to Validate Full Name allow only Spaces and Letters”, stackoverflow.com, para. 2, Apr. 4, 2013. [Online]. Available:https://stackoverflow.com/questions/15805555/java-regex-to-validate-full-name-allow-only-spaces-and-letters [Accessed Feb.11, 2018].
    [Reference 3] Jason Buberel, “Java regex email” stackoverflow.com, para. 2, Nov. 20, 2011. [Online]. Available:https://stackoverflow.com/questions/8204680/java-regex-email. [Accessed Feb.11, 2018].
    [Reference 4] Ravi Tamada, "Inserting data" in "Android working with Firebase Realtime Database",para.3.1, [Online]. Available:  https://www.androidhive.info/2016/10/android-working-with-firebase-realtime-database/ .  [Accessed Mar.16, 2018].
    [Reference 5] Benyamine Malki,Frank van Puffelen, "How to get child of child value from firebase in android?",stackoverflow.com ,para.3.1, Aug.,19,2017 [Online]. Available: https://stackoverflow.com/questions/43293935/how-to-get-child-of-child-value-from-firebase-in-android/43295289.   [Accessed Mar.16, 2018].
    */
