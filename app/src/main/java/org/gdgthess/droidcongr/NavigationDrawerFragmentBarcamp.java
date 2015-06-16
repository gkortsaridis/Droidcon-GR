package org.gdgthess.droidcongr;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragmentBarcamp extends Fragment {

    public static final String PREF_FILE_NAME="test_pref";
    public static final String KEY_USER_LEARNED_DRAWER="User learned drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private View containerView;

    ListView list;
    View v;

    ArrayList<String> names;
    ArrayList<String> dates;
    ArrayList<String> fromHours;
    ArrayList<String> toHours;
    ArrayList<Boolean> isClickables;
    ArrayList<String> descriptions;
    ArrayList<String> coverUrls;
    ArrayList<String> profImgUrls;
    ArrayList<String> speakers;

    AsyncTask<String, Void, String> httptask;

    public NavigationDrawerFragmentBarcamp() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        names = new ArrayList<>();
        dates = new ArrayList<>();
        fromHours = new ArrayList<>();
        toHours = new ArrayList<>();
        isClickables = new ArrayList<>();
        descriptions = new ArrayList<>();
        coverUrls = new ArrayList<>();
        profImgUrls = new ArrayList<>();
        speakers = new ArrayList<>();


        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState!=null)mFromSavedInstanceState = true;
        else mFromSavedInstanceState=false;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.activity_barcamp_navigation_drawer_fragment, container, false);
        v = layout;

        httptask = new HttpAsyncTask().execute("http://83.212.118.131/droidcongr/barcamp.php", "", "");
        String x;
        try {
            //Pairnoume tin apantisi tou server
            x = httptask.get();
            x = x.replace("\\", "");

            try {
                JSONObject jsonRootObject = new JSONObject(x);

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = jsonRootObject.optJSONArray("Barcamp");

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String name = jsonObject.optString("name").toString();
                    String date = jsonObject.optString("date").toString();
                    String fromHour = jsonObject.optString("from_time").toString();
                    String toHour = jsonObject.optString("to_time").toString();
                    String descr = jsonObject.optString("description").toString();
                    String coverurl = jsonObject.optString("photoCover").toString();
                    String logourl = jsonObject.optString("speakerPhoto").toString();
                    Boolean isclick = jsonObject.optBoolean("is_clickable");
                    String speaker = jsonObject.optString("speaker");
                    names.add(name);
                    dates.add(date);
                    fromHours.add(fromHour);
                    toHours.add(toHour);
                    descriptions.add(descr);
                    coverUrls.add(coverurl);
                    profImgUrls.add(logourl);
                    isClickables.add(isclick);
                    speakers.add(speaker);
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }


            list = (ListView) v.findViewById(R.id.customList);
            final CustomListAdapter adapter = new CustomListAdapter(getActivity(), names, fromHours, isClickables);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    Intent intent = new Intent(v.getContext(), SessionsActivity.class);
                    intent.putExtra("Name", names.get(position));
                    intent.putExtra("Date", dates.get(position));
                    intent.putExtra("Hours", fromHours.get(position) + " - " + toHours.get(position));
                    intent.putExtra("Description", descriptions.get(position));
                    intent.putExtra("CoverUrl", coverUrls.get(position));
                    intent.putExtra("LogoUrl", profImgUrls.get(position));
                    intent.putExtra("Speaker", speakers.get(position));
                    startActivity(intent);
                }

            });



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return layout;
    }

    public void openMyDrawer(){
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    public void setUp(int fragmentID, DrawerLayout drawerlayout ) {
        containerView = getActivity().findViewById(fragmentID);
        mDrawerLayout = drawerlayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerlayout,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");

                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();
            }

        };


        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }

        if(mDrawerLayout != null) {
            mDrawerLayout.setDrawerListener(mDrawerToggle);

            mDrawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    mDrawerToggle.syncState();
                }
            });
        }

    }

    public static void saveToPreferences(Context context,String prefName , String prefValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefName,prefValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context,String prefName , String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(prefName,defaultValue);
    }

}
