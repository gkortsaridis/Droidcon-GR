package org.gdgthess.droidcongr;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class SessionsActivity extends ActionBarActivity {

    ImageView coverImage , profImage;
    TextView name , time , date , descr , speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        coverImage = (ImageView) findViewById(R.id.coverImageView);
        profImage = (ImageView) findViewById(R.id.logoImageView);
        name = (TextView) findViewById(R.id.sessionNameTxt);
        time = (TextView) findViewById(R.id.theTimeTxt);
        date = (TextView) findViewById(R.id.theDateTxt);
        descr = (TextView) findViewById(R.id.descrTxt);
        speaker = (TextView) findViewById(R.id.speakerTxt);

        Bundle bundle = getIntent().getExtras();
        name.setText(bundle.getString("Name","Unknown Name"));
        time.setText(bundle.getString("Hours","Unknown time"));
        date.setText(bundle.getString("Date","Unknown date"));
        descr.setText(bundle.getString("Description","Unknown description"));
        speaker.setText(bundle.getString("Speaker","Unknown Speaker"));


        Picasso.with(getBaseContext()).load(bundle.getString("CoverUrl","http://i.imgur.com/DvpvklR.png")).into(coverImage);
        Picasso.with(getBaseContext()).load(bundle.getString("LogoUrl", "http://i.imgur.com/DvpvklR.png")).into(profImage);




    }



    public void AddToCalendar(View view){

    }
}
