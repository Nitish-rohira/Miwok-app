package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

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
        NumberList.add(new word("Father","apa", R.drawable.family_father, R.raw.family_father));
        NumberList.add(new word("Mother","ata", R.drawable.family_mother, R.raw.family_mother));
        NumberList.add(new word("Son","angsi",R.drawable.family_son, R.raw.family_son));
        NumberList.add(new word("Daughter","tune",R.drawable.family_daughter, R.raw.family_daughter));
        NumberList.add(new word("Elder Brother","taachi",R.drawable.family_older_brother, R.raw.family_older_brother));
        NumberList.add(new word("Younger Brother","chalitti",R.drawable.family_younger_brother, R.raw.family_younger_brother));
        NumberList.add(new word("Elder Sister","tete",R.drawable.family_older_sister, R.raw.family_older_sister));
        NumberList.add(new word("Younger Sister","kolitti",R.drawable.family_younger_sister, R.raw.family_younger_sister));
        NumberList.add(new word("Grandmother","amma",R.drawable.family_grandmother, R.raw.family_grandmother));
        NumberList.add(new word("Grandfather","pappa",R.drawable.family_grandfather, R.raw.family_grandfather));

        wordAdapter itemsAdapter = new wordAdapter (this ,NumberList, R.color.category_family);
        ListView listView= (ListView) findViewById(R.id.activity_numbers);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view , int position , long id){
                word wrd = NumberList.get(position);
                releaseMedia();

                int result = mAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC , AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, wrd.getmAudioResourceId());
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
