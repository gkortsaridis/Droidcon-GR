package org.gdgthess.droidcongr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by yoko on 6/12/15.
 */
public class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    RoundImage roundProf;
    String urldisplay;

    public LoadProfileImage(ImageView bmImage , String url) {
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
        roundProf = new RoundImage(result);
        bmImage.setImageDrawable(roundProf);
    }
}
