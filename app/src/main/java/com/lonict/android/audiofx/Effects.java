package com.lonict.android.audiofx;

import android.content.Context;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Virtualizer;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * Created by Efe Avsar on 26/05/2015.
 */
public class Effects {

    private Equalizer mEQ ;
    private BassBoost mBassBoost ;
    private Virtualizer mVirtualizer;
    //private Context context;
    private MainActivity mainActivity;

    private SeekBar lowSeekbar ;
    private SeekBar midSeekbar ;
    private SeekBar highSeekbar ;

    private CheckBox checkBox_bass;
    private CheckBox checkBox_vir;

    public Effects(Context context,Equalizer equalizer, Virtualizer virtualizer,BassBoost bassBoost)
    {
        //this.context = context;
        this.mVirtualizer= virtualizer;
        this.mEQ=equalizer;
        this.mBassBoost =bassBoost;
        mainActivity = (MainActivity)context;
        //init();

    }
    public void init()
    {
        initEQ();

        ImageView refresh = (ImageView)mainActivity.findViewById(R.id.imageView_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bypassSettings();
                mainActivity.refreshRotateAnimation();

            }
        });

        checkBox_bass = (CheckBox)mainActivity.findViewById(R.id.checkBox_bass);
        checkBox_bass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try
                {
                    if (isChecked)
                    {
                        mBassBoost.setStrength((short) ((short) 1000));
                        mBassBoost.setEnabled(true);
                    }else mBassBoost.setEnabled(false);
                }catch(Exception e)
                {
                    Log.d("Bass ERROR", e.toString());
                }


            }
        });

        checkBox_vir = (CheckBox)mainActivity.findViewById(R.id.checkBox_virtualizer);
        checkBox_vir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    try
                    {
                        mVirtualizer.setStrength((short)(1000));
                        mVirtualizer.setEnabled(true);

                    }catch (Exception e)
                    {
                        Log.d("Virt. ERROR", e.toString());
                    }
                   // Log.d("Virrrrrr", "checkkkk!!!");
                }else mVirtualizer.setEnabled(false);
            }
        });

    }


    public void initEQ()
    {
        try {
            mEQ.setEnabled(true);
            final ImageView bassImage = (ImageView)mainActivity.findViewById(R.id.imageView);
            bassImage.setAlpha(50);
            // LOW!!!
            lowSeekbar = (SeekBar) mainActivity.findViewById(R.id.seekBar_bass);
            lowSeekbar.setProgress(50);
            lowSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                    //              Log.d("STOP TRACKING","TEST123123123");
                    if (mainActivity.isValidAdCount())
                    {
                        mainActivity.showAds();
                        //                    Log.d("Adshow","TEST123123123");
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar arg0) {

                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    Log.d("Bassboost Progress", progress + "");
                    try {
                        if (progress<50)
                        {
                            setBandLevel(4,progress,true);
                            setBandLevel(3,progress*2/3,true);
                        } else if (progress>50)                        {
                            setBandLevel(4, progress, false);
                            setBandLevel(3, progress*2/3, false);
                        }
                        else
                        {
                            setBandLevel(4, 50, false);
                            setBandLevel(3, 50, false);
                        }
                        ImageView bassImage = (ImageView) mainActivity.findViewById(R.id.imageView);
                        bassImage.setAlpha(50 + progress);

                    } catch (Exception e) {
                        Log.d("Bassboost HATA:", e.getMessage());
                    }
                }
            });

            // MID!!!
            midSeekbar = (SeekBar) mainActivity.findViewById(R.id.seekBar_mid);
            midSeekbar.setProgress(50);
            midSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                    //              Log.d("STOP TRACKING","TEST123123123");
                    if (mainActivity.isValidAdCount())
                    {
                        mainActivity.showAds();
                        //                    Log.d("Adshow","TEST123123123");
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar arg0) {

                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    //                  Log.d("Bassboost Progress", progress + "");
                    try {

                        if (progress<50)
                        {
                            setBandLevel(2, progress, true);
                        } else if (progress>50)
                        {
                            setBandLevel(2, progress, false);
                        } else
                        {
                            setBandLevel(2,50,false);
                        }
                        ImageView bassImage = (ImageView) mainActivity.findViewById(R.id.imageView);
                        bassImage.setAlpha(50 + progress);

                    } catch (Exception e) {
                        Log.d("seekBar_mid HATA:", e.getMessage());
                    }
                }
            });

            // HIGH!!!
            highSeekbar = (SeekBar) mainActivity.findViewById(R.id.seekBar_high);
            highSeekbar.setProgress(50);
            highSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                    //              Log.d("STOP TRACKING","TEST123123123");
                    if (mainActivity.isValidAdCount())
                    {
                        mainActivity.showAds();
                        //                    Log.d("Adshow","TEST123123123");
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar arg0) {

                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    //                  Log.d("Bassboost Progress", progress + "");
                    try {

                        //Log.d("HIGH ",((50-progress)*mEQrange/100)+"");

                        if (progress<50)
                        {
                            setBandLevel(1,progress*2/3,true);
                            setBandLevel(0,progress,true);

                        } else if (progress>50)
                        {
                            setBandLevel(0,progress,false);
                            setBandLevel(1, progress*2/3, false);
                        } else
                        {
                            setBandLevel(0,50,false);
                            setBandLevel(1,50,false);
                        }
                        ImageView bassImage = (ImageView) mainActivity.findViewById(R.id.imageView);
                        bassImage.setAlpha(50 + progress);

                    } catch (Exception e) {
                        Log.d("highSeekbar HATA:", e.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            Log.d("highSeekbar HATA:", e.getMessage()+"");
        }
    }
    /*public void setEQLevel(short band, short level) {
        try{
            if(mEQrange >= Math.abs(level))
                mEQ.setBandLevel(band, level);
        }catch (Exception e){

            Log.d("ERROR!!! setEQLevel",""+e.getMessage());
            // dialog.showDialog();
        }

//        Log.e("setLevel band",""+band);
//        Log.e("setLevel level",""+level);
    }*/

    public void setBandLevel (int band , int level,boolean isnegative)
    {
        int range ;

        if(Math.abs(mEQ.getBandLevelRange()[0])== Math.abs(mEQ.getBandLevelRange()[1]))
            range= (short) Math.abs(mEQ.getBandLevelRange()[0]);
        else if(Math.abs(mEQ.getBandLevelRange()[0]) < Math.abs(mEQ.getBandLevelRange()[1]))
            range= (short) Math.abs(mEQ.getBandLevelRange()[0]);
        else range= (short) Math.abs(mEQ.getBandLevelRange()[1]);

        Log.d("Range",range+"");
        if (isnegative)
        {
            mEQ.setBandLevel((short)band,(short)(-(50-level)*range/50));
        } else mEQ.setBandLevel((short)band,(short)((level-50)*range/50));
    }

    public void bypassSettings()
    {
        /*mEQ.setBandLevel((short) 0, (short) 0);
        mEQ.setBandLevel((short)1,(short)0);
        mEQ.setBandLevel((short)2,(short)0);
        mEQ.setBandLevel((short)3,(short)0);
        mEQ.setBandLevel((short)4,(short)0);*/
        //mEQ.setEnabled(false);
        //mEQ.setEnabled(true);
        //mEQ.release();
        //mEQ.setEnabled(false);
        //mVirtualizer.setEnabled(false);
        //mVirtualizer.setEnabled(false);

        checkBox_vir.setChecked(false);
        checkBox_bass.setChecked(false);

        highSeekbar.setProgress(50);
        lowSeekbar.setProgress(50);
        midSeekbar.setProgress(50);


        //mEQ.setEnabled(false);
        Log.d("Test BYPASS","1123123123123123");
    }

}

