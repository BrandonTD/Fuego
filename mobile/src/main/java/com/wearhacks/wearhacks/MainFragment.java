package com.wearhacks.wearhacks;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainFragment extends Fragment {

    public static String TAG_NAME = "username";
    public static String TAG_LOCAL = "city";
    public static String TAG_ID = "id";
    public static String TAG_IMAGE = "avatar_url";
    public static String TAG_DESCRIPTION = "description";
    public static Boolean loadedArtistData = Boolean.FALSE;

    @InjectView(R.id.activity_main_artistName)
    TextView nameText;

    @InjectView(R.id.activity_main_artistLocal)
    public TextView localText;

    @InjectView(R.id.activity_main_profileImage)
    public ImageView pictureView;

    @InjectView(R.id.activity_main_headerImage)
    public ImageView headerView;

    // URL to get contacts JSON
    //public String url = "http://ec2-54-187-65-133.us-west-2.compute.amazonaws.com/retrieve_artist.php?auth=beaconboice&beacon_id=FAKEIDFAKEMAJFAKEMIN";
    //CHANGED FOR BEACON
    public String url = "http://ec2-54-187-65-133.us-west-2.compute.amazonaws.com/retrieve_artist.php?auth=beaconboice&beacon_id=";

//    MainActivity mainActivity = new MainActivity(); //commented for debugging //causes infinite loop problem
    public int trackNum = -1;

    private static String TAG_AUDIO = "stream_url";
    public String audioUrl = "";
    public JSONArray tracks;
    public JSONObject track;
    public JSONObject getMeSomeAlbumArt;

    //Fields
    public String name = "";
    public String local = "";
    public String id = "";
    public String picture = "";
    public String header = "http://wallcomphd.com/wp-content/uploads/2015/07/Red-Cubical-Shapes-Low-Poly-Wallpaper-HD-.png?dl=1";
    OnHeadlineSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_main, container, false);


        ButterKnife.inject(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new LoadData().execute();
    }




    public void playNextTrack(){
        try {
            track = tracks.getJSONObject(trackNum + 1);
            audioUrl = track.getString(TAG_AUDIO)+"?client_id=376f225bf427445fc4bfb6b99b72e0bf";
        }catch(JSONException e) {
            Log.d("track", "no valid track");
        }

        Log.d("play", "played");

        //mainActivity.getAudio().setAudioPath(audioUrl);
        MainActivity.audioUrl=audioUrl;
        //if I can do http requests here, it makes more sense to set albumArtURl here as well
        // MainActivity.albumArtUrl = ... [see below]
        Log.d("Response: ", "> " + audioUrl);
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    public void fragExecute() {
        nameText.setText(name);
        localText.setText(local);
        Picasso.with(getContext()).load(picture).into(pictureView);
        Picasso.with(getContext()).load(header).fit().into(headerView);

        Log.d("fill", "filled");

        url = "http://api.soundcloud.com/users/" + id + "/tracks?client_id=376f225bf427445fc4bfb6b99b72e0bf";
        //url+= "?client_id=" + id;
        MainActivity.audioUrl=audioUrl;
        loadedArtistData = true;

        LoadData loadData = new LoadData();
        loadData.execute(); //commented for debugging
    }


    public class LoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            Log.d("pass", "passed");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            if(!loadedArtistData) { //THIS IS ON THE FIRST RUN TO GET PERSON'S ID
                Log.d("run1", "reached");
                Log.d("uuid", MyApplication.uuid);
                //"00000000-1111-2222-3333-4444444444440101010101";
                url += MyApplication.uuid;
                // dont know how to make url visible here how u want it
            }

            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

           // Log.d("Response: ", "> " + jsonStr.length() + ", last element: "+ jsonStr.charAt(jsonStr.length()-1) + " : " + jsonStr);

            Log.d("URL1", url);
            if (jsonStr != null) {
                try {
                    Log.d("Entered", Boolean.toString(MyApplication.entered));
                    //JSONObject jsonObj = new JSONObject(jsonStr);
                    //Success
                    //if("http://ec2-54-187-65-133.us-west-2.compute.amazonaws.com/retrieve_artist.php?auth=beaconboice&beacon_id=FAKEIDFAKEMAJFAKEMIN".equals(url)){
                    if(MyApplication.entered) {//CHANGED FOR BEACONS, CHECK THIS FOR BUGS
                        Log.d("URL2", url);
                        if (!loadedArtistData) {
                            Log.d("loaded", "true");
                            JSONObject jsonObj = new JSONObject(jsonStr);
                            name = jsonObj.getString(TAG_NAME);
                            local = jsonObj.getString(TAG_LOCAL);
                            id = jsonObj.getString(TAG_ID);
                            picture = jsonObj.getString(TAG_IMAGE);
                            //
                            //  header = jsonObj.getString(TAG_HEAD);
                        } else {
                            tracks = new JSONArray(jsonStr);
                            String albumArtJSONString = sh.makeServiceCall("http://api.soundcloud.com/tracks/" + tracks.getJSONObject(trackNum + 1).getString("id") + "?client_id=376f225bf427445fc4bfb6b99b72e0bf", ServiceHandler.GET);
                            JSONObject gimmeArt = new JSONObject(albumArtJSONString);
                            MainActivity.albumArtUrl = gimmeArt.getString("artwork_url");
                            Log.d("Response: ", "> " + "album url??? : " + MainActivity.albumArtUrl);
                            MainActivity.loadedImage = Boolean.TRUE;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //if("http://ec2-54-187-65-133.us-west-2.compute.amazonaws.com/retrieve_artist.php?auth=beaconboice&beacon_id=FAKEIDFAKEMAJFAKEMIN".equals(url)) {
            if(MyApplication.entered ) {
                if (!loadedArtistData && !picture.equals("")) {
                    fragExecute(); //commented for debugging
                } else if(tracks != null){
                    playNextTrack(); //commented for debugging
                }else {
                    LoadData loadData = new LoadData();
                    loadData.execute(); //commented for debugging
                }
            }else{
                LoadData loadData = new LoadData();
                loadData.execute(); //commented for debugging
            }
            super.onPostExecute(result);
        }

    }
}
