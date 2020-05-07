package com.realraghavgupta.foodies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Collins on 02/04/2018.
 */
public class split_bill extends Fragment {
    public EditText numberOfUsers, billAmount, viewSplitPerPerson;
    public Button splitBill;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.split_bill_layout, container, false);
        numberOfUsers = root.findViewById(R.id.editTextUserNumber);
        billAmount = root.findViewById(R.id.editTextBill);
        viewSplitPerPerson = root.findViewById(R.id.editTextViewBill);
        splitBill = root.findViewById(R.id.buttonCalculateBill);
        viewSplitPerPerson.setKeyListener(null); // making edittext  uneditable
        splitBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndSplitBill(); // function to validate values and then split the bill
            }
        });
        return root;
    }

    public void validateAndSplitBill() {
        if (numberOfUsers.getText().toString().equals(""))     // if edittext is empty
        {
            Toast.makeText(getActivity().getApplicationContext(), "Please Enter Number of Users !", Toast.LENGTH_LONG).show();
            viewSplitPerPerson.setVisibility(View.INVISIBLE);
        } else if (billAmount.getText().toString().equals("")) // if edittext is empty
        {
            Toast.makeText(getActivity().getApplicationContext(), "Please Enter the Bill Amount to split !", Toast.LENGTH_LONG).show();
            viewSplitPerPerson.setVisibility(View.INVISIBLE);
        } else {
            calculateBill(); // function to split the bill
        }
    }

    public void calculateBill() {
        Double number_of_users = Double.parseDouble(numberOfUsers.getText().toString());  // cast conversion
        Double bill = Double.parseDouble(billAmount.getText().toString());
        Double split_bill;
        String temp; // used for storing bill split(value in double) to string temporarily
        int dot_position;// used for finding "." position in temp
        viewSplitPerPerson.setText("");
        if (number_of_users > 0 && number_of_users <= 10) // checking if number of users are >0 and < =10
        {
            if (bill >= 1 && bill <= 1000)  // checking if bill amount is between 1 and 1000 $
            {
                split_bill = bill / number_of_users;
                temp = split_bill.toString();
                dot_position = temp.indexOf('.', 0);
                temp = temp.substring(0, dot_position + 2);// storing values with one decimal position
                viewSplitPerPerson.setVisibility(View.VISIBLE);
                viewSplitPerPerson.setText("Bill Split per person is " + temp + " $");
            } else          //if bill amount is not between 1 and 1000 $
            {
                Toast.makeText(getActivity().getApplicationContext(), "Please enter bill amount from 1 to 1000 !", Toast.LENGTH_LONG).show();
                viewSplitPerPerson.setVisibility(View.INVISIBLE);
            }
        } else        //if number of users are not between 1 and 10
        {
            Toast.makeText(getActivity().getApplicationContext(), "Please enter number of users from 1 to 10 !", Toast.LENGTH_LONG).show();
            viewSplitPerPerson.setVisibility(View.INVISIBLE);
        }
    }
}