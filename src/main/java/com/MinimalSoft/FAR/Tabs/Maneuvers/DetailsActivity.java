package com.MinimalSoft.FAR.Tabs.Maneuvers;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.FAR.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_TITTLE = "Titulo";
    public static final String EXTRA_IMAGE = "Imagen";
    public static final String EXTRA_STEPS = "Steps";

    PhoneCallListener phoneListener = new PhoneCallListener();
    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        final String titulo = intent.getStringExtra(EXTRA_TITTLE);
        final String imagen = intent.getStringExtra(EXTRA_IMAGE);
        final int steps = intent.getIntExtra(EXTRA_STEPS,0);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(titulo);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCall) ;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel: 7771336447"));

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent); //the call start here, work perfect
            }
        });

        loadBackdrop(imagen);
        setSteps(steps, imagen);

    }

    private void loadBackdrop(String imagen) {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        int id = getResources().getIdentifier(imagen, "drawable", getPackageName());
        Picasso.with(this).load(id).into(imageView);
    }

    private void setSteps (int steps, String titulo)
    {
        TextView text1 = (TextView) findViewById(R.id.step1_text);
        TextView text2 = (TextView) findViewById(R.id.step2_text);
        TextView text3 = (TextView) findViewById(R.id.step3_text);
        TextView text4 = (TextView) findViewById(R.id.step4_text);

        CardView card4 = (CardView) findViewById(R.id.step4_card);

        text1.setText(getStringId(getApplicationContext(),"step1_"+titulo));
        text2.setText(getStringId(getApplicationContext(),"step2_"+titulo));
        text3.setText(getStringId(getApplicationContext(),"step3_"+titulo));

        if(steps<4) {
            card4.setVisibility(View.GONE);
        }
        else {
            card4.setVisibility(View.VISIBLE);
            text4.setText(getStringId(getApplicationContext(),"step4_"+titulo));
        }
    }

    public static int getStringId(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {

                //Flag to know when call is ended
                isPhoneCalling = true;

                //Turn on speaker
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setMode(AudioManager.MODE_IN_CALL);
                audioManager.setSpeakerphoneOn(true);
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                if (isPhoneCalling) {
                    //Turn off speaker for future calls
                    AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setSpeakerphoneOn(false);
                    audioManager.setMode(AudioManager.MODE_NORMAL);
                    isPhoneCalling = false;

                    //Unregister callState listener
                    telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_NONE);
                }
            }
        }
    }

}
