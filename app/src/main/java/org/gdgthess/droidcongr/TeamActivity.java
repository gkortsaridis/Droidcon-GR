package org.gdgthess.droidcongr;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class TeamActivity extends ActionBarActivity {

    ListView list;
    LinearLayout ll;
    ArrayList<String> itemname;
    ArrayList<String> imgurl;
    ArrayList<String> partnerUrl;


    private AlphaForeGroundColorSpan mAlphaForegroundColorSpan;
    private SpannableString mSpannableString;

    AsyncTask<String, Void, String> httptask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        itemname = new ArrayList<>();
        imgurl = new ArrayList<>();
        partnerUrl = new ArrayList<>();

        httptask = new HttpAsyncTask().execute("http://83.212.118.131/droidcongr/team.php", "", "");
        String x;
        try {
            //Pairnoume tin apantisi tou server
            x = httptask.get();
            x = x.replace("\\", "");

            try {
                JSONObject jsonRootObject = new JSONObject(x);

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = jsonRootObject.optJSONArray("Partners");

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String name = jsonObject.optString("name").toString();
                    String imgUrl = jsonObject.optString("imgUrl").toString();
                    String website = jsonObject.optString("website").toString();
                    Log.i("GOT NAME", name);
                    itemname.add(name);
                    imgurl.add(imgUrl);
                    partnerUrl.add(website);
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

            ll = (LinearLayout) findViewById(R.id.scrollviewLayoutPartners);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


            mSpannableString = new SpannableString("DROID-YOLO-CON _ G-YOLO-R");
            mAlphaForegroundColorSpan = new AlphaForeGroundColorSpan(0xFFFFFF);

            final ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
            cd.setAlpha(0);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
            actionBar.setBackgroundDrawable(cd);

            final ScrollViewHelper scrollViewHelper = (ScrollViewHelper)findViewById(R.id.scrollViewHelper);
            scrollViewHelper.setOnScrollViewListener(new ScrollViewHelper.OnScrollViewListener() {
                @Override
                public void onScrollChanged(ScrollViewHelper v, int l, int t, int oldl, int oldt) {
                    setTitleAlpha(255 - getAlphaforActionBar(v.getScrollY()));
                    cd.setAlpha(getAlphaforActionBar(v.getScrollY()));
                }

                private int getAlphaforActionBar(int scrollY) {
                    int minDist = 0, maxDist = 550;
                    if(scrollY>maxDist){
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

            for(int i=0; i<itemname.size(); i++){
                final PartnersCustomView pcv = new PartnersCustomView(getApplicationContext(), itemname.get(i) , imgurl.get(i) , partnerUrl.get(i) , this);
                ll.addView(pcv);
            }

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Intent intent = new Intent(TeamActivity.this , MainActivity.class);
            startActivity(intent);
        }
        else Log.i("Clicked",menuItem.getItemId()+"");
        return super.onOptionsItemSelected(menuItem);
    }

    private void setTitleAlpha(float alpha) {
        if(alpha<1){ alpha = 1; }
        mAlphaForegroundColorSpan.setAlpha(alpha);
        mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(mSpannableString);
    }

}
