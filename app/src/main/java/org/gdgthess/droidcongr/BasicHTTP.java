package org.gdgthess.droidcongr;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

public class BasicHTTP {


    public static String GET(String url,String parameter,String value){
        InputStream inputStream = null;
        String result = "";

        // Create a new HttpClient and Post Header
        HttpClient httpclient;
        HttpPost httppost = new HttpPost(url);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair(parameter, value));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
            //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.DEFAULT_CONTENT_CHARSET));

            // create HttpClient
            httpclient = new DefaultHttpClient();

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            // receive response as inputStream
            inputStream = response.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
            {
                //Log.i("result","NOT NULL");
                result = convertInputStreamToString(inputStream);
            }
            else
            {
                Log.i("result","NULL");
                result = "Did not work!";
            }

            //Log.i("BasicHTTP received : ",result);


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream,"UTF-8"));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();


        return result;

    }

}