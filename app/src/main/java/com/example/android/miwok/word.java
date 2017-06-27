package com.example.android.miwok;

import android.media.Image;

/**
 * Created by Nitish on 16-06-2017.
 */

public class word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mMiwokImage = HAS_NO_IMAGE;
    private int mAudioResourceId;

    public static final int HAS_NO_IMAGE = -1;

    public word(String mDefault, String mMiwok, int mImage , int mAudio ){
        mDefaultTranslation = mDefault;
        mMiwokTranslation = mMiwok;
        mMiwokImage = mImage;
        mAudioResourceId = mAudio;
    }


    public word(String mDefault, String mMiwok ,int mAudio ){
        mDefaultTranslation = mDefault;
        mMiwokTranslation = mMiwok;
        mAudioResourceId = mAudio;


    }

    public String getmDefaultTranslation(){
        return mDefaultTranslation;
    }


    public String getmMiwokTranslation(){
        return mMiwokTranslation;
    }

    public int getmMiwokImage(){
       return mMiwokImage;
    }

    public boolean hasImage(){
        return mMiwokImage != HAS_NO_IMAGE;
    }

    public int getmAudioResourceId(){
        return mAudioResourceId;
    }


}

