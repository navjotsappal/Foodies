package com.realraghavgupta.foodies;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Button btnCompare;

    private Toolbar toolbar;

    EditText first_name, last_name, email, password, confirm_password;
    Button Register;
    TextView showerrors;
    private Boolean clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //from neha's branch

        toolbar = (Toolbar) findViewById(R.id.tb);


        mSectionsPagerAdapter = new SectionsPageAdapter(getSupportFragmentManager()); //from neha's branch
        mViewPager = (ViewPager) findViewById(R.id.container); //from neha's branch
        setupViewPager(mViewPager);//from neha's branch

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);//from neha's branch
        tabLayout.setupWithViewPager(mViewPager);//from neha's branch

        ListView groceryList = (ListView) findViewById(R.id.lv_groceryList);//from neha's branch
        // setContentView(R.layout.user_registration_layout);  //from navjots's branch. Uncomment this to display user registration, and comment the above
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentPopular(), "popular");
        adapter.addFragment(new FragmentCatalog(), "catalog");
        viewPager.setAdapter(adapter);
    }
}
