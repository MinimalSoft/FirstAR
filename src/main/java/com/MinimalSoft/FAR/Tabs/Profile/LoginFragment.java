package com.MinimalSoft.FAR.Tabs.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MinimalSoft.FAR.R;
import com.MinimalSoft.FAR.Tabs.Maneuvers.DetailsActivity;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private View inflatedView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (inflatedView == null) {
            inflatedView = inflater.inflate(R.layout.fragment_login, container, false);

            RelativeLayout boton = (RelativeLayout) inflatedView.findViewById(R.id.login);
            boton.setOnClickListener(this);
        }
        return inflatedView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:

                Intent intent = new Intent(this.getContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

}