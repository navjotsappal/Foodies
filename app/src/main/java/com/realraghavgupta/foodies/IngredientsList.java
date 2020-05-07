package com.realraghavgupta.foodies;


import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class IngredientsList extends Fragment { //IngredientList Fragment
    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    static ArrayList<String> selectedIng = IngredientsListAdapter.selectedStrings;

    //Initialising all the required variables.
    private ListView mMessageListView;
    private IngredientsListAdapter mMessageAdapter;
    private Button mSendButton;
    private String mUsername;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private DatabaseReference mIngredientsDatabaseReference;
    private ChildEventListener mChildEventListener;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_ingredients_list, container, false);
        SharedPreferences prefs = getContext().getSharedPreferences("USER_EMAIL_SHARED_PREFERENCE", MODE_PRIVATE);  //Checking the email of the logged in user
        String user_email = prefs.getString("userEmail", "No email defined");
        if (user_email.equals("No email defined")) {
            user_email = "";
        }

        mUsername = ANONYMOUS;
        mFirebaseDatabase = FirebaseDatabase.getInstance(); //Initialising Firebase
        mIngredientsDatabaseReference = mFirebaseDatabase.getReference().child("Orders");   //Accessing the Orders child in the database
        Query ingbyemail = mIngredientsDatabaseReference.orderByChild("emailId").equalTo(user_email);   //Accessing the orders linked to a particular email address
        // Initialize references to views
        mMessageListView = root.findViewById(R.id.ingredientListView);
        mSendButton = root.findViewById(R.id.getRecipeButton);
        // Initialize message ListView and its adapter
        List<IngredientsListItem> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new IngredientsListAdapter(this.getContext(), R.layout.ingredients_list_item, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                IngredientsListItem friendlyMessage = dataSnapshot.getValue(IngredientsListItem.class); //Passing the data through the adapter.
                mMessageAdapter.add(friendlyMessage);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ingbyemail.addChildEventListener(mChildEventListener);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, new RecipeActivity()).commit();   //Passing the data and opening the Recipe lists.
            }
        });
        return root;
    }
}