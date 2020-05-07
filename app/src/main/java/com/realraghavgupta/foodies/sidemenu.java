package com.realraghavgupta.foodies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class sidemenu extends AppCompatActivity
{
    //side menu bar of application using the drawerbar
    private DrawerLayout dw;
    private ActionBarDrawerToggle abtogg;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidemenu);
        dw = (DrawerLayout) findViewById(R.id.draw);
        abtogg = new ActionBarDrawerToggle(this, dw, R.string.Open, R.string.Close);
        dw.addDrawerListener(abtogg);
        NavigationView nvdrwawer = (NavigationView) findViewById(R.id.nv);
//      View headerView = nvdrwawer.findViewById(R.id.headerparent);
        View headerView = nvdrwawer.getHeaderView(0);

        TextView header = (TextView) headerView.findViewById(R.id.tv_email);
        Menu list = nvdrwawer.getMenu();
        MenuItem lolo = list.findItem(R.id.Login);
        MenuItem lopro = list.findItem(R.id.profile);
        MenuItem lolog = list.findItem(R.id.Logout);


        //View lolo = nvdrwawer.findViewById(R.id.Login);
//        View lopro = nvdrwawer.findViewById(R.id.profile);
        //sharing the preferences for users' log in or registration
        SharedPreferences prefs = getSharedPreferences("USER_EMAIL_SHARED_PREFERENCE", MODE_PRIVATE);
        String user_email = prefs.getString("userEmail", "No email defined");
            if (user_email.equals("No email defined"))
                {
                     header.setText("");
                    lopro.setVisible(false);
                    lolog.setVisible(false);
                     //lopro.setVisibility(View.INVISIBLE);
                }
            else
                {
                     header.setText(user_email);
                     lolo.setVisible(false);
                }
        abtogg.syncState();
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvdrwawer);
        getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, new RecipeActivity()).commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (abtogg.onOptionsItemSelected(item))
            {
                return true;
            }
        return super.onOptionsItemSelected(item);
    }

    public void selectItemDrawer(MenuItem menuItem)
    {
//      Fragment myFragment = new RecipeActivity();
        Fragment myFragment = new Fragment();
        Class fragmentClass = RecipeActivity.class;

        if (menuItem.getItemId() == R.id.Home)
        {
            fragmentClass = RecipeActivity.class;
        }
        else if (menuItem.getItemId() == R.id.Login)
        {
            startActivity(new Intent(sidemenu.this, sign_in.class));
        }
        else if (menuItem.getItemId() == R.id.Orders)
        {
            startActivity(new Intent(sidemenu.this, MapsActivityCurrentPlace.class));
        }
        else if (menuItem.getItemId() == R.id.neworder)
        {
            fragmentClass = Ordering.class;
        }
        else if (menuItem.getItemId() == R.id.Recipes)
        {
            fragmentClass = IngredientsList.class;
        }
        else if (menuItem.getItemId() == R.id.profile)
        {
            startActivity(new Intent(sidemenu.this, user_edit_profile.class));
        }
        else if (menuItem.getItemId() == R.id.Logout)
        {
            SharedPreferences prefs = getSharedPreferences("USER_EMAIL_SHARED_PREFERENCE", MODE_PRIVATE);
            prefs.edit().clear().commit();//clearing the preferences for users' log out
            NavigationView nvdrwawer = (NavigationView) findViewById(R.id.nv);
//          View headerView = nvdrwawer.findViewById(R.id.headerparent);
            View headerView = nvdrwawer.getHeaderView(0);
            TextView header = (TextView) headerView.findViewById(R.id.tv_email);
            header.setText("");
            Menu list = nvdrwawer.getMenu();

            MenuItem lolo = list.findItem(R.id.Login);
            MenuItem lopro = list.findItem(R.id.profile);
            MenuItem lolog = list.findItem(R.id.Logout);

            lopro.setVisible(false);
            lolo.setVisible(true);

            lolog.setVisible(false);

            fragmentClass = RecipeActivity.class;
        }
        else if (menuItem.getItemId() == R.id.Splitbill)
        {
            fragmentClass = split_bill.class;
        } else
        {
            fragmentClass = RecipeActivity.class;
        }

        try
        {
            myFragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framlayout, myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        dw.closeDrawers();
    }
    private void setupDrawerContent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                selectItemDrawer(item);
                return true;
            }
        });
    }
}

//references:
// [1] "BottomNavigationView with Fragments - Android Studio Tutorial", YouTube, 2018. [Online].
// Available: https://www.youtube.com/watch?v=tPV8xA7m-iw. [Accessed: 09- Apr- 2018].

//[2] R. Tamada, "Android Custom ListView with Image and Text using Volley", AndroidHive, 2018. [Online].
// Available: https://www.androidhive.info/2014/07/android-custom-listview-with-image-and-text-using-volley/. [Accessed: 09- Apr- 2018].

// [3] small pictures for different vegetables - Google Search", Google.ca, 2018. [Online].
// Available: https://www.google.ca/search?biw=1280&bih=566&tbm=isch&sa=1&ei=dRqWWvKtHMWEtQX61JzoBw&q=small+pictures+for+different+vegetables&oq=small+pictures+for+different+vegetables&gs_l=psy-ab.3...14755.22279.0.22578.24.21.0.0.0.0.258.2096.6j7j3.16.0....0...1c.1.64.psy-ab..10.0.0....0.HKl9Sc5VAsQ.
// [Accessed: 09- Apr- 2018].( im1 image reference )

//[4] Stack Overflow - Where Developers Learn, Share, & Build Careers", Stackoverflow.com, 2018.
// [Online]. Available: https://stackoverflow.com/. [Accessed: 09- Apr- 2018].
