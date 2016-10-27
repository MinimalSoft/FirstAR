package com.MinimalSoft.FAR.Tabs.Tips;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.MinimalSoft.FAR.R;


public class TipsFragment extends Fragment implements View.OnClickListener {

    private View inflatedView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (inflatedView == null) {
            inflatedView = inflater.inflate(R.layout.fragment_tips, container, false);
        }
        return inflatedView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profileLoginButton:

                break;
        }
    }

}