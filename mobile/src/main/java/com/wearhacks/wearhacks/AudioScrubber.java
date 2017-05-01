package com.wearhacks.wearhacks;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.wearhacks.wearhacks.R;

import java.io.IOException;


public class AudioScrubber {

   // private String audioPath; //use global for now..........
    private MediaPlayer mediaPlayer;
    private int playbackPosition = 0;
    private MainActivity main;
    private SeekBar seekBar;

    public AudioScrubber(MainActivity m){
        main = m;
    }

    public void doClick(View view) {
        Log.d("Response: ", "> " + "AM IN AUDIOSCRUBBBBERERERRRRRERERERERRERERER " + R.id.activity_main_playbutton);
        switch (view.getId()) {
            case R.id.activity_main_playbutton:
                try {
                    playAudio(MainActivity.audioUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.activity_main_pausebutton:
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    playbackPosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                }
                break;
        }
    }

    public void playAudio(String url) throws Exception {
        killMediaPlayer();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare();
        mediaPlayer.seekTo(playbackPosition);
        mediaPlayer.start();
        seekFunc();
    }

    public void seekFunc() {
        seekBar = (SeekBar) main.findViewById(R.id.activity_main_seekBar);
        seekBar.setMax(mediaPlayer.getDuration());
    }

    Runnable run = new Runnable() {

        @Override
        public void run() {
            seekUpdation();
        }
    };

    public void seekUpdation() {
        final Handler mHandler = new Handler();
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        mHandler.postDelayed(run, 1000);
    }


    private void killMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public void setAudioPath(String url) {

        //this.audioPath = url;
        MainActivity.audioUrl = "";
    }

    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }

}