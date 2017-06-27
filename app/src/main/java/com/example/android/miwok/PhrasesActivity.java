package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.os.Build.VERSION_CODES.M;

public class PhrasesActivity extends AppCompatActivity {

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
        NumberList.add(new word("one plus one", "lutti",  R.raw.phrase_are_you_coming));
        NumberList.add(new word("Two plus Two", "ottiko", R.raw.phrase_come_here));
        NumberList.add(new word("Three and Three", "tolookosu",R.raw.phrase_how_are_you_feeling));
        NumberList.add(new word("Four or Four", "oyyisa", R.raw.phrase_im_coming));
        NumberList.add(new word("Five Five", "massokka", R.raw.phrase_im_feeling_good));
        NumberList.add(new word("Six Si", "temmokka", R.raw.phrase_lets_go));
        NumberList.add(new word("Seven Eminem", "kenekaku", R.raw.phrase_my_name_is));
        NumberList.add(new word("Eight Rate", "kawinta", R.raw.phrase_what_is_your_name));
        NumberList.add(new word("Nine Wine", "wo'e", R.raw.phrase_where_are_you_going));
        NumberList.add(new word("Ten Bro", "na'aacha", R.raw.phrase_yes_im_coming));

        wordAdapter itemsAdapter = new wordAdapter(this, NumberList , R.color.category_phrases);
        ListView listView = (ListView) findViewById(R.id.activity_numbers);
        listView.setAdapter(itemsAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view , int position , long id){
                word wrd = NumberList.get(position);

                releaseMedia();

                int result = mAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC , AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, wrd.getmAudioResourceId());
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