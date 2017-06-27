package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener(){
        public void onAudioFocusChange(int FocusChange){
            if(FocusChange== mAudioManager.AUDIOFOCUS_LOSS_TRANSIENT || FocusChange== mAudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(FocusChange == mAudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }

            else if(FocusChange == mAudioManager.AUDIOFOCUS_LOSS){
                releaseMedia();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletion = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp){
            releaseMedia();
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        releaseMedia();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<word> NumberList = new ArrayList<word>();
        NumberList.add(new word("red","lutti",R.drawable.color_red,  R.raw.color_red));
        NumberList.add(new word("Blue","ottiko",R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        NumberList.add(new word("Green","tolookosu",R.drawable.color_green, R.raw.color_green));
        NumberList.add(new word("Black","oyyisa",R.drawable.color_black, R.raw.color_black));
        NumberList.add(new word("Indigo","massokka",R.drawable.color_gray, R.raw.color_gray));
        NumberList.add(new word("Brown","temmokka",R.drawable.color_brown, R.raw.color_brown));
        NumberList.add(new word("Yellow","kenekaku",R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        NumberList.add(new word("Orange","kawinta",R.drawable.color_red, R.raw.color_red));
        NumberList.add(new word("Pink","wo'e",R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        NumberList.add(new word("White","na'aacha",R.drawable.color_white, R.raw.color_white));

        wordAdapter itemsAdapter = new wordAdapter (this ,NumberList , R.color.category_colors);
        ListView listView= (ListView) findViewById(R.id.activity_numbers);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view , int position , long id){
                word wrd = NumberList.get(position);
                releaseMedia();

                int result = mAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC , AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, wrd.getmAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletion);
                }
            }
        });
    }

    private void releaseMedia(){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;

            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }
}
