package com.realraghavgupta.foodies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Collins on 01/04/2018.
 */

public class user_edit_profile extends AppCompatActivity {
    EditText first_name, last_name, email, password, confirm_password;
    Button updateProfile;
    boolean check_email_in_db = false; // user email account does not exist
    boolean check_dsp_update = false; // allow onDataChange() to show toast message once


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit_profile_layout);

        first_name = findViewById(R.id.FirstName);
        last_name = findViewById(R.id.LastName);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        confirm_password = findViewById(R.id.Confirm_Password);
        updateProfile = findViewById(R.id.update);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            private boolean firstNameBool = false, lastNameBool = false, emailBool = false, passwordBool = false;//initially setting boolean variables as false(invalid)

            public void onClick(View v) {

                firstNameBool = checkFirstName(first_name.getText().toString());  // calling function for validating first name,returning false if invalid
                lastNameBool = checkFirstName(last_name.getText().toString()); // calling function for validating last name ,returning false if invalid
                emailBool = checkEmail(email.getText().toString()); // calling function for validating email ,returning false if invalid
                passwordBool = checkPassword(); // calling function for validating password,returning false if invalid

                if (firstNameBool == false)   // if first name is invalid
                {
                    Toast.makeText(user_edit_profile.this, "Invalid First Name! Use Alphabets Only!", Toast.LENGTH_SHORT).show();
                } else if (lastNameBool == false)  // if last name is invalid
                {
                    Toast.makeText(user_edit_profile.this, "Invalid Last Name! Use Alphabets Only!", Toast.LENGTH_SHORT).show();
                } else if (emailBool == false) // if email is invalid
                {
                    Toast.makeText(user_edit_profile.this, "Invalid Email!", Toast.LENGTH_SHORT).show();
                } else if (passwordBool == false)  // if password is invalid
                {
                    Toast.makeText(user_edit_profile.this, "Invalid Password or Confirm Password !", Toast.LENGTH_SHORT).show();
                } else {
                    updateUserProfile();  // after passing all validations update user details in database only if user email is in database before
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

    public void updateUserProfile()  // update user details in database only if user email is in database before

    {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");      // pointing towards user node            // Reference [4]
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            String tempEmail = email.getText().toString().trim();
            String userKey; // to store key of node that contain email id.

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())        // getting snapshot of all children                            //Reference [5]
                {
                    userKey = dataSnapshot1.getKey();   // getting reference of userId in database that contains email

                    String email_in_db = dataSnapshot1.child("email").getValue(String.class);

                    if (email.getText().toString().trim().toLowerCase().equals(email_in_db.toLowerCase())) {
                        check_email_in_db = true; // email account exists to update
                        break;
                    } else {
                        check_email_in_db = false; // email account does not exist
                    }

                }

                if (check_email_in_db == true)  // updating email account that exists
                {
                    if (check_dsp_update == false) { // to allow onDataChange() to show toast message once


                        String reference = "User/" + userKey;

                        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference(reference);// reference to child node having that email

                        Map<String, String> map_user = new HashMap<>();   // Reference [6]

                        map_user.put("email", tempEmail);
                        map_user.put("firstName", first_name.getText().toString());
                        map_user.put("lastName", last_name.getText().toString());
                        map_user.put("password", password.getText().toString());

                        databaseReference2.setValue(map_user); // updating child node containing email id

                        Toast.makeText(getApplicationContext(), "User Profile Updated !", Toast.LENGTH_SHORT).show();


                        check_email_in_db = true;
                        clearEditTexts();  // clearing edit texts values
                        check_dsp_update = true;  // preventing onDataChange() to show toast message multiple times

                        Intent intent = new Intent(user_edit_profile.this, RecipeActivity.class);           // redirecting to recipe activity
                        startActivity(intent);
                        finish();
                    }
                } else if (check_email_in_db == false)  // email account does not exists
                {
                    Toast.makeText(getApplicationContext(), " Email Id does not exist. Please create an account !", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), " Sorry. Looks like internet connectivity is lost !", Toast.LENGTH_SHORT).show();

            }
        });
        check_dsp_update = false; // to allow onDataChange() to show toast message once


    }


    public void clearEditTexts() {
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
    [Reference 6] Pankaj Rai,"Use map to create well formed structure" in "Firebase the dynamic database!" para. 10, May 9,2017,[Online], Available:https://android.jlelse.eu/firebase-the-dynamic-database-5b7878ebba2d .[Accessed:Mar. 18,2018].
    */
