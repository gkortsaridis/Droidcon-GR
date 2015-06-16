package org.gdgthess.droidcongr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class LineupActivity extends ActionBarActivity {

    ImageView profImage;
    RoundImage roundProf;
    RelativeLayout profCoverRel;
    TextView gplusName, gplusMail;
    DrawerLayout drawerLayout;

    private Toolbar toolbar;
    NavigationDrawerFragmentLineup drawerFragment;

    SharedPreferences sharedPreferences;

    LoadProfileImage loadprof;
    LoadCoverImage loadcover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineup);

        //Set the custom toolbar
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_lineup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.syncState();

        //Setting up the drawerFragment
        drawerFragment = (NavigationDrawerFragmentLineup) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer_lineup);
        drawerFragment.setUp(R.id.fragment_navigation_drawer_lineup, (DrawerLayout) findViewById(R.id.drawerLayout_lineup));


        //Getting the sharedPreferences data
        sharedPreferences = getSharedPreferences("INFO", Context.MODE_PRIVATE);
        String profName = sharedPreferences.getString("username", "");
        String profMail = sharedPreferences.getString("usermail","");
        String profPic = sharedPreferences.getString("userprofpic", "");
        String profCover = sharedPreferences.getString("usercovrpic", "");

        //Finding the Views
        gplusMail = (TextView)findViewById(R.id.gplusMail);
        gplusName = (TextView) findViewById(R.id.gplusName);
        profImage = (ImageView) findViewById(R.id.profPic);
        profCoverRel= (RelativeLayout)findViewById(R.id.gplusinfo);

        //Loading the profile picture
        loadprof = new LoadProfileImage(profImage , profPic);
        loadprof.execute();

        //Loading the cover picture
        loadcover = new LoadCoverImage(profCoverRel , profCover);
        loadcover.execute();

        //Setting the name & mail text
        gplusName.setText(profName);
        gplusMail.setText(profMail);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void Barcamp(View view){
        Intent intent = new Intent(LineupActivity.this , BarcampActivity.class);
        startActivity(intent);
    }

    public void Democamp(View view){

    }

    public void Speakers(View view){

    }

    public void Sessions(View view){

    }

}
