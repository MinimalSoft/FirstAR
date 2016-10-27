package com.MinimalSoft.FAR.SistemaExperto;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.MinimalSoft.FAR.R;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class Quiz extends AppCompatActivity  implements View.OnClickListener{

    private RadioGroup g1;
    private RadioGroup g2;
    private RadioGroup g3;
    private RadioGroup g31;
    private TextView t31;
    private RadioGroup g4;
    private RadioGroup g41;
    private TextView t41;
    private RadioGroup g5;
    private RadioGroup g51;
    private TextView t51;
    private RadioGroup g6;

    MaterialDialog.Builder builder;
    MaterialDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cuestionario");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        g1 = (RadioGroup) findViewById(R.id.group_1);
        g2 = (RadioGroup) findViewById(R.id.group_2);
        g3 = (RadioGroup) findViewById(R.id.group_3);
        g31 = (RadioGroup) findViewById(R.id.group_31);
        t31 = (TextView) findViewById(R.id.text_g31);
        g4 = (RadioGroup) findViewById(R.id.group_4);
        g41 = (RadioGroup) findViewById(R.id.group_41);
        t41 = (TextView) findViewById(R.id.text_g41);
        g5 = (RadioGroup) findViewById(R.id.group_5);
        g51 = (RadioGroup) findViewById(R.id.group_51);
        t51 = (TextView) findViewById(R.id.text_g51);
        g6 = (RadioGroup) findViewById(R.id.group_6);

        builder = new MaterialDialog.Builder(this);

        g3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radiob;
                int rid;

                rid = g3.getCheckedRadioButtonId();
                radiob = g3.findViewById(rid);
                int r3 = g3.indexOfChild(radiob);

                if(r3==0)
                {
                    g31.setVisibility(View.VISIBLE);
                    t31.setVisibility(View.VISIBLE);
                }
                else
                {
                    g31.setVisibility(View.GONE);
                    t31.setVisibility(View.GONE);
                }

            }
        });

        g4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radiob;
                int rid;

                rid = g4.getCheckedRadioButtonId();
                radiob = g4.findViewById(rid);
                int r4 = g4.indexOfChild(radiob);

                if(r4==0)
                {
                    g41.setVisibility(View.VISIBLE);
                    t41.setVisibility(View.VISIBLE);
                }
                else
                {
                    g41.setVisibility(View.GONE);
                    t41.setVisibility(View.GONE);
                }
            }
        });

        g5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radiob;
                int rid;

                rid = g5.getCheckedRadioButtonId();
                radiob = g5.findViewById(rid);
                int r5 = g5.indexOfChild(radiob);

                if(r5==0)
                {
                    g51.setVisibility(View.VISIBLE);
                    t51.setVisibility(View.VISIBLE);
                }
                else
                {
                    g51.setVisibility(View.GONE);
                    t51.setVisibility(View.GONE);
                }
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(this);
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

    @Override
    public void onBackPressed() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("¿Seguro que deseas salir?")
                .content("Aun no termia el cuestionario, te recomendamos continuar hasta terminar.")
                .negativeText("Si, salir")
                .negativeColorRes(R.color.colorPrimaryDark)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        SharedPreferences.Editor preferencesEditor = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).edit();
                        preferencesEditor.putBoolean("isEmergency", false);
                        preferencesEditor.commit();
                        Quiz.super.onBackPressed();

                    }
                })
                .positiveText("No, continuar");

        MaterialDialog dialog = builder.build();
        dialog.show();
    }

    @Override
    public void onClick(View v) {


        int resultado = 0;
        View radiob;
        int rid;

        rid = g1.getCheckedRadioButtonId();
        radiob = g1.findViewById(rid);
        int r1 = g1.indexOfChild(radiob);

        rid = g2.getCheckedRadioButtonId();
        radiob = g2.findViewById(rid);
        int r2 = g2.indexOfChild(radiob);

        rid = g3.getCheckedRadioButtonId();
        radiob = g3.findViewById(rid);
        int r3 = g3.indexOfChild(radiob);

        rid = g31.getCheckedRadioButtonId();
        radiob = g31.findViewById(rid);
        int r31 = g31.indexOfChild(radiob);

        rid = g4.getCheckedRadioButtonId();
        radiob = g4.findViewById(rid);
        int r4 = g4.indexOfChild(radiob);

        rid = g41.getCheckedRadioButtonId();
        radiob = g41.findViewById(rid);
        int r41 = g41.indexOfChild(radiob);

        rid = g5.getCheckedRadioButtonId();
        radiob = g5.findViewById(rid);
        int r5 = g5.indexOfChild(radiob);

        rid = g51.getCheckedRadioButtonId();
        radiob = g51.findViewById(rid);
        int r51 = g51.indexOfChild(radiob);

        rid = g6.getCheckedRadioButtonId();
        radiob = g6.findViewById(rid);
        int r6 = g6.indexOfChild(radiob);

        if (r1 == 0) {
            resultado = resultado + 15;
        }
        if (r2 == 0) {
            resultado = resultado + 5;
        }
        if (r3 == 0) {
            if (r31 == 0)
                resultado = resultado + 5;
            else if (r31 == 1)
                resultado = resultado + 10;
        }
        if (r4 == 0) {
            if (r41 == 0)
                resultado = resultado + 25;
            else if (r41 == 1)
                resultado = resultado + 20;
        }
        if (r5 == 0) {
            if (r51 == 0)
                resultado = resultado + 10;
            else if (r51 == 1)
                resultado = resultado + 15;
        }
        if (r6 == 0)
            resultado = resultado + 10;

        String probabilidad = "";

        if (resultado < 20) {
            probabilidad = "nada probable";
            Toast.makeText(getApplicationContext(), "Tu probabilidad de morir es: " + probabilidad, Toast.LENGTH_SHORT).show();
            builder .title("Resultado")
                    .content("Hemos calculado que es " + probabilidad + " que sufras un episodio cardiaco, \n¿deseas ver el procedemiento a seguir?\n (still working on it)")
                    .negativeText("Si, verlo")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            SharedPreferences.Editor preferencesEditor = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).edit();
                            preferencesEditor.putBoolean("isEmergency", false);
                            Quiz.super.onBackPressed();

                        }
                    })
                    .positiveText("No, gracias")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            SharedPreferences.Editor preferencesEditor = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).edit();
                            preferencesEditor.putBoolean("isEmergency", false);
                            Quiz.super.onBackPressed();
                        }
                    });
            dialog = builder.build();
            dialog.show();
            return;
        }


        if (resultado < 40 && resultado > 20) {

            probabilidad = "poco probable";
            Toast.makeText(getApplicationContext(), "Tu probabilidad de morir es: " + probabilidad, Toast.LENGTH_SHORT).show();
            builder .title("Resultado")
                    .content("Hemos calculado que es " + probabilidad + " que sufras un episodio cardiaco \n¿deseas ver el procedemiento a seguir?\n (still working on it)")
                    .negativeText("Si, verlo")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            SharedPreferences.Editor preferencesEditor = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).edit();
                            preferencesEditor.putBoolean("isEmergency", false);
                            Quiz.super.onBackPressed();

                        }
                    })
                    .positiveText("No, gracias")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            SharedPreferences.Editor preferencesEditor = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).edit();
                            preferencesEditor.putBoolean("isEmergency", false);
                            Quiz.super.onBackPressed();
                        }
                    });
            dialog = builder.build();
            dialog.show();
            return;

        }

        if (resultado < 60 && resultado > 40) {
            probabilidad = "probable";
            Toast.makeText(getApplicationContext(), "Tu probabilidad de morir es: " + probabilidad, Toast.LENGTH_SHORT).show();
            builder.title("Resultado")
                    .content("Hemos calculado que es " + probabilidad + " que sufras un episodio cardiaco, te mostraremos las instrucciones a seguir\n (still working on it)")
                    .negativeText("Continuar")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            SharedPreferences.Editor preferencesEditor = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).edit();
                            preferencesEditor.putBoolean("isEmergency", false);
                            Quiz.super.onBackPressed();

                        }
                    });
            dialog = builder.build();
            dialog.show();
            return;

        }
        if (resultado > 60) {
            probabilidad = "muy probable";
            Toast.makeText(getApplicationContext(), "Tu probabilidad de morir es: " + probabilidad, Toast.LENGTH_SHORT).show();
            builder.title("Resultado")
                    .content("Hemos calculado que es " + probabilidad + " que sufras un episodio cardiaco, te mostraremos las instrucciones a seguir\n (still working on it)")
                    .negativeText("Continuar")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            SharedPreferences.Editor preferencesEditor = getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).edit();
                            preferencesEditor.putBoolean("isEmergency", false);
                            Quiz.super.onBackPressed();

                        }
                    });
            dialog = builder.build();
            dialog.show();
            return;

        }

    }
}
