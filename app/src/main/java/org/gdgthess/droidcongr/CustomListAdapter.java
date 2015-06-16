package org.gdgthess.droidcongr;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;

    private final ArrayList<String> itemname;
    private final ArrayList<String> itemDescr;
    private final ArrayList<Boolean> isClickable;

    public CustomListAdapter(Activity context, ArrayList<String> itemname, ArrayList<String> itemDescr , ArrayList<Boolean> isClickable) {
        super(context, R.layout.two_text_list, itemname);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.itemname=itemname;
        this.itemDescr = itemDescr;
        this.isClickable = isClickable;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.two_text_list, null,true);



        TextView txtTitle = (TextView) rowView.findViewById(R.id.itemNameTxt);
        TextView txtDescr = (TextView) rowView.findViewById(R.id.itemDescrTxt);

        txtTitle.setText(itemname.get(position));
        txtDescr.setText(itemDescr.get(position));

        txtTitle.setEnabled(isClickable.get(position));
        txtDescr.setEnabled(isClickable.get(position));
        rowView.setEnabled(isClickable.get(position));

        txtTitle.setClickable(!isClickable.get(position));
        txtDescr.setClickable(!isClickable.get(position));
        rowView.setClickable(!isClickable.get(position));

        return rowView;

    };






}