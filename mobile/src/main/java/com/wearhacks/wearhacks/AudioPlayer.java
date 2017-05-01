package com.wearhacks.wearhacks;

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

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;


//what does this class do?????????????????????????????????????????????????????????????????????????????????????????????????????
public class AudioPlayer extends Fragment {

    @InjectView(R.id.album)
    ImageView albumArt;

    MainActivity main = new MainActivity();
    MainFragment mainFragment = new MainFragment();

    public int trackNum = -1;

    private static String TAG_AUDIO = "stream_url";

    //Use the global audioUrl -TRistan (who does not know what he is doing)
    //public String audioUrl = "";

    public JSONArray tracks;
    public JSONObject track;

    //Why is this here??????? I commented it out for now -Tristan
    //String url = "http://api.soundcloud.com/users/" + mainFragment.id +"/tracks?client_id=376f225bf427445fc4bfb6b99b72e0bf";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Response: ", "> " + "am currently in AudioPlayer.java!!");
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_audio_player, container, false);

        ButterKnife.inject(rootView);

        //new LoadData().execute();

        return rootView;
    }

    public void updateImageView(){
        if(MainActivity.loadedImage){
            Log.d("Response: ", "> " + "album url: " + MainActivity.albumArtUrl);
            Picasso.with(getContext()).load(MainActivity.albumArtUrl).into(albumArt);
        }
    }
/*
         *//* Async task class to get json by making HTTP call
         *//*
        private class LoadData extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected Void doInBackground(Void... arg0) {
                // Creating service handler class instance
                ServiceHandler sh = new ServiceHandler();

                // Making a request to url and getting response
                String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

                Log.d("id", "ADSADSA");
                Log.d("Response: ", "> " + jsonStr);

                if (jsonStr != null) {
                    try {
                        /*//*Success
                        if(("http://api.soundcloud.com/users/" + .id +"/tracks?client_id=376f225bf427445fc4bfb6b99b72e0bf").equals(url)){
                            tracks = new JSONArray(jsonStr);
                        }else{
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
                super.onPostExecute(result);
                playNextTrack();
            }

        }

    public void playNextTrack(){
            try {
                track = tracks.getJSONObject(trackNum + 1);
                audioUrl = track.getString(TAG_AUDIO);
            }catch(JSONException e) {
                Log.d("track", "no valid track");
            }
        main.getAudio().setAudioPath(audioUrl);
    }*/
}