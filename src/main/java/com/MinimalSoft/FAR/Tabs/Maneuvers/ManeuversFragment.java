package com.MinimalSoft.FAR.Tabs.Maneuvers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.MinimalSoft.FAR.R;


public class ManeuversFragment extends Fragment implements View.OnClickListener {

    private View inflatedView;
    public static final String EXTRA_TITTLE = "Titulo";
    public static final String EXTRA_IMAGE = "Imagen";
    public static final String EXTRA_STEPS = "Steps";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (inflatedView == null) {
            inflatedView = inflater.inflate(R.layout.fragment_maneuvers, container, false);

            Button man1 = (Button) inflatedView.findViewById(R.id.man_heimlich);
            Button man2 = (Button) inflatedView.findViewById(R.id.man_alergias);
            Button man3 = (Button) inflatedView.findViewById(R.id.man_asma);
            Button man4 = (Button) inflatedView.findViewById(R.id.man_sangrado);
            Button man5 = (Button) inflatedView.findViewById(R.id.man_fractura);
            Button man6 = (Button) inflatedView.findViewById(R.id.man_quemadura);
            Button man7 = (Button) inflatedView.findViewById(R.id.man_calor);
            Button man8 = (Button) inflatedView.findViewById(R.id.man_hipotermia);
            Button man9 = (Button) inflatedView.findViewById(R.id.man_envenenamiento);
            Button man10 = (Button) inflatedView.findViewById(R.id.man_picaduras);
            Button man11 = (Button) inflatedView.findViewById(R.id.man_inconsciente);

            man1.setOnClickListener(this);
            man2.setOnClickListener(this);
            man3.setOnClickListener(this);
            man4.setOnClickListener(this);
            man5.setOnClickListener(this);
            man6.setOnClickListener(this);
            man7.setOnClickListener(this);
            man8.setOnClickListener(this);
            man9.setOnClickListener(this);
            man10.setOnClickListener(this);
            man11.setOnClickListener(this);

        }
        return inflatedView;
    }


    @Override
    public void onClick(View v) {

        Intent intent;
        Bundle bundle = new Bundle();

        switch (v.getId()) {
            case R.id.man_heimlich:

                break;

            case R.id.man_alergias:
                bundle.putString(EXTRA_TITTLE, getResources().getString(R.string.text_maniobras_alergias));
                bundle.putString(EXTRA_IMAGE, "cat_alergia");
                bundle.putInt(EXTRA_STEPS, 4);
                break;

            case R.id.man_asma:
                bundle.putString(EXTRA_TITTLE, getResources().getString(R.string.text_maniobras_asma));
                bundle.putString(EXTRA_IMAGE, "cat_asma");
                bundle.putInt(EXTRA_STEPS, 3);
                break;

            case R.id.man_sangrado:
                bundle.putString(EXTRA_TITTLE, getResources().getString(R.string.text_maniobras_sangrado));
                bundle.putString(EXTRA_IMAGE, "cat_sangrado");
                bundle.putInt(EXTRA_STEPS, 3);
                break;

            case R.id.man_fractura:
                bundle.putString(EXTRA_TITTLE, getResources().getString(R.string.text_maniobras_fractura));
                bundle.putString(EXTRA_IMAGE, "cat_fractura");
                bundle.putInt(EXTRA_STEPS, 3);
                break;

            case R.id.man_quemadura:
                bundle.putString(EXTRA_TITTLE, getResources().getString(R.string.text_maniobras_quemadura));
                bundle.putString(EXTRA_IMAGE, "cat_quemadura");
                bundle.putInt(EXTRA_STEPS, 3);
                break;

            case R.id.man_calor:
                bundle.putString(EXTRA_TITTLE, getResources().getString(R.string.text_maniobras_golpe_de_calor));
                bundle.putString(EXTRA_IMAGE, "cat_calor");
                bundle.putInt(EXTRA_STEPS, 4);
                break;

            case R.id.man_hipotermia:
                bundle.putString(EXTRA_TITTLE, getResources().getString(R.string.text_maniobras_hipotermia));
                bundle.putString(EXTRA_IMAGE, "cat_hipotermia");
                bundle.putInt(EXTRA_STEPS, 3);
                break;

            case R.id.man_envenenamiento:
                bundle.putString(EXTRA_TITTLE, getResources().getString(R.string.text_maniobras_envenenamiento));
                bundle.putString(EXTRA_IMAGE, "cat_veneno");
                bundle.putInt(EXTRA_STEPS, 3);
                break;

            case R.id.man_picaduras:
                bundle.putString(EXTRA_TITTLE, getResources().getString(R.string.text_maniobras_picaduras));
                bundle.putString(EXTRA_IMAGE, "cat_picadura");
                bundle.putInt(EXTRA_STEPS, 3);
                break;

            case R.id.man_inconsciente:
                bundle.putString(EXTRA_TITTLE, getResources().getString(R.string.text_maniobras_inconsciente));
                bundle.putString(EXTRA_IMAGE, "cat_inconsciente");
                bundle.putInt(EXTRA_STEPS, 3);
                break;

        }

        intent = new Intent(this.getActivity(), DetailsActivity.class);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

}