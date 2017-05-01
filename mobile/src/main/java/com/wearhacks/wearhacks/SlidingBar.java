package com.wearhacks.wearhacks;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.wunderlist.slidinglayer.SlidingLayer;

public class SlidingBar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_bar);

        SlidingLayer slidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer1);

        slidingLayer.setShadowDrawable(R.drawable.ic_sidebar_shadow);
        slidingLayer.setShadowSizeRes(R.dimen.shadow_size);
        slidingLayer.setOffsetDistanceRes(R.dimen.offset_distance);
        slidingLayer.setPreviewOffsetDistanceRes(R.dimen.preview_offset_distance);
        slidingLayer.setStickTo(SlidingLayer.STICK_TO_BOTTOM);
        slidingLayer.setChangeStateOnTap(false);

        slidingLayer.addView(new Button(this));
    }

}
