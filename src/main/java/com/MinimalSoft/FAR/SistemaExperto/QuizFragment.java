package com.MinimalSoft.FAR.SistemaExperto;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MinimalSoft.FAR.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class QuizFragment extends Fragment {

    public QuizFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_quiz, container, false);
    }
}
