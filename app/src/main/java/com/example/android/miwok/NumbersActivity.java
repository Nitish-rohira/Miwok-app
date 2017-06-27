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



public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer ;
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
        NumberList.add(new word("one","lutti",R.drawable.number_one, R.raw.number_one ));
        NumberList.add(new word("Two","ottiko",R.drawable.number_two, R.raw.number_two));
        NumberList.add(new word("Three","tolookosu",R.drawable.number_three, R.raw.number_three));
        NumberList.add(new word("Four","oyyisa",R.drawable.number_four,  R.raw.number_four));
        NumberList.add(new word("Five","massokka",R.drawable.number_five,R.raw.number_five));
        NumberList.add(new word("Six","temmokka",R.drawable.number_six,  R.raw.number_six));
        NumberList.add(new word("Seven","kenekaku",R.drawable.number_seven, R.raw.number_seven));
        NumberList.add(new word("Eight","kawinta",R.drawable.number_eight, R.raw.number_eight));
        NumberList.add(new word("Nine","wo'e",R.drawable.number_nine, R.raw.number_nine));
        NumberList.add(new word("Ten","na'aacha",R.drawable.number_ten,  R.raw.number_ten));

        wordAdapter itemsAdapter = new wordAdapter (this ,NumberList,R.color.category_numbers);
        ListView listView= (ListView) findViewById(R.id.activity_numbers);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view , int position , long id){
                 word wrd = NumberList.get(position);
                releaseMedia();
                int result = mAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC , AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, wrd.getmAudioResourceId());
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
