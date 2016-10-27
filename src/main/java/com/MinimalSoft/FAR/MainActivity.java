package com.MinimalSoft.FAR;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;

import com.MinimalSoft.FAR.SistemaExperto.Quiz;
import com.MinimalSoft.FAR.Tabs.Measurements.ConnectFragment;
import com.MinimalSoft.FAR.Tabs.Maneuvers.ManeuversFragment;
import com.MinimalSoft.FAR.Tabs.Profile.LoginFragment;
import com.MinimalSoft.FAR.Tabs.Profile.ProfileFragment;
import com.MinimalSoft.FAR.Tabs.Tips.TipsFragment;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    PhoneCallListener phoneListener = new PhoneCallListener();
    TelephonyManager telephonyManager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);

        if(settings.getBoolean("isEmergency", false))
        {
            Intent intent = new Intent(getApplicationContext(), Quiz.class);
            startActivity(intent);
        }

        toolbar = (Toolbar) this.findViewById(R.id.main_toolbar);
        this.setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) this.findViewById(R.id.main_tabLayout);
        ViewPager viewPager = (ViewPager) this.findViewById(R.id.main_viewPager);

        FragmentsViewPagerAdapter pageAdapter = new FragmentsViewPagerAdapter(getSupportFragmentManager(), false);

        pageAdapter.addFragment(new ConnectFragment(), getResources().getString(R.string.tab_mediciones));
        pageAdapter.addFragment(new ManeuversFragment(), getResources().getString(R.string.tab_primerosAuxilios));
        pageAdapter.addFragment(new TipsFragment(), getResources().getString(R.string.tab_tips));

        if(settings.getBoolean("LOGGED_IN",false))
            pageAdapter.addFragment(new ProfileFragment(), getResources().getString(R.string.tab_perfil));
        else
            pageAdapter.addFragment(new LoginFragment(), getResources().getString(R.string.tab_perfil));

        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.tab_metrics);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab_first);
        tabLayout.getTabAt(2).setIcon(R.drawable.tab_tips);
        tabLayout.getTabAt(3).setIcon(R.drawable.tab_profile);

        onPageSelected(0);

        showMessage();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_call:
                showMessage();
                return true;

            default: return true;

        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void showMessage() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("¿Tienes una emergencia?")
                .content("Ante una emergencia es altamente recomendable llamar a los servicios de emergencia.")
                .negativeText("Si, llamar")
                .negativeColorRes(R.color.colorPrimaryDark)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        SharedPreferences.Editor preferencesEditor = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).edit();
                        preferencesEditor.putBoolean("isEmergency", true);
                        preferencesEditor.commit();

                        telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                        telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);

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
                })
                .positiveText("No, estoy bien");

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    //OnPageChangeListener Methods

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                toolbar.setTitle(R.string.tab_mediciones);
                break;
            case 1:
                toolbar.setTitle(R.string.tab_primerosAuxilios);
                break;
            case 2:
                toolbar.setTitle(R.string.tab_tips);
                break;
            case 3:
                toolbar.setTitle(R.string.tab_perfil);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }


    public class FragmentsViewPagerAdapter extends FragmentPagerAdapter {
        private boolean titled;
        private List<String> titleList;
        private List<Fragment> fragmentList;

        public FragmentsViewPagerAdapter(FragmentManager manager, boolean titled) {
            super(manager);
            this.titled = titled;
            titleList = new ArrayList<>();
            fragmentList = new ArrayList<>();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titled ? titleList.get(position) : super.getPageTitle(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            titleList.add(title);
        }


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

                //Restart app to continue with the ES
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(
                                getBaseContext().getPackageName());
                startActivity(i);
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