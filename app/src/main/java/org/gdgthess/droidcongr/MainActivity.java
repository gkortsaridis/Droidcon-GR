package org.gdgthess.droidcongr;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.squareup.picasso.Picasso;

import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    ImageView profImage;
    RoundImage roundProf;
    RelativeLayout profCoverRel;
    TextView gplusName, gplusMail;

    private Toolbar toolbar;
    NavigationDrawerFragment drawerFragment;

    SharedPreferences sharedPreferences;

    LoadProfileImage loadprof;
    LoadCoverImage loadcover;

    private AlphaForeGroundColorSpan mAlphaForegroundColorSpan;
    private SpannableString mSpannableString;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
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


        //Set the custom toolbar
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBarDrawerToggle.syncState();

        mSpannableString = new SpannableString("DROID-YOLO-CON _ G-YOLO-R");
        mAlphaForegroundColorSpan = new AlphaForeGroundColorSpan(0xFFFFFF);

        final ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
        cd.setAlpha(0);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        actionBar.setBackgroundDrawable(cd);

        ScrollViewHelper scrollViewHelper = (ScrollViewHelper)findViewById(R.id.scrollViewHelper);
        scrollViewHelper.setOnScrollViewListener(new ScrollViewHelper.OnScrollViewListener() {
            @Override
            public void onScrollChanged(ScrollViewHelper v, int l, int t, int oldl, int oldt) {
                setTitleAlpha(255 - getAlphaforActionBar(v.getScrollY()));
                cd.setAlpha(getAlphaforActionBar(v.getScrollY()));
            }

            private int getAlphaforActionBar(int scrollY) {
                int minDist = 0, maxDist = 550;
                if (scrollY > maxDist) {
                    return 255;
                } else {
                    if (scrollY < minDist) {
                        return 0;
                    } else {
                        return (int) ((255.0 / maxDist) * scrollY);
                    }
                }
            }
        });


        //Setting up the drawerFragment
        drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawerLayout));


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

    private int backButtonCount=0;
    public void onBackPressed()
    {

        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    public void LineUp(View view){
        Log.i("MainAtivity" , "Going to Line Up");
        Intent intent = new Intent(MainActivity.this , LineupActivity.class);
        startActivity(intent);
    }
    public void TheTeam(View view){
        Log.i("MainAtivity" , "Going to The Team");
        Intent intent = new Intent(MainActivity.this , TeamActivity.class);
        startActivity(intent);
    }
    public void Tickets(View view){
        Log.i("MainAtivity" , "Going to Tickets");
        Intent intent = new Intent(MainActivity.this , TicketsActivity.class);
        startActivity(intent);
    }
    public void Where(View view){
        Log.i("MainAtivity" , "Going to Where");
    }
    public void Partners(View view){
        Log.i("MainAtivity" , "Going to Partners");
        Intent intent = new Intent(MainActivity.this , PartnersActivity.class);
        startActivity(intent);
    }

    private void setTitleAlpha(float alpha) {
        if(alpha<1){ alpha = 1; }
        mAlphaForegroundColorSpan.setAlpha(alpha);
        mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(mSpannableString);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
