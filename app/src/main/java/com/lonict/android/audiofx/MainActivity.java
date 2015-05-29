package com.lonict.android.audiofx;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.audiofx.BassBoost;
import android.media.audiofx.EnvironmentalReverb;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Virtualizer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.plus.*;


public class MainActivity extends Activity {

    //private SeekBar volumeSeekbar = null;

    private AudioManager audioManager;
    //private BassBoost bassBoost = new BassBoost(0,1);
    private static InterstitialAd interstitial;
    private Equalizer mEQ = new Equalizer(0,0);
    private BassBoost mBass = new BassBoost(0,0);
    private Virtualizer mVirtualizer = new Virtualizer(0,0);
    PlusOneButton mPlusOneButton ;

    private int mId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.activity_main);
        initShare();
        initViews();
        new Effects(this,mEQ,mVirtualizer,mBass).init();
        createNotification("Active");
    }

    @Override
    public void onDestroy()
    {
        clearNotification();
        super.onDestroy();
    }


    public void clearNotification()
    {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(mId);
    }

    public void createNotification(String desc)
    {
        Intent intent = new Intent(this, MainActivity.class);

        //PendingIntent.FLAG_CANCEL_CURRENT will bring the app back up again
        PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this,PendingIntent.FLAG_CANCEL_CURRENT, intent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notif)
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText(desc)
                        .setContentIntent(pIntent)
                        .setOngoing(true);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }

    public void initViews()
    {
        Typeface font_small = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/airstrea.ttf");

        ((TextView)findViewById(R.id.textView2)).setTypeface(font_small);
        ((TextView)findViewById(R.id.textView3)).setTypeface(font_small);
        ((TextView)findViewById(R.id.textView4)).setTypeface(font_small);
        ((CheckBox)findViewById(R.id.checkBox_bass)).setTypeface(font_small);
        ((CheckBox)findViewById(R.id.checkBox_virtualizer)).setTypeface(font_small);
        ((TextView)findViewById(R.id.textView2)).setTextSize(20F);
        ((TextView)findViewById(R.id.textView3)).setTextSize(20F);
        ((TextView)findViewById(R.id.textView4)).setTextSize(20F);
        ((CheckBox)findViewById(R.id.checkBox_bass)).setTextSize(20F);
        ((CheckBox)findViewById(R.id.checkBox_virtualizer)).setTextSize(20F);
        //((TextView)findViewById(R.id.textView_bass_title)).setTextSize(45F);
        //((TextView)findViewById(R.id.textView_bass_title)).setTypeface(font_small);

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getResources().getString(R.string.admob_interstitial_id));
        //mPlusOneButton = (PlusOneButton) findViewById(R.id.plus_one_button);


    }
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    protected void onResume() {
        super.onResume();
        // Refresh the state of the +1 button each time the activity receives focus.
//        mPlusOneButton.initialize("https://play.google.com/store/apps/details?id=com.lonict.android.bassbooster", PLUS_ONE_REQUEST_CODE);
    }

    private void initShare() {

        ((ImageView)findViewById(R.id.imageView_share)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Please check : https://play.google.com/store/apps/details?id=com.lonict.android.audiofx";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Try Sound Mixer!");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }

    @Override
    public void onBackPressed() {
        showDialog();

        //Toast.makeText(getApplicationContext(), "Sound Mixer is running on background", Toast.LENGTH_SHORT).show();


    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //if one of the volume keys were pressed
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP)
        {
            //change the seek bar progress indicator position
            volumeSeekbar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        }
        //propagate the key event
        return super.onKeyDown(keyCode, event);
    }*/

    public static void showAds(){
        try {
            AdRequest adRequest = new AdRequest.Builder().build();
            interstitial.loadAd(adRequest);
            interstitial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    interstitial.show();
                }
            });
        }catch (Exception e){
            //Log.d("ShowAds ERROR",e.getMessage()+"");
        }
    }
    private int mAdCount=1  ;
    public  boolean isValidAdCount()
    {
        if ( (mAdCount%25)==0)
        {
            mAdCount=1;
            return true;
        }else
        {
            mAdCount++;
            return false;
        }
    }
    public RotateAnimation refreshRotateAnimation() {
        ImageView imageView_refresh = (ImageView) findViewById(R.id.imageView_refresh);
        RotateAnimation animation = new RotateAnimation(360f, 0f, imageView_refresh.getWidth() / 2, imageView_refresh.getHeight() / 2);
        animation.setRepeatCount(0);
        animation.setDuration(1000);
        imageView_refresh.startAnimation(animation);
        return animation;
    }

    public void showDialog()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Please select action");
        alertDialog.setMessage("Press run for activating sound mixer on background");
        alertDialog.setIcon(R.drawable.ic_notif);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                ((AlertDialog)dialog).getButton(dialog.BUTTON_POSITIVE).setTextSize(11F);
                ((AlertDialog)dialog).getButton(dialog.BUTTON_NEGATIVE).setTextSize(11F);
            }
        });

        class alertDialogOnClickListener implements DialogInterface.OnClickListener {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: {
                        moveTaskToBack(true);
                        break;
                    }
                    case DialogInterface.BUTTON_NEGATIVE: {
                        clearNotification();
                        //android.os.Process.killProcess(android.os.Process.myPid());
                        //moveTaskToBack(true);
                        finish();
                        System.exit(0);
                        break;
                    }
                }
            }
        }
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Run",new alertDialogOnClickListener());
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Close",new alertDialogOnClickListener());

        alertDialog.show();
    }


}