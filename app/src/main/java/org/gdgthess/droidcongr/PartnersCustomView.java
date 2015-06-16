package org.gdgthess.droidcongr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by yoko on 6/13/15.
 */
public class PartnersCustomView extends LinearLayout
{
    String myName;
    String myWebsite;

    final Activity callingActivity;

    public PartnersCustomView(Context context , final String Name , String imgUrl , String website , final Activity callingActivity)
    {
        super(context);

        myName = Name;
        myWebsite = website;
        this.callingActivity = callingActivity;

        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));


        ImageButton imageButton = new ImageButton(context);
        Picasso.with(context).load(imgUrl).into(imageButton);
        int width  = 300;
        int height = 300;
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
        parms.setMargins(50,0,0,0);
        imageButton.setLayoutParams(parms);
        imageButton.setBackgroundResource(R.drawable.transparent_background);
        imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myWebsite));
                callingActivity.startActivity(browserIntent);
            }
        });
        addView(imageButton);

        Button button = new Button(context);
        button.setText(Name);
        button.setTextColor(R.color.navigationBackColor);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myWebsite));
                callingActivity.startActivity(browserIntent);
            }
        });
        button.setBackgroundResource(R.drawable.transparent_background);
        parms = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        parms.setMargins(100,0,100,0);
        button.setLayoutParams(parms);
        addView(button);
    }

    public int getId(){ return getId(); }


}

