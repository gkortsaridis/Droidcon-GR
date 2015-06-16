package org.gdgthess.droidcongr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.InputStream;

/**
 * Created by yoko on 6/12/15.
 */
public class LoadCoverImage extends AsyncTask<String, Void, Bitmap> {
    RelativeLayout bmImage;
    String urldisplay;

    public LoadCoverImage(RelativeLayout bmImage , String url) {
        this.bmImage = bmImage;
        this.urldisplay = url;
    }

    protected Bitmap doInBackground(String... urls) {

        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        BitmapDrawable background = new BitmapDrawable(result);
        bmImage.setBackgroundDrawable(background);
    }
}
